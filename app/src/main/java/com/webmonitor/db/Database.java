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
    private static final String SQL_STRUCT = "CREATE TABLE IF NOT EXISTS pages(id_ INTEGER PRIMARY KEY AUTOINCREMENT, title TEXT NOT NULL,  url TEXT NOT NULL,  imageSource TEXT NOT NULL, timeInterval INTEGER DEFAULT 10000 NOT NULL, allowMobileConnection INTEGER NOT NULL, percentage DOUBLE DEFAULT 1 NOT NULL, lastTime INTEGER NOT NULL, content TEXT, lastUpdate INTEGER NOT NULL); ";
    private static final String SQL_INSERT = "INSERT INTO pages (title, imageSource, url, timeInterval, allowMobileConnection, percentage, lastTime, content, lastUpdate) VALUES ('%s', '%s', '%s', '%d', '%d', '%f', '%d', '%s', '%d');";
    private static final String SQL_SELECT_ALL = "SELECT * FROM pages;";
    private static final String SQL_CLEAR = "DROP TABLE IF EXISTS pages;";
    private static final String SQL_UPDATE = "UPDATE pages SET title = '%s', imageSource = '%s', url = '%s', timeInterval = %d, allowMobileConnection = %d, percentage = %f, lastTime = %d, content = '%s' WHERE id_ = %d;";
    private static final String SQL_UPDATE_LAST_CHECK = "UPDATE pages SET lastTime = %d WHERE id_ = %d;";
    private static final String SQL_UPDATE_LAST_UPDATE = "UPDATE pages SET lastUpdate = %d WHERE id_ = %d;";
    private static final String SQL_DELETE = "DELETE FROM pages WHERE id_ = %d;";
    private SQLiteDatabase database;
    private Cursor cursor;
    private int indexID, indexTitle, indexUrl, indexImageSource, indexTimeInterval, indexAllowMobileConnection, indexPercentage, indexLastTime, indexContent, indexLastUpdate;

    public Database(Context context) {
        //context.deleteDatabase(DATABASE_NAME);
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
        String query = String.format(
                SQL_INSERT,
                page.getTitle() != null ? page.getTitle() : "",
                page.getImageSource() != null ? page.getImageSource() : "",
                page.getUrl() != null ? page.getUrl() : "",
                page.getTimeInterval() != null ? page.getTimeInterval() : 0,
                page.getAllowMobileConnection() == true ? 1 : 0,
                page.getPercentage(),
                page.getLastTime() != null ? page.getLastTime().getTime() : 0,
                page.getContent() != null ? page.getContent() : "",
                page.getLastUpdate() != null ? page.getLastUpdate().getTime() : 0
        );

        //System.out.println("--------------------------------------------");
        //System.out.println(page.getLastTime());

        database.execSQL(query);
    }

    public void update(Page updatedPage){
        String query = String.format(
                SQL_UPDATE,
                updatedPage.getTitle() != null ? updatedPage.getTitle() : "",
                updatedPage.getImageSource() != null ? updatedPage.getImageSource() : "",
                updatedPage.getUrl() != null ? updatedPage.getUrl() : "",
                updatedPage.getTimeInterval() != null ? updatedPage.getTimeInterval() : 0,
                updatedPage.getAllowMobileConnection() == true ? 1 : 0,
                updatedPage.getPercentage() != null ? updatedPage.getPercentage() : 0,
                updatedPage.getLastTime() != null ? updatedPage.getLastTime().getTime() : 0,
                updatedPage.getContent() != null ? updatedPage.getContent() : "",
                updatedPage.getId() != null ? updatedPage.getId() : 0
        );
        database.execSQL(query);
    }

    public void updateLastTime(long pageId, Date lastCheck){
        String query = String.format(
                SQL_UPDATE_LAST_CHECK,
                lastCheck.getTime(),
                pageId
        );
        database.execSQL(query);
    }

    public void updateLastTime(Page page){
        String query = String.format(
                SQL_UPDATE_LAST_CHECK,
                page.getLastTime().getTime(),
                page.getId()
        );
        database.execSQL(query);
    }

    public void updateLastUpdate(long pageId, Date lastUpdate){
        String query = String.format(
                SQL_UPDATE_LAST_UPDATE,
                lastUpdate.getTime(),
                pageId
        );
        database.execSQL(query);
    }

    public void updateLastUpdate(Page page){
        String query = String.format(
                SQL_UPDATE_LAST_UPDATE,
                page.getLastUpdate().getTime(),
                page.getId()
        );
        database.execSQL(query);
    }

    public void delete(Page page){
        String query = String.format(
                SQL_DELETE,
                page.getId()
        );
        database.execSQL(query);
    }

    public void delete(long id){
        String query = String.format(
                SQL_DELETE,
                id
        );
        database.execSQL(query);
    }

    public List<Page> all() {
        List<Page> pages = new ArrayList<Page>();
        Page page;
        cursor = database.rawQuery(SQL_SELECT_ALL, null);

        if(cursor.moveToFirst()) {
            indexID = cursor.getColumnIndex("id_");
            indexTitle = cursor.getColumnIndex("title");
            indexImageSource = cursor.getColumnIndex("imageSource");
            indexUrl = cursor.getColumnIndex("url");
            indexTimeInterval = cursor.getColumnIndex("timeInterval");
            indexAllowMobileConnection = cursor.getColumnIndex("allowMobileConnection");
            indexPercentage = cursor.getColumnIndex("percentage");
            indexLastTime = cursor.getColumnIndex("lastTime");
            indexContent = cursor.getColumnIndex("content");
            indexLastUpdate = cursor.getColumnIndex("lastUpdate");

            do {
                page = new Page();
                page.setId(cursor.getLong(indexID));
                page.setTitle(cursor.getString(indexTitle));
                page.setImageSource(cursor.getString(indexImageSource));
                page.setUrl(cursor.getString(indexUrl));
                page.setTimeInterval(cursor.getLong(indexTimeInterval));
                page.setAllowMobileConnection(cursor.getInt(indexAllowMobileConnection) == 1);
                page.setPercentage(cursor.getDouble(indexPercentage));
                page.setLastTime(new Date(cursor.getLong(indexLastTime)));
                page.setContent(cursor.getString(indexContent));
                page.setLastUpdate(new Date(cursor.getLong(indexLastUpdate)));
                pages.add(page);
            } while (cursor.moveToNext());
        }

        cursor.close();
        return pages;
    }
}