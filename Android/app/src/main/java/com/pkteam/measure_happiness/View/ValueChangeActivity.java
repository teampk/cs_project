package com.pkteam.measure_happiness.view;

import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
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
import com.pkteam.measure_happiness.R;
import com.pkteam.measure_happiness.custom.MyMarkerView;

import java.util.ArrayList;

/*
 * Created by paeng on 18/08/2018.
 */

public class ValueChangeActivity extends AppCompatActivity {
    private LineChart mChart;
    private TextView tvGraphTitle;
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


    }
    private void bindingView(){

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
        setData(10, 9);

//        mChart.setVisibleXRange(20);
//        mChart.setVisibleYRange(20f, AxisDependency.LEFT);
//        mChart.centerViewTo(20, 50, AxisDependency.LEFT);

        mChart.animateX(1000);
        //mChart.invalidate();

        // get the legend (only possible after setting data)
        Legend l = mChart.getLegend();

        // modify the legend ...
        l.setForm(Legend.LegendForm.LINE);

        // // dont forget to refresh the drawing
        // mChart.invalidate();

    }

    private void setData(int count, float range) {

        ArrayList<Entry> values = new ArrayList<Entry>();

        for (int i = 0; i < count; i++) {

            int val = (int) (Math.random() * range) + 1;
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
    }

    Button.OnClickListener listener = new Button.OnClickListener(){

        @Override
        public void onClick(View v) {
            switch(v.getId()){
                case R.id.btn_back:
                    finish();
                    break;
                case R.id.bt_1:
                    tvGraphTitle.setText(graphTitle[1]);
                    setData(10, 9);
                    mChart.animateX(1000);
                    break;
                case R.id.bt_2:
                    tvGraphTitle.setText(graphTitle[2]);
                    setData(10, 9);
                    mChart.animateX(1000);
                    break;
                case R.id.bt_3:
                    tvGraphTitle.setText(graphTitle[3]);
                    setData(10, 9);
                    mChart.animateX(1000);
                    break;
                case R.id.bt_4:
                    tvGraphTitle.setText(graphTitle[4]);
                    setData(10, 9);
                    mChart.animateX(1000);
                    break;
                case R.id.bt_5:
                    tvGraphTitle.setText(graphTitle[5]);
                    setData(10, 9);
                    mChart.animateX(1000);
                    break;
                case R.id.bt_6:
                    tvGraphTitle.setText(graphTitle[6]);
                    setData(10, 9);
                    mChart.animateX(1000);
                    break;
                case R.id.bt_7:
                    tvGraphTitle.setText(graphTitle[7]);
                    setData(10, 9);
                    mChart.animateX(1000);
                    break;
                case R.id.bt_8:
                    tvGraphTitle.setText(graphTitle[8]);
                    setData(10, 9);
                    mChart.animateX(1000);
                    break;
                case R.id.bt_9:
                    tvGraphTitle.setText(graphTitle[9]);
                    setData(10, 9);
                    mChart.animateX(1000);
                    break;
                case R.id.bt_10:
                    tvGraphTitle.setText(graphTitle[10]);
                    setData(10, 9);
                    mChart.animateX(1000);
                    break;


            }
        }
    };
}
