package idealradar.idealradar;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.lang.reflect.Array;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class MsgList extends AppCompatActivity {

    ListView listView;
    MsgListAdapter mAdapter;
    String TAG = "MsgList";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_msg_list);

        String url = "http://hanea8199.vps.phps.kr/IdealRadar/GetMsgList.php";
        new mAsyncTask().execute(url);

        listView = (ListView) findViewById(R.id.mListView);

    }

    private class mAsyncTask extends AsyncTask<String, Void, String> {

        @Override
        protected void onPostExecute(String s) {
            // TODO: 2016. 11. 13.  json 으로 메세지 리스트 받아서 리스트뷰로 출력
            Log.d(TAG, "onPostExecute: "+s);

            final ArrayList<String> textArray = new ArrayList<>();
            final ArrayList<String> senderArray = new ArrayList<>();
            final ArrayList<Boolean> isSentArray = new ArrayList<>();

            try {

                JSONObject object = new JSONObject(s);
                JSONArray jsonArray = object.getJSONArray("msglist");
                for (int i=0; i<jsonArray.length();i++){
                    JSONObject msg = (JSONObject) jsonArray.get(i);
                    Boolean isSent = Boolean.parseBoolean(msg.getString("isSent"));
                    isSentArray.add(isSent);
                    String user_id = msg.getString("user_id");
                    senderArray.add(user_id);
                    String text = msg.getString("text");
                    textArray.add(text);
                    String created = msg.getString("created"); // TODO: 2016. 11. 13.  정렬
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }

            mAdapter = new MsgListAdapter(MsgList.this,textArray,senderArray,isSentArray);
            listView.setAdapter(mAdapter);
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Intent i = new Intent(MsgList.this,ReadMsg.class);
                    i.putExtra("text",textArray.get(position));
                    i.putExtra("sender",senderArray.get(position));
                    i.putExtra("isSent",isSentArray.get(position));
                    startActivity(i);
                }
            });

        }

        @Override
        protected String doInBackground(String... urls) {
            try {
                return downloadUrl(urls[0]);
            } catch (IOException e) {
                return "Unable to retrieve web page. URL may be invalid.";
            }
        }

        private String downloadUrl(String myurl) throws IOException {
            InputStream is = null;
            // Only display the first 500 characters of the retrieved
            // web page content.
            int len = 50000;

            try {
                URL url = new URL(myurl);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setReadTimeout(10000 /* milliseconds */);
                conn.setConnectTimeout(15000 /* milliseconds */);
                conn.setRequestMethod("POST");
                conn.setDoInput(true);

                // add post parameters
                OutputStream os = conn.getOutputStream();
                BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(os, "UTF-8"));
                writer.write("user_id=8637959_naver"); // TODO: 2016. 11. 13.
                writer.flush();
                writer.close();
                os.close();

                conn.connect();
                int response = conn.getResponseCode();
                Log.d(TAG, "The response is: " + response);
                is = conn.getInputStream();

                // Convert the InputStream into a string
                String contentAsString = readIt(is, len);
                return contentAsString;

                // Makes sure that the InputStream is closed after the app is
                // finished using it.
            } finally {
                if (is != null) {
                    is.close();
                }
            }
        }

        private String readIt(InputStream stream, int len) throws IOException {
            Reader reader = null;
            reader = new InputStreamReader(stream, "UTF-8");
            char[] buffer = new char[len];
            reader.read(buffer);
            return new String(buffer);
        }


    }
}
