package com.pkteam.measure_happiness.view;

import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.LimitLine;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.github.mikephil.charting.utils.Utils;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.pkteam.measure_happiness.R;
import com.pkteam.measure_happiness.custom.MyMarkerView;
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

/*
 * Created by paeng on 18/08/2018.
 */

public class ValueChangeActivity extends AppCompatActivity {
    private LineChart mChart;
    private TextView tvGraphTitle;

    private String mToken;
    private String mID;

    private SharedPreferences mSharedPreferences;
    private CompositeSubscription mSubscriptions;
    private ArrayList<Value> valueArrayList;
    private ArrayList<Integer> valueInteger[];


    int dataSize;
    String data[][];

    private static final String[] graphTitle = {"0",
            "1. 신체적 (Physical)", "2. 정신적 (Emotional)",
            "3. 사회적 (Social)", "4. 경제적 (Financial)",
            "5. 환경적 (Environmental)", "6. 지적 (Intellectual)",
            "7. 직업적 (Occupational)", "8. 영적 (Spiritual)",
            "9. 정신적 (Mental)", "10. 메디컬 (Medical)"};
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_value_change);
        bindingView();
        loadData();

    }

    @Override
    protected void onDestroy(){
        super.onDestroy();
        mSubscriptions.unsubscribe();
    }

    private void initSharedPreferences() {
        mSharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        mToken = mSharedPreferences.getString(Constants.TOKEN,"");
        mID = mSharedPreferences.getString(Constants.EMAIL,"");
    }

    private void initChart(){
        //mChart.setOnChartGestureListener(this);
        //mChart.setOnChartValueSelectedListener(this);
        mChart.setDrawGridBackground(false);

        // no description text
        mChart.getDescription().setEnabled(false);

        // enable touch gestures
        mChart.setTouchEnabled(true);

        // enable scaling and dragging
        mChart.setDragEnabled(true);
        mChart.setScaleEnabled(true);
        // mChart.setScaleXEnabled(true);
        // mChart.setScaleYEnabled(true);

        // if disabled, scaling can be done on x- and y-axis separately
        mChart.setPinchZoom(true);

        // set an alternative background color
        // mChart.setBackgroundColor(Color.GRAY);

        // create a custom MarkerView (extend MarkerView) and specify the layout
        // to use for it
        MyMarkerView mv = new MyMarkerView(this, R.layout.radar_markerview);
        mv.setChartView(mChart); // For bounds control
        mChart.setMarker(mv); // Set the marker to the chart

        // x-axis limit line
        LimitLine llXAxis = new LimitLine(10f, "Index 10");
        llXAxis.setLineWidth(4f);
        llXAxis.enableDashedLine(10f, 10f, 0f);
        llXAxis.setLabelPosition(LimitLine.LimitLabelPosition.RIGHT_BOTTOM);
        llXAxis.setTextSize(10f);


        XAxis xAxis = mChart.getXAxis();
        xAxis.enableGridDashedLine(10f, 10f, 0f);
        //xAxis.setValueFormatter(new MyCustomXAxisValueFormatter());
        //xAxis.addLimitLine(llXAxis); // add x-axis limit line


        //Typeface tf = Typeface.createFromAsset(getAssets(), "OpenSans-Regular.ttf");

        LimitLine ll1 = new LimitLine(10f, "Upper Limit");
        ll1.setLineWidth(2f);
        ll1.enableDashedLine(10f, 10f, 0f);
        ll1.setLabelPosition(LimitLine.LimitLabelPosition.RIGHT_TOP);
        ll1.setTextSize(10f);
        //ll1.setTypeface(tf);

        LimitLine ll2 = new LimitLine(0f, "Lower Limit");
        ll2.setLineWidth(2f);
        ll2.enableDashedLine(10f, 10f, 0f);
        ll2.setLabelPosition(LimitLine.LimitLabelPosition.RIGHT_BOTTOM);
        ll2.setTextSize(10f);
        //ll2.setTypeface(tf);

        YAxis leftAxis = mChart.getAxisLeft();
        leftAxis.removeAllLimitLines(); // reset all limit lines to avoid overlapping lines
        leftAxis.addLimitLine(ll1);
        leftAxis.addLimitLine(ll2);
        leftAxis.setAxisMaximum(12f);
        leftAxis.setAxisMinimum(-2f);
        //leftAxis.setYOffset(20f);
        leftAxis.enableGridDashedLine(10f, 10f, 0f);
        leftAxis.setDrawZeroLine(false);

        // limit lines are drawn behind data (and not on top)
        leftAxis.setDrawLimitLinesBehindData(true);

        mChart.getAxisRight().setEnabled(false);

        //mChart.getViewPortHandler().setMaximumScaleY(2f);
        //mChart.getViewPortHandler().setMaximumScaleX(2f);

        // add data
        setData(1);

    }

    private void loadData(){
        initSharedPreferences();
        // Retrofit2 (RxJAVA)
        mSubscriptions = new CompositeSubscription();
        mSubscriptions.add(NetworkUtil.getRetrofit(mToken).getValue(mID)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(this::handleResponse, this::handleError));
    }



    private void handleResponse(Value[] value) {

        valueArrayList = new ArrayList<>();

        for (Value valueItem : value){
            if(valueItem != null) {
                valueArrayList.add(valueItem);
            }
        }

        dataSize = 0;

        data = new String[10][11];

        if (valueArrayList.size()>9){
            for (int i=1;i<=10;i++){
                dataSize = 10;
                saveDataFromResponse(valueArrayList.get(valueArrayList.size()-i), i-1);


            }
        }else if (valueArrayList.size() == 0){
            for (int i=1;i<=10;i++){
                data[i][0] = "default";
                data[i][1] = "0";
                data[i][2] = "0";
                data[i][3] = "0";
                data[i][4] = "0";
                data[i][5] = "0";
                data[i][6] = "0";
                data[i][6] = "0";
                data[i][7] = "0";
                data[i][8] = "0";
                data[i][9] = "0";
                data[i][10] = "0";

            }
        }else{
            for (int i=1;i<=valueArrayList.size();i++){
                dataSize=valueArrayList.size();
                saveDataFromResponse(valueArrayList.get(valueArrayList.size()-i), i-1);
            }

            for (int i=valueArrayList.size(); i<10;i++){
                saveDataFromResponseForRest(i);
            }
        }

        initChart();


    }
    private void saveDataFromResponse(Value value, int i){
        data[i][0] = value.getCreatedAt();
        data[i][1] = value.getValue1();
        data[i][2] = value.getValue2();
        data[i][3] = value.getValue3();
        data[i][4] = value.getValue4();
        data[i][5] = value.getValue5();
        data[i][6] = value.getValue6();
        data[i][7] = value.getValue7();
        data[i][8] = value.getValue8();
        data[i][9] = value.getValue9();
        data[i][10] = value.getValue10();

    }
    private void saveDataFromResponseForRest(int i){
        data[i][0] = "default";
        data[i][1] = "0";
        data[i][2] = "0";
        data[i][3] = "0";
        data[i][4] = "0";
        data[i][5] = "0";
        data[i][6] = "0";
        data[i][6] = "0";
        data[i][7] = "0";
        data[i][8] = "0";
        data[i][9] = "0";
        data[i][10] = "0";
    }

    private void handleError(Throwable error) {

        if (error instanceof HttpException) {

            Gson gson = new GsonBuilder().create();
            try {
                String errorBody = ((HttpException) error).response().errorBody().string();
                Res response = gson.fromJson(errorBody, com.pkteam.measure_happiness.model.Res.class);
                showSnackBarMessage(response.getMessage());

            } catch (IOException e) {
                e.printStackTrace();
                showSnackBarMessage(e.toString());
            }
        } else {
            showSnackBarMessage("Network Error !");
        }
    }

    // setData(1);
    // setData(2);
    // ...
    // setData(10);


    private void setData(int num) {

        ArrayList<Entry> values = new ArrayList<Entry>();
        //int range = 9;

        for (int i = 0; i < 10; i++) {

            // int val = (int) (Math.random() * range) + 1;
            int val = Integer.valueOf(data[9-i][num]);
            values.add(new Entry(i, val, getResources().getDrawable(R.drawable.star)));
        }

        LineDataSet set1;

        if (mChart.getData() != null &&
                mChart.getData().getDataSetCount() > 0) {
            set1 = (LineDataSet)mChart.getData().getDataSetByIndex(0);
            set1.setValues(values);
            mChart.getData().notifyDataChanged();
            mChart.notifyDataSetChanged();
        } else {
            // create a dataset and give it a type
            set1 = new LineDataSet(values, "DataSet 1");

            set1.setDrawIcons(false);

            // set the line to be drawn like this "- - - - - -"
            set1.enableDashedLine(10f, 5f, 0f);
            set1.enableDashedHighlightLine(10f, 5f, 0f);
            set1.setColor(Color.BLACK);
            set1.setCircleColor(Color.BLACK);
            set1.setLineWidth(1f);
            set1.setCircleRadius(3f);
            set1.setDrawCircleHole(false);
            set1.setValueTextSize(9f);
            set1.setDrawFilled(true);
            set1.setFormLineWidth(1f);
            set1.setFormLineDashEffect(new DashPathEffect(new float[]{10f, 5f}, 0f));
            set1.setFormSize(15.f);

            if (Utils.getSDKInt() >= 18) {
                // fill drawable only supported on api level 18 and above
                Drawable drawable = ContextCompat.getDrawable(this, R.drawable.fade_red);
                set1.setFillDrawable(drawable);
            }
            else {
                set1.setFillColor(Color.BLACK);
            }

            ArrayList<ILineDataSet> dataSets = new ArrayList<ILineDataSet>();
            dataSets.add(set1); // add the datasets

            // create a data object with the datasets
            LineData data = new LineData(dataSets);

            // set data
            mChart.setData(data);

        }
        mChart.animateX(1000);

        //        mChart.setVisibleXRange(20);
        //        mChart.setVisibleYRange(20f, AxisDependency.LEFT);
        //        mChart.centerViewTo(20, 50, AxisDependency.LEFT);


        //mChart.invalidate();

        // get the legend (only possible after setting data)
        Legend l = mChart.getLegend();

        // modify the legend ...
        l.setForm(Legend.LegendForm.LINE);

        // // dont forget to refresh the drawing
        // mChart.invalidate();




    }

    Button.OnClickListener listener = new Button.OnClickListener(){

        @Override
        public void onClick(View v) {
            switch(v.getId()){
                case R.id.rl_test:
                    Log.d("PaengData1", data[0][0]+"  "+
                            data[0][1]+"/"+
                            data[0][2]+"/"+
                            data[0][3]+"/"+
                            data[0][4]+"/"+
                            data[0][5]+"/"+
                            data[0][6]+"/"+
                            data[0][7]+"/"+
                            data[0][8]+"/"+
                            data[0][9]+"/"+
                            data[0][10]);
                    Log.d("PaengData2", data[1][0]+"  "+
                            data[1][1]+"/"+
                            data[1][2]+"/"+
                            data[1][3]+"/"+
                            data[1][4]+"/"+
                            data[1][5]+"/"+
                            data[1][6]+"/"+
                            data[1][7]+"/"+
                            data[1][8]+"/"+
                            data[1][9]+"/"+
                            data[1][10]);
                    Log.d("PaengData3", data[2][0]+"  "+
                            data[2][1]+"/"+
                            data[2][2]+"/"+
                            data[2][3]+"/"+
                            data[2][4]+"/"+
                            data[2][5]+"/"+
                            data[2][6]+"/"+
                            data[2][7]+"/"+
                            data[2][8]+"/"+
                            data[2][9]+"/"+
                            data[2][10]);
                    Log.d("PaengData4", data[3][0]+"  "+
                            data[3][1]+"/"+
                            data[3][2]+"/"+
                            data[3][3]+"/"+
                            data[3][4]+"/"+
                            data[3][5]+"/"+
                            data[3][6]+"/"+
                            data[3][7]+"/"+
                            data[3][8]+"/"+
                            data[3][9]+"/"+
                            data[3][10]);
                    Log.d("PaengData5", data[4][0]+"  "+
                            data[4][1]+"/"+
                            data[4][2]+"/"+
                            data[4][3]+"/"+
                            data[4][4]+"/"+
                            data[4][5]+"/"+
                            data[4][6]+"/"+
                            data[4][7]+"/"+
                            data[4][8]+"/"+
                            data[4][9]+"/"+
                            data[4][10]);
                    Log.d("PaengData6", data[5][0]+"  "+
                            data[5][1]+"/"+
                            data[5][2]+"/"+
                            data[5][3]+"/"+
                            data[5][4]+"/"+
                            data[5][5]+"/"+
                            data[5][6]+"/"+
                            data[5][7]+"/"+
                            data[5][8]+"/"+
                            data[5][9]+"/"+
                            data[5][10]);
                    Log.d("PaengData7", data[6][0]+"  "+
                            data[6][1]+"/"+
                            data[6][2]+"/"+
                            data[6][3]+"/"+
                            data[6][4]+"/"+
                            data[6][5]+"/"+
                            data[6][6]+"/"+
                            data[6][7]+"/"+
                            data[6][8]+"/"+
                            data[6][9]+"/"+
                            data[6][10]);
                    Log.d("PaengData8", data[7][0]+"  "+
                            data[7][1]+"/"+
                            data[7][2]+"/"+
                            data[7][3]+"/"+
                            data[7][4]+"/"+
                            data[7][5]+"/"+
                            data[7][6]+"/"+
                            data[7][7]+"/"+
                            data[7][8]+"/"+
                            data[7][9]+"/"+
                            data[7][10]);
                    Log.d("PaengData9", data[8][0]+"  "+
                            data[8][1]+"/"+
                            data[8][2]+"/"+
                            data[8][3]+"/"+
                            data[8][4]+"/"+
                            data[8][5]+"/"+
                            data[8][6]+"/"+
                            data[8][7]+"/"+
                            data[8][8]+"/"+
                            data[8][9]+"/"+
                            data[8][10]);
                    Log.d("PaengData10", data[9][0]+"  "+
                            data[9][1]+"/"+
                            data[9][2]+"/"+
                            data[9][3]+"/"+
                            data[9][4]+"/"+
                            data[9][5]+"/"+
                            data[9][6]+"/"+
                            data[9][7]+"/"+
                            data[9][8]+"/"+
                            data[9][9]+"/"+
                            data[9][10]);
                    Log.d("PaengDataSize", "dataSize="+dataSize);

                    break;
                case R.id.btn_back:
                    finish();
                    break;
                case R.id.bt_1:
                    tvGraphTitle.setText(graphTitle[1]);
                    setData(1);

                    break;
                case R.id.bt_2:
                    tvGraphTitle.setText(graphTitle[2]);
                    setData(2);
                    break;
                case R.id.bt_3:
                    tvGraphTitle.setText(graphTitle[3]);
                    setData(3);
                    break;
                case R.id.bt_4:
                    tvGraphTitle.setText(graphTitle[4]);
                    setData(4);
                    break;
                case R.id.bt_5:
                    tvGraphTitle.setText(graphTitle[5]);
                    setData(5);
                    break;
                case R.id.bt_6:
                    tvGraphTitle.setText(graphTitle[6]);
                    setData(6);
                    break;
                case R.id.bt_7:
                    tvGraphTitle.setText(graphTitle[7]);
                    setData(7);
                    break;
                case R.id.bt_8:
                    tvGraphTitle.setText(graphTitle[8]);
                    setData(8);
                    break;
                case R.id.bt_9:
                    tvGraphTitle.setText(graphTitle[9]);
                    setData(9);
                    break;
                case R.id.bt_10:
                    tvGraphTitle.setText(graphTitle[10]);
                    setData(10);
                    break;


            }
        }
    };

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

    private void bindingView(){

        RelativeLayout rlTest = findViewById(R.id.rl_test);
        rlTest.setOnClickListener(listener);

        Button btnBack = findViewById(R.id.btn_back);
        btnBack.setOnClickListener(listener);
        tvGraphTitle = findViewById(R.id.tv_graph_title);
        Button bt1 = findViewById(R.id.bt_1);
        Button bt2 = findViewById(R.id.bt_2);
        Button bt3 = findViewById(R.id.bt_3);
        Button bt4 = findViewById(R.id.bt_4);
        Button bt5 = findViewById(R.id.bt_5);
        Button bt6 = findViewById(R.id.bt_6);
        Button bt7 = findViewById(R.id.bt_7);
        Button bt8 = findViewById(R.id.bt_8);
        Button bt9 = findViewById(R.id.bt_9);
        Button bt10 = findViewById(R.id.bt_10);
        bt1.setOnClickListener(listener);
        bt2.setOnClickListener(listener);
        bt3.setOnClickListener(listener);
        bt4.setOnClickListener(listener);
        bt5.setOnClickListener(listener);
        bt6.setOnClickListener(listener);
        bt7.setOnClickListener(listener);
        bt8.setOnClickListener(listener);
        bt9.setOnClickListener(listener);
        bt10.setOnClickListener(listener);

        mChart = findViewById(R.id.chart1);


    }
}
