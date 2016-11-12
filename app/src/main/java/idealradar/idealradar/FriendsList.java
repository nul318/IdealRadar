package idealradar.idealradar;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
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
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

public class FriendsList extends AppCompatActivity {

    private myAdapter Adapter;
    final ArrayList<Friend> Friends_list = new ArrayList<Friend>();
    String user_id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friends_list);
        user_id = getIntent().getStringExtra("user_id");

        Friends_list.add(new Friend("a","a","a"));
        Friends_list.add(new Friend("a","a","a"));
        Friends_list.add(new Friend("a","a","a"));
        Friends_list.add(new Friend("a","a","a"));
        Friends_list.add(new Friend("a","a","a"));
        Friends_list.add(new Friend("a","a","a"));
        Friends_list.add(new Friend("a","a","a"));
        Friends_list.add(new Friend("a","a","a"));
        Friends_list.add(new Friend("a","a","a"));
        Friends_list.add(new Friend("a","a","a"));
        Friends_list.add(new Friend("a","a","a"));
        Friends_list.add(new Friend("a","a","a"));
        Friends_list.add(new Friend("a","a","a"));
        Friends_list.add(new Friend("a","a","a"));
        Friends_list.add(new Friend("a","a","a"));

        Adapter = new myAdapter(this, R.layout.friend_item, Friends_list);
        ListView list = (ListView) findViewById(R.id.friends_list);

        list.setAdapter(Adapter);
        // 리스트뷰 속성(없어도 구현 가능)
        // 항목을 선택하는 모드
//		list.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        // 항목 사이의 구분선 지정
        list.setDivider(null); //xml 에서 직접 지정함
        // 구분선의 높이 지정
//		list.setDividerHeight(4);

        list.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            }
        });

        try {
            submit(user_id);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }


    }

    public class myAdapter extends BaseAdapter {
        Context con; // 이미지를 받는곳
        LayoutInflater inflater;
        ArrayList<Friend> message_list;
        int layout;
        myAdapter(Context context, int layout, ArrayList<Friend> message_list) {
            con = context;
            this.layout = layout;
            this.message_list = message_list;
            inflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
            // 멤버변수 초기화
        }

        @Override
        public int getCount() {
            // TODO Auto-generated method stub
            return message_list.size();
        }

        @Override
        public Object getItem(int position) {
            // TODO Auto-generated method stub
            return message_list.get(position);
        }

        @Override
        public long getItemId(int position) {
            // TODO Auto-generated method stub
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            // TODO Auto-generated method stub
            if (null == convertView) {
                convertView = inflater.inflate(layout, parent, false);
            }

            TextView name = (TextView) convertView.findViewById(R.id.user_name);
            TextView age = (TextView) convertView.findViewById(R.id.age);
            TextView similar_rate = (TextView) convertView.findViewById(R.id.similar_rate);

            return convertView;
        }
    }



    private void submit(final String user_id) throws UnsupportedEncodingException {
        new Thread() {
            @Override
            public void run() {
                // TODO Auto-generated method stub
                String URL = "http://hanea8199.vps.phps.kr/IdealRadar/find_friend.php";
                HttpClient httpClient = new DefaultHttpClient();
                HttpPost httpPost = new HttpPost(URL);
                List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(3);
                try {
                    nameValuePairs.add(new BasicNameValuePair("user_id", URLEncoder.encode(user_id,"UTF-8")));
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
                try {
                    httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
                    HttpResponse response = httpClient.execute(httpPost);
                    Log.d("Http Post Request:", nameValuePairs.toString());

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


                    Log.i("response" , results);

                    JSONArray friends = new JSONArray(results);
                    for(int i=0; i< friends.length(); i++){
                        JSONObject friend = friends.getJSONObject(i);
                    }

                    //아이디, 이름, 나이, 학교, 전공, 학번, 성별, 프로필사진

                } catch (IOException e) {
                    // Log exception
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }


}
