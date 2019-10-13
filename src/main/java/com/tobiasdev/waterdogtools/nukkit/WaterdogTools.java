package com.tobiasdev.waterdogtools.nukkit;

import cn.nukkit.plugin.PluginBase;
import com.tobiasdev.waterdogtools.nukkit.api.RequestPool;
import com.tobiasdev.waterdogtools.nukkit.listener.PacketListener;

public class WaterdogTools extends PluginBase {
    private RequestPool pool;
    private static WaterdogTools instance;

    @Override
    public void onEnable() {
        instance = this;
        pool = new RequestPool();
        this.getServer().getPluginManager().registerEvents(new PacketListener(), this);
    }

    public static WaterdogTools getInstance() {
        return instance;
    }

    public RequestPool getPool() {
        return pool;
    }
}
