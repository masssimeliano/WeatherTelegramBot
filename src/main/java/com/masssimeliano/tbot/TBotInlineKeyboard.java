package com.masssimeliano.tbot;

import com.masssimeliano.weathertbot.text.InlineKeyboardCommand;
import com.masssimeliano.weathertbot.text.KeyboardType;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.ArrayList;
import java.util.List;

public class TBotInlineKeyboard {

    public InlineKeyboardMarkup formInlineKeyboard(String keyboardType) {
        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();

        List<List<InlineKeyboardButton>> keyboardRowList = new ArrayList<>();

        List<InlineKeyboardButton> keyboardRow1 = new ArrayList<>();
        List<InlineKeyboardButton> keyboardRow2 = new ArrayList<>();

        switch (keyboardType) {
            case KeyboardType.INLINE_AGREE :
                InlineKeyboardButton keyboardButton1 = new InlineKeyboardButton(InlineKeyboardCommand.YES);
                InlineKeyboardButton keyboardButton2 = new InlineKeyboardButton(InlineKeyboardCommand.NO);
                keyboardButton1.setCallbackData(InlineKeyboardCommand.YES);
                keyboardButton2.setCallbackData(InlineKeyboardCommand.NO);

                keyboardRow1.add(keyboardButton1);
                keyboardRow1.add(keyboardButton2);

                keyboardRowList.add(keyboardRow1);

                break;

            case KeyboardType.INLINE_DAYS_OF_WEEK :
                InlineKeyboardButton[] keyboardButtons = new InlineKeyboardButton[8];
                String[] commands = new String[]
                                {InlineKeyboardCommand.MONDAY,
                                InlineKeyboardCommand.TUESDAY,
                                InlineKeyboardCommand.WEDNESDAY,
                                InlineKeyboardCommand.THURSDAY,
                                InlineKeyboardCommand.FRIDAY,
                                InlineKeyboardCommand.SATURDAY,
                                InlineKeyboardCommand.SUNDAY,
                                InlineKeyboardCommand.NO};

                for (int i = 0; i < 8; i++) {
                    keyboardButtons[i] = new InlineKeyboardButton(commands[i]);
                    keyboardButtons[i].setCallbackData(commands[i]);
                    if (i < 4)
                        keyboardRow1.add(keyboardButtons[i]);
                    else
                        keyboardRow2.add(keyboardButtons[i]);
                }

                keyboardRowList.add(keyboardRow1);
                keyboardRowList.add(keyboardRow2);

                break;
        }

        inlineKeyboardMarkup.setKeyboard(keyboardRowList);

        return inlineKeyboardMarkup;
    }

}
