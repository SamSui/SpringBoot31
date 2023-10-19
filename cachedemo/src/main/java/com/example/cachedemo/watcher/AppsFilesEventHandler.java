package com.example.cachedemo.watcher;

import org.springframework.core.env.CompositePropertySource;
import org.springframework.core.env.PropertySource;

import java.nio.file.Path;
import java.nio.file.StandardWatchEventKinds;
import java.util.List;

public interface AppsFilesEventHandler {
    void process(List<AppsFileEvent> events);

    default PropertySource selectTargetPropertySource(Iterable<PropertySource<?>> propertySources, Path handlerPath) {
        for(PropertySource p : propertySources){
            if(p instanceof CompositePropertySource){
                PropertySource target = selectTargetPropertySource(((CompositePropertySource)p).getPropertySources(), handlerPath);
                if(target != null){
                    return target;
                }
            }else{
                if(p.getName().toLowerCase().contains(handlerPath.toString().toLowerCase())){
                    return p;
                }
            }
        }
        return null;
    }

    default boolean triggerHandler(List<AppsFileEvent> events, Path handlerPath) {
        if(events == null){
            return false;
        }
        return events.stream()
                .filter(e -> handlerPath.toString().equalsIgnoreCase(e.getPath().toString()))
                //no handle delete
                .filter(e -> e.getKind() == StandardWatchEventKinds.ENTRY_CREATE  || e.getKind() == StandardWatchEventKinds.ENTRY_MODIFY)
                .findAny().isPresent();
    }
}
