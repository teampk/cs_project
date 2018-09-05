package com.pkteam.measure_happiness.View;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;

import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.pkteam.measure_happiness.CustomDialog;
import com.pkteam.measure_happiness.R;

/**
 * Created by paeng on 2018. 8. 1..
 */

public class SurveyActivity extends AppCompatActivity {
    private Button btnBack, btnSubmit;
    private CustomDialog mCustomDialog;
    private Button btnQuestion1, btnQuestion2, btnQuestion3, btnQuestion4, btnQuestion5,
            btnQuestion6, btnQuestion7, btnQuestion8, btnQuestion9, btnQuestion10;

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
                            getString(R.string.happy_title_1),
                            getString(R.string.happy_description_1),
                            getString(R.string.happy_question_1_1),
                            getString(R.string.happy_question_1_2),
                            getString(R.string.happy_question_1_3));
                    break;
                case R.id.btn_question_2:
                    CustomDialog customDialog2 = new CustomDialog(SurveyActivity.this);
                    customDialog2.callFunction(
                            getString(R.string.happy_title_2),
                            getString(R.string.happy_description_2),
                            getString(R.string.happy_question_2_1),
                            getString(R.string.happy_question_2_2),
                            getString(R.string.happy_question_2_3));
                    break;
                case R.id.btn_question_3:
                    CustomDialog customDialog3 = new CustomDialog(SurveyActivity.this);
                    customDialog3.callFunction(
                            getString(R.string.happy_title_3),
                            getString(R.string.happy_description_3),
                            getString(R.string.happy_question_3_1),
                            getString(R.string.happy_question_3_2),
                            getString(R.string.happy_question_3_3));
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

        btnQuestion1 = findViewById(R.id.btn_question_1);
        btnQuestion1.setOnClickListener(listener);
        btnQuestion2 = findViewById(R.id.btn_question_2);
        btnQuestion2.setOnClickListener(listener);
        btnQuestion3 = findViewById(R.id.btn_question_3);
        btnQuestion3.setOnClickListener(listener);




    }
}
