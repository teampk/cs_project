package com.pkteam.measure_happiness.View;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;

import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.pkteam.measure_happiness.CustomDialog;
import com.pkteam.measure_happiness.R;
import com.pkteam.measure_happiness.model.Res;
import com.pkteam.measure_happiness.model.Value;
import com.pkteam.measure_happiness.network.NetworkUtil;

import java.io.IOException;

import retrofit2.adapter.rxjava.HttpException;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by paeng on 2018. 8. 1..
 */

public class SurveyActivity extends AppCompatActivity {
    private TextView tvScore1, tvScore2, tvScore3, tvScore4, tvScore5,
            tvScore6, tvScore7, tvScore8, tvScore9, tvScore10;

    private CompositeSubscription mSubscriptions;




    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_survey);
        mSubscriptions = new CompositeSubscription();
        bindingView();


    }
    @Override
    protected void onDestroy(){
        super.onDestroy();
        mSubscriptions.unsubscribe();
    }


    private void registerProgress(Value surveyValue) {

        mSubscriptions.add(NetworkUtil.getRetrofit().registerValue(surveyValue)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(this::handleResponse,this::handleError));
    }

    private void handleResponse(Res response) {

        showMessage(response.getMessage());
    }

    private void handleError(Throwable error) {

        if (error instanceof HttpException) {
            Gson gson = new GsonBuilder().create();
            try {
                String errorBody = ((HttpException) error).response().errorBody().string();
                Res response = gson.fromJson(errorBody,Res.class);
                showMessage(response.getMessage());
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            showMessage("Network Error! 내트워크 연결을 확인하세요.");
        }
    }
    private void showMessage(String message){
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    private boolean registerCheck(){

        if (surveyNotComplete()){
            Toast.makeText(this, "설문을 완료해주세요.", Toast.LENGTH_SHORT).show();
            return false;
        }
        else{
            return true;
        }
    }

    private boolean surveyNotComplete(){

        return tvScore1.getText().toString().equals("0") ||
                tvScore2.getText().toString().equals("0") ||
                tvScore3.getText().toString().equals("0") ||
                tvScore4.getText().toString().equals("0") ||
                tvScore5.getText().toString().equals("0") ||
                tvScore6.getText().toString().equals("0") ||
                tvScore7.getText().toString().equals("0") ||
                tvScore8.getText().toString().equals("0") ||
                tvScore9.getText().toString().equals("0") ||
                tvScore10.getText().toString().equals("0");
    }

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

        RelativeLayout rlTest = findViewById(R.id.rl_test);
        rlTest.setOnClickListener(listener);




    }

    private View.OnClickListener listener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.rl_test:
                    tvScore1.setText("1");
                    tvScore2.setText("4");
                    tvScore3.setText("3");
                    tvScore4.setText("2");
                    tvScore5.setText("5");
                    tvScore6.setText("5");
                    tvScore7.setText("9");
                    tvScore8.setText("10");
                    tvScore9.setText("2");
                    tvScore10.setText("3");
                    break;
                case R.id.btn_back:
                    finish();
                    break;
                case R.id.btn_submit:

                    AlertDialog.Builder builder = new AlertDialog.Builder(SurveyActivity.this);
                    builder.setTitle("설문을 완료하셨나요?")
                            .setPositiveButton("예",
                                    new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int which) {

                                            if(registerCheck()){
                                                // 점수 데이터베이스에 입력
                                                String score1 = tvScore1.getText().toString();
                                                String score2 = tvScore2.getText().toString();
                                                String score3 = tvScore3.getText().toString();
                                                String score4 = tvScore4.getText().toString();
                                                String score5 = tvScore5.getText().toString();
                                                String score6 = tvScore6.getText().toString();
                                                String score7 = tvScore7.getText().toString();
                                                String score8 = tvScore8.getText().toString();
                                                String score9 = tvScore9.getText().toString();
                                                String score10 = tvScore10.getText().toString();


                                                Value surveyValue = new Value("nuggy875@naver.com", "201809150900",
                                                        score1, score2, score3, score4, score5,
                                                        score6, score7, score8, score9, score10);

                                                registerProgress(surveyValue);

                                                Toast.makeText(SurveyActivity.this, "완료.", Toast.LENGTH_SHORT).show();

                                                finish();
                                            }


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
}
