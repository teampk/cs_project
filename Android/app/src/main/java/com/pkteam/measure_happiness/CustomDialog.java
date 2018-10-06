package com.pkteam.measure_happiness;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.pkteam.measure_happiness.view.SurveyActivity;

import de.mateware.snacky.Snacky;


public class CustomDialog {
    private Context context;
    private TextView tvTitle, tvDescription, tvSatisfaction, tvQuestion1, tvQuestion2, tvQuestion3;
    private SeekBar sbSatisfaction;
    private RadioButton rb1a, rb1b, rb1c, rb1d, rb1e, rb2a, rb2b, rb2c, rb2d, rb2e, rb3a, rb3b, rb3c, rb3d, rb3e;

    public CustomDialog(Context context) {
        this.context = context;
    }

    // 호출할 다이얼로그 함수를 정의한다.
    public void callFunction(final TextView tvScore, Button btQuestion, String dialogTitle, String dialogDescription, String dialogQuestion1, String dialogQuestion2, String dialogQuestion3) {

        // 커스텀 다이얼로그를 정의하기위해 Dialog클래스를 생성한다.
        final Dialog dlg = new Dialog(context);

        // 액티비티의 타이틀바를 숨긴다.
        dlg.requestWindowFeature(Window.FEATURE_NO_TITLE);

        // 커스텀 다이얼로그의 레이아웃을 설정한다.
        dlg.setContentView(R.layout.custom_dialog);

        // 커스텀 다이얼로그를 노출한다.
        dlg.show();

        // 커스텀 다이얼로그의 각 위젯들을 정의한다.
        final Button okButton = dlg.findViewById(R.id.okButton);
        final Button cancelButton = dlg.findViewById(R.id.cancelButton);

        bindingView(dlg);

        tvTitle.setText(dialogTitle);
        tvDescription.setText(dialogDescription);
        tvQuestion1.setText(dialogQuestion1);
        tvQuestion2.setText(dialogQuestion2);
        tvQuestion3.setText(dialogQuestion3);

        sbSatisfaction.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                tvSatisfaction.setText(String.valueOf(progress));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });


        okButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (checkSubmit()) {
                    int score = 1;
                    score += calculateScoreRadioButton(rb1a, rb1b, rb1c, rb1d, rb1e);
                    score += calculateScoreRadioButton(rb2a, rb2b, rb2c, rb2d, rb2e);
                    score += calculateScoreRadioButton(rb3a, rb3b, rb3c, rb3d, rb3e);
                    Toast.makeText(context, "중요도=" + tvSatisfaction.getText() + " 만족도=" + score, Toast.LENGTH_SHORT).show();

                    int finalScore = getFinalScore(score);

                    tvScore.setText(String.valueOf(finalScore));
                    btQuestion.setBackgroundColor(context.getResources().getColor(R.color.colorGraphGray));
                    btQuestion.setText("Complete");

                    dlg.dismiss();
                }
            }
        });
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(context, "취소 했습니다.", Toast.LENGTH_SHORT).show();
                // 커스텀 다이얼로그를 종료한다.
                dlg.dismiss();
            }
        });
    }





    private int calculateScoreRadioButton(RadioButton rbA, RadioButton rbB, RadioButton rbC, RadioButton rbD, RadioButton rbE){
        int score=0;

        if (rbA.isChecked()){
            score = 0;
        }else if (rbB.isChecked()){
            score = 8;
        }else if (rbC.isChecked()){
            score = 16;
        }else if (rbD.isChecked()){
            score = 25;
        }else if (rbE.isChecked()){
            score = 33;
        }

        return score;
    }
    private int getFinalScore(int score){
        int finalScore=0;
        if (score < 14){
            finalScore = 1;
        }else if (score < 28){
            finalScore = 2;
        }else if (score < 42){
            finalScore = 3;
        }else if (score < 56){
            finalScore = 4;
        }else if (score < 70){
            finalScore = 5;
        }else if (score < 84){
            finalScore = 6;
        }else if (score <= 100){
            finalScore = 7;
        }
        return finalScore;
    }


    private void bindingView(Dialog dlg){
        tvTitle = dlg.findViewById(R.id.tv_title);
        tvDescription = dlg.findViewById(R.id.tv_description);
        tvSatisfaction = dlg.findViewById(R.id.tv_satisfaction);
        tvQuestion1 = dlg.findViewById(R.id.tv_question_1);
        tvQuestion2 = dlg.findViewById(R.id.tv_question_2);
        tvQuestion3 = dlg.findViewById(R.id.tv_question_3);
        sbSatisfaction = dlg.findViewById(R.id.sb_satisfaction);

        rb1a = dlg.findViewById(R.id.rb_1_a);
        rb1b = dlg.findViewById(R.id.rb_1_b);
        rb1c = dlg.findViewById(R.id.rb_1_c);
        rb1d = dlg.findViewById(R.id.rb_1_d);
        rb1e = dlg.findViewById(R.id.rb_1_e);

        rb2a = dlg.findViewById(R.id.rb_2_a);
        rb2b = dlg.findViewById(R.id.rb_2_b);
        rb2c = dlg.findViewById(R.id.rb_2_c);
        rb2d = dlg.findViewById(R.id.rb_2_d);
        rb2e = dlg.findViewById(R.id.rb_2_e);

        rb3a = dlg.findViewById(R.id.rb_3_a);
        rb3b = dlg.findViewById(R.id.rb_3_b);
        rb3c = dlg.findViewById(R.id.rb_3_c);
        rb3d = dlg.findViewById(R.id.rb_3_d);
        rb3e = dlg.findViewById(R.id.rb_3_e);

    }

    private boolean checkSubmit(){

        if (!rb1a.isChecked() && !rb1b.isChecked() && !rb1c.isChecked() && !rb1d.isChecked() && !rb1e.isChecked()) {
            Toast.makeText(context, "1번 문항을 체크해주세요.", Toast.LENGTH_SHORT).show();
            return false;
        }else if (!rb2a.isChecked() && !rb2b.isChecked() && !rb2c.isChecked() && !rb2d.isChecked() && !rb2e.isChecked()){
            Toast.makeText(context, "2번 문항을 체크해주세요.", Toast.LENGTH_SHORT).show();

            return false;
        }else if (!rb3a.isChecked() && !rb3b.isChecked() && !rb3c.isChecked() && !rb3d.isChecked() && !rb3e.isChecked()){
            Toast.makeText(context, "3번 문항을 체크해주세요.", Toast.LENGTH_SHORT).show();

            return false;
        }


        return true;
    }




}
