package com.masssimeliano.tbot;

import com.masssimeliano.text.KeyboardType;
import com.masssimeliano.text.ReplyKeyboardCommand;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboard;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;

import java.util.ArrayList;
import java.util.List;

public class TBotReplyKeyboard {

    public ReplyKeyboard formReplyKeyboard(String keyboardType) {
        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();

        replyKeyboardMarkup.setSelective(true);
        replyKeyboardMarkup.setResizeKeyboard(true);
        replyKeyboardMarkup.setOneTimeKeyboard(true);

        List<KeyboardRow> keyboardRowList = new ArrayList<KeyboardRow>();

        KeyboardRow keyboardRow1 = new KeyboardRow();
        KeyboardRow keyboardRow2 = new KeyboardRow();

        switch (keyboardType) {
            case KeyboardType.REPLY_MAIN :
                keyboardRow1.add(new KeyboardButton(ReplyKeyboardCommand.INSTRUCTION));
                keyboardRow1.add(new KeyboardButton(ReplyKeyboardCommand.WEATHER_FORECAST));
                keyboardRow2.add(new KeyboardButton(ReplyKeyboardCommand.LOCATION));
                keyboardRow2.add(new KeyboardButton(ReplyKeyboardCommand.HELP));

                keyboardRowList.add(keyboardRow1);
                keyboardRowList.add(keyboardRow2);

                break;

            case KeyboardType.REPLY_REQUEST_LOCATION :
                keyboardRow1.add(new KeyboardButton(ReplyKeyboardCommand.TELEGRAM_LOCATION));
                keyboardRow1.add(new KeyboardButton(ReplyKeyboardCommand.USER_LOCATION));

                keyboardRowList.add(keyboardRow1);

                break;

            case KeyboardType.REPLY_TELEGRAM_LOCATION :
                KeyboardButton keyboardButton = new KeyboardButton(ReplyKeyboardCommand.REQUEST_TELEGRAM_LOCATION);
                keyboardButton.setRequestLocation(true);

                keyboardRow1.add(keyboardButton);

                keyboardRowList.add(keyboardRow1);

                break;

            case KeyboardType.REPLY_WEATHER_FORECAST :
                keyboardRow1.add(new KeyboardButton(ReplyKeyboardCommand.WEATHER_FORECAST_NOW));
                keyboardRow1.add(new KeyboardButton(ReplyKeyboardCommand.WEATHER_FORECAST_DAY));
                keyboardRow1.add(new KeyboardButton(ReplyKeyboardCommand.WEATHER_FORECAST_WEEK));

                keyboardRowList.add(keyboardRow1);

                break;
        }

        replyKeyboardMarkup.setKeyboard(keyboardRowList);

        return replyKeyboardMarkup;
    }

}
