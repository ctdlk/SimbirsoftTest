package com.simbirsoft.test.entity;

import com.simbirsoft.test.utils.FileSaver;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Locale;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;

public class Dictionary {
    private final String delimiters = "[.%«»©(){}\\[—_\\],\\-+:;!?\\'\"\\s\\n\\r\\t]+";
    private String URL;
    private Map<String, Integer> dict;
    private FileSaver fileSaver = FileSaver.getInstance();

    public Dictionary(String url) {
        this.URL = url;
        this.dict = new TreeMap<>();
        saveWebpage(this.URL);
    }

    public void setURL(String URL) {
        this.URL = URL;
    }

    public String getURL() {
        return URL;
    }

    public void count() {
        try {
            String rawData = fileSaver.getWebpage(this.URL);
            Document webpage = Jsoup.parse(rawData);
            String data = webpage.body().text();
            for (String word : data.split(delimiters)) {
                if (!word.equals("")) {
                    int count = dict.getOrDefault(word, 0);
                    dict.put(word, count + 1);
                }
            }
            String dictionary = convertWithStream(dict);
            saveDictionary(this.URL, dictionary);
            System.out.println(dictionary);
        } catch (NullPointerException e){
            e.printStackTrace();
        }
    }

    public String convertWithStream(Map<String, Integer> map) {
        String mapAsString = map.keySet().stream()
                .map(key -> key.toUpperCase(Locale.ROOT) + " — " + map.get(key))
                .collect(Collectors.joining("\n"));
        return mapAsString;
    }

    private void saveWebpage(String url) {
        try {
            String html = Jsoup.connect(url).get().html();
            fileSaver.saveWebpage(url, html);
        } catch (IOException | IllegalArgumentException e) {
            e.printStackTrace();
        }
    }

    private void saveDictionary(String url, String dictionary) {
        fileSaver.saveDictionary(url,dictionary);
    }


}
