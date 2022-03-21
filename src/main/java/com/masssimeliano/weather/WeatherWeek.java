package com.masssimeliano.weather;

import org.json.JSONArray;
import org.json.JSONObject;

public class WeatherWeek {

    private final String API_URL1 = "https://api.openweathermap.org/data/2.5/onecall?lat=";
    private final String API_URL2 = "&lon=";
    private final String API_URL3 = "&exclude=minutely,hourly&appid=";

    private final String API_KEY = "0e7f08ec93c1ed828c29624e1573eddf";

    private String city;
    private float latitude, longitude;
    private int dayOfWeek;

    private String weatherForecast;

    private CurrentClock currentClock = new CurrentClock();

    public WeatherWeek(String city, float latitude, float longitude, int dayOfWeek) {
        this.city = city;
        this.latitude = latitude;
        this.longitude = longitude;
        this.dayOfWeek = dayOfWeek;

        formDayOfWeek();

        formWeatherForecast();
    }

    private void formWeatherForecast() {
        JSONFormer jsonFormer = new JSONFormer(API_URL1 + latitude + API_URL2 + longitude + API_URL3 + API_KEY);

        String textJSON = jsonFormer.getTextJSON();

        parseJSONWeatherForecast(textJSON);
    }

    private void parseJSONWeatherForecast(String textJSON) {
        String weatherForecast;

        try {
            JSONObject jsonObject = new JSONObject(textJSON);

            JSONArray jsonArray = jsonObject.getJSONArray("daily");

            JSONObject jsonObjectDay = jsonArray.getJSONObject(dayOfWeek);

            JSONObject jsonObjectWeather = jsonObjectDay.getJSONArray("weather").getJSONObject(0);
            String weather = jsonObjectWeather.getString("main");
            String weatherDescription = formWeatherDescription(jsonObjectWeather.getString("description"));

            JSONObject jsonObjectTemperature = jsonObjectDay.getJSONObject("temp");
            long temperatureMorning = Math.round(jsonObjectTemperature.getDouble("morn") - 273.15);
            long temperatureDay = Math.round(jsonObjectTemperature.getDouble("day") - 273.15);
            long temperatureEvening = Math.round(jsonObjectTemperature.getDouble("eve") - 273.15);
            long temperatureNight = Math.round(jsonObjectTemperature.getDouble("night") - 273.15);

            int humidity = jsonObjectDay.getInt("humidity");
            int windSpeed = jsonObjectDay.getInt("wind_speed");
            int cloudness = jsonObjectDay.getInt("clouds");

            long unixTime = jsonObjectDay.getLong("dt");

            String date = currentClock.getUnixDate(unixTime);

            Model model = new Model(date, city, weather, weatherDescription, temperatureMorning, temperatureDay, temperatureEvening, temperatureNight, humidity, windSpeed, cloudness);
            weatherForecast = model.getModel();
        }
        catch (Exception e) {
            weatherForecast = "";

            System.out.println("Error (Parsing - WeatherWeek) : " + e.getMessage());
        }

        this.weatherForecast = weatherForecast;
    }

    private void formDayOfWeek() {
        int result = dayOfWeek - currentClock.getDayOfWeek();

        if (result == 0)
            dayOfWeek = 7;
        else
            if (result > 0)
                dayOfWeek = result;
            else
                dayOfWeek = 7 - Math.abs(result);
    }

    private String formWeatherDescription(String weatherDescription) {
        return weatherDescription.substring(0, 1).toUpperCase() + weatherDescription.substring(1, weatherDescription.length());
    }

    public String getWeatherForecast() {
        return weatherForecast;
    }

}
