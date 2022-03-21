package com.masssimeliano.weather;

import org.json.JSONObject;

public class WeatherNow {

    private final String API_URL1 = "https://api.openweathermap.org/data/2.5/weather?q=";
    private final String API_URL2 = "&appid=";

    private final String API_KEY = "0e7f08ec93c1ed828c29624e1573eddf";

    private String city;
    private String weatherForecast;

    public WeatherNow(String city) {
        this.city = city;

        formWeatherForecast();
    }

    private void formWeatherForecast() {
        JSONFormer jsonFormer = new JSONFormer(API_URL1 + city + API_URL2 + API_KEY);

        String textJSON = jsonFormer.getTextJSON();

        parseJSONWeatherForecast(textJSON);
    }

    private void parseJSONWeatherForecast(String textJSON) {
        String weatherForecast;

        try {
            JSONObject jsonObject = new JSONObject(textJSON);

            JSONObject jsonObjectWeather = jsonObject.getJSONArray("weather").getJSONObject(0);
            String weather = jsonObjectWeather.getString("main");
            String weatherDescription = formWeatherDescription(jsonObjectWeather.getString("description"));

            JSONObject jsonObjectWeatherMain = jsonObject.getJSONObject("main");
            long temperature = Math.round(jsonObjectWeatherMain.getDouble("temp") - 273.15);
            long temperatureFeelsLike = Math.round(jsonObjectWeatherMain.getDouble("feels_like") - 273.15);
            int humidity = jsonObjectWeatherMain.getInt("humidity");

            JSONObject jsonObjectWind = jsonObject.getJSONObject("wind");
            int windSpeed = jsonObjectWind.getInt("speed");

            JSONObject jsonObjectClouds = jsonObject.getJSONObject("clouds");
            int cloudness = jsonObjectClouds.getInt("all");

            Model model = new Model(city, weather, weatherDescription, temperature, temperatureFeelsLike, humidity, windSpeed, cloudness);
            weatherForecast = model.getModel();
        }
        catch (Exception e) {
            weatherForecast = "";

            System.out.println("Error (Parsing - WeatherNow) : " + e.getMessage());
        }

        this.weatherForecast = weatherForecast;
    }

    private String formWeatherDescription(String weatherDescription) {
        return weatherDescription.substring(0, 1).toUpperCase() + weatherDescription.substring(1, weatherDescription.length());
    }

    public String getWeatherForecast() {
        return weatherForecast;
    }

}
