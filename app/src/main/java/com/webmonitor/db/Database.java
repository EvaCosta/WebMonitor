package com.webmonitor.db;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import com.webmonitor.model.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Database {
    private static final String DATABASE_NAME = "web_monitor";


    private static final int DATABASE_ACCESS = 0;
    private static final String SQL_STRUCT = "CREATE TABLE IF NOT EXISTS pages(id_ INTEGER PRIMARY KEY AUTOINCREMENT, title TEXT NOT NULL,  url TEXT NOT NULL,  imageSource TEXT NOT NULL, timeInterval INTEGER DEFAULT 10000 NOT NULL, allowMobileConnection INTEGER NOT NULL); ";
    private static final String SQL_INSERT = "INSERT INTO pages (title, imageSource, url, timeInterval, allowMobileConnection) VALUES ('%s', '%s', '%s', '%d', '%d');";
    private static final String SQL_SELECT_ALL = "SELECT * FROM pages;";
    private static final String SQL_CLEAR = "DROP TABLE IF EXISTS pages;";
    private SQLiteDatabase database;
    private Cursor cursor;
    private int indexID, indexTitle, indexUrl, indexImageSource, indexTimeInterval, indexAllowMobileConnection;

    public Database(Context context) {
        database = context.openOrCreateDatabase(DATABASE_NAME, DATABASE_ACCESS, null);
        database.execSQL(SQL_STRUCT);
    }

    public void clear() {
        database.execSQL(SQL_CLEAR);
    }

    public void close() {
        database.close();
    }

    public void insert(Page page) {
        String query = String.format(SQL_INSERT, page.getTitle(), page.getUrl(), page.getImageSource(), page.getTimeInterval(), page.getAllowMobileConnection() == true ? 1 : 0);
        database.execSQL(query);
    }

    public List<Page> all() {
        List<Page> pages = new ArrayList<Page>();
        Page page;

        cursor = database.rawQuery(SQL_SELECT_ALL, null);

        if(cursor.moveToFirst()) {
            indexID = cursor.getColumnIndex("id_");
            indexTitle = cursor.getColumnIndex("title");
            indexUrl = cursor.getColumnIndex("url");
            indexImageSource = cursor.getColumnIndex("imageSource");
            indexTimeInterval = cursor.getColumnIndex("timeInterval");
            indexAllowMobileConnection = cursor.getColumnIndex("timeInterval");

            do {
                page = new Page();
                page.setId(cursor.getLong(indexID));
                page.setTitle(cursor.getString(indexTitle));
                page.setUrl(cursor.getString(indexUrl));
                page.setImageSource(cursor.getString(indexImageSource));
                page.setTimeInterval(cursor.getLong(indexTimeInterval));
                page.setAllowMobileConnection(cursor.getInt(indexAllowMobileConnection) == 1 ? true : false);

                pages.add(page);
            } while (cursor.moveToNext());
        }

        cursor.close();
        return pages;
    }
}
