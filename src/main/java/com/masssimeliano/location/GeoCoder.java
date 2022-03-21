package com.masssimeliano.location;

import org.json.JSONObject;

import java.io.InputStream;
import java.net.URL;
import java.util.Scanner;

public class GeoCoder {

    private String city;
    private float latitude, longitude;

    private final String API_URL1 = "https://api.openweathermap.org/data/2.5/weather?lat=";
    private final String API_URL2 = "&lon=";
    private final String API_URL3 = "&appid=";

    private final String API_URL4 = "https://api.openweathermap.org/data/2.5/weather?q=";

    private final String API_KEY = "0e7f08ec93c1ed828c29624e1573eddf";

    public GeoCoder(float latitude, float longitude) {
        this.latitude = latitude;
        this.longitude = longitude;

        formCity();
    }

    public GeoCoder(String city) {
        this.city = city;

        formCoordinates();
    }

    private void formCity() {
        URL url = formURL(API_URL1 + Float.toString(latitude) + API_URL2 + Float.toString(longitude) + API_URL3 + API_KEY);

        String textJSON = formJSON(url);

        String city = parseJSONGetCity(textJSON);

        this.city = city;
    }

    private void formCoordinates() {
        URL url = formURL(API_URL4 + city + API_URL3 + API_KEY);

        String textJSON = formJSON(url);

        parseJSONGetCoordinates(textJSON);
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

    private String parseJSONGetCity(String textJSON) {
        String city;

        try {
            JSONObject jsonObject = new JSONObject(textJSON);

            city = jsonObject.getString("name");
        }
        catch (Exception e) {

            city = "Error";
        }

        System.out.println("JSON (City): " + city);

        return city;
    }

    private void parseJSONGetCoordinates(String textJSON) {
        double latitude, longitude;

        try {
            JSONObject jsonObject = new JSONObject(textJSON);

            JSONObject jsonObjectCoordinates = jsonObject.getJSONObject("coord");

            latitude = jsonObjectCoordinates.getDouble("lat");
            longitude = jsonObjectCoordinates.getDouble("lon");
        }
        catch (Exception e) {

            latitude = 0;
            longitude = 0;
        }

        System.out.println("JSON (Coordinates) : " + Double.toString(latitude) + " " + Double.toString(longitude));

        this.latitude = Float.parseFloat(Double.toString(latitude));
        this.longitude = Float.parseFloat(Double.toString(longitude));
    }

    public String getCity() {
        return city;
    }

    public float getLatitude() {
        return latitude;
    }

    public float getLongitude() {
        return longitude;
    }

}
