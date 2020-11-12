package com.ssso_knrdist;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.ssso_knrdist.Activties.BottomPageActivity;
import com.ssso_knrdist.Activties.LoginActivity;
import com.ssso_knrdist.Utils.PrefManager;

public class SplashActivity extends AppCompatActivity {
PrefManager prefManager;
ImageView logo;
TextView spals_text;
    Handler handler;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        logo=findViewById(R.id.logo);
        spals_text=findViewById(R.id.spals_text);
        prefManager = new PrefManager(this);

        Animation an1= AnimationUtils.loadAnimation(this,R.anim.pro_animation);
        logo.startAnimation(an1);
        Animation an2= AnimationUtils.loadAnimation(this,R.anim.app_name_animation);
        spals_text.startAnimation(an2);

         handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (!prefManager.getBoolean(prefManager.APP_USER_LOGIN)) {
                    Intent intent = new Intent(SplashActivity.this, LoginActivity.class);
                    startActivity(intent);
                    finish();
                }
                else
                {
                    Intent intent = new Intent(SplashActivity.this, BottomPageActivity.class);
                    startActivity(intent);
                    finish();
                }
            }
        }, 3000);
    }
}
