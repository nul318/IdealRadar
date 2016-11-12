package idealradar.idealradar;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class Home extends AppCompatActivity implements View.OnClickListener{

    ImageButton btn_profile,btn_alert,btn_map,btn_friend,btn_chat;

    FriendAdapter friendAdapter=null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        btn_profile=(ImageButton)findViewById(R.id.home_profile);
        btn_profile.setOnClickListener(this);

        btn_alert=(ImageButton)findViewById(R.id.home_alert);
        btn_alert.setOnClickListener(this);

        btn_map=(ImageButton)findViewById(R.id.home_map);
        btn_map.setOnClickListener(this);

        btn_friend=(ImageButton)findViewById(R.id.home_friend);
        btn_friend.setOnClickListener(this);

        btn_chat=(ImageButton)findViewById(R.id.home_chat);
        btn_chat.setOnClickListener(this);

    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
//		if(dtToggle.onOptionsItemSelected(item)){
//			return true;
//		}
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }


    @Override
    public void onClick(View view) {
        Intent it;
        switch (view.getId())
        {
            case R.id.home_alert:
                AlertDialog.Builder alertBuilder = new AlertDialog.Builder(
                        Home.this);
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
            case R.id.home_map:
                it=new Intent(getApplicationContext(),Map.class);
                startActivity(it);
                finish();
                break;


        }
    }
}
