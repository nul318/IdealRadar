package idealradar.idealradar;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.sun.jna.platform.win32.WinDef;

import java.util.ArrayList;

/**
 * Created by parkhanee on 2016. 11. 13..
 */

public class MsgListAdapter extends BaseAdapter {
    Context context;
    ArrayList<String> textArray;
    ArrayList<String> senderArray;
    ArrayList<Boolean> isSentArray;

    public MsgListAdapter(Context context, ArrayList<String> textArray, ArrayList<String> senderArray, ArrayList<Boolean> isSent){
        this.context = context;
        this.textArray = textArray;
        this.senderArray = senderArray;
        this.isSentArray = isSent;
    }

    @Override
    public int getCount() {
        return textArray.size();
    }

    @Override
    public Object getItem(int position) {
        return textArray.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View v, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        ViewHolder holder;
        if (v == null) {
            holder = new ViewHolder();
            v = inflater.inflate(R.layout.listview_msg_list, null);
            holder.tv_sender = (TextView) v.findViewById(R.id.textView);
            holder.tv_text = (TextView) v.findViewById(R.id.textView2);
            holder.iv = (ImageView) v.findViewById(R.id.msgIcon);
            v.setTag(holder);
        } else {
            holder = (ViewHolder) v.getTag(); // we call the view created before to not create a view in each time
        }

        holder.tv_sender.setText(senderArray.get(position));
        holder.tv_text.setText(textArray.get(position));
        if(isSentArray.get(position)){
            holder.iv.setImageResource(R.drawable.send);
        }else {
            holder.iv.setImageResource(R.drawable.receive);
        }


        return v;
    }

    private static class ViewHolder{
        TextView tv_sender;
        TextView tv_text;
        ImageView iv;
    }
}
