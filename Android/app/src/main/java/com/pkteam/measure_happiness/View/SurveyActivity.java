package com.pkteam.measure_happiness.view;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;

import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
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
import com.pkteam.measure_happiness.utils.Constants;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;


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

    Button btnQuestion1,btnQuestion2,btnQuestion3,btnQuestion4,btnQuestion5,
            btnQuestion6,btnQuestion7,btnQuestion8,btnQuestion9,btnQuestion10;

    private CompositeSubscription mSubscriptions;
    private SharedPreferences mSharedPreferences;

    private String mToken, mEmail;




    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_survey);
        mSubscriptions = new CompositeSubscription();
        initSharedPreferences();
        bindingView();


    }
    @Override
    protected void onDestroy(){
        super.onDestroy();
        mSubscriptions.unsubscribe();
    }

    private void initSharedPreferences() {

        mSharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        mToken = mSharedPreferences.getString(Constants.TOKEN,"");
        mEmail = mSharedPreferences.getString(Constants.EMAIL,"");
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

        btnQuestion1 = findViewById(R.id.btn_question_1);
        btnQuestion1.setOnClickListener(listener);
        btnQuestion2 = findViewById(R.id.btn_question_2);
        btnQuestion2.setOnClickListener(listener);
        btnQuestion3 = findViewById(R.id.btn_question_3);
        btnQuestion3.setOnClickListener(listener);
        btnQuestion4 = findViewById(R.id.btn_question_4);
        btnQuestion4.setOnClickListener(listener);
        btnQuestion5 = findViewById(R.id.btn_question_5);
        btnQuestion5.setOnClickListener(listener);
        btnQuestion6 = findViewById(R.id.btn_question_6);
        btnQuestion6.setOnClickListener(listener);
        btnQuestion7 = findViewById(R.id.btn_question_7);
        btnQuestion7.setOnClickListener(listener);
        btnQuestion8 = findViewById(R.id.btn_question_8);
        btnQuestion8.setOnClickListener(listener);
        btnQuestion9 = findViewById(R.id.btn_question_9);
        btnQuestion9.setOnClickListener(listener);
        btnQuestion10 = findViewById(R.id.btn_question_10);
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

    private int getRandomValue(){

        Random generator = new Random();
        return  generator.nextInt(10) + 1;

    }

    private View.OnClickListener listener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.rl_test:
                    tvScore1.setText(String.valueOf(getRandomValue()));
                    tvScore2.setText(String.valueOf(getRandomValue()));
                    tvScore3.setText(String.valueOf(getRandomValue()));
                    tvScore4.setText(String.valueOf(getRandomValue()));
                    tvScore5.setText(String.valueOf(getRandomValue()));
                    tvScore6.setText(String.valueOf(getRandomValue()));
                    tvScore7.setText(String.valueOf(getRandomValue()));
                    tvScore8.setText(String.valueOf(getRandomValue()));
                    tvScore9.setText(String.valueOf(getRandomValue()));
                    tvScore10.setText(String.valueOf(getRandomValue()));

                    btnQuestion1.setBackgroundColor(getResources().getColor(R.color.colorGraphGray));
                    btnQuestion1.setText("Complete");
                    btnQuestion2.setBackgroundColor(getResources().getColor(R.color.colorGraphGray));
                    btnQuestion2.setText("Complete");
                    btnQuestion3.setBackgroundColor(getResources().getColor(R.color.colorGraphGray));
                    btnQuestion3.setText("Complete");
                    btnQuestion4.setBackgroundColor(getResources().getColor(R.color.colorGraphGray));
                    btnQuestion4.setText("Complete");
                    btnQuestion5.setBackgroundColor(getResources().getColor(R.color.colorGraphGray));
                    btnQuestion5.setText("Complete");
                    btnQuestion6.setBackgroundColor(getResources().getColor(R.color.colorGraphGray));
                    btnQuestion6.setText("Complete");
                    btnQuestion7.setBackgroundColor(getResources().getColor(R.color.colorGraphGray));
                    btnQuestion7.setText("Complete");
                    btnQuestion8.setBackgroundColor(getResources().getColor(R.color.colorGraphGray));
                    btnQuestion8.setText("Complete");
                    btnQuestion9.setBackgroundColor(getResources().getColor(R.color.colorGraphGray));
                    btnQuestion9.setText("Complete");
                    btnQuestion10.setBackgroundColor(getResources().getColor(R.color.colorGraphGray));
                    btnQuestion10.setText("Complete");

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

                                                long now = System.currentTimeMillis();
                                                Date mDate = new Date(now);
                                                SimpleDateFormat simpleDate = new SimpleDateFormat("yyyy/MM/dd hh:mm:ss");
                                                String getTime = simpleDate.format(mDate);



                                                Value surveyValue = new Value(mEmail, getTime,
                                                        score1, score2, score3, score4, score5,
                                                        score6, score7, score8, score9, score10);

                                                registerProgress(surveyValue);


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
                            tvScore1, btnQuestion1,
                            getString(R.string.happy_title_1),
                            getString(R.string.happy_description_1),
                            getString(R.string.happy_question_1_1),
                            getString(R.string.happy_question_1_2),
                            getString(R.string.happy_question_1_3));
                    break;
                case R.id.btn_question_2:
                    CustomDialog customDialog2 = new CustomDialog(SurveyActivity.this);
                    customDialog2.callFunction(
                            tvScore2, btnQuestion2,
                            getString(R.string.happy_title_2),
                            getString(R.string.happy_description_2),
                            getString(R.string.happy_question_2_1),
                            getString(R.string.happy_question_2_2),
                            getString(R.string.happy_question_2_3));
                    break;
                case R.id.btn_question_3:
                    CustomDialog customDialog3 = new CustomDialog(SurveyActivity.this);
                    customDialog3.callFunction(
                            tvScore3, btnQuestion3,
                            getString(R.string.happy_title_3),
                            getString(R.string.happy_description_3),
                            getString(R.string.happy_question_3_1),
                            getString(R.string.happy_question_3_2),
                            getString(R.string.happy_question_3_3));
                    break;
                case R.id.btn_question_4:
                    CustomDialog customDialog4 = new CustomDialog(SurveyActivity.this);
                    customDialog4.callFunction(
                            tvScore4, btnQuestion4,
                            getString(R.string.happy_title_4),
                            getString(R.string.happy_description_4),
                            getString(R.string.happy_question_4_1),
                            getString(R.string.happy_question_4_2),
                            getString(R.string.happy_question_4_3));
                    break;
                case R.id.btn_question_5:
                    CustomDialog customDialog5 = new CustomDialog(SurveyActivity.this);
                    customDialog5.callFunction(
                            tvScore5, btnQuestion5,
                            getString(R.string.happy_title_5),
                            getString(R.string.happy_description_5),
                            getString(R.string.happy_question_5_1),
                            getString(R.string.happy_question_5_2),
                            getString(R.string.happy_question_5_3));
                    break;
                case R.id.btn_question_6:
                    CustomDialog customDialog6 = new CustomDialog(SurveyActivity.this);
                    customDialog6.callFunction(
                            tvScore6, btnQuestion6,
                            getString(R.string.happy_title_6),
                            getString(R.string.happy_description_6),
                            getString(R.string.happy_question_6_1),
                            getString(R.string.happy_question_6_2),
                            getString(R.string.happy_question_6_3));
                    break;
                case R.id.btn_question_7:
                    CustomDialog customDialog7 = new CustomDialog(SurveyActivity.this);
                    customDialog7.callFunction(
                            tvScore7, btnQuestion7,
                            getString(R.string.happy_title_7),
                            getString(R.string.happy_description_7),
                            getString(R.string.happy_question_7_1),
                            getString(R.string.happy_question_7_2),
                            getString(R.string.happy_question_7_3));
                    break;
                case R.id.btn_question_8:
                    CustomDialog customDialog8 = new CustomDialog(SurveyActivity.this);
                    customDialog8.callFunction(
                            tvScore8, btnQuestion8,
                            getString(R.string.happy_title_8),
                            getString(R.string.happy_description_8),
                            getString(R.string.happy_question_8_1),
                            getString(R.string.happy_question_8_2),
                            getString(R.string.happy_question_8_3));
                    break;
                case R.id.btn_question_9:
                    CustomDialog customDialog9 = new CustomDialog(SurveyActivity.this);
                    customDialog9.callFunction(
                            tvScore9, btnQuestion9,
                            getString(R.string.happy_title_9),
                            getString(R.string.happy_description_9),
                            getString(R.string.happy_question_9_1),
                            getString(R.string.happy_question_9_2),
                            getString(R.string.happy_question_9_3));
                    break;
                case R.id.btn_question_10:
                    CustomDialog customDialog10 = new CustomDialog(SurveyActivity.this);
                    customDialog10.callFunction(
                            tvScore10, btnQuestion10,
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
