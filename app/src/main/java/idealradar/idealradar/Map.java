package idealradar.idealradar;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
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

import java.util.ArrayList;

public class Map extends NMapActivity implements NMapView.OnMapViewTouchEventListener,NMapView.OnMapStateChangeListener,View.OnClickListener
{
    ImageButton btn_profile,btn_alert,btn_home,btn_friend,btn_chat;
    NMapView mMapView=null;
    NMapController mMapController=null;
    NMapViewerResourceProvider mMapViewerResourceProvider=null;
    String clientId="j6dfzQJmWqbBP28epdFN";
    NMapOverlayManager mOverlayManager=null;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);
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
        NMapPOIdata poiData = new NMapPOIdata(2, mMapViewerResourceProvider);
        poiData.beginPOIdata(2);
        poiData.addPOIitem(126.653084, 37.449891, "klight1994", markerId, 0);
        poiData.addPOIitem(126.653288, 37.449685, "iny27", markerId, 0);
        poiData.addPOIitem(126.653488, 37.449685, "sky", markerId, 0);
        poiData.endPOIdata();

// create POI data overlay
        NMapPOIdataOverlay poiDataOverlay = mOverlayManager.createPOIdataOverlay(poiData, null);
        poiDataOverlay.showAllPOIdata(0);

        poiDataOverlay.setOnStateChangeListener(onPOIdataStateChangeListener);
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
            case R.id.map_alert:
                AlertDialog.Builder alertBuilder = new AlertDialog.Builder(
                        Map.this);
                alertBuilder.setTitle("친구요청");
                // List Adapter 생성
                ArrayList<Friend> arrayList=new ArrayList<Friend>();

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
            case R.id.map_home:
                it=new Intent(getApplicationContext(),Home.class);
                startActivity(it);
                finish();
                break;


        }
    }
}
