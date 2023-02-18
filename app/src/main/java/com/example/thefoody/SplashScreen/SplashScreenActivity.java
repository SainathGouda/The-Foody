package com.example.thefoody.SplashScreen;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ImageView;

import com.airbnb.lottie.LottieAnimationView;
import com.example.thefoody.MainActivity;
import com.example.thefoody.R;
import com.example.thefoody.signin.LoginMainActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class SplashScreenActivity extends AppCompatActivity {

    ImageView logo,splashImage;
    LottieAnimationView lottieAnimationView;
    FirebaseAuth mAuth;
    FirebaseUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        logo = findViewById(R.id.logo);
        splashImage = findViewById(R.id.splash_img);
        lottieAnimationView = findViewById(R.id.load_anim);
        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();

        splashImage.animate().translationY(-2650).setDuration(1000).setStartDelay(4000);
        logo.animate().translationY(1650).setDuration(1000).setStartDelay(4000);
        lottieAnimationView.animate().translationY(1400).setDuration(1000).setStartDelay(4000);

        //Fullscreen for splashscreen
        View decorView = getWindow().getDecorView();
        int uiOptions = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_FULLSCREEN;
        decorView.setSystemUiVisibility(uiOptions);




        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                SharedPreferences sharedPreferences = getSharedPreferences(LoginMainActivity.PRES_NAME,0);
                boolean hasLoggedIn= sharedPreferences.getBoolean("hasLoggedIn",false);

                if (hasLoggedIn) {
                    startActivity(new Intent(SplashScreenActivity.this, MainActivity.class));
                }
                else {
                    startActivity(new Intent(SplashScreenActivity.this, LoginMainActivity.class));
                }

                SplashScreenActivity.this.finish();
            }
        }, 5120);
    }
}