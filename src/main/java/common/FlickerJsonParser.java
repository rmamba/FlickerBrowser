package common;

import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.List;

/**
 * Helper class to parse JSON from web URL to class objects.
 */
public class FlickerJsonParser {
    String url;

    /**
     * Read JSON from online URL address.
     * @return JSON string
     * @throws Exception
     */
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

    //Media structure
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

    /**
     * Initialize class.
     * @param url web address we would like to parse JSON from
     */
    public FlickerJsonParser(String url) {
        this.url = url;
    }

    /**
     * This will parse JSON from provided URL and return an array of String representing image titles.
     * @return Array of titles for given URL.
     */
    public String[] returnItems() {
        String londonJsonString = null;
        try {
            //read JSON as string from URL provided
            londonJsonString = this.readUrl();
            Gson gson = new Gson();
            //decode JSON string into class objects
            Page page = gson.fromJson(londonJsonString, Page.class);
            String[] ret = new String[page.items.size()];
            int pos = 0;
            //for every item in JSON store it's title
            for (Item item : page.items) {
                ret[pos++] = item.title;
            }
            //return and array of titles
            return ret;
        } catch (Exception e) {
            e.printStackTrace();
        }
        // in case of error return null
        return null;
    }
}
