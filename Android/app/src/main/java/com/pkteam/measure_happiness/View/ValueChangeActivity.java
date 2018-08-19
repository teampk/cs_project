package com.pkteam.measure_happiness.View;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.pkteam.measure_happiness.R;

/*
 * Created by paeng on 18/08/2018.
 */

public class ValueChangeActivity extends AppCompatActivity {

    private Button btnBack;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_value_change);
        bindingView();


    }
    private void bindingView(){

        btnBack = findViewById(R.id.btn_back);
        btnBack.setOnClickListener(listener);

    }

    Button.OnClickListener listener = new Button.OnClickListener(){

        @Override
        public void onClick(View v) {
            switch(v.getId()){
                case R.id.btn_back:
                    finish();

                    break;


            }
        }
    };
}
