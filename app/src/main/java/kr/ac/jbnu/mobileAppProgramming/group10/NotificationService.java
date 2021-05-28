package kr.ac.jbnu.mobileAppProgramming.group10;

import android.app.Service;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.support.annotation.Nullable;

public class NotificationService extends Service {
    TimeReceiver timeReceiver;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        timeReceiver = new TimeReceiver();
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        registerReceiver(timeReceiver, new IntentFilter(Intent.ACTION_TIME_TICK));
        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        unregisterReceiver(timeReceiver);
    }
}
