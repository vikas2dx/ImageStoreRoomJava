package com.projects.imagedownloadjava;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.List;

public class ImageColorAdapter extends BaseAdapter {

    Context context;
    List<String> list;

    public ImageColorAdapter(Context context, List<String> list) {
        this.context = context;
        this.list = list;

    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int i) {
        return list.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        view = LayoutInflater.from(context).inflate(R.layout.row_color_list, viewGroup, false);

        TextView ivTitle = view.findViewById(R.id.ivTitle);
        RelativeLayout iv_image = view.findViewById(R.id.iv_image);

        ivTitle.setText(list.get(i));
        iv_image.setBackgroundColor(Color.parseColor(list.get(i)));

        return view;
    }
}
