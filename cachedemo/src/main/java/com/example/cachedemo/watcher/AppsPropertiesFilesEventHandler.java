package com.example.cachedemo.watcher;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.PropertySource;
import org.springframework.core.env.StandardEnvironment;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Properties;

@Component("appsPropertiesFilesEventHandler")
public class AppsPropertiesFilesEventHandler implements AppsFilesEventHandler{
    private static final Logger logger = LoggerFactory.getLogger(AppsPropertiesFilesEventHandler.class);

    @Autowired
    private StandardEnvironment environment;

    @Override
    public void process(List<AppsFileEvent> events) {
        Path handlerPath = Paths.get(AppsFilesPathsConstants.TEST_PROPERTIES);
        if(triggerHandler(events,handlerPath)){
            logger.info("[AppsPropertiesFilesEventHandler] start to reload properties");

            if(environment == null){
                logger.error("[AppsPropertiesFilesEventHandler] fail to reload properties, no environment in context");
                return;
            }
            if (!Files.exists(handlerPath) || !Files.isRegularFile(handlerPath) || !Files.isReadable(handlerPath)) {
                logger.error("[AppsPropertiesFilesEventHandler] fail to reload properties , it is not an existing file with readable permission");
                return;
            }

            PropertySource targetSource = selectTargetPropertySource(environment.getPropertySources(), handlerPath);
            if(targetSource == null){
                logger.error("[AppsPropertiesFilesEventHandler] fail to reload properties , not found property source in environment");
                return;
            }

            if(! (targetSource.getSource() instanceof Properties)){
                logger.error("[AppsPropertiesFilesEventHandler] fail to reload properties , source do not support to be overwrite");
                return;
            }
            logger.info("old value: " + environment.getProperty("appVersion"));
            try (InputStream fin = Files.newInputStream(handlerPath)) {
                ((Properties)targetSource.getSource()).load(fin);
                logger.info("new value: " + environment.getProperty("appVersion"));
                logger.info("[AppsPropertiesFilesEventHandler] success to reload properties");
            } catch (IOException e) {
                logger.error("[AppsPropertiesFilesEventHandler] fail to reload properties", e);
            }
        }
    }
}
