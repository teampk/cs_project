package com.pkteam.measure_happiness;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.WindowManager;

import com.github.paolorotolo.appintro.AppIntro;
import com.github.paolorotolo.appintro.AppIntroFragment;
import com.github.paolorotolo.appintro.model.SliderPage;

/*
 * Created by paeng on 15/08/2018
 */

public class AppIntroActivity extends AppIntro {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        SliderPage sliderPage1 = new SliderPage();
        sliderPage1.setTitle("안녕하세요");
        sliderPage1.setTitleColor(getColor(R.color.colorPrimary));
        sliderPage1.setDescription("매일 매일의 간단한 설문으로 당신의 행복을 측정해보세요.");
        sliderPage1.setDescColor(getColor(R.color.colorPrimary));
        sliderPage1.setImageDrawable(R.drawable.ic_child_care_orange_24dp);
        sliderPage1.setBgColor(Color.WHITE);
        addSlide(AppIntroFragment.newInstance(sliderPage1));

        SliderPage sliderPage2 = new SliderPage();
        sliderPage2.setTitle("Clean App Intros");
        sliderPage2.setDescription("This library offers developers the ability to add clean app intros at the start of their apps.");
        sliderPage2.setImageDrawable(R.drawable.ic_favorite_black_24dp);
        sliderPage2.setBgColor(Color.TRANSPARENT);
        addSlide(AppIntroFragment.newInstance(sliderPage2));

        SliderPage sliderPage3 = new SliderPage();
        sliderPage3.setTitle("Simple, yet Customizable");
        sliderPage3.setDescription("The library offers a lot of customization, while keeping it simple for those that like simple.");
        sliderPage3.setImageDrawable(R.drawable.ic_menu_camera);
        sliderPage3.setBgColor(Color.TRANSPARENT);
        addSlide(AppIntroFragment.newInstance(sliderPage3));

        SliderPage sliderPage4 = new SliderPage();
        sliderPage4.setTitle("Explore");
        sliderPage4.setDescription("Feel free to explore the rest of the library demo!");
        sliderPage4.setDescColor(getColor(R.color.colorPrimary));
        sliderPage4.setImageDrawable(R.drawable.ic_menu_share);
        sliderPage4.setBgColor(Color.TRANSPARENT);
        addSlide(AppIntroFragment.newInstance(sliderPage4));
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
