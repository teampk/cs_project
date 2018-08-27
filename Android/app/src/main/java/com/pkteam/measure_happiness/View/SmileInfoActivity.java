package com.pkteam.measure_happiness.View;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.pkteam.measure_happiness.R;

/**
 * Created by paeng on 27/08/2018.
 */

public class SmileInfoActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_smile_info);
        bindingView();
    }
    private void bindingView(){
        Button btnBack = findViewById(R.id.btn_back);
        btnBack.setOnClickListener(listener);
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
}
