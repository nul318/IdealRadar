package idealradar.idealradar;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

/**
 * Created by Kim Gyu Hwan on 2016-11-12.
 */

public class BootReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {

        if (intent.getAction().equals(Intent.ACTION_BOOT_COMPLETED)) {
            context.startService(new Intent("idealrader.idealrader.gpsservice"));
            Log.d("GYU","부팅 스타터!");
        }
    }
}