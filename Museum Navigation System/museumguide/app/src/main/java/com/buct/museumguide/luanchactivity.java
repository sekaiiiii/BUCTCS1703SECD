package com.buct.museumguide;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

public class luanchactivity extends AppCompatActivity {
    private Boolean isfirst;
    private Handler handler=new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            switch (msg.what) {
                case 0x666:
                    //判断程序是否是第一次运行
                    if (!luanchactivity.this.isfirst) {
                        startActivity(new Intent(luanchactivity.this, AppSlide.class));
                    } else {
                        startActivity(new Intent(luanchactivity.this, MainActivity.class));
                    }
                    finish();
                    break;
                default:
                    break;
            }
            return false;
        }
    });
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_luanchactivity);
        SharedPreferences Infos = getSharedPreferences("data", Context.MODE_PRIVATE);
        isfirst=Infos.getBoolean("start",false);
        handler.sendEmptyMessageDelayed(0x666, 2000);
    }
}
