package com.webmonitor.app;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkInfo;
import android.util.Log;

import com.webmonitor.model.DummyPages;
import com.webmonitor.model.Page;

import java.util.Calendar;
import java.util.Date;
import java.util.List;


public class AlarmReceiver extends BroadcastReceiver {


    private Context context;
    private Intent intent;
    private ConnectivityInfo connectivityInfo;

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.i("WebMonitor", "AlarmReceiver.onReceive");

        this.context = context;
        this.intent = intent;

        connectivityInfo = new ConnectivityInfo(context);
        if(connectivityInfo.hasConnection()){
            List<Page> pages = getPages();
            selectPagesToCheck(pages);
        }
    }

    /**** Aguardando serviço de persistencia *******/
    private List<Page> getPages(){
        //TODO pegar as "Pages" do serviço de persistência
        return DummyPages.data;
    }

    private void selectPagesToCheck(List<Page> pages){
        Log.i("WebMonitor", "AlarmReceiver.selectPagesToCheck");
        Date currentTime = Calendar.getInstance().getTime();
        for (Page page : pages) {
            if(connectivityInfo.isWifiConnection() || page.getAllowMobileConnection()){
                Long timeToCheck = page.getLastTime().getTime() + page.getTimeInterval();
                if (timeToCheck/1000L <= (currentTime.getTime()/1000L)) {
                    readyToCheck(page);
                    page.setLastTime(currentTime);
                }
            }
        }
    }

    private void readyToCheck(Page page){

        boolean wasModified = checkPage(page);
        if(wasModified){
            AlertNotification.sendNotification(context, intent, page);
        }
    }

    /********* Aguardando serviço que verifica se a "Page" foi alterada *****/
    private boolean checkPage(Page page){
        //TODO verificar se hoube modificação
        return true;
    }

}
