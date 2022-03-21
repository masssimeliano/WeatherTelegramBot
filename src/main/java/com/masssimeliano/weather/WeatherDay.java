package com.masssimeliano.weather;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class WeatherDay {

    private final String API_URL1 = "https://api.openweathermap.org/data/2.5/forecast?q=";
    private final String API_URL2 = "&appid=";

    private final String API_KEY = "0e7f08ec93c1ed828c29624e1573eddf";

    private String city;

    private ArrayList<String> weatherForecast;

    private CurrentClock currentClock = new CurrentClock();

    public WeatherDay(String city) {
        this.city = city;

        formWeatherForecast();
    }

    private void formWeatherForecast() {
        JSONFormer jsonFormer = new JSONFormer(API_URL1 + city + API_URL2 + API_KEY);

        String textJSON = jsonFormer.getTextJSON();

        parseJSONWeatherForecast(textJSON);
    }

    private void parseJSONWeatherForecast(String textJSON) {
        ArrayList<String> weatherForecast = new ArrayList<String>();

        String formattedDate = currentClock.getFormattedDate();

        try {
            JSONObject jsonObject = new JSONObject(textJSON);

            JSONArray jsonArray = jsonObject.getJSONArray("list");

            int i = 0;

            while (i < jsonArray.length()) {
                JSONObject jsonObjectListItem = jsonArray.getJSONObject(i);

                String itemTime = jsonObjectListItem.getString("dt_txt").substring(0, 10);

                if (!itemTime.equals(formattedDate))
                    break;

                JSONObject jsonObjectWeather = jsonObjectListItem.getJSONArray("weather").getJSONObject(0);
                String weatherDescription = formWeatherDescription(jsonObjectWeather.getString("description"));
                String weather = jsonObjectWeather.getString("main");

                JSONObject jsonObjectWeatherMain = jsonObjectListItem.getJSONObject("main");
                long temperature = Math.round(jsonObjectWeatherMain.getDouble("temp") - 273.15);
                long temperatureFeelsLike = Math.round(jsonObjectWeatherMain.getDouble("feels_like") - 273.15);
                int humidity = jsonObjectWeatherMain.getInt("humidity");

                JSONObject jsonObjectWind = jsonObjectListItem.getJSONObject("wind");
                int windSpeed = jsonObjectWind.getInt("speed");

                JSONObject jsonObjectClouds = jsonObjectListItem.getJSONObject("clouds");
                int cloudness = jsonObjectClouds.getInt("all");

                String modelTime = jsonObjectListItem.getString("dt_txt").substring(10, 16);

                Model model = new Model(modelTime, city, weather, weatherDescription, temperature, temperatureFeelsLike, humidity, windSpeed, cloudness);
                weatherForecast.add(model.getModel());

                i++;
            }
        }
        catch (Exception e) {
            weatherForecast = new ArrayList<String>();

            System.out.println("Error (Parsing - WeatherDay) : " + e.getMessage());
        }

        this.weatherForecast = weatherForecast;
    }

    private String formWeatherDescription(String weatherDescription) {
        return weatherDescription.substring(0, 1).toUpperCase() + weatherDescription.substring(1, weatherDescription.length());
    }

    public ArrayList<String> getWeatherForecast() {
        return weatherForecast;
    }

}
