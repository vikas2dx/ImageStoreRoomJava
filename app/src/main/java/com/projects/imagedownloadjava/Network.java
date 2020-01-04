package com.projects.imagedownloadjava;

import android.content.Context;
import android.net.ConnectivityManager;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class Network {

    public static boolean isInternetAvailable() {

        ExecutorService executor = Executors.newSingleThreadExecutor();

        Callable<Boolean> callable = new Callable<Boolean>() {
            @Override
            public Boolean call() {
                try {
                    InetAddress ipAddr = InetAddress.getByName("google.com");
                    return !ipAddr.equals("");
                } catch (UnknownHostException e) {

                }
                return false;
            }
        };

        try {
            Future<Boolean> future = executor.submit(callable);
            boolean status = future.get();// returns 2 or raises an exception if the thread dies, so safer
            executor.shutdown();
            return status;
        } catch (Exception e) {
            return false;
        }
    }
}
