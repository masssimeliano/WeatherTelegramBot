package com.masssimeliano.weather;

import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Locale;

public class CurrentClock {

    public String getFormattedDate() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");

        String time = simpleDateFormat.format(new Date());

        return time;
    }

    public String getUnixDate(long unixTime) {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd.MM");

        String date = Instant.ofEpochSecond(unixTime)
                .atZone(ZoneId.systemDefault())
                .format(dateTimeFormatter);

        return date;
    }

    public int getDayOfWeek() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("E", Locale.UK);

        String date = simpleDateFormat.format(new Date()).toUpperCase().substring(0, 2);

        int day = 0;

        switch (date) {
            case "MO" :
                day = 0;

                break;

            case "TU" :
                day = 1;

                break;

            case "WE" :
                day = 2;

                break;

            case "TH" :
                day = 3;

                break;

            case "FR" :
                day = 4;

                break;

            case "SA" :
                day = 5;

                break;

            case "SU" :
                day = 6;

                break;
        }

        return day;
    }

}
