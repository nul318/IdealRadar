package idealradar.idealradar;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;

public class Launcher extends AppCompatActivity {

    Handler handler = new Handler();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launcher);
        if(!isServiceRunningCheck()) {
            startService(new Intent("idealrader.idealrader.gpsservice"));
           }
        activityStart();


    }
    public boolean isServiceRunningCheck() {
        ActivityManager manager = (ActivityManager) this.getSystemService(Activity.ACTIVITY_SERVICE);
        for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
            if ("idealrader.idealrader.gpsservice".equals(service.service.getClassName())) {
                return true;
            }
        }
        return false;
    }
    protected void activityStart(){
        new Thread() {
            @Override
            public void run() {
                try {
                    Thread.sleep(1500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        // TODO Auto-generated method stub

                        Intent intent = new Intent(Launcher.this, Login.class);
                        startActivity(intent);
                        finish();
                    }
                });
            }
        }.start();
    }

}
