package com.masssimeliano.weather;

import com.masssimeliano.text.WeatherForecastCommand;

public class Model {

    private String date, time, city, weather, weatherDescription;
    private long temperature, temperatureFeelsLike, temperatureMorning, temperatureDay, temperatureEvening, temperatureNight;
    private int humidity, windSpeed, cloudness;

    private boolean withTime, withDate, withAdditionalTemperature;

    private String weatherForecast;

    public Model(String date, String city, String weather, String weatherDescription, long temperatureMorning, long temperatureDay, long temperatureEvening, long temperatureNight, int humidity, int windSpeed, int cloudness) {
        this.date = date;
        this.city = city;
        this.weather = weather;
        this.weatherDescription = weatherDescription;
        this.temperatureMorning = temperatureMorning;
        this.temperatureDay = temperatureDay;
        this.temperatureEvening = temperatureEvening;
        this.temperatureNight = temperatureNight;
        this.humidity = humidity;
        this.windSpeed = windSpeed;
        this.cloudness = cloudness;

        withTime = false;
        withDate = true;
        withAdditionalTemperature = true;

        formModel();
    }

    public Model(String time, String city, String weather, String weatherDescription, long temperature, long temperatureFeelsLike, int humidity, int windSpeed, int cloudness) {
        this.time = time;
        this.city = city;
        this.weather = weather;
        this.weatherDescription = weatherDescription;
        this.temperature = temperature;
        this.temperatureFeelsLike = temperatureFeelsLike;
        this.humidity = humidity;
        this.windSpeed = windSpeed;
        this.cloudness = cloudness;

        withTime = true;
        withDate = false;
        withAdditionalTemperature = false;

        formModel();
    }

    public Model(String city, String weather, String weatherDescription, long temperature, long temperatureFeelsLike, int humidity, int windSpeed, int cloudness) {
        this.city = city;
        this.weather = weather;
        this.weatherDescription = weatherDescription;
        this.temperature = temperature;
        this.temperatureFeelsLike = temperatureFeelsLike;
        this.humidity = humidity;
        this.windSpeed = windSpeed;
        this.cloudness = cloudness;

        withTime = false;
        withDate = false;
        withAdditionalTemperature = false;

        formModel();
    }

    private void formModel() {
        StringBuffer weatherForecast = new StringBuffer("");

        if (withDate)
            weatherForecast.append("*" + WeatherForecastCommand.DATE + "* : " + date + '\n');

        if (withTime)
            weatherForecast.append("*" + WeatherForecastCommand.TIME + "* : " + time + '\n');

        weatherForecast.append("*" + WeatherForecastCommand.CITY + "* : " + city + '\n');
        weatherForecast.append("*" + WeatherForecastCommand.WEATHER + "* : " + weather + " (" + weatherDescription + ")" + '\n');

        if (withAdditionalTemperature) {
            weatherForecast.append("*" + WeatherForecastCommand.TEMPERATURE_MORNING + "* : " + temperatureMorning + " " + WeatherForecastCommand.GRAD + '\n');
            weatherForecast.append("*" + WeatherForecastCommand.TEMPERATURE_DAY + "* : " + temperatureDay + " " + WeatherForecastCommand.GRAD + '\n');
            weatherForecast.append("*" + WeatherForecastCommand.TEMPERATURE_EVENING + "* : " + temperatureEvening + " " + WeatherForecastCommand.GRAD + '\n');
            weatherForecast.append("*" + WeatherForecastCommand.TEMPERATURE_NIGHT + "* : " + temperatureNight + " " + WeatherForecastCommand.GRAD + '\n');
        }
        else {
            weatherForecast.append("*" + WeatherForecastCommand.TEMPERATURE + "* : " + temperature + " " + WeatherForecastCommand.GRAD + '\n');
            weatherForecast.append("*" + WeatherForecastCommand.TEMPERATURE_FEELS_LIKE + "* : " + temperatureFeelsLike + " " + WeatherForecastCommand.GRAD + '\n');
        }

        weatherForecast.append("*" + WeatherForecastCommand.WIND_SPEED + "* : " + windSpeed + " " + WeatherForecastCommand.MS + '\n');
        weatherForecast.append("*" + WeatherForecastCommand.HUMIDITY + "* : " + humidity + " " + WeatherForecastCommand.PERCENT + '\n');
        weatherForecast.append("*" + WeatherForecastCommand.CLOUDNESS + "* : " + cloudness + " "  + WeatherForecastCommand.PERCENT);

        this.weatherForecast = weatherForecast.toString();
    }

    public String getModel() {
        return weatherForecast;
    }

}
