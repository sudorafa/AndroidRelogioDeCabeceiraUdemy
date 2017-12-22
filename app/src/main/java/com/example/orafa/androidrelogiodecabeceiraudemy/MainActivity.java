package com.example.orafa.androidrelogiodecabeceiraudemy;

import android.os.Handler;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.CheckBox;
import android.widget.TextView;

import java.util.Calendar;

import butterknife.BindView;
import butterknife.ButterKnife;


public class MainActivity extends AppCompatActivity {

    private Handler mHandler = new Handler();
    private Runnable mRunnable;
    private boolean mRunnableStopped = false;

    @BindView(R.id.text_view_hour_minute)
    TextView mTextViewHourMinute;
    @BindView(R.id.text_view_seconds)
    TextView mTextViewSeconds;
    @BindView(R.id.check_box_battery)
    CheckBox mCheckBoxBattery;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        this.mRunnableStopped = false;
        this.startBedside();
    }

    @Override
    protected void onStop() {
        super.onStop();
        this.mRunnableStopped = true;
    }

    private void startBedside() {

        final Calendar calendar = Calendar.getInstance();
        this.mRunnable = new Runnable() {
            @Override
            public void run() {
                if (mRunnableStopped) {
                    return;
                }
                calendar.setTimeInMillis(System.currentTimeMillis());

                String hourMinutesFormat = String.format("%02d:%02d", calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE));
                String secondFormat = String.format("%02d", calendar.get(Calendar.SECOND));

                mTextViewHourMinute.setText(hourMinutesFormat);
                mTextViewSeconds.setText(secondFormat);

                //mile de agora
                long now = SystemClock.uptimeMillis();
                //calcular para cair o próximo segundo
                long next = now + (1000 - (now % 1000));

                //rodar conforme chamado | calculo acima
                mHandler.postAtTime(mRunnable, next);
            }
        };
        this.mRunnable.run();
    }
}
