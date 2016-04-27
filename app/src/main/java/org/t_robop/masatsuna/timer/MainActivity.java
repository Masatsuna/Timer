package org.t_robop.masatsuna.timer;

import android.os.CountDownTimer;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.NumberPicker;
import android.widget.TextView;
import android.widget.TimePicker;

public class MainActivity extends AppCompatActivity {

    Button button1,button3;

    //初期設定時間
    long setMinute = 0;
    long setSecond = 20;

    //現在の残り時間
    long minute = setMinute;
    long second = setSecond;
    long timerTime = minute * 60000 + second * 1000;

    boolean judge = false;
    boolean move = false;

    MyCountDownTimer cdt;

    TextView textView1, textView2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textView1 = (TextView) findViewById(R.id.tMinute);
        textView2 = (TextView) findViewById(R.id.tSecond);

        button1 = (Button) findViewById(R.id.start_stop);
        button3 = (Button) findViewById(R.id.reset);

        button1.setText("スタート");


        //スタート・ストップボタンの処理
        button1.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View view) {

                if (judge == false) {

                    cdt = new MyCountDownTimer(timerTime, 500);

                    button1.setText("ストップ");

                    judge = true;
                    move = true;
                    cdt.start();

                } else {

                    //テキストを取得して、時間を更新
                    timerTime = minute * 60000 + second * 1000;

                    button1.setText("スタート");

                    judge = false;
                    move = false;
                    cdt.cancel();

                }

            }
        });

        //リセットボタンの処理
        button3.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                minute = setMinute;
                second = setSecond;
                timerTime = minute * 60000 + second * 1000;

                if (move = true) {

                    button1.setText("スタート");

                    judge = false;
                    move = false;
                    cdt.cancel();

                }

                //分の数字が１桁なら０をつけて表示する
                if (setMinute < 10) {


                } else {

                }

                //秒の数字が１桁なら０をつけて表示する
                if (setSecond < 10) {


                } else {


                }

            }


        });
    }

 /*-----------------------------------------------------------------------------------------*/

    //CountDownTimerの継承クラス
    public class MyCountDownTimer extends CountDownTimer {

        public MyCountDownTimer(long millisInFuture,long countDownInterval){
            super(millisInFuture, countDownInterval);
            timerTime = millisInFuture;
        }

        //カウントダウン終了後の処理
        @Override
        public void onFinish() {


        }

        //インターバル毎の処理
        @Override
        public void onTick(long millisUntilFinished){

            long minute = millisUntilFinished/ 1000 / 60;
            long second = millisUntilFinished / 1000 % 60;

            // インターバル(countDownInterval)毎に呼ばれる
            if (minute < 10) {


            } else {


            }

            if (second < 10) {


            } else {


            }

        }

    }

}