package com.pkteam.measure_happiness.View;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.pkteam.measure_happiness.R;

/**
 * Created by paeng on 16/08/2018.
 */

public class SignInActivity extends AppCompatActivity {
    private TextView tvGotoSignUp;
    private EditText etId, etPw;




    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        bindingView();


    }
    private void bindingView(){
        tvGotoSignUp = findViewById(R.id.tv_goto_sign_up);
        tvGotoSignUp.setOnClickListener(listener);
        etId = findViewById(R.id.et_id);
        etPw = findViewById(R.id.et_pw);
        Button btSignIn = findViewById(R.id.bt_sign_in);
        btSignIn.setOnClickListener(listener);
    }

    private View.OnClickListener listener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            switch (view.getId()){
                case R.id.tv_goto_sign_up:
                    Intent intentSignUp = new Intent(getApplicationContext(), SignUpActivity.class);
                    startActivity(intentSignUp);
                    finish();
                    break;
                case R.id.bt_sign_in:
                    if(checkSignIn()){
                        Intent intentMain = new Intent(getApplicationContext(), MainActivity.class);
                        startActivity(intentMain);
                        finish();
                    }

                    break;

                default:


                    break;
            }
        }
    };
    private boolean checkSignIn(){
        if(etId.getText().toString().equals("nugg")){
            Toast.makeText(this, "Check your Id or Password", Toast.LENGTH_SHORT).show();
            return false;
        }else{
            return true;
        }
    }
}
