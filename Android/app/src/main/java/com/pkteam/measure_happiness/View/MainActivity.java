package com.pkteam.measure_happiness.view;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.github.mikephil.charting.charts.RadarChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.MarkerView;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.RadarData;
import com.github.mikephil.charting.data.RadarDataSet;
import com.github.mikephil.charting.data.RadarEntry;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.IRadarDataSet;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.pkteam.measure_happiness.R;
import com.pkteam.measure_happiness.custom.RadarMarkerView;
import com.pkteam.measure_happiness.model.Res;
import com.pkteam.measure_happiness.model.Value;
import com.pkteam.measure_happiness.network.NetworkUtil;
import com.pkteam.measure_happiness.utils.Constants;


import java.io.IOException;
import java.util.ArrayList;

import de.mateware.snacky.Snacky;
import retrofit2.adapter.rxjava.HttpException;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private RadarChart mChart;
    private TextView tvValueSmile, navTvName, navTvEmail, tvScoreTotal;

    private LinearLayout llSmile;

    private String mToken;
    private String mID;

    private SharedPreferences mSharedPreferences;
    private CompositeSubscription mSubscriptions;

    private ArrayList<Value> valueArrayList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initSharedPreferences();
        bindingView();
    }

    @Override
    protected void onResume(){
        super.onResume();
        loadData();
        setChart();
        initiateView();
    }

    @Override
    protected void onDestroy(){
        super.onDestroy();
        mSubscriptions.unsubscribe();
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_user_info) {
            Intent intentUserInfo = new Intent(getApplicationContext(), UserInfoActivity.class);
            startActivity(intentUserInfo);

        } else if (id == R.id.nav_sign_out) {
            Intent intentSignOut = new Intent(getApplicationContext(), SignInActivity.class);
            startActivity(intentSignOut);
            Toast.makeText(this, "로그아웃 되었습니다.", Toast.LENGTH_SHORT).show();
            finish();

        } else if (id == R.id.nav_feedback) {
            showSnackBarMessage("피드배백");

        } else if (id == R.id.nav_setting) {
            showSnackBarMessage("Setting");

        } else if (id == R.id.nav_change) {
            Intent intent1 = new Intent(getApplicationContext(), ValueChangeActivity.class);
            startActivity(intent1);

        } else if (id == R.id.nav_ranking) {
            Intent intent2 = new Intent(getApplicationContext(), ValueRankingActivity.class);
            startActivity(intent2);
        }

        ////////// testing

        else if (id == R.id.test_app_intro){
            Intent intent = new Intent(getApplicationContext(), AppIntroActivity.class);
            startActivity(intent);
        }else if (id == R.id.test_sign_in){
            Intent intent = new Intent(getApplicationContext(), SignInActivity.class);
            startActivity(intent);
        }else if (id == R.id.test_sign_up){
            Intent intent = new Intent(getApplicationContext(), SignUpActivity.class);
            startActivity(intent);
        }

        //////////

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void bindingView(){
        setContentView(R.layout.activity_main);
        navTvName = findViewById(R.id.tv_name);
        navTvEmail = findViewById(R.id.tv_email);

        tvScoreTotal = findViewById(R.id.tv_score_total);

        // SURVEY 버튼
        Button btnSurvey = findViewById(R.id.btn_survey);
        btnSurvey.setOnClickListener(listener);
        llSmile = findViewById(R.id.ll_smile);
        tvValueSmile = findViewById(R.id.tv_value_smile);

        llSmile.setOnClickListener(listener);

        // Toolbar
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        // Toolbar에 제목 안 뜨도록
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        // Retrofit2 (RxJAVA)
        mSubscriptions = new CompositeSubscription();


        // CHART
        mChart = findViewById(R.id.chart_radar);
        mChart.setBackgroundColor(getColor(R.color.colorBackgroundLightGray));

        mChart.getDescription().setEnabled(false);

        mChart.setWebLineWidth(1);
        mChart.setWebColor(getColor(R.color.colorBlack));
        mChart.setWebLineWidthInner(1);
        mChart.setWebColorInner(getColor(R.color.colorBlack));
        mChart.setWebAlpha(100);
        MarkerView mv = new RadarMarkerView(this, R.layout.radar_markerview);
        mv.setChartView(mChart); // For bounds control
        mChart.setMarker(mv); // Set the marker to the chart




    }

    private void initiateView(){

        mChart.animateXY(1400, 1400);
    }

    private void loadData(){

        mSubscriptions.add(NetworkUtil.getRetrofit(mToken).getValue(mID)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(this::handleResponse, this::handleError));

    }
    private void setChart(){
        XAxis xAxis = mChart.getXAxis();
        xAxis.setTextSize(14);
        xAxis.setYOffset(0);
        xAxis.setXOffset(0);
        xAxis.setValueFormatter(new IAxisValueFormatter() {

            private String[] mActivities = new String[]{"1.신체적", "2.정서적", "3.사회적", "4.경제적", "5.환경적", "6.지적", "7.직업적", "8.영적", "9.정신적", "10.메디컬"};

            @Override
            public String getFormattedValue(float value, AxisBase axis) {
                return mActivities[(int) value % mActivities.length];
            }
        });
        xAxis.setTextColor(getColor(R.color.colorBlack));

        YAxis yAxis = mChart.getYAxis();
        yAxis.setLabelCount(5, false);
        yAxis.setTextSize(11);
        yAxis.setAxisMinimum(0);
        yAxis.setAxisMaximum(8);
        yAxis.setDrawLabels(true);

        Legend l = mChart.getLegend();
        l.setVerticalAlignment(Legend.LegendVerticalAlignment.TOP);
        l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.CENTER);
        l.setOrientation(Legend.LegendOrientation.HORIZONTAL);
        l.setDrawInside(false);
        l.setXEntrySpace(7);
        l.setYEntrySpace(5);
        l.setTextColor(getColor(R.color.colorBlack));
    }


    private void initSharedPreferences() {

        mSharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        mToken = mSharedPreferences.getString(Constants.TOKEN,"");
        mID = mSharedPreferences.getString(Constants.EMAIL,"");
    }

    private void handleResponse(Value[] value) {

        valueArrayList = new ArrayList<>();

        for (Value valueItem : value){
            if(valueItem != null) {
                valueArrayList.add(valueItem);
            }
        }
        String[] valueArray1 = getValueArray(valueArrayList.get(valueArrayList.size()-2));
        String[] valueArray2 = getValueArray(valueArrayList.get(valueArrayList.size()-1));

        int cnt = 10;
        ArrayList<RadarEntry> entries1 = new ArrayList<>();
        ArrayList<RadarEntry> entries2 = new ArrayList<>();

        float totalScore = 0;

        for (int i = 0; i < cnt; i++){
            entries1.add(new RadarEntry(Float.valueOf(valueArray1[i+1])));
            entries2.add(new RadarEntry(Float.valueOf(valueArray2[i+1])));
            totalScore += Float.valueOf(valueArray2[i+1]);
        }
        tvScoreTotal.setText(String.valueOf(totalScore));


        RadarDataSet set1 = new RadarDataSet(entries1, "유저 평균값");
        set1.setColor(getColor(R.color.colorGraphGray));
        set1.setFillColor(getColor(R.color.colorGraphGray));
        set1.setDrawFilled(true);
        set1.setFillAlpha(180);
        set1.setLineWidth(2f);
        set1.setDrawHighlightCircleEnabled(true);
        set1.setDrawHighlightIndicators(false);

        RadarDataSet set2 = new RadarDataSet(entries2, "최근 측정값 ("+valueArray2[0]+")");
        set2.setColor(getColor(R.color.colorPrimary));
        set2.setFillColor(getColor(R.color.colorPrimary));
        set2.setDrawFilled(true);
        set2.setFillAlpha(180);
        set2.setLineWidth(2f);
        set2.setDrawHighlightCircleEnabled(true);
        set2.setDrawHighlightIndicators(false);


        ArrayList<IRadarDataSet> sets = new ArrayList<>();
        sets.add(set1);
        sets.add(set2);

        RadarData data = new RadarData(sets);
        data.setValueTextSize(11);
        data.setDrawValues(false);
        data.setValueTextColor(Color.WHITE);

        mChart.setData(data);
        mChart.invalidate();

    }
    private String[] getValueArray (Value valueItem){
        String[] valueArray = new String[11];
        valueArray[0] = valueItem.getCreatedAt();
        valueArray[1] = valueItem.getValue1();
        valueArray[2] = valueItem.getValue2();
        valueArray[3] = valueItem.getValue3();
        valueArray[4] = valueItem.getValue4();
        valueArray[5] = valueItem.getValue5();
        valueArray[6] = valueItem.getValue6();
        valueArray[7] = valueItem.getValue7();
        valueArray[8] = valueItem.getValue8();
        valueArray[9] = valueItem.getValue9();
        valueArray[10] = valueItem.getValue10();

        return valueArray;
    }

    private void handleError(Throwable error) {

        if (error instanceof HttpException) {

            Log.d("PaengTest1", error.toString());

            Gson gson = new GsonBuilder().create();
            try {
                String errorBody = ((HttpException) error).response().errorBody().string();
                Res response = gson.fromJson(errorBody, com.pkteam.measure_happiness.model.Res.class);
                showSnackBarMessage(response.getMessage());

            } catch (IOException e) {
                e.printStackTrace();
                showSnackBarMessage(e.toString());
                Log.d("PaengTest", e.toString());
            }
        } else {
            showSnackBarMessage("Network Error !");
        }
    }

    long pressTime;
    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            //super.onBackPressed();
            if(System.currentTimeMillis() - pressTime <2000){
                finish();
                return;
            }
            showSnackBarMessage("한번 더 누르시면 앱이 종료됩니다.");
            pressTime = System.currentTimeMillis();
        }
    }

    private void showSnackBarMessage(String message){
        Snacky.builder()
                .setActivity(this)
                .setBackgroundColor(getColor(R.color.colorPrimary))
                .setText(message)
                .setTextColor(getColor(R.color.colorTextInButton))
                .centerText()
                .setDuration(Snacky.LENGTH_LONG)
                .build()
                .show();
    }





    private View.OnClickListener listener = new View.OnClickListener(){
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.btn_survey:
                    Intent intent3 = new Intent(getApplicationContext(), SurveyActivity.class);
                    startActivity(intent3);
                    break;

                case R.id.ll_smile:
                    Intent intent4 = new Intent(getApplicationContext(), SmileInfoActivity.class);
                    startActivity(intent4);
                    break;
            }
        }
    };
}
