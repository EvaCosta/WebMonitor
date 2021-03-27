package com.webmonitor.app;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.webmonitor.db.Database;
import com.webmonitor.model.Page;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;
import java.lang.annotation.Documented;
import java.util.Calendar;
import java.util.Date;
import java.util.List;


public class AlarmReceiver extends BroadcastReceiver {

    private static boolean noConnectivityNotificationSended = false;

    private Context context;
    private Intent intent;
    private Database db;
    private ConnectivityInfo connectivityInfo;

    @Override
    public void onReceive(Context context, Intent intent) {
        this.context = context;
        this.intent = intent;
        db = new Database(context);

        connectivityInfo = new ConnectivityInfo(context);
        if(connectivityInfo.hasConnection()){
            if(this.noConnectivityNotificationSended){
                AlertNotification.removeNoConectivityNotifications(context);
                this.noConnectivityNotificationSended = false;
            }
            List<Page> pages = getPages();
            selectPagesToCheck(pages);
        }else{
            if(this.noConnectivityNotificationSended == false){
                AlertNotification.sendNoConectivityNotification(context);
                this.noConnectivityNotificationSended = true;
            }
        }
    }

    /**** Aguardando serviço de persistencia *******/
    private List<Page> getPages(){
        return db.all();
    }

    private void selectPagesToCheck(List<Page> pages){
        Date currentTime = Calendar.getInstance().getTime();
        for (Page page : pages) {
            if(connectivityInfo.isWifiConnection() || page.getAllowMobileConnection()){
                long timeToCheck = page.getLastTime().getTime() + page.getTimeInterval();
                if (timeToCheck/1000L <= (currentTime.getTime()/1000L)) {
                    startPageCheck(page);
                    page.setLastTime(currentTime);
                }
            }
        }
    }
    
    /********* Aguardando serviço que verifica se a "Page" foi alterada *****/
    private void startPageCheck(Page page){
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Document doc;

                    if (page.getHttpRequestMethod().equals("GET")) {
                        doc = Jsoup.connect(page.getUrl()).get();
                    }
                    else {
                        doc = Jsoup.connect(page.getUrl()).post();
                    }
                    String newContent = doc.body().text();
                    double similarity = (similarity(page.getContent(), newContent) * -100) + 100.0;
                    Log.i("Check", "Similarity: " + page.getUrl() +"(" + similarity + ", "+ page.getPercentage() +")");

                    if (similarity > page.getPercentage()) {
                        AlertNotification.sendUpdateNotification(context, page);
                        page.setContent(newContent);
                        page.setLastUpdate(Calendar.getInstance().getTime());
                        db.update(page);
                    };
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        thread.start();
    }

    /**
     * Calculates the similarity (a number within 0 and 1) between two strings.
     */
    private static double similarity(String s1, String s2) {
        String longer = s1, shorter = s2;
        if (s1.length() < s2.length()) { // longer should always have greater length
            longer = s2; shorter = s1;
        }
        int longerLength = longer.length();
        if (longerLength == 0) { return 1.0; /* both strings are zero length */ }
    /* // If you have Apache Commons Text, you can use it to calculate the edit distance:
    LevenshteinDistance levenshteinDistance = new LevenshteinDistance();
    return (longerLength - levenshteinDistance.apply(longer, shorter)) / (double) longerLength; */
        return (longerLength - editDistance(longer, shorter)) / (double) longerLength;

    }

    // Example implementation of the Levenshtein Edit Distance
    // See http://rosettacode.org/wiki/Levenshtein_distance#Java
    private static int editDistance(String s1, String s2) {
        s1 = s1.toLowerCase();
        s2 = s2.toLowerCase();

        int[] costs = new int[s2.length() + 1];
        for (int i = 0; i <= s1.length(); i++) {
            int lastValue = i;
            for (int j = 0; j <= s2.length(); j++) {
                if (i == 0)
                    costs[j] = j;
                else {
                    if (j > 0) {
                        int newValue = costs[j - 1];
                        if (s1.charAt(i - 1) != s2.charAt(j - 1))
                            newValue = Math.min(Math.min(newValue, lastValue),
                                    costs[j]) + 1;
                        costs[j - 1] = lastValue;
                        lastValue = newValue;
                    }
                }
            }
            if (i > 0)
                costs[s2.length()] = lastValue;
        }
        return costs[s2.length()];
    }
}
