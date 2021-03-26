package com.webmonitor.app;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.webmonitor.db.Database;
import com.webmonitor.model.Page;

import java.util.Calendar;
import java.util.Date;
import java.util.List;


public class AlarmReceiver extends BroadcastReceiver {


    private Context context;
    private Intent intent;
    private Database db;
    private ConnectivityInfo connectivityInfo;

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.i("WebMonitor", "AlarmReceiver.onReceive");

        this.context = context;
        this.intent = intent;
        db = new Database(context);

        connectivityInfo = new ConnectivityInfo(context);
        if(connectivityInfo.hasConnection()){
            List<Page> pages = getPages();
            selectPagesToCheck(pages);
        }
    }

    /**** Aguardando serviço de persistencia *******/
    private List<Page> getPages(){
        return db.all();
    }

    private void selectPagesToCheck(List<Page> pages){
        Log.i("WebMonitor", "AlarmReceiver.selectPagesToCheck");
        Date currentTime = Calendar.getInstance().getTime();
        for (Page page : pages) {
            if(connectivityInfo.isWifiConnection() || page.getAllowMobileConnection()){
                long timeToCheck = page.getLastTime().getTime() + page.getTimeInterval();
                if (timeToCheck/1000L <= (currentTime.getTime()/1000L)) {
                    readyToCheck(page);
                    page.setLastTime(currentTime);
                }
            }
        }
    }

    private void readyToCheck(Page page){

        boolean wasModified = checkPage(page);
        if(!MainActivity.isActivityVisible() && wasModified){
            AlertNotification.sendNotification(context, intent, page);
        }
    }

    /********* Aguardando serviço que verifica se a "Page" foi alterada *****/
    private boolean checkPage(Page page){
        //TODO verificar se hoube modificação
        return true;
    }

}
