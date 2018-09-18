package com.pkteam.measure_happiness.View;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.pkteam.measure_happiness.R;

import java.util.Calendar;

/*
 * Created by paeng on 16/08/2018.
 */

public class SignUpActivity extends AppCompatActivity {
    private Button btnMale, btnFemale;
    private TextView tvBirth;

    private EditText etId, etPw, etName, etPw2;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        bindingView();
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

                case R.id.btn_submit:
                    if (checkJoin()){
                        Intent intentSubmit = new Intent(getApplicationContext(), SignInActivity.class);
                        startActivity(intentSubmit);
                        finish();
                    }



                    break;

            }
        }
    };

    private boolean checkJoin(){
        if (etId.getText().toString().matches("")){
            Toast.makeText(this, "아이디를 입력해주세요.", Toast.LENGTH_SHORT).show();
        }else if (etPw.getText().toString().matches("")){
            Toast.makeText(this, "패스워드를 입력해주세요.", Toast.LENGTH_SHORT).show();
        }else if (etPw2.getText().toString().matches("")){
            Toast.makeText(this, "두번째 패스워드를 입력해주세요.", Toast.LENGTH_SHORT).show();
        }else if (etPw.getText().toString().matches(etPw2.getText().toString())){
            Toast.makeText(this, "패스워드가 일치하지 않습니다.", Toast.LENGTH_SHORT).show();
        }else if (etName.getText().toString().matches("")){
            Toast.makeText(this, "이름을 입력해주세요.", Toast.LENGTH_SHORT).show();
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
