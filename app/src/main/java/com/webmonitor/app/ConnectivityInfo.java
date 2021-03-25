package com.webmonitor.app;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.Network;
import android.util.Log;

public class ConnectivityInfo {

    Context context;
    ConnectivityManager connectivityManager;

    public ConnectivityInfo(Context context){
        this.context = context;
        this.connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
    }

    public boolean hasConnection(){
        Log.i("WebMonitor", "ConnectivityInfo.hasConnection");

        boolean wifiConn = false;
        boolean mobileConn = false;
        for (Network network : this.connectivityManager.getAllNetworks()) {
            android.net.NetworkInfo networkInfo = this.connectivityManager.getNetworkInfo(network);
            if (networkInfo.getType() == ConnectivityManager.TYPE_WIFI) {
                wifiConn = networkInfo.isConnected();
            }
            if (networkInfo.getType() == ConnectivityManager.TYPE_MOBILE) {
                mobileConn = networkInfo.isConnected();
            }
        }

        return wifiConn || mobileConn;
    }

    public boolean isWifiConnection(){
        Log.i("WebMonitor", "ConnectivityInfo.isWifiConnection");

        for (Network network : this.connectivityManager.getAllNetworks()) {
            android.net.NetworkInfo networkInfo = this.connectivityManager.getNetworkInfo(network);
            if (networkInfo.getType() == ConnectivityManager.TYPE_WIFI) {
                return networkInfo.isConnected();
            }
        }
        return false;
    }
}
