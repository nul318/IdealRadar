package idealradar.idealradar;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import com.nhn.android.naverlogin.OAuthLogin;
import com.nhn.android.naverlogin.OAuthLoginHandler;
import com.nhn.android.naverlogin.ui.view.OAuthLoginButton;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import static com.nhn.android.naverlogin.OAuthLogin.mOAuthLoginHandler;

public class Login extends AppCompatActivity {
    Context mContext = Login.this;
    String ClientID = "DdSDMKlhgPW2HcEougvi";
    String ClientSecret = "YNJkCJRDLe";
    String ClientName = "IdealRadar";
    OAuthLogin mOAuthLoginModule = OAuthLogin.getInstance(); //네이버 로그인 모듈
    Handler handler = new Handler();
    String profile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        naverLoginButton();
        naverModule();
    }

    protected void naverLoginButton(){
        mOAuthLoginModule.init(Login.this ,ClientID  , ClientSecret ,ClientName);
        OAuthLoginButton mOAuthLoginButton = (OAuthLoginButton) findViewById(R.id.buttonOAuthLoginImg);
        mOAuthLoginButton.setOAuthLoginHandler(mOAuthLoginHandler);
        mOAuthLoginButton.setBgResourceId(R.drawable.naver_login);
    }

    protected void naverModule(){



        /**
         * OAuthLoginHandler를 startOAuthLoginActivity() 메서드 호출 시 파라미터로 전달하거나 OAuthLoginButton
         객체에 등록하면 인증이 종료되는 것을 확인할 수 있습니다.
         */
        OAuthLoginHandler mOAuthLoginHandler = new OAuthLoginHandler() {
            @Override
            public void run(boolean success) {

                if (success) {
                    final String accessToken = mOAuthLoginModule.getAccessToken(mContext);
                    String refreshToken = mOAuthLoginModule.getRefreshToken(mContext);
                    long expiresAt = mOAuthLoginModule.getExpiresAt(mContext);
                    String tokenType = mOAuthLoginModule.getTokenType(mContext);


                    new Thread() {
                        @Override
                        public void run() {
                            profile = mOAuthLoginModule.requestApi(mContext, accessToken, "https://openapi.naver.com/v1/nid/me");
                            Log.i("NAVER_PROFILE", profile);

                            JSONObject mainObject = null;
                            try {
                                mainObject = new JSONObject(profile);
                                JSONObject response = mainObject.getJSONObject("response");
                                String user_email = response.getString("email") + " ";
                                String user_id = response.getString("id");
                                String user_gender = response.getString("gender");
                                String user_name = response.getString("name");
                                String user_image = response.getString("profile_image");
                                String user_age = response.getString("age");

                                submit(user_email, user_id , user_gender, user_name, user_image, user_age);

                                //아이디, 이름, 나이, 학교, 전공, 학번, 성별, 프로필사진
                            } catch (JSONException e) {
                                e.printStackTrace();
                            } catch (UnsupportedEncodingException e) {
                                e.printStackTrace();
                            }

                            handler.post(new Runnable() {
                                @Override
                                public void run() {
                                    // TODO Auto-generated method stub
                                    Intent intent = new Intent(Login.this, Home.class);
                                    intent.putExtra("profile", profile);
                                    startActivity(intent);
                                }
                            });

                        }
                    }.start();








//                    mOauthAT.setText(accessToken);
//                    mOauthRT.setText(refreshToken);
//                    mOauthExpires.setText(String.valueOf(expiresAt));
//                    mOauthTokenType.setText(tokenType);
//                    mOAuthState.setText(mOAuthLoginModule.getState(mContext).toString());

                } else {
                    String errorCode = mOAuthLoginModule.getLastErrorCode(mContext).getCode();
                    String errorDesc = mOAuthLoginModule.getLastErrorDesc(mContext);
                    Toast.makeText(mContext, "errorCode:" + errorCode
                            + ", errorDesc:" + errorDesc, Toast.LENGTH_SHORT).show();
                }
            };
        };
        mOAuthLoginModule.startOauthLoginActivity( (Activity) mContext, mOAuthLoginHandler);
    }


    private void submit(final String user_email, final String user_id , final String user_gender, final String user_name, final String user_image, final String user_age) throws UnsupportedEncodingException {
        new Thread() {
            @Override
            public void run() {
                // TODO Auto-generated method stub
                String URL = "http://hanea8199.vps.phps.kr/IdealRadar/LogIn.php";
                HttpClient httpClient = new DefaultHttpClient();
                HttpPost httpPost = new HttpPost(URL);
                List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(6);
                try {
                    nameValuePairs.add(new BasicNameValuePair("email", URLEncoder.encode(user_email,"UTF-8")));
                    nameValuePairs.add(new BasicNameValuePair("user_id", URLEncoder.encode(user_id,"UTF-8")));
                    nameValuePairs.add(new BasicNameValuePair("gender", URLEncoder.encode(user_gender,"UTF-8")));
                    nameValuePairs.add(new BasicNameValuePair("name", URLEncoder.encode(user_name,"UTF-8")));
                    nameValuePairs.add(new BasicNameValuePair("image_path", URLEncoder.encode(user_image,"UTF-8")));
                    nameValuePairs.add(new BasicNameValuePair("age", URLEncoder.encode(user_age,"UTF-8")));
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
                try {
                    httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
                    HttpResponse response = httpClient.execute(httpPost);
                    Log.d("Http Post Request:", nameValuePairs.toString());
                    Log.d("Http Post Response:", response.toString());
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                } catch (ClientProtocolException e) {
                    // Log exception
                    e.printStackTrace();
                } catch (IOException e) {
                    // Log exception
                    e.printStackTrace();
                }
            }
        }.start();
    }

}
