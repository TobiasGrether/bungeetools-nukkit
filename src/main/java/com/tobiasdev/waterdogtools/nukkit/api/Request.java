package com.tobiasdev.waterdogtools.nukkit.api;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteStreams;
import com.tobiasdev.waterdogtools.nukkit.WaterdogTools;

import java.util.concurrent.ConcurrentHashMap;

public class Request {
    public RequestResponseAction action;
    public String query, player;
    public RequestType.TYPES type;

    public void notify(byte[] data) {
        ByteArrayDataInput in = ByteStreams.newDataInput(data);
        ConcurrentHashMap<String, Object> response = new ConcurrentHashMap<String, Object>();
        switch (this.type) {
            case GET_PING:
                response.put("ping", in.readInt());
                break;
            case GET_SERVER:
                response.put("server_name", in.readUTF());
                break;
            case GET_PLAYER_IP:
                response.put("player_ip", in.readUTF());
                response.put("player_port", in.readInt());
                break;
            case GET_SERVER_IP:
                response.put("server_name", in.readUTF());
                response.put("server_ip", in.readUTF());
                response.put("server_port", in.readUnsignedShort());
                break;
            case GET_PLAYER_LIST:
                response.put("server", in.readUTF());
                response.put("player_list", in.readUTF().split(", "));
                break;
            case GET_SERVER_LIST:
                response.put("server_list", in.readUTF().split(", "));
                break;
            case GET_PLAYER_COUNT:
                response.put("server", in.readUTF());
                response.put("player_count", in.readInt());
                break;
        }
        action.handle(response);
        WaterdogTools.getInstance().getPool().remove(this.player);
    }
}
