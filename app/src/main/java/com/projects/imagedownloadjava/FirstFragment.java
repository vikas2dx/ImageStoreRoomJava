package com.projects.imagedownloadjava;

import android.Manifest;
import android.app.DownloadManager;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

public class FirstFragment extends Fragment {

    GridView rv_imageList;
    ImageOnlineListAdapter imageOnlineListAdapter;
    ImageOfflineListAdapter imageOfflineListAdapter;
    List<String> list;
    int PERMISSION_ALL = 1;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_first, container, false);
        list=new ArrayList<>();

        rv_imageList=view.findViewById(R.id.rv_imageList);
        if (ActivityCompat.checkSelfPermission(getContext(),
                Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(getContext(),
                        Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE,
                            Manifest.permission.READ_EXTERNAL_STORAGE},
                    PERMISSION_ALL);
        } else {
            Log.e("DB", "PERMISSION GRANTED");
            if (Network.isInternetAvailable()) {
                getOnlineData();

            } else {
                getOfflineData();
            }
        }





        return view;

    }

    private void getOfflineData() {

        AppDatabaseRoom appDatabaseRoom = AppDatabaseRoom.getInstance(getActivity());

        AppExecutors.getInstance().diskIO().execute(new Runnable() {
            @Override
            public void run() {

//                String filexits = appDatabaseRoom.imagesDao().getItembyFilename("Sample-jpg-image-50kb.jpg");


                List<ImagesTb> newsNewRoomList = appDatabaseRoom.imagesDao().getAll();
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                        List<Bitmap> listbitmap = new ArrayList();
                        List<String> listfilename = new ArrayList();

                        if (newsNewRoomList != null) {
                            if (!newsNewRoomList.isEmpty()) {

                                for (int i = 0; i < newsNewRoomList.size(); i++) {

                                    try {

                                        File f = new File(newsNewRoomList.get(i).getFilepath());
                                        Bitmap b = BitmapFactory.decodeStream(new FileInputStream(f));
                                        listfilename.add(newsNewRoomList.get(i).getFilename());
                                        listbitmap.add(b);

                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }
                                }

                                imageOfflineListAdapter = new ImageOfflineListAdapter(getActivity().getApplicationContext(), listbitmap, listfilename);
                                rv_imageList.setAdapter(imageOfflineListAdapter);
                            }
                        }

                    }
                });


            }
        });


    }

    private void getOnlineData() {

        //static link
        list.add("https://sample-videos.com/img/Sample-jpg-image-50kb.jpg");
        list.add("https://sample-videos.com/img/Sample-jpg-image-100kb.jpg");
        list.add("https://sample-videos.com/img/Sample-jpg-image-200kb.jpg");
        list.add("https://sample-videos.com/img/Sample-jpg-image-500kb.jpg");
        list.add("https://sample-videos.com/img/Sample-jpg-image-1mb.jpg");


        //two adpter 1 online 1 offline

        imageOnlineListAdapter = new ImageOnlineListAdapter(getContext(), list);
        rv_imageList.setAdapter(imageOnlineListAdapter);

        for (String url : list) {

            //async block main thrad , thread pool
            String fileName =
                    url.substring(url.lastIndexOf('/') + 1, url.length());//get file last after /

            ThreadPoolExecutor executor = (ThreadPoolExecutor) Executors.newFixedThreadPool(list.size());

            DowonloadNewTasks dowonloadNewTask = new DowonloadNewTasks(
                    url,
                    getActivity().getApplicationContext(),
                    fileName,
                    Environment.getExternalStorageDirectory()
                            .toString() + "/ImageLoaderFiles"
            );
            //download image from url cand convert to bitmap
            executor.execute(dowonloadNewTask);
//        }


        }
    }


    private class DowonloadNewTasks implements Runnable {
        String url;
        Context applicationContext;
        String fileName, path;

        public DowonloadNewTasks(String url, Context applicationContext, String fileName, String path) {
            this.url = url;
            this.applicationContext = applicationContext;
            this.fileName = fileName;
            this.path = path;

        }

        @Override
        public void run() {
            try {

                AppDatabaseRoom appDatabaseRoom = AppDatabaseRoom.getInstance(applicationContext);

                //if not exits insert
                String filexits = appDatabaseRoom.imagesDao().getItembyFilename(fileName);

                if (filexits==null)
                {
                    ImagesTb imagesTb = new ImagesTb();
                    imagesTb.setFilename(fileName);
                    imagesTb.setFilepath(path + "/" + fileName);

                    long status=appDatabaseRoom.imagesDao().insert(imagesTb);



                    downloadFile(url, fileName);//internal storage download

                    Log.d("TAG","status="+status);
                }



            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void downloadFile(String url, String fileName) {


        File direct = new File(
                Environment.getExternalStorageDirectory()
                        .toString() + "/ImageLoaderFiles"
        );
        if (!direct.exists()) {
            direct.mkdirs();
        }
        File applictionFile = new File(Environment.getExternalStorageDirectory()
                .toString() + "/ImageLoaderFiles" + "/" + fileName);

        if (applictionFile.isFile() || applictionFile.canRead()) {
            Log.d("TAG", "File exits");
        } else {

            DownloadManager mgr = (DownloadManager) getActivity().getSystemService(Context.DOWNLOAD_SERVICE);
            Uri downloadUri = Uri.parse(url);
            DownloadManager.Request request = new DownloadManager.Request(
                    downloadUri
            );
            request.setAllowedNetworkTypes(
                    DownloadManager.Request.NETWORK_WIFI |
                            DownloadManager.Request.NETWORK_MOBILE
            )
                    .setAllowedOverRoaming(false).setTitle(fileName)
                    .setDescription("Something useful. No, really.")
                    .setDestinationInExternalPublicDir("/ImageLoaderFiles", fileName);

            mgr.enqueue(request);

        }


    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            // permissions granted.

            if (Network.isInternetAvailable()) {
                getOnlineData();

            } else {
                getOfflineData();
            }

        } else {
         getActivity().finish();
            // permissions list of don't granted permission
        }


    }
}
