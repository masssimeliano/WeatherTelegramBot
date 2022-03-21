package com.masssimeliano.text;

public interface Text {

    String WELCOME = "Welcome to *WeatherBot*! This is a telegram bot that can show you weather forecast by location " + Emoji.WEATHER;
    String LIST_OF_COMMANDS = "*List of commands* " + Emoji.BOOK + " :" + '\n'
                            + "*" + ReplyKeyboardCommand.INSTRUCTION + "*" + " - Commands of WeatherBot" + '\n'
                            + "*" + ReplyKeyboardCommand.WEATHER_FORECAST + "*" + " - Weather forecast (Now, day and week)" + '\n'
                            + "*" + ReplyKeyboardCommand.LOCATION + "*" + " - Your location" + '\n'
                            + "*" + ReplyKeyboardCommand.HELP + "*" + " - Support help of WeatherBot";
    String HELP = "*Questions/Offers* - @masssimeliano " + Emoji.MAN;

    String CHANGE_LOCATION_1 = "Your currently location is ";
    String CHANGE_LOCATION_2 = ". Would you like to change it? " + Emoji.REVERSE;
    String CHOOSE_WAY_CHANGE_LOCATION = "Now choose the way to change your location :";
    String ALLOW_TELEGRAM_LOCATION = "Now allow Telegram to know your currently location : ";
    String WRITE_LOCATION = "Now write your new location : ";
    String LOCATION_SAVED1 = "Your new location (";
    String LOCATION_SAVED2 = ") was successfully saved " + Emoji.YES;

    String WEATHER_FORECAST1 = "Weather forecast (";
    String WEATHER_FORECAST2 = ")";
    String CHOOSE_WEATHER_FORECAST = "Now choose type of weather forecast : ";
    String CHOOSE_DAY_OF_WEEK = "Now choose a day of week for weather forecast : ";

    String WAIT = "Please, wait a little bit (5 - 8 minutes) " + Emoji.CLOCK;
    String REQUEST_CANCELLED = "Request was cancelled " + Emoji.ERROR;
    String NO_ITEMS = "Sorry, there is no forecast for today already " + Emoji.SORRY;
    String INCORRECT_DATA = "Incorrect location. Please, try again " + Emoji.ERROR;
    String UNRECOGNIZED_COMMAND = "Sorry, this command was not recognized. Please, try again " + Emoji.ERROR;
    String ERROR = "Unknown error (Server) " + Emoji.BOT;

}
