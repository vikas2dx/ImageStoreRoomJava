package com.projects.imagedownloadjava;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.util.ArrayList;
import java.util.List;

public class SecondFragment extends Fragment {
    GridView grid_color;
    List<String> list;
    ImageColorAdapter imageColorAdapter;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_second, container, false);

        grid_color=view.findViewById(R.id.grid_color);
        list=new ArrayList<>();

        list.add("#FF5733");
        list.add("#52FF33");
        list.add("#FFEC33");
        list.add("#33FF5E");
        list.add("#33FFE3");
        list.add("#52FF33");
        list.add("#C133FF");
        list.add("#FF3380");
        list.add("#123635");
        list.add("#127D0A");
        imageColorAdapter= new ImageColorAdapter(getContext(),list);
        grid_color.setAdapter(imageColorAdapter);

        return view;

    }
}
