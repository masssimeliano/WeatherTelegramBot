package com.masssimeliano.text;

public interface WeatherForecastCommand {

    String DATE = "Date " + Emoji.CALENDAR;
    String TIME = "Time " + Emoji.CLOCK;
    String CITY = "City " + Emoji.CITY;
    String WEATHER = "Weather " + Emoji.WEATHER;
    String TEMPERATURE = "Temperature " + Emoji.TEMPERATURE;
    String TEMPERATURE_FEELS_LIKE = "Temperature (Feels like) " + Emoji.THINKING + Emoji.TEMPERATURE;
    String TEMPERATURE_MORNING = "Temperature (Morning) " + Emoji.MORNING;
    String TEMPERATURE_DAY = "Temperature (Day) " + Emoji.DAY;
    String TEMPERATURE_EVENING = "Temperature (Evening) " + Emoji.EVENING;
    String TEMPERATURE_NIGHT = "Temperature (Night) " + Emoji.NIGHT;
    String HUMIDITY = "Humidity " + Emoji.WATER;
    String WIND_SPEED = "Wind speed " + Emoji.WIND;
    String CLOUDNESS = "Cloudness " + Emoji.CLOUD;

    String GRAD = "°C";
    String MS = "m/s";
    String PERCENT = "%";

}
