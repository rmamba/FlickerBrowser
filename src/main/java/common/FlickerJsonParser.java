package common;

import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.List;

public class FlickerJsonParser {
    String url;

    private String readUrl() throws Exception {
        BufferedReader reader = null;
        try {
            URL url = new URL(this.url);
            reader = new BufferedReader(new InputStreamReader(url.openStream()));
            StringBuffer buffer = new StringBuffer();
            int read;
            char[] chars = new char[1024];
            while ((read = reader.read(chars)) != -1)
                buffer.append(chars, 0, read);

            return buffer.toString();
        } finally {
            if (reader != null)
                reader.close();
        }
    }

    //JSON structure of returned results
    static class Page {
        String title;
        String link;
        String description;
        String modified;
        String generator;
        List<Item> items;
    }

    public class Media {
        String m;
    }

    //Structure of each item in results
    //Only really interested in title here
    public class Item {
        String title;
        String link;
        Media media;
        String date_taken;
        String description;
        String published;
        String author;
        String author_id;
        String tags;
    }

    public FlickerJsonParser(String url) {
        this.url = url;
    }

    public List<Item> returnItems() {
        String londonJsonString = null;
        try {
            londonJsonString = this.readUrl();
            Gson gson = new Gson();
            Page page = gson.fromJson(londonJsonString, Page.class);
            return page.items;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
