package com.example.orafa.androidrelogiodecabeceiraudemy;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.CheckBox;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;


public class MainActivity extends AppCompatActivity {

    @BindView(R.id.text_view_hour_minute)
    TextView mTextViewHourMinute;
    @BindView(R.id.text_view_seconds)
    TextView mTextViewSeconds;
    @BindView(R.id.check_box_battery)
    CheckBox mCheckBoxBatery;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);
    }
}
