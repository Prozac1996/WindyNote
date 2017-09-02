package cn.lcu.threepersontravel.WindyNote;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Administrator on 2017/6/10.
 */
public class CategoryAdapter extends BaseAdapter{

    private Context mContext;
    private List<Category> mList;

    public CategoryAdapter(Context mContext, List<Category> mList) {
        this.mContext = mContext;
        this.mList = mList;
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
            v = LayoutInflater.from(mContext).inflate(R.layout.layout_item_category, parent, false);
        TextView tv_name = (TextView) v.findViewById(R.id.tv_category_name);

        Category category = mList.get(position);
        tv_name.setText(category.getmName());

        return v;
    }

    public void notifyData(List list){
        mList.clear();
        mList.addAll(list);
        notifyDataSetChanged();
    }

    public int getPosition(long id){
        for(Category c : mList){
            if(c.getmId() == id){
                return mList.indexOf(c);
            }
        }
        return 0;
    };
}
