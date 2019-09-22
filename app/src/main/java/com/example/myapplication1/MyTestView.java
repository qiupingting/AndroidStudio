package com.example.myapplication1;

import android.app.Activity;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.WindowManager;
import android.widget.DatePicker;
import android.widget.ProgressBar;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.Calendar;
import java.util.logging.Handler;
import java.util.logging.LogRecord;

public class MyTestView extends Activity {
  private ProgressBar progressBar;
    private  int mProgress=0;
    private android.os.Handler mHander;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_test_view);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        progressBar=(ProgressBar)findViewById(R.id.pb_1);
       mHander=new android.os.Handler(){
           @Override
           public void handleMessage(Message msg) {
               if(msg.what==0x111){
                   progressBar.setProgress(mProgress);
               }else{
                   Toast.makeText(getApplicationContext(),"耗时操作已完成",Toast.LENGTH_SHORT).show();
               }
           }
       };
       new Thread(new Runnable() {
           @Override
           public void run() {
               while(true){
                   mProgress=dowork();
                   Message  message=new Message();
                   if(mProgress<100){
                       message.what=0x111;
                       mHander.sendMessage(message);
                   }
                   else{
                       message.what=0x110;
                       mHander.sendMessage(message);
                       break;
                   }
               }
           }
           private  int dowork(){
               mProgress+=Math.random()*10;
               try {
                   Thread.sleep(200);
               } catch (InterruptedException e) {
                   e.printStackTrace();
               }
               return mProgress;
           }
       }).start();
    }
}
