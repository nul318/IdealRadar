package idealradar.idealradar;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

public class Home extends AppCompatActivity implements View.OnClickListener{

    ImageButton btn_profile,btn_alert,btn_map,btn_friend,btn_chat;

    FriendAdapter friendAdapter=null;
    String user_id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        user_id = getIntent().getStringExtra("user_id");

        Button search_ideal = (Button) findViewById(R.id.search_ideal);




        View.OnClickListener search_listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                try {
                    submit(user_id);
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
            }
        };

        search_ideal.setOnClickListener(search_listener);

        btn_profile=(ImageButton)findViewById(R.id.home_profile);
        btn_profile.setOnClickListener(this);

        btn_alert=(ImageButton)findViewById(R.id.home_alert);
        btn_alert.setOnClickListener(this);

        btn_map=(ImageButton)findViewById(R.id.home_map);
        btn_map.setOnClickListener(this);

        btn_friend=(ImageButton)findViewById(R.id.home_friend);
        btn_friend.setOnClickListener(this);

        btn_chat=(ImageButton)findViewById(R.id.home_chat);
        btn_chat.setOnClickListener(this);


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
//		if(dtToggle.onOptionsItemSelected(item)){
//			return true;
//		}
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }


    @Override
    public void onClick(View view) {
        Intent it;
        switch (view.getId())
        {
            case R.id.home_alert:
                AlertDialog.Builder alertBuilder = new AlertDialog.Builder(
                        Home.this);
                alertBuilder.setTitle("친구요청");
                // List Adapter 생성
                ArrayList<FriendsQueue> arrayList=new ArrayList<FriendsQueue>();

                FriendAdapter friendAdapter= new FriendAdapter(getApplicationContext(),R.layout.friend_pop_list,arrayList);
                friendAdapter.addItem("사과",0.3);
                friendAdapter.addItem("딸기",0.4);
                friendAdapter.addItem("오렌지",0.32);
                friendAdapter.addItem("수박",0.64);
                friendAdapter.addItem("참외",0.24);

                // 버튼 생성
                alertBuilder.setNegativeButton("취소",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,
                                                int which) {
                                dialog.dismiss();
                            }
                        });

                // Adapter 셋팅
                alertBuilder.setAdapter(friendAdapter,
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,
                                                int id) {

                            }
                        });
                alertBuilder.show();
                break;

            case R.id.home_friend:

                it=new Intent(getApplicationContext(),FriendsList.class);
                it.putExtra("user_id", user_id);

                startActivity(it);
                finish();

                break;
            case R.id.home_chat:
                it=new Intent(getApplicationContext(),MsgList.class);
                it.putExtra("user_id", user_id);
                startActivity(it);
                finish();
                break;

            case R.id.home_map:
                it=new Intent(getApplicationContext(),Map.class);
                it.putExtra("user_id", user_id);
                startActivity(it);
                finish();
                break;


        }
    }


    private void submit(final String user_id) throws UnsupportedEncodingException {
        new Thread() {
            @Override
            public void run() {
                // TODO Auto-generated method stub
                String URL = "http://hanea8199.vps.phps.kr/IdealRadar/Detect.php";
                HttpClient httpClient = new DefaultHttpClient();
                HttpPost httpPost = new HttpPost(URL);
                List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(1);
                try {
                    nameValuePairs.add(new BasicNameValuePair("user_id", URLEncoder.encode(user_id,"UTF-8")));
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
                try {
                    httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
                    HttpResponse response = httpClient.execute(httpPost);
                    Log.d("Http Post Request:", nameValuePairs.toString());

//                    Log.d("Http Post Response:", response.toString());

                    /*
                     * HttpResponse response 로 서버 응답 알아내는 코드
                     */
                    InputStream inputStream = response.getEntity().getContent();
                    InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
                    BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                    final StringBuilder stringBuilder = new StringBuilder();
                    String bufferedStrChunk = null;
                    while ((bufferedStrChunk = bufferedReader.readLine()) != null) {
                        stringBuilder.append(bufferedStrChunk);
                    }
                    Log.v("Http Post Response:", stringBuilder.toString());// 서버 응답

                    final String results = stringBuilder.toString().replaceAll("\\p{Z}", "");


                } catch (IOException e) {
                    // Log exception
                    e.printStackTrace();
                }
            }
        }.start();
    }

}
