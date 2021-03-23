package com.webmonitor.app;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.webmonitor.model.DummyPages;
import com.webmonitor.model.Page;

import java.util.Calendar;
import java.util.Date;


public class AlarmReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.i("WebMonitor", "AlarmReceiver.onReceive");

        checkPages(context, intent);
    }

    private void checkPages(Context context, Intent intent){
        Log.i("WebMonitor", "AlarmReceiver.checkPages");
        Date currentTime = Calendar.getInstance().getTime();
        //TODO pegar as páginas do serviço de persistência
        for (Page page : DummyPages.data) {
            Long timeToCheck = page.getLastTime().getTime() + page.getTimeInterval() * 60 * 1000;
            if (timeToCheck < currentTime.getTime()) {
                //TODO verificar se hoube modificação na página
                AlertNotification.sendNotification(context, intent, page);
                page.setLastTime(currentTime);
            }
        }
    }
}
