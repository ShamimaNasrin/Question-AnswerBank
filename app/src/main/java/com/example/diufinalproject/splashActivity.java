package com.example.diufinalproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import StudentAllActivity.StudentDashboardActivity;
import TeacherAllActivity.TeacherDashboardActivity;

public class splashActivity extends AppCompatActivity {

    ImageView topIV,bottomIV,splashIV;
    TextView splashTV;
    Animation topAnim,bottomAnim,middleAnim,lowerMiddleAnim;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash);
        initialization();

        topAnim = AnimationUtils.loadAnimation(this,R.anim.top_animation);
        bottomAnim = AnimationUtils.loadAnimation(this,R.anim.bottom_animation);
        middleAnim = AnimationUtils.loadAnimation(this,R.anim.middle_animation);
        lowerMiddleAnim = AnimationUtils.loadAnimation(this,R.anim.lower_middle);

        splashIV.setAnimation(middleAnim);
        topIV.setAnimation(topAnim);
        bottomIV.setAnimation(bottomAnim);
        splashTV.setAnimation(lowerMiddleAnim);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                SharedPreferences sharedPreferences = getSharedPreferences("UserDetails", Context.MODE_PRIVATE);
                if(sharedPreferences.contains("keyTeacher")){
                    String sharedKey = sharedPreferences.getString("teacher","Data is not found");
                    startActivity(new Intent(splashActivity.this, TeacherDashboardActivity.class));
                    finish();

                }else if(sharedPreferences.contains("keyStudent")) {
                    String sharedKey = sharedPreferences.getString("student","Data is not found");
                    startActivity(new Intent(splashActivity.this, StudentDashboardActivity.class));
                    finish();
                }
                else {
                    startActivity(new Intent(splashActivity.this, LoginActivity.class));
                    finish();
                }
            }
        },3000);
    }

    private void initialization() {
        splashIV = (ImageView) findViewById(R.id.splashPhotoID);
        topIV = (ImageView) findViewById(R.id.top_IVid);
        bottomIV = (ImageView) findViewById(R.id.bottom_IVid);
        splashTV = (TextView) findViewById(R.id.splashTextID);
    }
}
