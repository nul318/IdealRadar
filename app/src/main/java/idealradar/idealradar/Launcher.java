package idealradar.idealradar;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;

public class Launcher extends AppCompatActivity {

    Handler handler = new Handler();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launcher);
        activitStart();

    }

    protected void activitStart(){
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
                    }
                });
            }
        }.start();
    }

}
