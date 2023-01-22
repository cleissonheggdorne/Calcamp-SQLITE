package com.example.calcamp.tools;

import android.content.Context;
import android.widget.Toast;

public class Alert {
    public static void alert(String msg, Context context) {
        Toast.makeText(context, msg, Toast.LENGTH_LONG).show();
    }

}
