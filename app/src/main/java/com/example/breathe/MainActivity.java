package com.example.breathe;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.CountDownTimer;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.breathe.util.Prefs;
import com.github.florent37.viewanimator.AnimationListener;
import com.github.florent37.viewanimator.ViewAnimator;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity {
    private ImageView imageView;
    private TextView breathsTxt, timeTxt, sessionTxt, guideTxt;
    private Button startButton;
    private Prefs prefs;
    private String areWeBreathing;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        imageView = findViewById(R.id.lotusImage);
        breathsTxt = findViewById(R.id.breathsTakenTxt);
        timeTxt = findViewById(R.id.lastBreathTxt);
        sessionTxt = findViewById(R.id.todayMinTxt);
        guideTxt = findViewById(R.id.guideTxt);
        startButton = findViewById(R.id.button);
        prefs = new Prefs(this);

        startIntroAnimation();

        // setting session statistics

         if (Calendar.DAY_OF_MONTH > prefs.getDay())
         {
            prefs.setSessions(0);
            prefs.setBreaths(0);
         }

        sessionTxt.setText(prefs.getSessions() + " min today");

        if (prefs.getBreaths() == 1)
            areWeBreathing = "breath";
        else
            areWeBreathing = " breaths";

        breathsTxt.setText(prefs.getBreaths() + areWeBreathing);
        timeTxt.setText(prefs.getDate());

        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startAnimation();
            }
        });
    }

    private void startIntroAnimation() {
        ViewAnimator
                .animate(guideTxt)
                .scale(0, 1)
                .duration(1500)
                .onStart(new AnimationListener.Start() {
                    @Override
                    public void onStart() {
                        guideTxt.setText("breathe");
                    }
                })
                .start();
    }

    private void startAnimation() {
        ViewAnimator
                .animate(imageView)
                .alpha(0, 1)
                .onStart(new AnimationListener.Start() {
                    @Override
                    public void onStart() {
                        guideTxt.setText("Inhale... Exhale");
                    }
                })
                .decelerate()
                .duration(1000)
                .thenAnimate(imageView)
                .scale(0.02f, 1.4f, 0.02f)
                .rotation(360)
                .repeatCount(6)
                .accelerate()
                .duration(5000)
                .onStop(new AnimationListener.Stop() {
                    @Override
                    public void onStop() {
                        guideTxt.setText("Good Job");
                        imageView.setScaleX(1.0f);
                        imageView.setScaleY(1.0f);

                        // updating statistics after run
                        prefs.setSessions(prefs.getSessions() + 1);
                        prefs.setBreaths(prefs.getBreaths() + 1);
                        prefs.setDate(System.currentTimeMillis());
                        prefs.setDay();

                        //refresh activity
                        new CountDownTimer(2000, 1000) {

                            @Override
                            public void onTick(long millisUntilFinished) {
                                // put code to show ticking ... 1, 2, 3...
                            }

                            @Override
                            public void onFinish() {
                                startActivity(new Intent(getApplicationContext(), MainActivity.class));
                                finish();
                            }
                        }.start();

                    }
                })
                .start();
    }
}
