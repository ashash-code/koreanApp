package com.example.korean;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class SplashActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        // Check if user is already signed in
        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();

        // Delay for 3 seconds then decide where to go
        new Handler(Looper.getMainLooper()).postDelayed(() -> {
            Intent intent;
            if (currentUser != null && currentUser.isEmailVerified()) {
                // User is signed in and verified, go to main menu
                intent = new Intent(SplashActivity.this, mainMenu.class);
            } else {
                // No user or not verified, go to login
                intent = new Intent(SplashActivity.this, MainActivity.class);
            }
            startActivity(intent);
            finish();
        }, 3000);
    }
}