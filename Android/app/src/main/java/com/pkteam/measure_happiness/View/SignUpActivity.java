package com.pkteam.measure_happiness.view;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.pkteam.measure_happiness.R;
import com.pkteam.measure_happiness.model.Res;
import com.pkteam.measure_happiness.model.User;
import com.pkteam.measure_happiness.network.NetworkUtil;

import java.io.IOException;
import java.util.Calendar;

import retrofit2.adapter.rxjava.HttpException;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

/*
 * Created by paeng on 16/08/2018.
 */

public class SignUpActivity extends AppCompatActivity {
    private Button btnMale, btnFemale;
    private TextView tvBirth;

    private EditText etId, etPw, etName, etPw2;
    private Spinner spDepartment;

    private boolean isMale, genderChecked, birthChecked;
    private CompositeSubscription mSubscriptions;

    private ProgressBar mProgressbar;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        mSubscriptions = new CompositeSubscription();
        bindingView();
    }
    @Override
    public void onDestroy(){
        super.onDestroy();
        mSubscriptions.unsubscribe();
    }
    private void bindingView(){

        etId = findViewById(R.id.et_id);
        etPw = findViewById(R.id.et_pw);
        etPw2 = findViewById(R.id.et_pw2);
        etName = findViewById(R.id.et_name);

        btnMale = findViewById(R.id.btn_male);
        btnFemale = findViewById(R.id.btn_female);
        tvBirth = findViewById(R.id.tv_birth);
        TextView tvGotoSignIn = findViewById(R.id.tv_goto_sign_in);

        btnMale.setOnClickListener(listener);
        btnFemale.setOnClickListener(listener);
        tvBirth.setOnClickListener(listener);
        tvGotoSignIn.setOnClickListener(listener);
        Button btnSubmit = findViewById(R.id.btn_submit);
        btnSubmit.setOnClickListener(listener);

        spDepartment = findViewById(R.id.sp_department);

        mProgressbar = findViewById(R.id.progress);

        genderChecked = false;
        isMale = true;
        birthChecked = false;
    }

    private View.OnClickListener listener = new View.OnClickListener(){
        @Override
        public void onClick(View view) {
            switch(view.getId()){
                case R.id.btn_male:
                    btnMale.setBackground(getDrawable(R.drawable.button_blue_round));
                    btnFemale.setBackground(getDrawable(R.drawable.button_gray_round));
                    genderChecked = true;
                    isMale = true;
                    break;

                case R.id.btn_female:
                    btnMale.setBackground(getDrawable(R.drawable.button_gray_round));
                    btnFemale.setBackground(getDrawable(R.drawable.button_red_round));
                    genderChecked = true;
                    isMale = false;
                    break;

                case R.id.tv_birth:
                    Calendar calendarStart = Calendar.getInstance();
                    int todayYear = calendarStart.get(Calendar.YEAR);
                    int todayMonth = calendarStart.get(Calendar.MONTH);
                    int todayDay = calendarStart.get(Calendar.DAY_OF_MONTH);
                    DatePickerDialog dialog = new DatePickerDialog(SignUpActivity.this, listenerDate, todayYear, todayMonth, todayDay);
                    dialog.show();
                    break;

                case R.id.tv_goto_sign_in:
                    Intent intentSignIn = new Intent(getApplicationContext(), SignInActivity.class);
                    startActivity(intentSignIn);
                    finish();
                    break;

                case R.id.btn_submit:
                    if (checkJoin()){

                        User userDB = new User();
                        userDB.setmId(etId.getText().toString());
                        userDB.setmPassword(etPw.getText().toString());
                        userDB.setmName(etName.getText().toString());
                        if (isMale){
                            userDB.setmGender("Male");
                        }else{
                            userDB.setmGender("Female");
                        }
                        userDB.setmBirth(tvBirth.getText().toString());
                        userDB.setmDepartment(spDepartment.getSelectedItem().toString());
                        mProgressbar.setVisibility(View.VISIBLE);

                        registerProcess(userDB);

                        finish();
                    }

                    break;

            }
        }
    };

    private void registerProcess(User user) {

        mSubscriptions.add(NetworkUtil.getRetrofit().register(user)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(this::handleResponse,this::handleError));
    }
    private void handleResponse(Res response) {

        mProgressbar.setVisibility(View.GONE);
        showSnackBarMessage(response.getMessage());
    }

    private void handleError(Throwable error) {

        mProgressbar.setVisibility(View.GONE);

        if (error instanceof HttpException) {

            Gson gson = new GsonBuilder().create();

            try {

                String errorBody = ((HttpException) error).response().errorBody().string();
                Res response = gson.fromJson(errorBody,Res.class);
                showSnackBarMessage(response.getMessage());

            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {

            showSnackBarMessage("Network Error !");
        }
    }

    private void showSnackBarMessage(String message) {

        Toast.makeText(SignUpActivity.this, message, Toast.LENGTH_SHORT).show();


    }



    private boolean checkJoin(){
        if (etId.getText().toString().matches("")){
            Toast.makeText(this, "아이디를 입력해주세요.", Toast.LENGTH_SHORT).show();
            return false;
        }else if (etPw.getText().toString().matches("")){
            Toast.makeText(this, "패스워드를 입력해주세요.", Toast.LENGTH_SHORT).show();
            return false;
        }else if (etPw2.getText().toString().matches("")){
            Toast.makeText(this, "두번째 패스워드를 입력해주세요.", Toast.LENGTH_SHORT).show();
            return false;
        }else if (!etPw.getText().toString().matches(etPw2.getText().toString())){
            Toast.makeText(this, "패스워드가 일치하지 않습니다.", Toast.LENGTH_SHORT).show();
            return false;
        }else if (etName.getText().toString().matches("")){
            Toast.makeText(this, "이름을 입력해주세요.", Toast.LENGTH_SHORT).show();
            return false;
        }else if (!genderChecked){
            Toast.makeText(this, "성별을 선택해주세요.", Toast.LENGTH_SHORT).show();
            return false;
        }else if (!birthChecked){
            Toast.makeText(this, "생년월일을 선택해주세요.", Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;
    }

    private DatePickerDialog.OnDateSetListener listenerDate = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            monthOfYear++;
            Toast.makeText(getApplicationContext(), year + "." + monthOfYear + "." + dayOfMonth, Toast.LENGTH_SHORT).show();


            tvBirth.setText(year+"."+monthOfYear+"."+dayOfMonth);
            tvBirth.setTextColor(getColor(R.color.colorTextBlack));
            birthChecked = true;

            String todoStartDateMonth, todoStartDateDay;
            if (monthOfYear>0 && monthOfYear<10){
                todoStartDateMonth = "0" + Integer.toString(monthOfYear);
            }else{
                todoStartDateMonth = Integer.toString(monthOfYear);
            }
            if (dayOfMonth>0 && dayOfMonth<10){
                todoStartDateDay = "0" + Integer.toString(dayOfMonth);
            }else{
                todoStartDateDay = Integer.toString(dayOfMonth);
            }
            // todoStartDate = Integer.toString(year)+"."+todoStartDateMonth+"."+todoStartDateDay;



        }
    };
}
