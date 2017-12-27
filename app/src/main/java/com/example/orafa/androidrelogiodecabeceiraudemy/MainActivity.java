package com.example.orafa.androidrelogiodecabeceiraudemy;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.BatteryManager;
import android.os.Handler;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.WindowManager;
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
    @BindView(R.id.text_view_battery)
    TextView textViewBattery;

    private BroadcastReceiver mBatteryReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            //recuperar level da battery
            int level = intent.getIntExtra(BatteryManager.EXTRA_LEVEL, 0);
            textViewBattery.setText(String.valueOf(String.format("%d%%", level)));
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);

        //tela do app nunca bloqueia quando ele está aberto
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        //ouvir broadcast | saber quando mudar nivel da bateria
        this.registerReceiver(this.mBatteryReceiver, new IntentFilter(Intent.ACTION_BATTERY_CHANGED));
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
