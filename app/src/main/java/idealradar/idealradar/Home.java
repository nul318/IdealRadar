package idealradar.idealradar;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;

public class Home extends AppCompatActivity {

    final Handler handler = new Handler();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

//        TextView tmp = (TextView) findViewById(R.id.tmp);
//        Intent intent = getIntent();

        String user_id = getIntent().getStringExtra("user_id");
        Intent intent = new Intent(Home.this, FriendsList.class);
        intent.putExtra("user_id",user_id);
        startActivity(intent);
        finish();

    }


}
