package com.koushikdutta.async.http.socketio.transport;

import com.koushikdutta.async.AsyncServer;
import com.koushikdutta.async.callback.CompletedCallback;
import com.koushikdutta.async.callback.DataCallback;
import com.koushikdutta.async.http.WebSocket;

import java.util.logging.Level;
import java.util.logging.Logger;

public class WebSocketTransport implements SocketIOTransport {

    private static final Logger LOGGER = Logger.getLogger(WebSocketTransport.class.getName());

    private WebSocket webSocket;
    private StringCallback stringCallback;
	private String sessionId;

    public WebSocketTransport(WebSocket webSocket, String sessionId) {
        this.webSocket = webSocket;
        this.sessionId = sessionId;
        this.webSocket.setDataCallback(new DataCallback.NullDataCallback());
    }

    @Override
    public boolean isConnected() {
        return this.webSocket.isOpen();
    }

    @Override
    public void setClosedCallback(CompletedCallback handler) {
        this.webSocket.setClosedCallback(handler);
    }

    @Override
    public void disconnect() {
        this.webSocket.close();
    }

    @Override
    public AsyncServer getServer() {
        return this.webSocket.getServer();
    }

    @Override
    public void send(String message) {
        this.webSocket.send(message);
        if (LOGGER.isLoggable(Level.FINEST)) {
            LOGGER.finest("Tx\n" + message);
        }
    }

    @Override
    public void setStringCallback(final StringCallback callback) {
        if (this.stringCallback == callback)
            return;

        if (callback == null) {
            this.webSocket.setStringCallback(null);
        } else {
            this.webSocket.setStringCallback(new WebSocket.StringCallback() {
                @Override
                public void onStringAvailable(String s) {
                    if (LOGGER.isLoggable(Level.FINEST)) {
                        LOGGER.finest("Rx\n" + s);
                    }
                    callback.onStringAvailable(s);
                }
            });
        }

        this.stringCallback = callback;
    }

    @Override
    public boolean heartbeats() {
        return true;
    }

	@Override
	public String getSessionId() {
		return this.sessionId;
	}
}