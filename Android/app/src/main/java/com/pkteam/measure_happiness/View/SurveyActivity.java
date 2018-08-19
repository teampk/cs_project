package com.pkteam.measure_happiness.View;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;

import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.pkteam.measure_happiness.R;

/**
 * Created by paeng on 2018. 8. 1..
 */

public class SurveyActivity extends AppCompatActivity {
    private Button btnBack, btnSubmit;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_survey);

        bindingView();


    }


    private void setSeekbar(final SeekBar sb, final TextView tv, final int ver) {
        sb.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                if (ver == 1){
                    tv.setText(String.valueOf(i));

                }else if (ver == 2) {
                    tv.setText(String.valueOf(i*10));
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

    }

    private View.OnClickListener listener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.btn_back:
                    finish();
                    break;
                case R.id.btn_submit:

                    AlertDialog.Builder builder = new AlertDialog.Builder(SurveyActivity.this);
                    builder.setTitle("설문을 완료하셨나요?")
                            .setPositiveButton("예",
                                    new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int which) {
                                            Toast.makeText(SurveyActivity.this, "완료.", Toast.LENGTH_SHORT).show();
                                            finish();
                                        }
                                    })
                            .setNegativeButton("아니오",
                                    new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int which) {

                                        }
                                    });
                    AlertDialog dialog = builder.create();    // 알림창 객체 생성
                    dialog.show();


                    break;
                default:
                    break;
            }
        }
    };

    private void bindingView() {
        btnBack = findViewById(R.id.btn_back);
        btnBack.setOnClickListener(listener);
        btnSubmit = findViewById(R.id.btn_submit);
        btnSubmit.setOnClickListener(listener);
        SeekBar sb1_1 = findViewById(R.id.sb_1_1);
        TextView tv1_1 = findViewById(R.id.tv_1_1);
        SeekBar sb1_2 = findViewById(R.id.sb_1_2);
        TextView tv1_2 = findViewById(R.id.tv_1_2);
        SeekBar sb2_1 = findViewById(R.id.sb_2_1);
        TextView tv2_1 = findViewById(R.id.tv_2_1);
        SeekBar sb2_2 = findViewById(R.id.sb_2_2);
        TextView tv2_2 = findViewById(R.id.tv_2_2);
        SeekBar sb3_1 = findViewById(R.id.sb_3_1);
        TextView tv3_1 = findViewById(R.id.tv_3_1);
        SeekBar sb3_2 = findViewById(R.id.sb_3_2);
        TextView tv3_2 = findViewById(R.id.tv_3_2);
        SeekBar sb4_1 = findViewById(R.id.sb_4_1);
        TextView tv4_1 = findViewById(R.id.tv_4_1);
        SeekBar sb4_2 = findViewById(R.id.sb_4_2);
        TextView tv4_2 = findViewById(R.id.tv_4_2);
        SeekBar sb5_1 = findViewById(R.id.sb_5_1);
        TextView tv5_1 = findViewById(R.id.tv_5_1);
        SeekBar sb5_2 = findViewById(R.id.sb_5_2);
        TextView tv5_2 = findViewById(R.id.tv_5_2);
        SeekBar sb6_1 = findViewById(R.id.sb_6_1);
        TextView tv6_1 = findViewById(R.id.tv_6_1);
        SeekBar sb6_2 = findViewById(R.id.sb_6_2);
        TextView tv6_2 = findViewById(R.id.tv_6_2);
        SeekBar sb7_1 = findViewById(R.id.sb_7_1);
        TextView tv7_1 = findViewById(R.id.tv_7_1);
        SeekBar sb7_2 = findViewById(R.id.sb_7_2);
        TextView tv7_2 = findViewById(R.id.tv_7_2);
        SeekBar sb8_1 = findViewById(R.id.sb_8_1);
        TextView tv8_1 = findViewById(R.id.tv_8_1);
        SeekBar sb8_2 = findViewById(R.id.sb_8_2);
        TextView tv8_2 = findViewById(R.id.tv_8_2);
        SeekBar sb9_1 = findViewById(R.id.sb_9_1);
        TextView tv9_1 = findViewById(R.id.tv_9_1);
        SeekBar sb9_2 = findViewById(R.id.sb_9_2);
        TextView tv9_2 = findViewById(R.id.tv_9_2);
        SeekBar sb10_1 = findViewById(R.id.sb_10_1);
        TextView tv10_1 = findViewById(R.id.tv_10_1);
        SeekBar sb10_2 = findViewById(R.id.sb_10_2);
        TextView tv10_2 = findViewById(R.id.tv_10_2);


        setSeekbar(sb1_1, tv1_1, 1);
        setSeekbar(sb1_2, tv1_2, 2);
        setSeekbar(sb2_1, tv2_1, 1);
        setSeekbar(sb2_2, tv2_2, 2);
        setSeekbar(sb3_1, tv3_1, 1);
        setSeekbar(sb3_2, tv3_2, 2);
        setSeekbar(sb4_1, tv4_1, 1);
        setSeekbar(sb4_2, tv4_2, 2);
        setSeekbar(sb5_1, tv5_1, 1);
        setSeekbar(sb5_2, tv5_2, 2);
        setSeekbar(sb6_1, tv6_1, 1);
        setSeekbar(sb6_2, tv6_2, 2);
        setSeekbar(sb7_1, tv7_1, 1);
        setSeekbar(sb7_2, tv7_2, 2);
        setSeekbar(sb8_1, tv8_1, 1);
        setSeekbar(sb8_2, tv8_2, 2);
        setSeekbar(sb9_1, tv9_1, 1);
        setSeekbar(sb9_2, tv9_2, 2);
        setSeekbar(sb10_1, tv10_1, 1);
        setSeekbar(sb10_2, tv10_2, 2);

    }
}
