package com.example.wyx.linyue;

import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

import android.app.Activity;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.DisplayMetrics;
import android.view.View;

public class MainActivity extends Activity {
    private MediaPlayer mp = null;
    private FlowerView mFlowerView;
    // 屏幕宽度
    public static int screenWidth;
    // 屏幕高度
    public static int screenHeight;
    Timer myTimer = null;
    TimerTask mTask = null;
    private static final int SNOW_BLOCK = 1;
    private Handler mHandler = new Handler() {
        public void dispatchMessage(Message msg) {
            mFlowerView.inva();
        }

        ;
    };

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.btnPlaySong).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(mp != null){
                    mp.start();
                }
            }
        });

        mFlowerView = (FlowerView) findViewById(R.id.flowerview);
        screenWidth = getWindow().getWindowManager().getDefaultDisplay()
                .getWidth();//获取屏幕宽度
        screenHeight = getWindow().getWindowManager().getDefaultDisplay()
                .getHeight();//获取屏幕高度

        DisplayMetrics dis = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dis);
        float de = dis.density;
        mFlowerView.setWH(screenWidth, screenHeight, de);
        mFlowerView.loadFlower();
        mFlowerView.addRect();

        myTimer = new Timer();
        mTask = new TimerTask() {
            @Override
            public void run() {
                Message msg = new Message();
                msg.what = SNOW_BLOCK;
                mHandler.sendMessage(msg);
            }
        };
        myTimer.schedule(mTask, 3000, 10);
    }

    @Override
    protected void onDestroy() {
        // TODO Auto-generated method stub
        super.onDestroy();
        mFlowerView.recly();
    }
    @Override
    protected void onResume() {

        mp = MediaPlayer.create(this,R.raw.abc);

        try {
            mp.prepare();
        } catch (IllegalStateException e){
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        super.onResume();
    }

    @Override
    protected void onPause() {

        if(mp != null){
            mp.release();
        }

        super.onPause();
    }
}