package org.t_robop.masatsuna.timer;

import android.content.DialogInterface;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.CountDownTimer;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.NumberPicker;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    Button button1,button2; //スタートボタンとリセットボタン

    //設定時間
    long setMinute = 0;
    long setSecond = 0;

    //時間保持
    long minute = 0;
    long second = 0;
    long timerTime = 0;

    boolean move = false;   //タイマーが動作しているかを記憶

    MyCountDownTimer cdt;

    TextView textView;

    NumberPicker numberPicker1,numberPicker2;

    SoundPool soundPool;
    int soundId;

    //テキストをtTimeに取得して、分と秒をsTimeに格納
    String tTime;
    String[] sTime;

    //取得したテキストをlong型で格納
    long lMinute;
    long lSecond;

    //取得したテキストをint型で格納
    int iMinute;
    int iSecond;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textView = (TextView) findViewById(R.id.time);

        button1 = (Button) findViewById(R.id.start_stop);
        button2 = (Button) findViewById(R.id.reset);

        soundPool = new SoundPool(1, AudioManager.STREAM_MUSIC,0);
        soundId = soundPool.load(this,R.raw.alarm,1);

        //スタート・ストップボタンの処理
        button1.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View view) {

                tTime = textView.getText().toString();
                sTime = tTime.split(":",0);

                lMinute = Long.parseLong(sTime[0]);
                lSecond = Long.parseLong(sTime[1]);

                //スタートボタンの処理
                if (move == false) {

                    //0.5秒毎に処理するカウントダウンタイマーを起動
                    cdt = new MyCountDownTimer(timerTime, 500);
                    button1.setText("ストップ");
                    move = true;
                    cdt.start();

                //ストップボタンの処理
                } else {

                    memoTime(lMinute,lSecond);
                    button1.setText("スタート");
                    move = false;
                    cdt.cancel();
                }
            }
        });

        //リセットボタンの処理
        button2.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                memoTime(setMinute,setSecond);  //設定時間に戻す

                //タイマーが動いていたら、止めてから処理する
                if (move = true) {

                    button1.setText("スタート");
                    move = false;
                    cdt.cancel();
                }

                //テキストに設定時間をセット
                textView.setText(String.format("%02d",setMinute) + ":" + String.format("%02d",setSecond));
            }


        });
    }

    //時間設定のダイアログ
    public void setTime(View view) {

        tTime = textView.getText().toString();
        sTime = tTime.split(":",0);

        lMinute = Long.parseLong(sTime[0]);
        lSecond = Long.parseLong(sTime[1]);

        iMinute = Integer.parseInt(sTime[0]);
        iSecond = Integer.parseInt(sTime[1]);

        //タイマーが動いていたら時間を記憶して止める
        if (move == true) {

            memoTime(lMinute, lSecond);
            button1.setText("スタート");
            move = false;
            cdt.cancel();
        }

        //LayoutInflaterを使って、ダイアログのレイアウトを取得
        LayoutInflater inflater = LayoutInflater.from(MainActivity.this);
        View view2 = inflater.inflate(R.layout.set_time,null,false);

        numberPicker1 = (NumberPicker) view2.findViewById(R.id.setMinute);
        numberPicker2 = (NumberPicker) view2.findViewById(R.id.setSecond);

        numberPicker1.setMaxValue(59);
        numberPicker2.setMaxValue(59);

        //現在の時間からNumberPickerで選択できるようにする
        numberPicker1.setValue(iMinute);
        numberPicker2.setValue(iSecond);

        //ダイアログ作成
        AlertDialog.Builder alertDlg = new AlertDialog.Builder(this);
        alertDlg.setTitle("設定時間");
        alertDlg.setPositiveButton(
                        "OK",
        new DialogInterface.OnClickListener() {

            //OKボタンの処理
            public void onClick(DialogInterface dialog, int which) {

                //NumberPickerの値を設定時間にセット
                setMinute = numberPicker1.getValue();
                setSecond = numberPicker2.getValue();

                //現在の時間を設定時間にする
                memoTime(setMinute, setSecond);

                textView.setText(String.format("%02d",minute) + ":" + String.format("%02d",second));
            }
        });

        //キャンセルボタンの処理
        alertDlg.setNegativeButton("キャンセル", null);

        //view2のLayoutをダイアログにセット
        alertDlg.setView(view2);
        alertDlg.create().show();
    }

    //時間の記憶
    public void memoTime(long min,long sec) {

        minute = min;
        second = sec;
        timerTime = minute * 60000 + second * 1000;
    }


    //CountDownTimerの継承クラス
    public class MyCountDownTimer extends CountDownTimer {

        public MyCountDownTimer(long millisInFuture,long countDownInterval){
            super(millisInFuture, countDownInterval);
        }

        //カウントダウン終了後の処理
        @Override
        public void onFinish() {
            soundPool.play(soundId,1,1,0,0,1);
        }

        //インターバル毎の処理
        @Override
        public void onTick(long millisUntilFinished){

            //残り時間
            long sMinute = millisUntilFinished/ 1000 / 60;
            long sSecond = millisUntilFinished / 1000 % 60;

            // インターバル毎に時間をセット
            textView.setText(String.format("%02d", sMinute) + ":" + String.format("%02d", sSecond));
        }
    }
}