package com.tobiasdev.waterdogtools.nukkit.api;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;

import java.util.concurrent.TimeUnit;

public class RequestPool {
    private Cache<String, Request> pool;
    public RequestPool(){
        pool = CacheBuilder.newBuilder().expireAfterWrite(1, TimeUnit.MINUTES).maximumSize(200).build();
    }
    public void add(String player, Request request){
        pool.put(player, request);
    }
    public void remove(String player){
        pool.asMap().remove(player);
    }
    public Request getIfSet(String player){
        return pool.asMap().getOrDefault(player, null);
    }
}
