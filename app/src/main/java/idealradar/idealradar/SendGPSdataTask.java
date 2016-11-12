package idealradar.idealradar;

import android.os.AsyncTask;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by Kim Gyu Hwan on 2016-11-12.
 */


public class SendGPSdataTask extends AsyncTask<String, String, Void> {

    @Override
    protected Void doInBackground(String... params) {
        try
        {
            sendData(params[0],params[1],params[2]);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return null;
    }
    private String sendData(String user_id, String longitude,String latitude) throws ClientProtocolException, IOException {
        // TODO Auto-generated method stub
        String url="http://hanea8199.vps.phps.kr/IdealRadar/UpdateLocation.php";
        HttpClient http = new DefaultHttpClient();
        try {

            ArrayList<NameValuePair> nameValuePairs =
                    new ArrayList<NameValuePair>();
            nameValuePairs.add(new BasicNameValuePair("user_id", user_id));
            nameValuePairs.add(new BasicNameValuePair("lat", latitude));
            nameValuePairs.add(new BasicNameValuePair("lng", longitude));

            HttpParams params = http.getParams();
            HttpConnectionParams.setConnectionTimeout(params, 5000);
            HttpConnectionParams.setSoTimeout(params, 5000);

            HttpPost httpPost = new HttpPost(url);
            UrlEncodedFormEntity entityRequest =
                    new UrlEncodedFormEntity(nameValuePairs, "EUC-KR");

            httpPost.setEntity(entityRequest);

            HttpResponse responsePost = http.execute(httpPost);
            HttpEntity resEntity = responsePost.getEntity();
            String res=EntityUtils.toString(resEntity);
            return res;
        }catch(Exception e){e.printStackTrace();}
        return null;
    }


}
