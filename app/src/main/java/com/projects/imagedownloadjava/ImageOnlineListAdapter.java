package com.projects.imagedownloadjava;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

public class ImageOnlineListAdapter extends BaseAdapter {

    Context context;
    List<String> list;

    public ImageOnlineListAdapter(Context context, List<String> list) {
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
        view = LayoutInflater.from(context).inflate(R.layout.row_image_list, viewGroup,false);

        TextView ivTitle=view.findViewById(R.id.ivTitle);
        ImageView iv_image=view.findViewById(R.id.iv_image);

        String url = list.get(i);
        String fileName = url.substring(url.lastIndexOf('/') + 1, url.length());
        ivTitle.setText(fileName);
        Picasso.get().load(url).into(iv_image);

        return view;    }
}
