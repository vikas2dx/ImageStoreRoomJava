package com.projects.imagedownloadjava;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class ImageOfflineListAdapter extends BaseAdapter {

    Context context;
    List<Bitmap> list;
    List<String> filenanme;

    public ImageOfflineListAdapter(Context context, List<Bitmap> list, List<String> filenanme) {
        this.context=context;
        this.list=list;
        this.filenanme=filenanme;
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
        ivTitle.setText(filenanme.get(i));
        iv_image.setImageBitmap(list.get(i));
        return view;
    }
}
