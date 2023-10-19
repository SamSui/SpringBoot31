package com.example.cachedemo.watcher;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.env.OriginTrackedMapPropertySource;
import org.springframework.boot.env.YamlPropertySourceLoader;
import org.springframework.core.env.PropertySource;
import org.springframework.core.env.StandardEnvironment;
import org.springframework.core.io.PathResource;
import org.springframework.stereotype.Component;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Component("appsYmlFilesEventHandler")
public class AppsYmlFilesEventHandler implements AppsFilesEventHandler{
    private static final Logger logger = LoggerFactory.getLogger(AppsYmlFilesEventHandler.class);

    @Autowired
    private StandardEnvironment environment;

    @Override
    public void process(List<AppsFileEvent> events) {
        Path handlerPath = Paths.get(AppsFilesPathsConstants.TEST_YML);
        if(triggerHandler(events,handlerPath)){
            logger.info("[AppsYmlFilesEventHandler] start to reload yml file");

            if(environment == null){
                logger.error("[AppsYmlFilesEventHandler] fail to reload yml file, no environment in context");
                return;
            }
            if (!Files.exists(handlerPath) || !Files.isRegularFile(handlerPath) || !Files.isReadable(handlerPath)) {
                logger.error("[AppsYmlFilesEventHandler] fail to reload yml file , it is not an existing file with readable permission");
                return;
            }

            PropertySource targetSource = selectTargetPropertySource(environment.getPropertySources(), handlerPath);
            if(!(targetSource instanceof OriginTrackedMapPropertySource)){
                logger.error("[AppsYmlFilesEventHandler] fail to reload yml file , not found property source or not OriginTrackedMapPropertySource in environment");
                return;
            }

            try{
                String name = targetSource.getName();
                PathResource resource = new PathResource(handlerPath);
                List<PropertySource<?>> propertySources = new YamlPropertySourceLoader().load(name, resource);
                if(propertySources == null || propertySources.isEmpty() || !(propertySources.get(0) instanceof OriginTrackedMapPropertySource)){
                    logger.error("[AppsYmlFilesEventHandler] fail to reload yml file , could not load new content from yml");
                    return;
                }
                if(propertySources.size() > 1){
                    logger.info("[AppsYmlFilesEventHandler] generate more than one propertySource and will only use the first one");
                }
                logger.info("old value: " + environment.getProperty("spring.application.version"));
                PropertySource newPropertySource = propertySources.get(0);

                printDiffs(((OriginTrackedMapPropertySource) targetSource).getSource(),((OriginTrackedMapPropertySource) newPropertySource).getSource());
                environment.getPropertySources().replace(name, newPropertySource);
                logger.info("new value: " + environment.getProperty("spring.application.version"));
                logger.info("[AppsYmlFilesEventHandler] success to reload yml file");
            }catch (Exception e){
                logger.error("[AppsYmlFilesEventHandler] fail to reload yml file", e);
            }
        }
    }

    private void printDiffs(Map<String, Object> targetSourceMap, Map<String, Object> newSourceMap){
        for(Map.Entry<String,Object> newEntry : newSourceMap.entrySet()){
            if(!Objects.equals(targetSourceMap.get(newEntry.getKey()), newEntry.getValue())){
                logger.info("[AppsYmlFilesEventHandler] key {} changed, old={}, new={}", newEntry.getKey(),
                        targetSourceMap.get(newEntry.getKey()), newEntry.getValue());
            }
        }
    }
}
