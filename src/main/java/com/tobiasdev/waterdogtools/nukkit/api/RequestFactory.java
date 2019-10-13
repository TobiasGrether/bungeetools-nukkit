package com.tobiasdev.waterdogtools.nukkit.api;

import cn.nukkit.Player;
import cn.nukkit.network.protocol.ScriptCustomEventPacket;
import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;
import com.sun.istack.internal.NotNull;
import com.tobiasdev.waterdogtools.nukkit.WaterdogTools;

import java.util.HashMap;

public class RequestFactory {
    private RequestType.TYPES type;
    private Player player;
    private String query;
    private HashMap<String, String> data;
    private RequestResponseAction action;

    public static RequestFactory create() {
        return new RequestFactory();
    }


    /**
     * Handler Implementation that should execute after data is received
     *
     * @param action
     * @return
     */
    public RequestFactory setHandler(@NotNull RequestResponseAction action) {
        this.action = action;
        return this;
    }

    /**
     * Player that the packet will be sent through. Depending on the Type, this can be important ( See BungeeCord Plugin Massaging Docs )
     *
     * @param player
     * @return
     */
    public RequestFactory setPlayer(@NotNull Player player) {
        this.player = player;
        return this;
    }

    /**
     * Type of Request, list can be found in
     *
     * @param type
     * @return
     * @see RequestType
     */
    public RequestFactory setType(@NotNull RequestType.TYPES type) {
        this.type = type;
        return this;
    }

    /**
     * The Data you want to pass to the server, list can be found in Plugin Messaging Channel Docs, so everything that you want to give the request as additional information
     *
     * @param data
     * @return
     */
    public RequestFactory setData(@NotNull HashMap<String, String> data) {
        this.data = data;
        return this;
    }

    public Request build() {
        Request r = new Request();
        r.action = this.action;
        r.query = this.query;
        r.type = this.type;
        r.player = this.player.getName();
        WaterdogTools.getInstance().getPool().add(this.player.getName(), r);
        sendRequest(r);
        return r;
    }

    private void sendRequest(@NotNull Request r) {
        ScriptCustomEventPacket pk = new ScriptCustomEventPacket();
        pk.eventName = "bungeecord:main";
        ByteArrayDataOutput out = ByteStreams.newDataOutput();
        switch (this.type) {
            case GET_PLAYER_COUNT:
                out.writeUTF("PlayerCount");
                out.writeUTF(this.data.get("server"));
                break;
            case GET_SERVER_LIST:
                out.writeUTF("GetServers");
                break;
            case GET_PLAYER_LIST:
                out.writeUTF("PlayerList");
                out.writeUTF(this.data.get("server"));
                break;
            case GET_PING:
                out.writeUTF("GetPing");
                out.writeUTF(this.data.get("player"));
                break;
            case GET_SERVER_IP:
                out.writeUTF("ServerIP");
                out.writeUTF(this.data.get("server"));
                break;
            case GET_PLAYER_IP:
                out.writeUTF("IP");
                break;
            case GET_SERVER:
                out.writeUTF("GetServer");
                break;
        }
        pk.eventData = out.toByteArray();
        this.player.dataPacket(pk);
    }
}
