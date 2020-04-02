package com.example.carbuzz.utils;

import android.content.Context;
import android.widget.Toast;

public class Toasts {
    public static final Toasts show = new Toasts();

    private Toasts() {
    }

    public void shortMsg(Context context, String message) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }

    public void longMsg(Context context, String message) {
        Toast.makeText(context, message, Toast.LENGTH_LONG).show();
    }
}
