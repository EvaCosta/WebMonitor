package com.webmonitor.db;

import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.MediumTest;

import com.webmonitor.model.Page;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.List;

import static androidx.test.core.app.ApplicationProvider.getApplicationContext;

@MediumTest
@RunWith(AndroidJUnit4.class)
public class DatabaseTest {
    private class DummyPages {
        private List<Page> data;
        public DummyPages() {

            data = new ArrayList<>();

            data.add(new Page(1L, "Resultado da Pesquisa do Google", "google.com", "link.com", (long) 60000, true, 1));
            data.add(new Page(2L, "Instagram", "instagram.com", "link.com", 2L * 60000, false, 1));
            data.add(new Page(3L, "Facebook", "facebook.com", "link.com", 3L * 60000, true, 1));
            data.add(new Page(4L, "IFBarbacena", "google.com", "link.com", 4L * 60000, false, 1));
            data.add(new Page(5L, "GitHub", "google.com", "link.com", 5L * 60000, true, 1));
            data.add(new Page(6L, "Uol", "google.com", "link.com", 6L * 60000, false, 1));
            data.add(new Page(7L, "Terra", "google.com", "link.com", 7L * 60000, true, 1));
            data.add(new Page(8L, "Gmail", "google.com", "link.com", 8L * 60000, false, 1));
        }


        public List<Page> getData() {
            return  data;
        }
    }

    public DummyPages pages = new DummyPages();

    @Test
    public void insertSuccessful() {
        Page page = pages.getData().get(1);
        new Database(getApplicationContext()).insert(page);
        Assert.assertTrue(new Database(getApplicationContext()).all().contains(page));
    }

    @Test
    public void deleteSuccessful() {
        Page page = pages.getData().get(1);
        new Database(getApplicationContext()).insert(page);
        Assert.assertTrue(new Database(getApplicationContext()).all().contains(page));
        new Database(getApplicationContext()).delete(page);
        Assert.assertFalse(new Database(getApplicationContext()).all().contains(page));
    }

    @Test
    public void updateSuccessful() {
        Page page = pages.getData().get(1);
        Database db = new Database(getApplicationContext());
        db.insert(page);

        Page beforeUpdate = db.all().get(db.all().indexOf(page));
        beforeUpdate.setTitle("updated title");
        db.update(beforeUpdate);

        Page afterUpdate = db.all().get(db.all().indexOf(page));
        Assert.assertEquals(beforeUpdate.getTitle(), afterUpdate.getTitle());
    }
}