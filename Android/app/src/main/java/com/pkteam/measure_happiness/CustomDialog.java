package com.pkteam.measure_happiness;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;


public class CustomDialog {
    private Context context;
    private TextView tvTitle, tvDescription, tvSatisfaction, tvQuestion1, tvQuestion2, tvQuestion3;
    private SeekBar sbSatisfaction;

    public CustomDialog(Context context) {
        this.context = context;
    }

    // 호출할 다이얼로그 함수를 정의한다.
    public void callFunction(String dialogTitle, String dialogDescription, String dialogQuestion1, String dialogQuestion2, String dialogQuestion3) {

        // 커스텀 다이얼로그를 정의하기위해 Dialog클래스를 생성한다.
        final Dialog dlg = new Dialog(context);

        // 액티비티의 타이틀바를 숨긴다.
        dlg.requestWindowFeature(Window.FEATURE_NO_TITLE);

        // 커스텀 다이얼로그의 레이아웃을 설정한다.
        dlg.setContentView(R.layout.custom_dialog);

        // 커스텀 다이얼로그를 노출한다.
        dlg.show();

        // 커스텀 다이얼로그의 각 위젯들을 정의한다.
        final EditText message = dlg.findViewById(R.id.mesgase);
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
                // '확인' 버튼 클릭시 메인 액티비티에서 설정한 main_label에
                // 커스텀 다이얼로그에서 입력한 메시지를 대입한다.
                // 커스텀 다이얼로그를 종료한다.
                dlg.dismiss();
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

    private void bindingView(Dialog dlg){
        tvTitle = dlg.findViewById(R.id.tv_title);
        tvDescription = dlg.findViewById(R.id.tv_description);
        tvSatisfaction = dlg.findViewById(R.id.tv_satisfaction);
        tvQuestion1 = dlg.findViewById(R.id.tv_question_1);
        tvQuestion2 = dlg.findViewById(R.id.tv_question_2);
        tvQuestion3 = dlg.findViewById(R.id.tv_question_3);
        sbSatisfaction = dlg.findViewById(R.id.sb_satisfaction);
    }


}
