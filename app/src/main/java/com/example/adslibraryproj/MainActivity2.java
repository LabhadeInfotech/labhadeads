package com.example.adslibraryproj;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;

import androidx.appcompat.app.AppCompatActivity;

import com.labhade.adsdk.LabhadeAds;

public class MainActivity2 extends AppCompatActivity {
    RelativeLayout rlBanner,rlNative;
    View tvSpace;
    Button next;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        LabhadeAds.InitializeAds(this);

        rlBanner = findViewById(com.labhade.adsdk.R.id.rlBanner);
        rlNative = findViewById(com.labhade.adsdk.R.id.rlNative);
        tvSpace = findViewById(com.labhade.adsdk.R.id.tvSpace);
        next = findViewById(R.id.next);


        LabhadeAds.showBanner(this,rlBanner);
        LabhadeAds.showNative(this,rlNative,tvSpace, LabhadeAds.AdTemplate.NATIVE_350);

        next.setOnClickListener(v -> LabhadeAds.showInterstitial(this, isFail -> {}));
    }
}