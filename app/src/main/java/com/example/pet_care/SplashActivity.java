package com.example.pet_care;

import android.animation.ObjectAnimator;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

// SplashActivity.java
public class SplashActivity extends AppCompatActivity {
    private static int SPLASH_SCREEN =5000;

    Animation topanime, bottomanime;
    ImageView image;
    TextView logo, slogan;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_splash);

        //animations
        topanime = AnimationUtils.loadAnimation(this,R.anim.top_animation);
        bottomanime= AnimationUtils.loadAnimation(this,R.anim.bottom_animation);

        //hooks
        image =findViewById(R.id.imageView2);
        logo =findViewById(R.id.textView);
        slogan=findViewById(R.id.textView2);

        image.setAnimation(topanime);
        logo.setAnimation(bottomanime);
        slogan.setAnimation(bottomanime);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(SplashActivity.this, index.class);
                startActivity(intent);
                finish();

            }
        },SPLASH_SCREEN);
    }
}