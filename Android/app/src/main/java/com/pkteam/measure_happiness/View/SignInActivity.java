package com.pkteam.measure_happiness.view;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.pkteam.measure_happiness.R;
import com.pkteam.measure_happiness.model.Res;
import com.pkteam.measure_happiness.network.NetworkUtil;
import com.pkteam.measure_happiness.utils.Constants;

import java.io.IOException;

import de.mateware.snacky.Snacky;
import retrofit2.adapter.rxjava.HttpException;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by paeng on 16/08/2018.
 */

public class SignInActivity extends AppCompatActivity {
    private TextView tvGotoSignUp;
    private EditText etId, etPw;
    private ProgressBar mProgressBar;
    private CompositeSubscription mSubscriptions;
    private SharedPreferences mSharedPreferences;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        bindingView();
        mSubscriptions = new CompositeSubscription();
        initSharedPreferences();
    }

    @Override
    protected void onDestroy(){
        super.onDestroy();
        mSubscriptions.unsubscribe();
    }

    private void initSharedPreferences() {
        mSharedPreferences = PreferenceManager.getDefaultSharedPreferences(SignInActivity.this);
    }


    private void bindingView(){
        tvGotoSignUp = findViewById(R.id.tv_goto_sign_up);
        tvGotoSignUp.setOnClickListener(listener);
        etId = findViewById(R.id.et_id);
        etPw = findViewById(R.id.et_pw);
        Button btSignIn = findViewById(R.id.bt_sign_in);
        btSignIn.setOnClickListener(listener);
        mProgressBar = findViewById(R.id.progress_bar);
    }

    private void loginProcess(String email, String password) {

        mSubscriptions.add(NetworkUtil.getRetrofit(email, password).login()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(this::handleResponse,this::handleError));
    }

    private void handleResponse(Res response) {

        mProgressBar.setVisibility(View.GONE);

        SharedPreferences.Editor editor = mSharedPreferences.edit();
        editor.putString(Constants.TOKEN,response.getToken());
        editor.putString(Constants.EMAIL,response.getMessage());
        editor.apply();
        Intent intentMain = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(intentMain);
        etId.setText(null);
        etPw.setText(null);
        finish();
    }

    private void handleError(Throwable error) {

        mProgressBar.setVisibility(View.GONE);

        if (error instanceof HttpException) {

            Gson gson = new GsonBuilder().create();

            try {
                String errorBody = ((HttpException) error).response().errorBody().string();
                Res response = gson.fromJson(errorBody,Res.class);
                showToastMessage(response.getMessage());

            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            showSnackBarMessage("네트워크 상태를 확인해주세요!");
        }
    }
    long pressTime;
    @Override
    public void onBackPressed() {

        if(System.currentTimeMillis() - pressTime <2000){
            finish();
            return;
        }
        showSnackBarMessage("한번 더 누르시면 앱이 종료됩니다.");
        pressTime = System.currentTimeMillis();

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

    private void showToastMessage(String message){
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    private View.OnClickListener listener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            switch (view.getId()){
                case R.id.tv_goto_sign_up:
                    Intent intentSignUp = new Intent(getApplicationContext(), SignUpActivity.class);
                    startActivity(intentSignUp);
                    break;
                case R.id.bt_sign_in:
                    String id = etId.getText().toString();
                    String pw = etPw.getText().toString();
                    mProgressBar.setVisibility(View.VISIBLE);
                    loginProcess(id,pw);
                    break;

                default:

                    break;
            }
        }
    };
}
