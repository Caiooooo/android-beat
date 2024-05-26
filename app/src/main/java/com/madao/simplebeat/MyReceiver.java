package com.madao.simplebeat;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.util.Log;
import android.widget.Toast;

public class MyReceiver extends BroadcastReceiver {

    public interface OnNightModeChangedListener{
        void onNightModeChanged();
    }
    private OnNightModeChangedListener listener;
    public void setOnNightModeChangedListener(OnNightModeChangedListener listener) {
        this.listener = listener;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equals(Intent.ACTION_CONFIGURATION_CHANGED))
            listener.onNightModeChanged();
    }
}
