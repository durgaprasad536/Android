package com.figsinc.app.analyze;

import android.os.Bundle;

public class MessageEvent {
    private final Bundle message;

    public MessageEvent(Bundle message) {
        this.message = message;
    }

    public Bundle getMessage() {
        return message;
    }
}