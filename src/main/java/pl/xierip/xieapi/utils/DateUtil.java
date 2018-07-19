package pl.xierip.xieapi.utils;

import java.text.SimpleDateFormat;
import java.util.GregorianCalendar;

/**
 * @author Xierip on 05.08.2015.
 */
public class DateUtil {
    public static long calculatedTimeMillis(final String format) {
        return getCurrentTimeMillis() + formatToMillis(format);
    }

    public static long dayToMillis(final int time) {
        return (((time * 24L) * 60L) * 60L) * 1000L;
    }

    public static String formatTimeDiff(final long diff) {
        if (diff <= 0) {
            return "1 sekunde";
        }
        long seconds = diff / 1000L;
        long minutes = seconds / 60L;
        long hours = minutes / 60L;
        final long days = hours / 24L;
        seconds -= minutes * 60L;
        minutes -= hours * 60L;
        hours -= days * 24L;
        String time = "";
        if (days != 0L) {
            time = time + days + (days == 1 ? " dzien " : " dni ");
        }
        if (hours != 0L) {
            time = time + hours + (getHoursFormat((int) hours));
        }
        if (minutes != 0L) {
            time = time + minutes + (getMinutesFormat((int) minutes));
        }
        if (seconds != 0L) {
            time = time + seconds + (getSecondsFormat((int) seconds));
        }
        if (time.equals("")) {
            return "1 sekunde";
        }
        return time.substring(0, time.length() - 1);
    }

    public static String formatTimeTo(final long to) {
        return formatTimeDiff(to - System.currentTimeMillis());
    }

    public static long formatToMillis(String format) {
        format = format.toLowerCase();
        if (format.contains("hours")) {
            format = StringUtil.replace(format, "hours", "");
            return hourToMillis(Integer.parseInt(format));
        } else if (format.contains("days")) {
            format = StringUtil.replace(format, "days", "");
            return dayToMillis(Integer.parseInt(format));
        } else if (format.contains("s")) {
            format = StringUtil.replace(format, "s", "");
            return secToMillis(Integer.parseInt(format));
        } else if (format.contains("sec")) {
            format = StringUtil.replace(format, "sec", "");
            return secToMillis(Integer.parseInt(format));
        } else if (format.contains("sek")) {
            format = StringUtil.replace(format, "sek", "");
            return secToMillis(Integer.parseInt(format));
        } else if (format.contains("min")) {
            format = StringUtil.replace(format, "min", "");
            return minToMillis(Integer.parseInt(format));
        } else if (format.contains("hour")) {
            format = StringUtil.replace(format, "hour", "");
            return hourToMillis(Integer.parseInt(format));
        } else if (format.contains("h")) {
            format = StringUtil.replace(format, "h", "");
            return hourToMillis(Integer.parseInt(format));
        } else if (format.contains("day")) {
            format = StringUtil.replace(format, "day", "");
            return dayToMillis(Integer.parseInt(format));
        } else if (format.contains("d")) {
            format = StringUtil.replace(format, "d", "");
            return dayToMillis(Integer.parseInt(format));
        } else if (format.contains("mies")) {
            format = StringUtil.replace(format, "mies", "");
            return monthToMillis(Integer.parseInt(format));
        } else if (format.contains("y")) {
            format = StringUtil.replace(format, "y", "");
            return yearsToMillis(Integer.parseInt(format));
        } else {
            return secToMillis(Integer.parseInt(format));
        }
    }

    public static String getCurrentDate() {
        return getDateFromMillis(getCurrentTimeMillis());
    }

    public static long getCurrentTimeMillis() {
        final GregorianCalendar calendar = new GregorianCalendar();
        return calendar.getTimeInMillis();
    }

    public static String getDate() {
        final GregorianCalendar calendar = new GregorianCalendar();
        final SimpleDateFormat simpleDateHere = new SimpleDateFormat("dd.MM.yyyy HH:mm");
        simpleDateHere.setCalendar(calendar);
        return simpleDateHere.format(calendar.getTime());
    }

    public static String getDateFromMillis(final long time) {
        final GregorianCalendar calendar = new GregorianCalendar();
        calendar.setTimeInMillis(time);
        final SimpleDateFormat simpleDateHere = new SimpleDateFormat("dd.MM.yyyy HH:mm");
        simpleDateHere.setCalendar(calendar);
        return simpleDateHere.format(calendar.getTime());
    }

    public static String getDateFromMillisOneString(final long time) {
        final GregorianCalendar calendar = new GregorianCalendar();
        calendar.setTimeInMillis(time);
        final SimpleDateFormat simpleDateHere = new SimpleDateFormat("dd.MM.yyyy-HH:mm:ss");
        simpleDateHere.setCalendar(calendar);
        return simpleDateHere.format(calendar.getTime());
    }

    public static String getHoursFormat(int hours) {
        switch (hours) {
            case 1:
                return " godzine ";
            case 2:
            case 3:
            case 4:
            case 22:
            case 23:
            case 24:
                return " godziny ";
            default:
                return " godzin ";
        }
    }

    public static String getMinutesFormat(int minutes) {
        switch (minutes) {
            case 1:
                return " minute ";
            case 2:
            case 3:
            case 4:
            case 22:
            case 23:
            case 24:
            case 32:
            case 33:
            case 34:
            case 42:
            case 43:
            case 44:
            case 52:
            case 53:
            case 54:
                return " minuty ";
            default:
                return " minut ";
        }
    }

    public static String getSecondsFormat(int seconds) {
        switch (seconds) {
            case 1:
                return " sekunde ";
            case 2:
            case 3:
            case 4:
            case 22:
            case 23:
            case 24:
            case 32:
            case 33:
            case 34:
            case 42:
            case 43:
            case 44:
            case 52:
            case 53:
            case 54:
                return " sekundy ";
            default:
                return " sekund ";
        }
    }

    public static long hourToMillis(final int time) {
        return ((time * 60L) * 60L) * 1000L;
    }

    public static long minToMillis(final int time) {
        return (time * 60) * 1000L;
    }

    public static long monthToMillis(final int time) {
        return ((((time * 31L) * 24L) * 60L) * 60L) * 1000L;
    }

    public static long secToMillis(final int time) {
        return time * 1000L;
    }

    public static long yearsToMillis(final int time) {
        return (((((time * 365L) * 31L) * 24L) * 60L) * 60L) * 1000L;
    }
}
