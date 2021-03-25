package com.webmonitor.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

//PARA TESTE
public class DummyPages {

    public static List<Page> data  = Arrays.asList(

            new Page(1L,"Resultado da Pesquisa do Google", "google.com", "link.com", 1L*60000, true),
            new Page(2L,"Instagram", "google.com", "link.com", 2L*60000, false),
            new Page(3L, "Facebook", "google.com", "link.com", 3L*60000, true),
            new Page(4L, "IFBarbacena", "google.com", "link.com", 4L*60000, false),
            new Page(5L, "GitHub", "google.com", "link.com", 5L*60000, true),
            new Page(6L, "Uol", "google.com", "link.com", 6L*60000, false),
            new Page(7L, "Terra", "google.com", "link.com", 7L*60000, true),
            new Page(8L, "Gmail", "google.com", "link.com",  8L*60000, false)
    );

}
