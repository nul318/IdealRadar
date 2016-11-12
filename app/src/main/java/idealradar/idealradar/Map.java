package idealradar.idealradar;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
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

public class Map extends NMapActivity implements NMapView.OnMapViewTouchEventListener,NMapView.OnMapStateChangeListener
{
    NMapView mMapView=null;
    NMapController mMapController=null;
    NMapViewerResourceProvider mMapViewerResourceProvider=null;
    String clientId="j6dfzQJmWqbBP28epdFN";
    NMapOverlayManager mOverlayManager=null;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

// create map view
         mMapView = new NMapView(this);

// 기존 API key 방식은 deprecated 함수이며, 2016년 말까지만 사용 가능합니다.
// mMapView.setApiKey(API_KEY);

// set Client ID for Open MapViewer Library
        mMapView.setClientId(clientId);

// set the activity content to the map view
        setContentView(mMapView);

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
}
