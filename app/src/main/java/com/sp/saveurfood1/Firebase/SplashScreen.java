package com.sp.saveurfood1.Firebase;

import androidx.appcompat.app.AppCompatActivity;


import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;

import android.os.Handler;
import android.speech.tts.TextToSpeech;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.sp.saveurfood1.Firebase.Login1;
import com.sp.saveurfood1.R;
import com.sp.saveurfood1.Storage.DashBoard;

import java.util.Locale;

public class SplashScreen extends AppCompatActivity {
    ImageView imageView;
    Animation animation;
    TextToSpeech TTS;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        String speech="Save your food, save your earth!";
        imageView = findViewById(R.id.saveurfood);
        animation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.zoom);
        imageView.startAnimation(animation);
        animation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                Intent intent = new Intent(getApplicationContext(), Login1.class);
                startActivity(intent);
                finish();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
            }
        });
        TTS = new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if (status != TextToSpeech.ERROR) {
                    TTS.setLanguage(Locale.US);
                }
                TTS.speak(speech, TextToSpeech.QUEUE_FLUSH, null);
            }
        });
    }
}




