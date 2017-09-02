package cn.lcu.threepersontravel.WindyNote;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Administrator on 2017/6/4.
 */
public class NoteAdapter extends BaseAdapter {

    private Context mContext;
    private List<Note> mList;

    public NoteAdapter(Context context, List<Note> list) {
        mContext = context;
        mList = list;
    }

    public void notifyData(List<Note> list){
        mList.clear();
        mList.addAll(list);
        notifyDataSetChanged();

    }


    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public Object getItem(int position) {
        return mList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return mList.get(position).getmId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v;
        if (convertView != null)
            v = convertView;
        else
            v = LayoutInflater.from(mContext).inflate(R.layout.layout_list_item, parent, false);
        TextView tv_title = (TextView) v.findViewById(R.id.list_item_title);
        TextView tv_date = (TextView) v.findViewById(R.id.list_item_date);

        Note note = mList.get(position);
        tv_title.setText(note.getmTitle());
        tv_date.setText(note.getmDate());
        return v;
    }
}
