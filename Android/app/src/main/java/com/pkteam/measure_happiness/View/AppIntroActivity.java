package com.pkteam.measure_happiness.View;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.WindowManager;

import com.github.paolorotolo.appintro.AppIntro;
import com.pkteam.measure_happiness.R;

/*
 * Created by paeng on 15/08/2018
 */

public class AppIntroActivity extends AppIntro {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
/*
        SliderPage sliderPage1 = new SliderPage();
        sliderPage1.setTitle(getString(R.string.app_intro_title_1));
        sliderPage1.setDescription(getString(R.string.app_intro_description_1));
        sliderPage1.setTitleColor(getColor(R.color.colorPrimary));
        sliderPage1.setDescColor(getColor(R.color.colorPrimary));
        sliderPage1.setImageDrawable(R.drawable.ic_child_care_orange_24dp);
        sliderPage1.setBgColor(Color.WHITE);
*/
        addSlide(AppIntroFragment.newInstance(R.layout.app_intro_1));
        addSlide(AppIntroFragment.newInstance(R.layout.app_intro_2));
        addSlide(AppIntroFragment.newInstance(R.layout.app_intro_3));
        addSlide(AppIntroFragment.newInstance(R.layout.app_intro_4));

    }

    @Override
    public void onSkipPressed(Fragment currentFragment) {
        super.onSkipPressed(currentFragment);
        Intent mainIntent = new Intent(AppIntroActivity.this, MainActivity.class);
        AppIntroActivity.this.startActivity(mainIntent);
        AppIntroActivity.this.finish();
    }

    @Override
    public void onDonePressed(Fragment currentFragment) {
        super.onDonePressed(currentFragment);
        Intent mainIntent = new Intent(AppIntroActivity.this, MainActivity.class);
        AppIntroActivity.this.startActivity(mainIntent);
        AppIntroActivity.this.finish();
    }
}
