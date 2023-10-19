package com.example.cachedemo.watcher;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.file.ClosedWatchServiceException;
import java.nio.file.Path;
import java.nio.file.StandardWatchEventKinds;
import java.nio.file.WatchEvent;
import java.nio.file.WatchKey;
import java.nio.file.WatchService;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

public class AppsFilesWatcherThread implements Runnable{
    private static final Logger logger = LoggerFactory.getLogger(AppsFilesEventWatcher.class);
    private WatchService watchService;
    private Path watcherPath;
    private List<AppsFilesEventHandler> eventHandlers;

    public AppsFilesWatcherThread(WatchService watchService, Path watcherPath, List<AppsFilesEventHandler> eventHandlers){
        this.watchService = watchService;
        this.watcherPath = watcherPath;
        this.eventHandlers = eventHandlers;
    }

    @Override
    public void run() {
        try{
            watcherPath.register(watchService, StandardWatchEventKinds.ENTRY_CREATE, StandardWatchEventKinds.ENTRY_MODIFY, StandardWatchEventKinds.ENTRY_DELETE);
        }catch (IOException e){
            logger.error("[Watch service] fail to monitor " + watcherPath , e);
            return;
        }

        while (true) {
            WatchKey key;
            try{
                key = watchService.take();
            }catch (InterruptedException | ClosedWatchServiceException e){
                logger.error("[Watch service] interrupted" , e);
                break;
            }

            if(key == null){
                logger.error("[Watch service] key taken is invalid(null)");
                break;
            }

            try {
                //eliminate occurrence events for single action
                TimeUnit.SECONDS.sleep(2);
            } catch (InterruptedException e) {
                logger.error("[Watch service] 2 seconds sleeping interrupted" , e);
            }

            try{
                List<AppsFileEvent> fileEvents =  key.pollEvents().stream()
                        .map(this::fileEvent).filter(Objects::nonNull).collect(Collectors.toList());
                //eventHandlers could not be null,  fileEvents could not be null
                eventHandlers.forEach(handler -> handler.process(fileEvents));
            }catch (Exception e){
                logger.error("[Watch service] fail to handle file event" , e);
                //not break, let it retry next time
            }finally {
                key.reset();
            }
        }
    }

    private AppsFileEvent fileEvent(WatchEvent<?> watchEvent) {
        logger.info("[Watch service] {} have {} action", watchEvent.context(), watchEvent.kind());
        if (watchEvent.kind() == StandardWatchEventKinds.OVERFLOW) {
            return null;
        }

        Object context = watchEvent.context();
        if(!(context instanceof Path)){
            return null;
        }


        Path absolutePath = watcherPath.resolve((Path)context);
        return new AppsFileEvent(absolutePath , watchEvent.kind());
    }
}
