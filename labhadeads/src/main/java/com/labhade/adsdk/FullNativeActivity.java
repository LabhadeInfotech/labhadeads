package com.labhade.adsdk;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.labhade.adsdk.adUtils.NativeUtilsBig;

public class FullNativeActivity extends AppCompatActivity {
    boolean isLoaded = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_full_native);

        NativeUtilsBig.load_native(this,findViewById(R.id.rl_native),findViewById(R.id.tv_space));
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                isLoaded = true;
                ((ImageView) findViewById(R.id.iv_close)).setVisibility(View.VISIBLE);
            }
        },2000);
        ((ImageView) findViewById(R.id.iv_close)).setOnClickListener(v -> finish());

    }

    @Override
    public void onBackPressed() {
        if (isLoaded) {
            super.onBackPressed();
        }
    }
}