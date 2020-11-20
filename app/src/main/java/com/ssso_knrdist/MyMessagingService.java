package com.ssso_knrdist;

import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.media.RingtoneManager;
import android.os.Build;
import android.os.PowerManager;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.ssso_knrdist.Activties.BottomPageActivity;

import static androidx.core.app.NotificationCompat.VISIBILITY_PUBLIC;

public class MyMessagingService extends FirebaseMessagingService {
    private static final String CHANNEL_ID = "4565";

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);
        wakeUpScreen();
        showNotificationMessage(remoteMessage.getData().get("Notification title"), remoteMessage.getData().get("Notification text"));
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

            CharSequence name = getString(R.string.channel_name);
            String description = getString(R.string.channcel_desc);
            int importance = NotificationManager.IMPORTANCE_HIGH;
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
            channel.setLockscreenVisibility(Notification.VISIBILITY_PUBLIC);
            channel.getLockscreenVisibility();
            channel.setDescription(description);
            channel.enableLights(true);
            channel.setLightColor(Color.RED);
            channel.enableVibration(true);
            channel.setVibrationPattern(new long[]{100, 200, 300, 400, 500, 400, 300, 200, 400});

            NotificationManager notificationManager = (NotificationManager) this.getSystemService(this.NOTIFICATION_SERVICE);
            notificationManager.createNotificationChannel(channel);
            FirebaseMessaging.getInstance().subscribeToTopic("weather")
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            String msg = getString(R.string.msg_subscribed);
                            if (!task.isSuccessful()) {
                                msg = getString(R.string.msg_subscribe_failed);
                            }
                            Log.d("app", msg);
                            Toast.makeText(MyMessagingService.this, msg, Toast.LENGTH_SHORT).show();
                        }
                    });
        }

    }

    public void showNotificationMessage(String title, String message) {
        wakeUpScreen();  
        Intent intent = new Intent(MyMessagingService.this, BottomPageActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_ONE_SHOT);

        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setDefaults(Notification.DEFAULT_ALL)
                .setSmallIcon(R.drawable.saibaba_logo)
                .setContentText(message)
                .setContentTitle(title)
                .setChannelId(CHANNEL_ID)
                .setAutoCancel(true)
                .setVisibility(VISIBILITY_PUBLIC)
                .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_RINGTONE))
                .setPriority(Notification.PRIORITY_HIGH)
                .setContentIntent(pendingIntent);

        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        if (notificationManager != null)
            notificationManager.notify(999, notificationBuilder.build());

    }

    /* when your phone is locked screen wakeup method*/
    private void wakeUpScreen() {
        PowerManager pm = (PowerManager) this.getSystemService(Context.POWER_SERVICE);
        boolean isScreenOn = pm.isScreenOn();

        Log.e("screen on......", "" + isScreenOn);
        if (isScreenOn == false) {
            @SuppressLint("InvalidWakeLockTag") PowerManager.WakeLock wl = pm.newWakeLock(PowerManager.FULL_WAKE_LOCK | PowerManager.ACQUIRE_CAUSES_WAKEUP | PowerManager.ON_AFTER_RELEASE, "MyLock");
            wl.acquire(10000);
            @SuppressLint("InvalidWakeLockTag") PowerManager.WakeLock wl_cpu = pm.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK, "MyCpuLock");
            wl_cpu.acquire(10000);
        }
    }
}