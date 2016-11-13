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
import android.widget.ImageButton;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.InterruptedIOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

public class Home extends AppCompatActivity implements View.OnClickListener{

    ImageButton btn_profile,btn_alert,btn_map,btn_friend,btn_chat;
    ArrayList<FriendsQueue> arrayList;
    static FriendAdapter friendAdapter=null;
    static String user_id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        user_id = getIntent().getStringExtra("user_id");


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

     //Thread A=new Thread() {
     class SendThread extends Thread
         {
         public void run() {
             String URL = "http://hanea8199.vps.phps.kr/IdealRadar/find_waiting_friend.php";
             HttpClient httpClient = new DefaultHttpClient();
             HttpPost httpPost = new HttpPost(URL);

             List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(1);
             try {
                 nameValuePairs.add(new BasicNameValuePair("user_id", URLEncoder.encode(user_id, "UTF-8")));

//                    Log.i("user_name",URLEncoder.encode(user_name,"UTF-8"));
             } catch (UnsupportedEncodingException e) {
                 e.printStackTrace();
             }
             try {
                 httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
                 HttpResponse response = httpClient.execute(httpPost);
                 Log.d("Http Post Request:", nameValuePairs.toString());
//                    Log.d("Http Post Response:", response.toString()); //서버 응답이 가공되지 않음

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



                friendAdapter= new FriendAdapter(getApplicationContext(),R.layout.friend_pop_list,arrayList);
                 try {
                     JSONArray friends = new JSONArray(results);
                     for (int i = 0; i < friends.length(); i++) {
                         JSONObject friend = friends.getJSONObject(i);
                         double val;
                         try {
                             val = Double.parseDouble(friend.getString("similar_rate").toString());
                         } catch (NumberFormatException e) {
                             val = 0;
                         }

                         friendAdapter.addItem(friend.getString("sender").toString(), val, friend.getString("nickname").toString());
                         Log.d("AAAAAA",friend.getString("nickname"));
                         Log.d("AAAAA",String.valueOf(friendAdapter.getCount()));
                         friendAdapter.notifyDataSetChanged();
                     }
                     //아이디, 이름, 나이, 학교, 전공, 학번, 성별, 프로필사진
                 } catch (JSONException e) {
                     e.printStackTrace();
                 }
                 // 버튼 생성

             } catch (UnsupportedEncodingException | ClientProtocolException e) {
                 e.printStackTrace();
             } catch (IOException e) {
                 e.printStackTrace();
             }

         }
     };

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

                arrayList=new ArrayList<FriendsQueue>();


                SendThread A=new SendThread();
                A.start();

                try {
                    A.join();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
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
}
