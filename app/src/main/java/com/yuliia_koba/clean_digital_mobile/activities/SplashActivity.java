package com.yuliia_koba.clean_digital_mobile.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.auth0.jwt.JWT;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.yuliia_koba.clean_digital_mobile.R;
import com.yuliia_koba.clean_digital_mobile.services.PreferencesService;

import java.time.Instant;
import java.util.Date;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        PreferencesService.init(this);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        navigate();

    }

    void navigate (){
        if (PreferencesService.getToken().isEmpty()){
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
        } else {
            DecodedJWT jwt = JWT.decode(PreferencesService.getToken());
            if( jwt.getExpiresAt().before(new Date())) {
                Intent intent = new Intent(this, LoginActivity.class);
                startActivity(intent);
            } else {
                Intent intent = new Intent(this, MainActivity.class);
                startActivity(intent);
            }
        }
        finish();
    }
}