package com.figsinc.app.analyze.search;

import android.os.Bundle;

public class SearchEvent {
    private final Bundle message;

    public SearchEvent(Bundle message) {
        this.message = message;
    }

    public Bundle getMessage() {
        return message;
    }
}