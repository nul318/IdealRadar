package idealradar.idealradar;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import jp.wasabeef.glide.transformations.CropCircleTransformation;

public class AddInfo extends AppCompatActivity {
    String image_url;
    String user_id;
    Boolean image_update_check=false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addinfo);

        final EditText univ_edit = (EditText) findViewById(R.id.universitiy_input);
        final EditText major_edit = (EditText) findViewById(R.id.major_input);
        final EditText student_code_edit = (EditText) findViewById(R.id.student_code_input);
        ImageView profile_image = (ImageView) findViewById(R.id.profile_image);

        Intent intent = getIntent();
        user_id = intent.getStringExtra("user_id");
        /*
         * 네이버 프로필 사진 먼저 등록.
         */

        String default_image = intent.getStringExtra("image");
        Glide.with(this).load(default_image).bitmapTransform(new CropCircleTransformation(new CustomBitmapPool() {})).into(profile_image);
        image_url=default_image;
        Log.i("image_url", image_url);
        //------------------------------------------------------------------

        Button submit = (Button) findViewById(R.id.submit);



        View.OnClickListener submit_listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                String univ = univ_edit.getText().toString();
                String major = major_edit.getText().toString();;
                String student_code = student_code_edit.getText().toString();

                try {
                    submit(univ, major, student_code);
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }


                try {
                    if(image_update_check){
                        String realpath = getRealImagePath(Uri.parse(image_url));
                        String key = URLDecoder.decode(realpath, "UTF-8");
                        fileUpload(key);
                    }
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }



                Intent intent = new Intent(AddInfo.this, Home.class);
                intent.putExtra("image", image_url);
                intent.putExtra("user_id", user_id);
                startActivity(intent);
                finish();
            }
        };

        View.OnClickListener image_listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("*/*");
                startActivityForResult(intent, 0);
            }
        };


        submit.setOnClickListener(submit_listener);
        profile_image.setOnClickListener(image_listener);


    }

    private void submit(final String univ, final String major, final String student_code) throws UnsupportedEncodingException {
        new Thread() {
            @Override
            public void run() {
                // TODO Auto-generated method stub
                String URL = "http://hanea8199.vps.phps.kr/IdealRadar/UpdateUserInfo.php";
                HttpClient httpClient = new DefaultHttpClient();
                HttpPost httpPost = new HttpPost(URL);
                List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(3);
                try {
                    nameValuePairs.add(new BasicNameValuePair("univ", URLEncoder.encode(univ,"UTF-8")));
                    nameValuePairs.add(new BasicNameValuePair("major", URLEncoder.encode(major,"UTF-8")));
                    nameValuePairs.add(new BasicNameValuePair("student_code", URLEncoder.encode(student_code,"UTF-8")));


                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
                try {
                    httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
                    HttpResponse response = httpClient.execute(httpPost);
                    Log.d("Http Post Request:", nameValuePairs.toString());

//                    Log.d("Http Post Response:", response.toString());

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
                } catch (IOException e) {
                    // Log exception
                    e.printStackTrace();
                }
            }
        }.start();
    }

    private void fileUpload(final String uri) throws UnsupportedEncodingException {
        new Thread() {
            @Override
            public void run() {
                // TODO Auto-generated method stub

                File path = new File(uri);
                HttpClient client = new DefaultHttpClient();
                HttpPost post = new HttpPost("http://hanea8199.vps.phps.kr/IdealRadar/UploadImage.php");

                MultipartEntityBuilder entityBuilder = MultipartEntityBuilder.create();
                entityBuilder.setMode(HttpMultipartMode.BROWSER_COMPATIBLE);
                FileBody bin = new FileBody(path);

                try {
                    entityBuilder.addPart("image", bin);
                    entityBuilder.addPart("user_id", new StringBody(user_id)); //POST 는 이런식으로 보내셈
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }

                HttpEntity entity = entityBuilder.build();

//                entity.addPart("posting_id", new StringBody(postDataParams.get("posting_id")));
                post.setEntity(entity);
                try {
                    HttpResponse response = client.execute(post);
                    HttpEntity httpEntity = response.getEntity();

                    String result = EntityUtils.toString(httpEntity);
                    Log.i("result", result);
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        }.start();
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // TODO Auto-generated method stub
        switch (requestCode) {
            case 0:
                if (resultCode == RESULT_OK) {
                    Uri mDataUri = data.getData();
                    ImageView profile_image = (ImageView) findViewById(R.id.profile_image);
                    image_url = String.valueOf(data.getData());
                    Log.i("image_url", image_url);
                    Glide.with(this).load(mDataUri).bitmapTransform(new CropCircleTransformation(new CustomBitmapPool() {})).into(profile_image);
                    image_update_check=true;
                }
                break;
        }
    }


    public String getRealImagePath(Uri uriPath) { // uri에서 실제경로 찾아오기
        String[] proj = { MediaStore.Images.Media.DATA };
        @SuppressWarnings("deprecation")
        Cursor cursor = managedQuery(uriPath, proj, null, null, null);
        int index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        String path = cursor.getString(index);
//		path = path.substring(5);
        return path;

    }
    public class CustomBitmapPool implements BitmapPool {
        @Override
        public int getMaxSize() {
            return 0;
        }

        @Override
        public void setSizeMultiplier(float sizeMultiplier) {

        }

        @Override
        public boolean put(Bitmap bitmap) {
            return false;
        }

        @Override
        public Bitmap get(int width, int height, Bitmap.Config config) {
            return null;
        }

        @Override
        public Bitmap getDirty(int width, int height, Bitmap.Config config) {
            return null;
        }

        @Override
        public void clearMemory() {

        }

        @Override
        public void trimMemory(int level) {

        }
    }
}
