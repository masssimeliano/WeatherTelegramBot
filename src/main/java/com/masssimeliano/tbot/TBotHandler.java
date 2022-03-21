package com.masssimeliano.tbot;

import com.annimon.tgbotsmodule.BotHandler;

import com.masssimeliano.location.*;
import com.masssimeliano.text.*;
import com.masssimeliano.weather.*;
import com.masssimeliano.database.*;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.DeleteMessage;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Location;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboard;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.ArrayList;

public class TBotHandler extends BotHandler {

    private DBHelper dbHelper = new DBHelper();

    @Override
    public String getBotUsername() {
        return "WeatherBotTelegramTBot";
    }

    @Override
    public String getBotToken() {
        return "5034553210:AAHVhMvk0GIL4-obUV0FEvMBDLBN95Pxi8Q";
    }

    @Override
    public BotApiMethod onUpdate(Update update) {
        if (update.hasMessage()) {
            System.out.println("XD");
            Message message = update.getMessage();
            String chatID = Long.toString(message.getChatId());

            String lastMessage = getLM(chatID);

            if (message.hasLocation()) {

                Location location = message.getLocation();

                float latitude = (float) (double) location.getLatitude();
                float longitude = (float) (double) location.getLongitude();

                GeoCoder geoCoder = new GeoCoder(latitude, longitude);
                String city = geoCoder.getCity();

                dbHelper.connect();
                dbHelper.setCoordinates(chatID, latitude, longitude);
                dbHelper.setCity(chatID, city);
                dbHelper.close();

                sendMessage(chatID, formSavedResult(city), new TBotReplyKeyboard().formReplyKeyboard(KeyboardType.REPLY_MAIN));
            }
            else
            if (message.hasText()) {
                String messageText = message.getText();

                switch (messageText) {
                    case "/start" :
                        dbHelper.connect();
                        dbHelper.insert(chatID);
                        dbHelper.close();

                        sendMessage(chatID, Text.WELCOME, null);
                        sendMessage(chatID, Text.LIST_OF_COMMANDS,  new TBotReplyKeyboard().formReplyKeyboard(KeyboardType.REPLY_MAIN));

                        break;

                    /** Debugging */
                    case "/show_database" :
                        dbHelper.connect();
                        dbHelper.showDB();
                        dbHelper.close();

                        break;

                    /** Debugging */
                    case "/show_last_message" :
                        getLM(chatID);

                        break;

                    /** Base Commands */
                    case ReplyKeyboardCommand.INSTRUCTION :
                        sendMessage(chatID, Text.LIST_OF_COMMANDS,
                                new TBotReplyKeyboard().formReplyKeyboard(KeyboardType.REPLY_MAIN));

                        break;

                    /** Base Commands */
                    case ReplyKeyboardCommand.HELP :
                        sendMessage(chatID, Text.HELP,
                                new TBotReplyKeyboard().formReplyKeyboard(KeyboardType.REPLY_MAIN));

                    break;

                    /** Location Commands */
                    case ReplyKeyboardCommand.LOCATION :
                        String city = getCity(chatID);

                        sendMessage(chatID, Text.CHANGE_LOCATION_1 + "*" + city + "*" + Text.CHANGE_LOCATION_2,
                                new TBotInlineKeyboard().formInlineKeyboard(KeyboardType.INLINE_AGREE));

                        break;

                    /** Location Commands */
                    case ReplyKeyboardCommand.TELEGRAM_LOCATION :
                        if (lastMessage.equals(Text.CHOOSE_WAY_CHANGE_LOCATION)) {
                            sendMessage(chatID, Text.WAIT, null);
                            sendMessage(chatID, Text.ALLOW_TELEGRAM_LOCATION,
                                    new TBotReplyKeyboard().formReplyKeyboard(KeyboardType.REPLY_TELEGRAM_LOCATION));
                        }
                        else
                            sendMessage(chatID, Text.UNRECOGNIZED_COMMAND,
                                    new TBotReplyKeyboard().formReplyKeyboard(KeyboardType.REPLY_MAIN));

                        break;

                    /** Location Commands */
                    case ReplyKeyboardCommand.USER_LOCATION :
                        if (lastMessage.equals(Text.CHOOSE_WAY_CHANGE_LOCATION))
                            sendMessage(chatID, Text.WRITE_LOCATION, null);
                        else
                            sendMessage(chatID, Text.UNRECOGNIZED_COMMAND,
                                    new TBotReplyKeyboard().formReplyKeyboard(KeyboardType.REPLY_MAIN));

                        break;

                    /** Weather Forecast Commands */
                    case ReplyKeyboardCommand.WEATHER_FORECAST :
                        sendMessage(chatID, Text.CHOOSE_WEATHER_FORECAST,
                                new TBotReplyKeyboard().formReplyKeyboard(KeyboardType.REPLY_WEATHER_FORECAST));

                        break;

                    /** Weather Forecast Commands */
                    case ReplyKeyboardCommand.WEATHER_FORECAST_NOW :
                        if (lastMessage.equals(Text.CHOOSE_WEATHER_FORECAST)) {
                            sendMessage(chatID, Text.WAIT, null);

                            WeatherNow weatherNow = new WeatherNow(getCity(chatID));
                            String weatherForecast = weatherNow.getWeatherForecast();

                            if (!weatherForecast.equals("")) {
                                sendMessage(chatID, formForecastTitle(ReplyKeyboardCommand.WEATHER_FORECAST_NOW), null);
                                sendMessage(chatID, weatherForecast,
                                        new TBotReplyKeyboard().formReplyKeyboard(KeyboardType.REPLY_MAIN));
                            }
                            else
                                sendMessage(chatID, Text.ERROR,
                                        new TBotReplyKeyboard().formReplyKeyboard(KeyboardType.REPLY_MAIN));
                        }
                        else
                            sendMessage(chatID, Text.UNRECOGNIZED_COMMAND,
                                    new TBotReplyKeyboard().formReplyKeyboard(KeyboardType.REPLY_MAIN));

                        break;

                    /** Weather Forecast Commands */
                    case ReplyKeyboardCommand.WEATHER_FORECAST_DAY :
                        if (lastMessage.equals(Text.CHOOSE_WEATHER_FORECAST)) {
                            sendMessage(chatID, Text.WAIT, null);

                            WeatherDay weatherDay = new WeatherDay(getCity(chatID));
                            ArrayList<String> weatherForecast = weatherDay.getWeatherForecast();

                            if (weatherForecast.size() != 0) {
                                sendMessage(chatID, formForecastTitle(ReplyKeyboardCommand.WEATHER_FORECAST_DAY), null);

                                for (int i = 0; i < weatherForecast.size() - 1; i++)
                                    sendMessage(chatID, weatherForecast.get(i),
                                            new TBotReplyKeyboard().formReplyKeyboard(KeyboardType.REPLY_MAIN));

                                sendMessage(chatID, weatherForecast.get(weatherForecast.size() - 1),
                                        new TBotReplyKeyboard().formReplyKeyboard(KeyboardType.REPLY_MAIN));
                            }
                            else
                                sendMessage(chatID, Text.NO_ITEMS,
                                        new TBotReplyKeyboard().formReplyKeyboard(KeyboardType.REPLY_MAIN));
                        }
                        else
                            sendMessage(chatID, Text.UNRECOGNIZED_COMMAND,
                                    new TBotReplyKeyboard().formReplyKeyboard(KeyboardType.REPLY_MAIN));

                        break;

                    case ReplyKeyboardCommand.WEATHER_FORECAST_WEEK :
                        if (lastMessage.equals(Text.CHOOSE_WEATHER_FORECAST)) {
                            sendMessage(chatID, Text.CHOOSE_DAY_OF_WEEK,
                                    new TBotInlineKeyboard().formInlineKeyboard(KeyboardType.INLINE_DAYS_OF_WEEK));
                        }
                        else
                            sendMessage(chatID, Text.UNRECOGNIZED_COMMAND,
                                    new TBotReplyKeyboard().formReplyKeyboard(KeyboardType.REPLY_MAIN));

                        break;

                    default :
                        if (lastMessage.equals(Text.WRITE_LOCATION) || lastMessage.equals(Text.INCORRECT_DATA)) {
                            GeoCoder geoCoder = new GeoCoder(messageText);
                            float latitude = geoCoder.getLatitude();
                            float longitude = geoCoder.getLongitude();

                            if ((latitude != 0) || (longitude != 0)) {
                                dbHelper.connect();
                                dbHelper.setCity(chatID, messageText);
                                dbHelper.setCoordinates(chatID, latitude, longitude);
                                dbHelper.close();

                                sendMessage(chatID, formSavedResult(messageText),
                                        new TBotReplyKeyboard().formReplyKeyboard(KeyboardType.REPLY_MAIN));
                            }
                            else
                                sendMessage(chatID, Text.INCORRECT_DATA, null);
                        }
                        else
                            sendMessage(chatID, Text.UNRECOGNIZED_COMMAND,
                                    new TBotReplyKeyboard().formReplyKeyboard(KeyboardType.REPLY_MAIN));

                        break;
                }
            }
            else
                sendMessage(chatID, Text.UNRECOGNIZED_COMMAND,
                        new TBotReplyKeyboard().formReplyKeyboard(KeyboardType.REPLY_MAIN));
        }

        if (update.hasCallbackQuery()) {
            CallbackQuery callbackQuery = update.getCallbackQuery();
            String callbackData = callbackQuery.getData();

            Message message = callbackQuery.getMessage();
            String chatID = Long.toString(message.getChatId());
            int messageID = message.getMessageId();

            deleteMessage(chatID, messageID);

            switch (callbackData) {
                case InlineKeyboardCommand.YES :
                    sendMessage(chatID, InlineKeyboardCommand.YES, null);
                    sendMessage(chatID, Text.CHOOSE_WAY_CHANGE_LOCATION,
                            new TBotReplyKeyboard().formReplyKeyboard(KeyboardType.REPLY_REQUEST_LOCATION));

                    break;

                case InlineKeyboardCommand.NO :
                    sendMessage(chatID, InlineKeyboardCommand.NO, null);
                    sendMessage(chatID, Text.REQUEST_CANCELLED,
                            new TBotReplyKeyboard().formReplyKeyboard(KeyboardType.REPLY_MAIN));

                    break;

                default :
                    sendMessage(chatID, Text.WAIT, null);

                    String city = getCity(chatID);
                    float latitude = getLatitude(chatID);
                    float longitude = getLongitude(chatID);

                    WeatherWeek weatherWeek;

                    switch (callbackData) {
                        case InlineKeyboardCommand.MONDAY :
                            sendMessage(chatID, InlineKeyboardCommand.MONDAY, null);

                            weatherWeek = new WeatherWeek(city, latitude, longitude, 0);

                            break;

                        case InlineKeyboardCommand.TUESDAY :
                            sendMessage(chatID, InlineKeyboardCommand.TUESDAY, null);

                            weatherWeek = new WeatherWeek(city, latitude, longitude, 1);

                            break;

                        case InlineKeyboardCommand.WEDNESDAY :
                            sendMessage(chatID, InlineKeyboardCommand.WEDNESDAY, null);

                            weatherWeek = new WeatherWeek(city, latitude, longitude, 2);

                            break;

                        case InlineKeyboardCommand.THURSDAY :
                            sendMessage(chatID, InlineKeyboardCommand.THURSDAY, null);

                            weatherWeek = new WeatherWeek(city, latitude, longitude, 3);

                            break;

                        case InlineKeyboardCommand.FRIDAY :
                            sendMessage(chatID, InlineKeyboardCommand.FRIDAY, null);

                            weatherWeek = new WeatherWeek(city, latitude, longitude, 4);

                            break;

                        case InlineKeyboardCommand.SATURDAY :
                            sendMessage(chatID, InlineKeyboardCommand.SATURDAY, null);

                            weatherWeek = new WeatherWeek(city, latitude, longitude, 5);

                            break;

                        case InlineKeyboardCommand.SUNDAY :
                            sendMessage(chatID, InlineKeyboardCommand.SUNDAY, null);

                            weatherWeek = new WeatherWeek(city, latitude, longitude, 6);

                            break;

                        default :
                            weatherWeek = new WeatherWeek(city, latitude, longitude, -1);

                            break;
                    }

                    String weatherForecast = weatherWeek.getWeatherForecast();

                    if (!weatherForecast.equals("")) {
                        sendMessage(chatID, formForecastTitle(ReplyKeyboardCommand.WEATHER_FORECAST_WEEK), null);
                        sendMessage(chatID, weatherForecast,
                                new TBotReplyKeyboard().formReplyKeyboard(KeyboardType.REPLY_MAIN));
                    }
                    else
                        sendMessage(chatID, Text.ERROR,
                                new TBotReplyKeyboard().formReplyKeyboard(KeyboardType.REPLY_MAIN));


                    break;
            }
        }

        return null;
    }

    private void sendMessage(String chatID, String text, ReplyKeyboard replyKeyboard) {
        SendMessage sendMessage = new SendMessage(chatID, text);
        sendMessage.enableMarkdown(true);

        if (replyKeyboard != null)
            sendMessage.setReplyMarkup(replyKeyboard);

        try {
            execute(sendMessage);
        }
        catch (TelegramApiException e) {
            e.printStackTrace();
        }

        dbHelper.connect();
        dbHelper.setLastMessageText(chatID, text);
        dbHelper.close();
    }

    private void deleteMessage(String chatID, int messageID) {
        DeleteMessage deleteMessage = new DeleteMessage(chatID, messageID);

        try {
            execute(deleteMessage);
        }
        catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    private String getLM(String chatID) {
        dbHelper.connect();
        String LM = dbHelper.getLastMessageText(chatID);
        dbHelper.close();

        return LM;
    }

    private String getCity(String chatID) {
        dbHelper.connect();
        String city = dbHelper.getCity(chatID);
        dbHelper.close();

        return city;
    }

    private float getLatitude(String chatID) {
        dbHelper.connect();
        float latitude = dbHelper.getLatitude(chatID);
        dbHelper.close();

        return latitude;
    }

    private float getLongitude(String chatID) {
        dbHelper.connect();
        float longitude = dbHelper.getLongitude(chatID);
        dbHelper.close();

        return longitude;
    }

    private String formForecastTitle(String weatherForecastType) {
        return Text.WEATHER_FORECAST1 + "*" + weatherForecastType + "*" + Text.WEATHER_FORECAST2;
    }

    private String formSavedResult(String result) {
        return Text.LOCATION_SAVED1 + "*" + result + "*" + Text.LOCATION_SAVED2;
    }

}
