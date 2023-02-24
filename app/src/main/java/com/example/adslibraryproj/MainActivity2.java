package com.example.adslibraryproj;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;

import androidx.appcompat.app.AppCompatActivity;

import com.labhade.adsdk.FullNativeActivity;
import com.labhade.adsdk.LabhadeAds;

public class MainActivity2 extends AppCompatActivity {
    RelativeLayout rlBanner,rlNative;
    View tvSpace;
    Button next,reward;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        LabhadeAds.InitializeAds(this);

        rlBanner = findViewById(com.labhade.adsdk.R.id.rlBanner);
        rlNative = findViewById(com.labhade.adsdk.R.id.rlNative);
        tvSpace = findViewById(com.labhade.adsdk.R.id.tvSpace);
        next = findViewById(R.id.next);
        reward = findViewById(R.id.btn_click_here);



        LabhadeAds.showBanner(this,rlBanner);
        LabhadeAds.showNative(this,rlNative,tvSpace, LabhadeAds.AdTemplate.NATIVE_100);

        next.setOnClickListener(v -> LabhadeAds.showInterstitial(this, isFail -> {
            startActivity(new Intent(MainActivity2.this, FullNativeActivity.class));
        }));
        reward.setOnClickListener(v -> LabhadeAds.showRewardAd(this, isFail -> {

        }));

    }
}