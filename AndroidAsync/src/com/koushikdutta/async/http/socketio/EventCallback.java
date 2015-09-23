package com.koushikdutta.async.http.socketio;

import org.json.JSONArray;

public interface EventCallback {
    // vcube changes
    public void onEvent(String event, JSONArray argument, Acknowledge acknowledge);
    // end of vcube changes
}