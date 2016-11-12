package idealradar.idealradar;

import android.Manifest;
import android.app.AlertDialog;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.annotation.StringDef;
import android.support.v4.app.ActivityCompat;
import android.util.Log;

/**
 * Created by Kim Gyu Hwan on 2016-11-12.
 */

public class GpsService extends Service {


    LocationManager lm = null;
    boolean isWorked = false;

    @Nullable

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
    public static void Send(String user_id, String longi, String lati)
    {
        new SendGPSdataTask().execute(user_id,longi,lati);
    }
    private final LocationListener mLocationListener = new LocationListener() {
        @Override
        public void onLocationChanged(Location location) {
            double longitude = location.getLongitude(); //경도
            double latitude = location.getLatitude();   //위도
            double altitude = location.getAltitude();   //고도
            float accuracy = location.getAccuracy();    //정확도
            String provider = location.getProvider();
            String id = "aaa"; //네이버 아이디 API에서 가져올 값
            Log.d("GYU","GPS 데이터 쏴줌");
            Send(id,String.valueOf(longitude), String.valueOf(latitude));
        }

        @Override
        public void onStatusChanged(String s, int i, Bundle bundle) {

        }

        @Override
        public void onProviderEnabled(String s) {
            Log.d("GYU","GPS 사용중");
        }

        @Override
        public void onProviderDisabled(String s) {
            Log.d("GYU","GPS 사용불가");
        }
    };

    @Override
    public void onStart(Intent intent, int startId) {
        Log.d("slog", "onStart()");
        super.onStart(intent, startId);

        LocationManager lm = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        lm.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1000, 1, mLocationListener);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        lm.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 1000, 1, mLocationListener);
    }

    @Override
    public void onDestroy() {
        Log.d("slog", "onDestroy()");
        super.onDestroy();
    }

}
