package com.example.cachedemo.watcher;

import java.nio.file.Path;
import java.nio.file.WatchEvent;

public class AppsFileEvent {
    private Path path;
    private WatchEvent.Kind kind;

    public AppsFileEvent(Path path, WatchEvent.Kind kind){
        this.path = path;
        this.kind = kind;
    }

    public Path getPath() {
        return path;
    }

    public void setPath(Path path) {
        this.path = path;
    }

    public WatchEvent.Kind getKind() {
        return kind;
    }

    public void setKind(WatchEvent.Kind kind) {
        this.kind = kind;
    }
}
