package com.masssimeliano.weather;

import java.io.InputStream;
import java.net.URL;
import java.util.Scanner;

public class JSONFormer {

    private String textJSON;

    public JSONFormer(String urlString) {
        URL url = formURL(urlString);

        String textJSON = formJSON(url);

        this.textJSON = textJSON;
    }

    private URL formURL(String urlString) {
        URL url = null;

        try {
            url = new URL(urlString);
        }
        catch (Exception e) {
            e.printStackTrace();
        }

        return url;
    }

    private String formJSON(URL url) {
        StringBuffer textJSON = new StringBuffer("");

        try {
            Scanner scanner = new Scanner((InputStream) url.getContent());

            while (scanner.hasNext() == true)
            {
                textJSON.append(scanner.nextLine());
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }

        return textJSON.toString();
    }

    public String getTextJSON() {
        return textJSON;
    }

}
