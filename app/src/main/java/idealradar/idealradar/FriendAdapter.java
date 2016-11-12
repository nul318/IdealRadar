package idealradar.idealradar;

import android.content.Context;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Kim Gyu Hwan on 2016-11-12.
 */

public class FriendAdapter extends ArrayAdapter implements View.OnClickListener {

    private ArrayList<Friend> listViewItemList = new ArrayList<Friend>() ;
    TextView friendidTextView;
    ImageButton btnCheck;
    ImageButton btnCancel;

    int resourceId;

    FriendAdapter(Context context, int resource, ArrayList<Friend> list)
    {
        super(context, resource, list) ;
        this.resourceId = resource ;

    }




    @Override
    public int getCount() {
        return listViewItemList.size();
    }

    @Override
    public Object getItem(int i) {
        return listViewItemList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }


    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final int pos = position;
        final Context context = parent.getContext();

        // "listview_item" Layout을 inflate하여 convertView 참조 획득.
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.friend_pop_list, parent, false);
        }

        // 화면에 표시될 View(Layout이 inflate된)으로부터 위젯에 대한 참조 획득
        friendidTextView = (TextView) convertView.findViewById(R.id.friend_id) ;
        btnCheck = (ImageButton) convertView.findViewById(R.id.BtnCheck) ;
        btnCancel = (ImageButton) convertView.findViewById(R.id.BtnCancel);
        // Data Set(listViewItemList)에서 position에 위치한 데이터 참조 획득
        Friend listViewItem = listViewItemList.get(position);


        // 아이템 내 각 위젯에 데이터 반영
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listViewItemList.remove(position);
                notifyDataSetChanged();
            }
        });

        friendidTextView.setText(listViewItem.get_User_id());
        return convertView;
    }
    // 아이템 데이터 추가를 위한 함수. 개발자가 원하는대로 작성 가능.
    public void addItem(String id, double rate) {
        Friend item = new Friend(id,rate);
        listViewItemList.add(item);
    }

    @Override
    public void onClick(View view) {

    }
}
