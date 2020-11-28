package com.example.lisview_app;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.SwitchCompat;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.CompoundButton;
import android.widget.ImageSwitcher;
import android.widget.ImageView;
import android.widget.ViewSwitcher;

public class DayNightActivity extends AppCompatActivity {
    SwitchCompat aSwitch;
    SharePref sharePref;
    ImageSwitcher imageSwitcher;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_day_night);
        sharePref= new SharePref(this);
        aSwitch=(SwitchCompat) findViewById(R.id.Night_switch);
        imageSwitcher=(ImageSwitcher) findViewById(R.id.ImageSwitch);
        if(sharePref.loadNightModeState()==true) aSwitch.setChecked(true);
        imageSwitcher.setFactory(new ViewSwitcher.ViewFactory() {
            @Override
            public View makeView() {
                ImageView imageView=new ImageView(getApplicationContext());
                imageView.setScaleType(ImageView.ScaleType.FIT_XY);
                ViewGroup.LayoutParams params = new ImageSwitcher.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
                imageView.setLayoutParams(params);
                return imageView;
            }
        });
        final Animation imgAnimationIn =  AnimationUtils.loadAnimation(this,   android.R.anim.fade_in);
        Animation imgAnimationOut =  AnimationUtils.loadAnimation(this,   android.R.anim.fade_out);
        imgAnimationIn.setDuration(2000);
        imgAnimationOut.setDuration(2000);
        imageSwitcher.setInAnimation(imgAnimationIn);
        imageSwitcher.setOutAnimation(imgAnimationOut);
        if(sharePref.loadNightModeState()==true) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
            imageSwitcher.setImageResource(R.drawable.nightback);
        }
        else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
            imageSwitcher.setImageResource(R.drawable.dayback);
        }
        aSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if(isChecked){

                    sharePref.setNightModeState(true);
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                    imageSwitcher.setImageResource(R.drawable.nightback);
                }
                else{
                    sharePref.setNightModeState(false);
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                    imageSwitcher.setImageResource(R.drawable.dayback);
                }
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
