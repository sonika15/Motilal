package com.motilal.project.service

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import android.util.Log

class BroadcastReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        Log.e("Broadcast", "intent ")
        if (intent!!.action.equals(Intent.ACTION_BOOT_COMPLETED)) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
                context?.startForegroundService(intent);
            else
                context?.startService(intent);
        }else{
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
                context?.startForegroundService(intent);
            else
                context?.startService(intent);
        }

    }
}