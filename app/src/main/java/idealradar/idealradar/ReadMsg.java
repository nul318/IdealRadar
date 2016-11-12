package idealradar.idealradar;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class ReadMsg extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_read_msg);

        Intent i = getIntent();
        String text = i.getStringExtra("text");
        String sender = i.getStringExtra("sender");

        TextView tv_text = (TextView) findViewById(R.id.tv_text);
        tv_text.setText(text);
        TextView tv_sender = (TextView) findViewById(R.id.tv_sender);
        tv_sender.setText(sender);

    }
}
