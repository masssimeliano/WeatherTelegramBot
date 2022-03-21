package com.masssimeliano.text;

public interface ReplyKeyboardCommand {

    String INSTRUCTION = "Instruction " + Emoji.BOOK;
    String WEATHER_FORECAST = "Weather Forecast " + Emoji.WEATHER;
    String LOCATION = "Location " + Emoji.LOLLIPOP;
    String HELP = "Help " + Emoji.QUESTION;

    String TELEGRAM_LOCATION = "Telegram " + Emoji.INTERNET;
    String USER_LOCATION = "User " + Emoji.USER;
    String REQUEST_TELEGRAM_LOCATION = "Allow Telegram To Know Your Location " + Emoji.LOLLIPOP;

    String WEATHER_FORECAST_NOW = "Now " + Emoji.ALARM;
    String WEATHER_FORECAST_DAY = "Day " + Emoji.ONE;
    String WEATHER_FORECAST_WEEK = "Week " + Emoji.CALENDAR;

}
