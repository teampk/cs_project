package com.pkteam.measure_happiness;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.WindowManager;

/**
 * Created by paeng on 15/08/2018.
 */

public class Splash extends AppCompatActivity {
    private final int SPLASH_DISPLAY_LENGTH = 1000;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.splash);

        new Handler().postDelayed(new Runnable(){
            @Override
            public void run() {


                SharedPreferences pref = getSharedPreferences("isFirst", Activity.MODE_PRIVATE);
                boolean first = pref.getBoolean("isFirst", false);
                if(!first){

                    SharedPreferences.Editor editor = pref.edit();
                    editor.putBoolean("isFirst",true);
                    editor.commit();


                    /* 메뉴액티비티를 실행하고 로딩화면을 죽인다.*/
                    Intent appIntroIntent = new Intent(Splash.this, AppIntroActivity.class);
                    Splash.this.startActivity(appIntroIntent);
                    Splash.this.finish();


                }else{

                    /* 메뉴액티비티를 실행하고 로딩화면을 죽인다.*/
                    Intent mainIntent = new Intent(Splash.this, AppIntroActivity.class);
                    Splash.this.startActivity(mainIntent);
                    Splash.this.finish();
                }


            }
        }, SPLASH_DISPLAY_LENGTH);
    }
}
