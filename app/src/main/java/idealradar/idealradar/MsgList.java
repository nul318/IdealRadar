package idealradar.idealradar;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class MsgList extends AppCompatActivity {

    ListView listView;
    MsgListAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_msg_list);



        final ArrayList<String> textArray = new ArrayList<>();
        textArray.add("text 1 ");
        textArray.add("text 2");
        final ArrayList<String> senderArray = new ArrayList<>();
        senderArray.add("sender 1");
        senderArray.add("sender 2");
        final ArrayList<Boolean> isSentArray = new ArrayList<>();
        isSentArray.add(true);
        isSentArray.add(false);

        listView = (ListView) findViewById(R.id.mListView);
        mAdapter = new MsgListAdapter(MsgList.this,textArray,senderArray,isSentArray);
        listView.setAdapter(mAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent i = new Intent(MsgList.this,ReadMsg.class);
                i.putExtra("text",textArray.get(position));
                i.putExtra("sender",senderArray.get(position));
                startActivity(i);
            }
        });
    }
}
