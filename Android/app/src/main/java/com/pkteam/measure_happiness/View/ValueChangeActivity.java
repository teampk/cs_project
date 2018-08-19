package com.pkteam.measure_happiness.View;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.pkteam.measure_happiness.R;

/*
 * Created by paeng on 18/08/2018.
 */

public class ValueChangeActivity extends AppCompatActivity {

    private Button btnBack;
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


        btnBack = findViewById(R.id.btn_back);
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
                    break;
                case R.id.bt_2:
                    tvGraphTitle.setText(graphTitle[2]);
                    break;
                case R.id.bt_3:
                    tvGraphTitle.setText(graphTitle[3]);
                    break;
                case R.id.bt_4:
                    tvGraphTitle.setText(graphTitle[4]);
                    break;
                case R.id.bt_5:
                    tvGraphTitle.setText(graphTitle[5]);
                    break;
                case R.id.bt_6:
                    tvGraphTitle.setText(graphTitle[6]);
                    break;
                case R.id.bt_7:
                    tvGraphTitle.setText(graphTitle[7]);
                    break;
                case R.id.bt_8:
                    tvGraphTitle.setText(graphTitle[8]);
                    break;
                case R.id.bt_9:
                    tvGraphTitle.setText(graphTitle[9]);
                    break;
                case R.id.bt_10:
                    tvGraphTitle.setText(graphTitle[10]);
                    break;


            }
        }
    };
}
