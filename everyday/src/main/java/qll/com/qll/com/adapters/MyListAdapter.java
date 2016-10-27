package qll.com.qll.com.adapters;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/10/27.
 */

public class MyListAdapter extends BaseAdapter{
    private Context context;
    private List<List<String>> data;
    private List<Integer> imageResources;

    public MyListAdapter(Context context,List<List<String>> data,List<Integer> imageResources){
        this.context = context;
        this.data = data;
        this.imageResources = imageResources;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return null;
    }

    @Override
    public int getCount() {
        return 0;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    private class ViewHolder{
        private TextView tvFTitle;
        private TextView tvSTitle;
        private ImageView imHead;
        private ImageButton ibInfo;
    }
}
