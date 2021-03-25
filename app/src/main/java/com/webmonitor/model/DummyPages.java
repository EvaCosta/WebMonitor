package com.webmonitor.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static java.util.Arrays.asList;

//PARA TESTE
public class DummyPages {
    private List<Page> data;
    public DummyPages() {


        data = new ArrayList<Page>();

        data.add(new Page(1L, "Resultado da Pesquisa do Google", "google.com", "link.com", 1L * 60000, true));
        data.add(new Page(2L, "Instagram", "instagram.com", "link.com", 2L * 60000, false));
        data.add(new Page(3L, "Facebook", "facebook.com", "link.com", 3L * 60000, true));
        data.add(new Page(4L, "IFBarbacena", "google.com", "link.com", 4L * 60000, false));
        data.add(new Page(5L, "GitHub", "google.com", "link.com", 5L * 60000, true));
        data.add(new Page(6L, "Uol", "google.com", "link.com", 6L * 60000, false));
        data.add(new Page(7L, "Terra", "google.com", "link.com", 7L * 60000, true));
        data.add(new Page(8L, "Gmail", "google.com", "link.com", 8L * 60000, false));
    }

    public List<Page> getData() {
        return  data;
    }
}
