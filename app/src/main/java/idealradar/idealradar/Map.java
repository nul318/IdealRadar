package idealradar.idealradar;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import com.nhn.android.maps.NMapActivity;
import com.nhn.android.maps.NMapController;
import com.nhn.android.maps.NMapView;
import com.nhn.android.maps.maplib.NGeoPoint;
import com.nhn.android.maps.nmapmodel.NMapError;
import com.nhn.android.maps.overlay.NMapPOIdata;
import com.nhn.android.maps.overlay.NMapPOIitem;
import com.nhn.android.mapviewer.overlay.NMapOverlayManager;
import com.nhn.android.mapviewer.overlay.NMapPOIdataOverlay;

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

public class Map extends NMapActivity implements NMapView.OnMapViewTouchEventListener,NMapView.OnMapStateChangeListener,View.OnClickListener
{
    ImageButton btn_profile,btn_alert,btn_home,btn_friend,btn_chat;
    NMapView mMapView=null;
    NMapController mMapController=null;
    NMapViewerResourceProvider mMapViewerResourceProvider=null;
    String clientId="j6dfzQJmWqbBP28epdFN";
    NMapOverlayManager mOverlayManager=null;
    String user_id;
    NMapPOIdata poiData;
    Handler handler;

    ArrayList<String> lat;
    ArrayList<String> lng;
    ArrayList<String> name;
    ArrayList<String> friend_id;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);
        handler = new Handler();
        lat = new ArrayList<String>();
        lng = new ArrayList<String>();
        name = new ArrayList<String>();
        friend_id = new ArrayList<String>();

        try {
            submit();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }












        user_id = getIntent().getStringExtra("user_id");

// create map view
        btn_profile=(ImageButton)findViewById(R.id.map_profile);
        btn_profile.setOnClickListener(this);

        btn_alert=(ImageButton)findViewById(R.id.map_alert);
        btn_alert.setOnClickListener(this);

        btn_home=(ImageButton)findViewById(R.id.map_home);
        btn_home.setOnClickListener(this);

        btn_friend=(ImageButton)findViewById(R.id.map_friend);
        btn_friend.setOnClickListener(this);

        btn_chat=(ImageButton)findViewById(R.id.map_chat);
        btn_chat.setOnClickListener(this);
         mMapView = (NMapView)findViewById(R.id.Nmapview);

// 기존 API key 방식은 deprecated 함수이며, 2016년 말까지만 사용 가능합니다.
// mMapView.setApiKey(API_KEY);

// set Client ID for Open MapViewer Library
        mMapView.setClientId(clientId);

// set the activity content to the map view

// initialize map view
        mMapView.setClickable(true);

// register listener for map state changes
        mMapView.setOnMapStateChangeListener(this);
        mMapView.setOnMapViewTouchEventListener(this);

// use map controller to zoom in/out, pan and set map center, zoom level etc.
        mMapController = mMapView.getMapController();

        mMapViewerResourceProvider = new NMapViewerResourceProvider(this);
        mOverlayManager = new NMapOverlayManager(this, mMapView, mMapViewerResourceProvider);
        int markerId = NMapPOIflagType.SPOT;

// set POI data
//        poiData = new NMapPOIdata(2, mMapViewerResourceProvider);
//        poiData.beginPOIdata(2);
//        poiData.addPOIitem(126.653084, 37.449891, "klight1994", markerId, 0);
//        poiData.addPOIitem(126.653288, 37.449685, "iny27", markerId, 0);
//        poiData.addPOIitem(126.653488, 37.449685, "sky", markerId, 0);











    }

    private NMapPOIdataOverlay.OnStateChangeListener onPOIdataStateChangeListener=new NMapPOIdataOverlay.OnStateChangeListener() {
        @Override
        public void onFocusChanged(NMapPOIdataOverlay nMapPOIdataOverlay, NMapPOIitem item) {
            if (item != null) {
                Log.i("AAAA", "onFocusChanged: " + item.toString());
            } else {
                Log.i("AAAA", "onFocusChanged: ");
            }
        }

        @Override
            public void onCalloutClick(NMapPOIdataOverlay nMapPOIdataOverlay, NMapPOIitem item) {
            Toast.makeText(Map.this, "onCalloutClick: " + item.getTitle(), Toast.LENGTH_LONG).show();
            Intent it=new Intent(getApplicationContext(),SendMsg.class);
            it.putExtra("userData",item.getTitle());
            startActivity(it);
        }
    };
    @Override
    public void onMapInitHandler(NMapView mapView, NMapError errorInfo) {
        if (errorInfo == null) { // success
            mMapController.setMapCenter(new NGeoPoint(126.653084, 37.449891), 14);
        } else { // fail
            Log.e("NAVERMAPP", "onMapInitHandler: error=" + errorInfo.toString());
        }
    }

    @Override
    public void onMapCenterChange(NMapView nMapView, NGeoPoint nGeoPoint) {

    }

    @Override
    public void onMapCenterChangeFine(NMapView nMapView) {

    }

    @Override
    public void onZoomLevelChange(NMapView nMapView, int i) {

    }

    @Override
    public void onAnimationStateChange(NMapView nMapView, int i, int i1) {

    }

    @Override
    public void onLongPress(NMapView nMapView, MotionEvent motionEvent) {

    }

    @Override
    public void onLongPressCanceled(NMapView nMapView) {

    }

    @Override
    public void onTouchDown(NMapView nMapView, MotionEvent motionEvent) {

    }

    @Override
    public void onTouchUp(NMapView nMapView, MotionEvent motionEvent) {

    }

    @Override
    public void onScroll(NMapView nMapView, MotionEvent motionEvent, MotionEvent motionEvent1) {

    }

    @Override
    public void onSingleTapUp(NMapView nMapView, MotionEvent motionEvent) {

    }

    @Override
    public void onClick(View view) {
        Intent it=null;
        switch (view.getId())
        {
            case R.id.home_alert:
                AlertDialog.Builder alertBuilder = new AlertDialog.Builder(
                        Map.this);
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

            case R.id.map_friend:

                it=new Intent(getApplicationContext(),FriendsList.class);
                it.putExtra("user_id", user_id);
                startActivity(it);
                finish();

                break;
            case R.id.map_chat:
                it=new Intent(getApplicationContext(),MsgList.class);
                it.putExtra("user_id", user_id);
                startActivity(it);
                finish();
                break;

            case R.id.map_home:
                it=new Intent(getApplicationContext(),Home.class);
                it.putExtra("user_id", user_id);
                startActivity(it);
                finish();
                break;

        }
    }



    private void submit() throws UnsupportedEncodingException {
        new Thread() {
            @Override
            public void run() {
                // TODO Auto-generated method stub
                String URL = "http://hanea8199.vps.phps.kr/IdealRadar/friends-map.php";
                HttpClient httpClient = new DefaultHttpClient();
                HttpPost httpPost = new HttpPost(URL);

                List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(1);
                try {
                    nameValuePairs.add(new BasicNameValuePair("user_id", URLEncoder.encode(user_id,"UTF-8")));

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

                    final JSONArray friends = new JSONArray(results);

                    for(int i=0; i< friends.length(); i++){
                        JSONObject friend = friends.getJSONObject(i);
                        friend_id.add(friend.getString("user_id"));
                        name.add(friend.getString("nick_name"));
                        lat.add(friend.getString("lat"));
                        lng.add(friend.getString("lng"));
                    }
                    final int markerId = NMapPOIflagType.SPOT;

                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            // TODO Auto-generated method
                            poiData = new NMapPOIdata(friends.length(), mMapViewerResourceProvider);
                            poiData.beginPOIdata(friends.length());
                            for(int i=0; i<friends.length(); i++){
                                poiData.addPOIitem(Double.parseDouble(lat.get(i)), Double.parseDouble(lng.get(i)), name.get(i), markerId, 0);
                            }
                            poiData.endPOIdata();

                            // create POI data overlay
                            NMapPOIdataOverlay poiDataOverlay = mOverlayManager.createPOIdataOverlay(poiData, null);
                            poiDataOverlay.showAllPOIdata(0);

                            poiDataOverlay.setOnStateChangeListener(onPOIdataStateChangeListener);
                        }
                    });





                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                } catch (ClientProtocolException e) {
                    // Log exception
                    e.printStackTrace();
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
