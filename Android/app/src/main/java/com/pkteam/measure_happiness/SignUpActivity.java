package com.pkteam.measure_happiness;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;

/*
 * Created by paeng on 16/08/2018.
 */

public class SignUpActivity extends AppCompatActivity {
    private Button btnMale, btnFemale;
    private TextView tvBirth, tvGotoSignIn;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        bindingView();
    }
    private void bindingView(){

        btnMale = findViewById(R.id.btn_male);
        btnFemale = findViewById(R.id.btn_female);
        tvBirth = findViewById(R.id.tv_birth);
        tvGotoSignIn = findViewById(R.id.tv_goto_sign_in);

        btnMale.setOnClickListener(listener);
        btnFemale.setOnClickListener(listener);
        tvBirth.setOnClickListener(listener);
        tvGotoSignIn.setOnClickListener(listener);
    }

    private View.OnClickListener listener = new View.OnClickListener(){
        @Override
        public void onClick(View view) {
            switch(view.getId()){
                case R.id.btn_male:
                    btnMale.setBackground(getDrawable(R.drawable.button_blue_round));
                    btnFemale.setBackground(getDrawable(R.drawable.button_gray_round));
                    break;
                case R.id.btn_female:
                    btnMale.setBackground(getDrawable(R.drawable.button_gray_round));
                    btnFemale.setBackground(getDrawable(R.drawable.button_red_round));
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

            }
        }
    };

    private DatePickerDialog.OnDateSetListener listenerDate = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            monthOfYear++;
            Toast.makeText(getApplicationContext(), year + "." + monthOfYear + "." + dayOfMonth, Toast.LENGTH_SHORT).show();


            tvBirth.setText(year+"."+monthOfYear+"."+dayOfMonth);
            tvBirth.setTextColor(getColor(R.color.colorTextBlack));

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
