package com.pason.justtest;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Window;

/**
 * Created by Admin on 2015/8/8.
 */
public class Welcome extends Activity {
    private static final int TIME = 3000;
    private static final int GO_HOME = 1000;
    private static final int GO_GUIDE = 1001;

    private Boolean isFirst;


    public Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case GO_HOME:
                    goHome();
                    break;
                case GO_GUIDE:
                    goGuide();
                    break;
            }
        }
    };

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.welcome);
        init();
    }
    private void init(){
        SharedPreferences preferences = getSharedPreferences("data",MODE_PRIVATE);
        isFirst = preferences.getBoolean("isFirst",true);
        if (!isFirst){
            mHandler.sendEmptyMessageDelayed(GO_HOME,TIME);
        }else {
            mHandler.sendEmptyMessageDelayed(GO_GUIDE,TIME);
            SharedPreferences.Editor editor = preferences.edit();
            editor.putBoolean("isFirst",false);
            editor.commit();
        }
    }
    private void goGuide(){
        startActivity(new Intent(getApplicationContext(),SlideGuide.class));
        finish();
    }
    private void goHome(){
        startActivity(new Intent(getApplicationContext(),MainActivity.class));
        finish();
    }

}
