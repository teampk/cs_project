package com.pkteam.measure_happiness.View;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;

import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.pkteam.measure_happiness.CustomDialog;
import com.pkteam.measure_happiness.R;

/**
 * Created by paeng on 2018. 8. 1..
 */

public class SurveyActivity extends AppCompatActivity {
    private CustomDialog mCustomDialog;
    private TextView tvScore1, tvScore2, tvScore3, tvScore4, tvScore5,
            tvScore6, tvScore7, tvScore8, tvScore9, tvScore10;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_survey);
        bindingView();


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

                case R.id.btn_question_1:
                    CustomDialog customDialog1 = new CustomDialog(SurveyActivity.this);
                    customDialog1.callFunction(
                            tvScore1,
                            getString(R.string.happy_title_1),
                            getString(R.string.happy_description_1),
                            getString(R.string.happy_question_1_1),
                            getString(R.string.happy_question_1_2),
                            getString(R.string.happy_question_1_3));
                    break;
                case R.id.btn_question_2:
                    CustomDialog customDialog2 = new CustomDialog(SurveyActivity.this);
                    customDialog2.callFunction(
                            tvScore2,
                            getString(R.string.happy_title_2),
                            getString(R.string.happy_description_2),
                            getString(R.string.happy_question_2_1),
                            getString(R.string.happy_question_2_2),
                            getString(R.string.happy_question_2_3));
                    break;
                case R.id.btn_question_3:
                    CustomDialog customDialog3 = new CustomDialog(SurveyActivity.this);
                    customDialog3.callFunction(
                            tvScore3,
                            getString(R.string.happy_title_3),
                            getString(R.string.happy_description_3),
                            getString(R.string.happy_question_3_1),
                            getString(R.string.happy_question_3_2),
                            getString(R.string.happy_question_3_3));
                    break;
                case R.id.btn_question_4:
                    CustomDialog customDialog4 = new CustomDialog(SurveyActivity.this);
                    customDialog4.callFunction(
                            tvScore4,
                            getString(R.string.happy_title_4),
                            getString(R.string.happy_description_4),
                            getString(R.string.happy_question_4_1),
                            getString(R.string.happy_question_4_2),
                            getString(R.string.happy_question_4_3));
                    break;
                case R.id.btn_question_5:
                    CustomDialog customDialog5 = new CustomDialog(SurveyActivity.this);
                    customDialog5.callFunction(
                            tvScore5,
                            getString(R.string.happy_title_5),
                            getString(R.string.happy_description_5),
                            getString(R.string.happy_question_5_1),
                            getString(R.string.happy_question_5_2),
                            getString(R.string.happy_question_5_3));
                    break;
                case R.id.btn_question_6:
                    CustomDialog customDialog6 = new CustomDialog(SurveyActivity.this);
                    customDialog6.callFunction(
                            tvScore6,
                            getString(R.string.happy_title_6),
                            getString(R.string.happy_description_6),
                            getString(R.string.happy_question_6_1),
                            getString(R.string.happy_question_6_2),
                            getString(R.string.happy_question_6_3));
                    break;
                case R.id.btn_question_7:
                    CustomDialog customDialog7 = new CustomDialog(SurveyActivity.this);
                    customDialog7.callFunction(
                            tvScore7,
                            getString(R.string.happy_title_7),
                            getString(R.string.happy_description_7),
                            getString(R.string.happy_question_7_1),
                            getString(R.string.happy_question_7_2),
                            getString(R.string.happy_question_7_3));
                    break;
                case R.id.btn_question_8:
                    CustomDialog customDialog8 = new CustomDialog(SurveyActivity.this);
                    customDialog8.callFunction(
                            tvScore8,
                            getString(R.string.happy_title_8),
                            getString(R.string.happy_description_8),
                            getString(R.string.happy_question_8_1),
                            getString(R.string.happy_question_8_2),
                            getString(R.string.happy_question_8_3));
                    break;
                case R.id.btn_question_9:
                    CustomDialog customDialog9 = new CustomDialog(SurveyActivity.this);
                    customDialog9.callFunction(
                            tvScore9,
                            getString(R.string.happy_title_9),
                            getString(R.string.happy_description_9),
                            getString(R.string.happy_question_9_1),
                            getString(R.string.happy_question_9_2),
                            getString(R.string.happy_question_9_3));
                    break;
                case R.id.btn_question_10:
                    CustomDialog customDialog10 = new CustomDialog(SurveyActivity.this);
                    customDialog10.callFunction(
                            tvScore10,
                            getString(R.string.happy_title_10),
                            getString(R.string.happy_description_10),
                            getString(R.string.happy_question_10_1),
                            getString(R.string.happy_question_10_2),
                            getString(R.string.happy_question_10_3));
                    break;
                default:
                    break;
            }
        }
    };


    private void bindingView() {

        Button btnBack = findViewById(R.id.btn_back);
        btnBack.setOnClickListener(listener);
        Button btnSubmit = findViewById(R.id.btn_submit);
        btnSubmit.setOnClickListener(listener);

        Button btnQuestion1 = findViewById(R.id.btn_question_1);
        btnQuestion1.setOnClickListener(listener);
        Button btnQuestion2 = findViewById(R.id.btn_question_2);
        btnQuestion2.setOnClickListener(listener);
        Button btnQuestion3 = findViewById(R.id.btn_question_3);
        btnQuestion3.setOnClickListener(listener);
        Button btnQuestion4 = findViewById(R.id.btn_question_4);
        btnQuestion4.setOnClickListener(listener);
        Button btnQuestion5 = findViewById(R.id.btn_question_5);
        btnQuestion5.setOnClickListener(listener);
        Button btnQuestion6 = findViewById(R.id.btn_question_6);
        btnQuestion6.setOnClickListener(listener);
        Button btnQuestion7 = findViewById(R.id.btn_question_7);
        btnQuestion7.setOnClickListener(listener);
        Button btnQuestion8 = findViewById(R.id.btn_question_8);
        btnQuestion8.setOnClickListener(listener);
        Button btnQuestion9 = findViewById(R.id.btn_question_9);
        btnQuestion9.setOnClickListener(listener);
        Button btnQuestion10 = findViewById(R.id.btn_question_10);
        btnQuestion10.setOnClickListener(listener);


        tvScore1 = findViewById(R.id.tv_score_1);
        tvScore2 = findViewById(R.id.tv_score_2);
        tvScore3 = findViewById(R.id.tv_score_3);
        tvScore4 = findViewById(R.id.tv_score_4);
        tvScore5 = findViewById(R.id.tv_score_5);
        tvScore6 = findViewById(R.id.tv_score_6);
        tvScore7 = findViewById(R.id.tv_score_7);
        tvScore8 = findViewById(R.id.tv_score_8);
        tvScore9 = findViewById(R.id.tv_score_9);
        tvScore10 = findViewById(R.id.tv_score_10);




    }
}
