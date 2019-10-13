package com.tobiasdev.waterdogtools.nukkit.listener;

import cn.nukkit.event.EventHandler;
import cn.nukkit.event.Listener;
import cn.nukkit.event.server.DataPacketReceiveEvent;
import cn.nukkit.network.protocol.DataPacket;
import cn.nukkit.network.protocol.ScriptCustomEventPacket;
import com.tobiasdev.waterdogtools.nukkit.WaterdogTools;
import com.tobiasdev.waterdogtools.nukkit.api.Request;

public class PacketListener implements Listener {
    @EventHandler
    public void onReceive(DataPacketReceiveEvent event) {
        DataPacket pk = event.getPacket();
        if (pk instanceof ScriptCustomEventPacket) {
            Request r;
            if((r = WaterdogTools.getInstance().getPool().getIfSet(event.getPlayer().getName())) != null){
                r.notify(((ScriptCustomEventPacket) pk).eventData);
            }
        }
    }
}
