package idealradar.idealradar;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class SendMsg extends AppCompatActivity {

    TextView textid;
    Button sendbtn;
    EditText textdata;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_msg);
        textdata=(EditText)findViewById(R.id.sendText);
        sendbtn=(Button)findViewById(R.id.sendBtn);
        textid=(TextView)findViewById(R.id.sendID);
        Intent it =getIntent();
        String user_id=(String)it.getSerializableExtra("userData");
        textid.setText(user_id);
    }
}
