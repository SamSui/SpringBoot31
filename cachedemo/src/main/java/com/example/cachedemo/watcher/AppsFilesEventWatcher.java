package com.example.cachedemo.watcher;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.WatchService;
import java.util.List;

@Component
public class AppsFilesEventWatcher {
    private static final Logger logger = LoggerFactory.getLogger(AppsFilesEventWatcher.class);

    @Autowired
    private List<AppsFilesEventHandler> eventHandlers;

    private WatchService watchService;

    private Path watcherPath;

    @PostConstruct
    public void initWatcher(){
        try{
            watchService = FileSystems.getDefault().newWatchService();
            watcherPath = Paths.get(AppsFilesPathsConstants.WATCHER_ROOT_PATH);
        }catch (Exception e){
            logger.error("[Watch service] fail to start", e);
            return;
        }

        Thread watcher = new Thread(new AppsFilesWatcherThread(watchService, watcherPath, eventHandlers));

        watcher.setName("WebappsPropertiesWatcher");
        watcher.setDaemon(true);
        watcher.start();
    }

    @PreDestroy
    public void destroyWatcher(){
        try {
            if(watchService != null){
                watchService.close();
            }
        } catch (IOException e) {
            logger.error("[Watch service] fail to stop", e);
        }
    }
}
