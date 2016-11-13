package idealradar.idealradar;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.messaging.FirebaseMessaging;

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
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import static idealradar.idealradar.Home.user_id;

/**
 * Created by Kim Gyu Hwan on 2016-11-12.
 */

public class FriendAdapter extends ArrayAdapter implements View.OnClickListener {

    private ArrayList<FriendsQueue> listViewItemList = new ArrayList<FriendsQueue>() ;
    TextView friendidTextView,friendPercent;
    ImageButton btnCheck;
    ImageButton btnCancel;
    int i=-1;
    int resourceId;

    FriendAdapter(Context context, int resource, ArrayList<FriendsQueue> list)
    {
        super(context, resource, list) ;
        this.resourceId = resource ;

    }




    @Override
    public int getCount() {
        return listViewItemList.size();
    }

    @Override
    public Object getItem(int i) {
        return listViewItemList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }



    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final int pos = position;
        final Context context = parent.getContext();

        // "listview_item" Layout을 inflate하여 convertView 참조 획득.
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.friend_pop_list, parent, false);
        }

        // 화면에 표시될 View(Layout이 inflate된)으로부터 위젯에 대한 참조 획득
        friendidTextView = (TextView) convertView.findViewById(R.id.friend_id) ;
       friendPercent=(TextView)convertView.findViewById(R.id.friend_sim);
        btnCheck = (ImageButton) convertView.findViewById(R.id.BtnCheck) ;
        btnCancel = (ImageButton) convertView.findViewById(R.id.BtnCancel);
        // Data Set(listViewItemList)에서 position에 위치한 데이터 참조 획득
        FriendsQueue listViewItem = listViewItemList.get(position);


        btnCheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                i=position;
                UThread A=new UThread();
                A.start();
                try {
                    A.join();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                listViewItemList.remove(i);
                notifyDataSetChanged();
            }
        });



        // 아이템 내 각 위젯에 데이터 반영
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listViewItemList.remove(position);
                notifyDataSetChanged();
            }
        });

        friendidTextView.setText(listViewItem.get_NickName());
        int val=(int)(listViewItem.get_Similar_rate()*100);
        friendPercent.setText(String.valueOf(val)+"%");
        return convertView;
    }
    class UThread extends Thread
    {
        public void run() {
            String URL = "http://hanea8199.vps.phps.kr/IdealRadar/FriendProcess.php";
            HttpClient httpClient = new DefaultHttpClient();
            HttpPost httpPost = new HttpPost(URL);

            List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(3);
            if(i!=-1) {
                FriendsQueue listViewItem = listViewItemList.get(i);

                try {
                    String val=listViewItem.get_User_id();
                    nameValuePairs.add(new BasicNameValuePair("sender", URLEncoder.encode(val.substring(0,val.length()-6), "UTF-8")));
                    nameValuePairs.add(new BasicNameValuePair("receiver", URLEncoder.encode(user_id, "UTF-8")));
                    nameValuePairs.add(new BasicNameValuePair("state", URLEncoder.encode("Accepted", "UTF-8")));


//                    Log.i("user_name",URLEncoder.encode(user_name,"UTF-8"));
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
                try {
                    httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
                    HttpResponse response = httpClient.execute(httpPost);
                    Log.d("Http Post Request:", nameValuePairs.toString());
                    Log.d("HTTPP 리스폰",response.toString());
//                    Log.d("Http Post Response:", response.toString()); //서버 응답이 가공되지 않음
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
                    Log.v("REUSLT",results);
                } catch (UnsupportedEncodingException | ClientProtocolException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        }
    };
    // 아이템 데이터 추가를 위한 함수. 개발자가 원하는대로 작성 가능.
    public void addItem(String id, double rate,String Nickname) {
        FriendsQueue item = new FriendsQueue(id,rate,Nickname);
        listViewItemList.add(item);
        notifyDataSetChanged();
    }

    @Override
    public void onClick(View view) {

    }
}
