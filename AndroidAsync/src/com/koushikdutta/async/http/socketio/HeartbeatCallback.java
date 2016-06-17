package com.koushikdutta.async.http.socketio;

/**
 * Created by koush on 7/2/13.
 */
public interface HeartbeatCallback {
    void onHeartbeat(SocketIOClient client);
}
