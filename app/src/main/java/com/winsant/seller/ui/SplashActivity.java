package com.winsant.seller.ui;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.winsant.seller.R;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        ImageView splashLogo = (ImageView) findViewById(R.id.splashLogo);
        Glide.with(SplashActivity.this).load(R.drawable.winsant_logo).into(splashLogo);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                if (MyApplication.getInstance().getPreferenceUtility().getLogin())
                    startActivity(new Intent(SplashActivity.this, HomeActivity.class));
                else
                    startActivity(new Intent(SplashActivity.this, LoginActivity.class));
                finish();
            }
        }, 2000);
    }
}
