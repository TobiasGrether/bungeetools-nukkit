package com.tobiasdev.waterdogtools.nukkit.api;

import java.util.concurrent.ConcurrentHashMap;

abstract class RequestResponseAction {
    public abstract void handle(ConcurrentHashMap<String, Object> data);
}
