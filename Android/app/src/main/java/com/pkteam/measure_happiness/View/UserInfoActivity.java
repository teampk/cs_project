package com.pkteam.measure_happiness.view;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.pkteam.measure_happiness.R;
import com.pkteam.measure_happiness.model.Res;
import com.pkteam.measure_happiness.model.User;
import com.pkteam.measure_happiness.network.NetworkUtil;
import com.pkteam.measure_happiness.utils.Constants;

import java.io.IOException;

import de.mateware.snacky.Snacky;
import retrofit2.adapter.rxjava.HttpException;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by paeng on 27/08/2018.
 */

public class UserInfoActivity extends AppCompatActivity {

    private CompositeSubscription mSubscriptions;
    private SharedPreferences mSharedPreferences;

    private String mToken, mEmail;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_info);
        mSubscriptions = new CompositeSubscription();
        initSharedPreferences();
        bindingView();
        loadData();


    }
    private void initSharedPreferences() {
        mSharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        mToken = mSharedPreferences.getString(Constants.TOKEN,"");
        mEmail = mSharedPreferences.getString(Constants.EMAIL,"");
    }
    private void bindingView(){
        Button btnBack = findViewById(R.id.btn_back);
        btnBack.setOnClickListener(listener);
    }
    private void loadData(){
        mSubscriptions.add(NetworkUtil.getRetrofit(mToken).getProfile(mEmail)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(this::handleResponse, this::handleError));
    }

    private void handleResponse(User user){

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


    private View.OnClickListener listener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.btn_back:
                    finish();
                    break;
            }
        }
    };

    @Override
    protected void onDestroy(){
        super.onDestroy();
        mSubscriptions.unsubscribe();
    }
}
