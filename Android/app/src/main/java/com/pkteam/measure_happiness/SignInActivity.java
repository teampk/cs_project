package com.pkteam.measure_happiness;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

/**
 * Created by paeng on 16/08/2018.
 */

public class SignInActivity extends AppCompatActivity {
    private TextView tvGotoSignUp;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        bindingView();


    }
    private void bindingView(){
        tvGotoSignUp = findViewById(R.id.tv_goto_sign_up);
        tvGotoSignUp.setOnClickListener(listener);
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

            }
        }
    };
}
