package com.ssso_knrdist;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.util.Log;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;
import com.google.firebase.messaging.FirebaseMessaging;
import com.ssso_knrdist.Activties.BottomPageActivity;
import com.ssso_knrdist.Activties.LoginActivity;
import com.ssso_knrdist.Utils.PrefManager;

public class
SplashActivity extends AppCompatActivity {
    PrefManager prefManager;
    private static final int NOTIFICATION_PERMISSION_CODE = 123;
    private static final String CHANNEL_ID = "4565";
    ImageView logo;
    TextView spals_text;
    Handler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        logo = findViewById(R.id.logo);
        spals_text = findViewById(R.id.spals_text);
        prefManager = new PrefManager(this);

        Animation an1 = AnimationUtils.loadAnimation(this, R.anim.pro_animation);
        logo.startAnimation(an1);
        Animation an2 = AnimationUtils.loadAnimation(this, R.anim.app_name_animation);
        spals_text.startAnimation(an2);
        //requestNotificationPermission();
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//            CharSequence name = getString(R.string.channel_name);
//            String description = getString(R.string.channcel_desc);
//            int importance = NotificationManager.IMPORTANCE_DEFAULT;
//            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
//            channel.setDescription(description);
//            NotificationManager notificationManager = getSystemService(NotificationManager.class);
//            notificationManager.createNotificationChannel(channel);
//            FirebaseMessaging.getInstance().subscribeToTopic("weather")
//                    .addOnCompleteListener(new OnCompleteListener<Void>() {
//                        @Override
//                        public void onComplete(@NonNull Task<Void> task) {
//                            String msg = getString(R.string.msg_subscribed);
//                            if (!task.isSuccessful()) {
//                                msg = getString(R.string.msg_subscribe_failed);
//                            }
//                            Log.d("app", msg);
//                            // Toast.makeText(SplashActivity.this, msg, Toast.LENGTH_SHORT).show();
//                        }
//                    });
//
//        }

        FirebaseInstanceId.getInstance().getInstanceId().addOnSuccessListener(new OnSuccessListener<InstanceIdResult>() {
            @Override
            public void onSuccess(InstanceIdResult instanceIdResult) {
                String deviceToken = instanceIdResult.getToken();
                Log.d("devicetoken", "devicesToken  :" + deviceToken);
//                prefManager.storeValue(AppConstants.REFRESH_TOKEN, deviceToken);
//                prefManager.setToken(deviceToken);
//                Log.d("token", "token" + prefManager.getToken());
            }
            // or directly send it to server
        });

        runtimehand();

    }


    private void requestNotificationPermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_NOTIFICATION_POLICY) == PackageManager.PERMISSION_GRANTED)
            return;

        if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.ACCESS_NOTIFICATION_POLICY)) {

        }

        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_NOTIFICATION_POLICY}, NOTIFICATION_PERMISSION_CODE);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        //Checking the request code of our request
        if (requestCode == NOTIFICATION_PERMISSION_CODE) {

            //If permission is granted
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                //Displaying a toast
                Toast.makeText(this, "Permission granted now you can read the storage", Toast.LENGTH_LONG).show();

                runtimehand();
            } else {
                //Displaying another toast if permission is not granted

                prefManager = new PrefManager(this);
                Intent i = new Intent();
                i.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                i.addCategory(Intent.CATEGORY_DEFAULT);
                i.setData(Uri.parse("package:" + this.getPackageName()));
                i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                i.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                i.addFlags(Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS);
                this.startActivity(i);
                Toast.makeText(this, "Please on the notifications to display notifictions on lockscreen", Toast.LENGTH_LONG).show();

            }
        }
    }

    private void runtimehand() {
        handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (!prefManager.getBoolean(prefManager.APP_USER_LOGIN)) {
                    Intent intent = new Intent(SplashActivity.this, LoginActivity.class);
                    startActivity(intent);
                    finish();
                } else {
                    Intent intent = new Intent(SplashActivity.this, BottomPageActivity.class);
                    startActivity(intent);
                    finish();
                }
            }
        }, 3000);
    }

}
