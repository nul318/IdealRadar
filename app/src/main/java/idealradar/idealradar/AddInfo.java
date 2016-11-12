package idealradar.idealradar;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

public class AddInfo extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addinfo);

        final EditText univ_edit = (EditText) findViewById(R.id.universitiy_input);
        final EditText major_edit = (EditText) findViewById(R.id.major_input);
        final EditText student_code_edit = (EditText) findViewById(R.id.student_code_input);
        ImageView profile_image_edit = (ImageView) findViewById(R.id.profile_image);

        Button submit = (Button) findViewById(R.id.submit);



        View.OnClickListener submit_listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                String univ = univ_edit.getText().toString();
                String major = major_edit.getText().toString();;
                String student_code = student_code_edit.getText().toString();
                String profile_image;
                try {
                    submit(univ, major, student_code);
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
            }
        };
        View.OnClickListener image_listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("*/*");
                startActivityForResult(intent, 0);
            }
        };


        submit.setOnClickListener(submit_listener);
        profile_image_edit.setOnClickListener(image_listener);


    }

    private void submit(final String univ, final String major, final String student_code) throws UnsupportedEncodingException {
        new Thread() {
            @Override
            public void run() {
                // TODO Auto-generated method stub
                String URL = "Server URL";
                HttpClient httpClient = new DefaultHttpClient();
                HttpPost httpPost = new HttpPost(URL);
                List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(3);
                try {
                    nameValuePairs.add(new BasicNameValuePair("univ", URLEncoder.encode(univ,"UTF-8")));
                    nameValuePairs.add(new BasicNameValuePair("major", URLEncoder.encode(major,"UTF-8")));
                    nameValuePairs.add(new BasicNameValuePair("student_code", URLEncoder.encode(student_code,"UTF-8")));
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
                try {
                    httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
                    HttpResponse response = httpClient.execute(httpPost);
                    Log.d("Http Post Request:", nameValuePairs.toString());
                    Log.d("Http Post Response:", response.toString());
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                } catch (ClientProtocolException e) {
                    // Log exception
                    e.printStackTrace();
                } catch (IOException e) {
                    // Log exception
                    e.printStackTrace();
                }
            }
        }.start();
    }


}
