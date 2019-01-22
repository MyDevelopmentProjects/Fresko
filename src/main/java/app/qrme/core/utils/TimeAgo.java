package app.qrme.core.utils;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class TimeAgo {

    static Map<String, String> texts = new HashMap<String, String>() {{
        put("now_ka", "ახლა");
        put("now_ru", "Сейчас");
        put("now_en", "Now");

        put("minute_ka", " წუთის წინ");
        put("minute_ru", " минуту назад");
        put("minute_en", " minute ago");

        put("hour_ka", " საათის წინ");
        put("hour_ru", " час назад");
        put("hour_en", " hour ago");

        put("day_ka", " დღის წინ");
        put("day_ru", " день назад");
        put("day_en", " day ago");

        put("week_ka", " კვირის წინ");
        put("week_ru", " неделю назад");
        put("week_en", " week ago");

        put("month_ka", " თვის წინ");
        put("month_ru", " месяц назад");
        put("month_en", " month ago");

        put("year_ka", " წლის წინ");
        put("year_ru", " год назад");
        put("year_en", " year ago");

        put("yesterday_en", "yesterday");
        put("yesterday_ka", "გუშინ");
        put("yesterday_ru", "вчера");
    }};

    public static String timeAgo(long time_ago, String locale) {
        locale = locale.toLowerCase();
        long cur_time = (Calendar.getInstance().getTimeInMillis()) / 1000;
        long time_elapsed = cur_time - time_ago;
        long seconds = time_elapsed;
        int minutes = Math.round(time_elapsed / 60);
        int hours = Math.round(time_elapsed / 3600);
        int days = Math.round(time_elapsed / 86400);
        int weeks = Math.round(time_elapsed / 604800);
        int months = Math.round(time_elapsed / 2600640);
        int years = Math.round(time_elapsed / 31207680);

        // Seconds
        if (seconds <= 60) {
            return texts.get("now_" + locale);
        }
        //Minutes
        else if (minutes <= 60) {
            if (minutes == 1) {
                return "1" + texts.get("minute_" + locale);
            } else {
                return minutes + texts.get("minute_" + locale);
            }
        }
        //Hours
        else if (hours <= 24) {
            if (hours == 1) {
                return "1" + texts.get("hour_" + locale);
            } else {
                return hours + texts.get("hour_" + locale);
            }
        }
        //Days
        else if (days <= 7) {
            if (days == 1) {
                return texts.get("yesterday_" + locale);
            } else {
                return days + texts.get("day_" + locale);
            }
        }
        //Weeks
        else if (weeks <= 4.3) {
            if (weeks == 1) {
                return "1" + texts.get("week_" + locale);
            } else {
                return weeks + texts.get("week_" + locale);
            }
        }
        //Months
        else if (months <= 12) {
            if (months == 1) {
                return "1" + texts.get("month_" + locale);
            } else {
                return months + texts.get("month_" + locale);
            }
        }
        //Years
        else {
            if (years == 1) {
                return "1" + texts.get("year_" + locale);
            } else {
                return years + texts.get("year_" + locale);
            }
        }
    }
}