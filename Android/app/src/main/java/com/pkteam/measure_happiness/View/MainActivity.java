package com.pkteam.measure_happiness.View;

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

import retrofit2.adapter.rxjava.HttpException;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private RadarChart mChart;
    private TextView tvValueSmile;

    private LinearLayout llSmile;

    private String mToken;
    private String mID;

    private SharedPreferences mSharedPreferences;
    private CompositeSubscription mSubscriptions;

    private ArrayList<Value> valueArrayList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mID = "nuggy875@naver.com";
        bindingView();
        initiateView();

    }
    @Override
    protected void onResume(){
        super.onResume();
        initiateView();
    }

    @Override
    protected void onDestroy(){
        super.onDestroy();
        mSubscriptions.unsubscribe();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }


    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {

        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

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

        // SURVEY 버튼
        Button btnSurvey = findViewById(R.id.btn_survey);
        Button btnChange = findViewById(R.id.btn_change);
        Button btnRanking = findViewById(R.id.btn_ranking);
        btnSurvey.setOnClickListener(listener);
        btnChange.setOnClickListener(listener);
        btnRanking.setOnClickListener(listener);
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

        mChart.setWebLineWidth(1f);
        mChart.setWebColor(getColor(R.color.colorBlack));
        mChart.setWebLineWidthInner(1f);
        mChart.setWebColorInner(getColor(R.color.colorBlack));
        mChart.setWebAlpha(100);
        MarkerView mv = new RadarMarkerView(this, R.layout.radar_markerview);
        mv.setChartView(mChart); // For bounds control
        mChart.setMarker(mv); // Set the marker to the chart
        Log.d("PaengTestId", mID);


        loadData();

        setData();


        XAxis xAxis = mChart.getXAxis();
        xAxis.setTextSize(11);
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
        yAxis.setTextSize(9f);
        yAxis.setAxisMinimum(0);
        yAxis.setAxisMaximum(9);
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

    private void initiateView(){

        mChart.animateXY(1400, 1400);
    }

    private void loadData(){


        mSubscriptions.add(NetworkUtil.getRetrofit().getValue("nuggy875@naver.com")
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(this::handleResponse, this::handleError));

    }

    public void setData() {

        float mult = 9;
        float min = 1;
        int cnt = 10;

        ArrayList<RadarEntry> entries1 = new ArrayList<RadarEntry>();
        ArrayList<RadarEntry> entries2 = new ArrayList<RadarEntry>();

        // NOTE: The order of the entries when being added to the entries array determines their position around the center of
        // the chart.
        for (int i = 0; i < cnt; i++) {
            float val1 = (float) (Math.random() * mult) + min;
            entries1.add(new RadarEntry(val1));

            float val2 = (float) (Math.random() * mult) + min;
            entries2.add(new RadarEntry(val2));
        }

        RadarDataSet set1 = new RadarDataSet(entries1, "지난 측정값");
        set1.setColor(getColor(R.color.colorGraphGray));
        set1.setFillColor(getColor(R.color.colorGraphGray));
        set1.setDrawFilled(true);
        set1.setFillAlpha(180);
        set1.setLineWidth(2f);
        set1.setDrawHighlightCircleEnabled(true);
        set1.setDrawHighlightIndicators(false);

        RadarDataSet set2 = new RadarDataSet(entries2, "이번 측정값");
        set2.setColor(getColor(R.color.colorPrimary));
        set2.setFillColor(getColor(R.color.colorPrimary));
        set2.setDrawFilled(true);
        set2.setFillAlpha(180);
        set2.setLineWidth(2f);
        set2.setDrawHighlightCircleEnabled(true);
        set2.setDrawHighlightIndicators(false);

        ArrayList<IRadarDataSet> sets = new ArrayList<IRadarDataSet>();
        sets.add(set1);
        sets.add(set2);

        RadarData data = new RadarData(sets);
        data.setValueTextSize(8f);
        data.setDrawValues(false);
        data.setValueTextColor(Color.WHITE);

        mChart.setData(data);
        mChart.invalidate();
    }

    private void initSharedPreferences() {

        mSharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        mToken = mSharedPreferences.getString(Constants.TOKEN,"");
        mID = mSharedPreferences.getString(Constants.ID,"");
    }

    private void handleResponse(Value[] value) {

        valueArrayList = new ArrayList<Value>();


        for (Value valueItem : value){
            if(valueItem != null){

                Log.d("TestPaengData", valueItem.getUserId());
                Log.d("TestPaengDataCA", valueItem.getCreatedAt());
                Log.d("TestPaengData1", valueItem.getValue1());

            }
        }

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

    private void showSnackBarMessage(String message){
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();

    }


    private View.OnClickListener listener = new View.OnClickListener(){
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.btn_change:
                    Intent intent1 = new Intent(getApplicationContext(), ValueChangeActivity.class);
                    startActivity(intent1);
                    break;

                case R.id.btn_ranking:

                    Intent intent2 = new Intent(getApplicationContext(), ValueRankingActivity.class);
                    startActivity(intent2);
                    break;

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
