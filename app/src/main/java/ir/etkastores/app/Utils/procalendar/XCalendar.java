package ir.etkastores.app.Utils.procalendar;

import android.util.Log;

import java.util.Calendar;
import java.util.HashMap;

import ir.etkastores.app.BuildConfig;
import ir.etkastores.app.Utils.procalendar.core.JalaliCalendar;
import ir.etkastores.app.Utils.procalendar.repositories.CalGregorian;
import ir.etkastores.app.Utils.procalendar.repositories.CalGregorianArabic;
import ir.etkastores.app.Utils.procalendar.repositories.CalJalali;
import ir.etkastores.app.Utils.procalendar.repositories.CalendarRepoInterface;

/**
 * Created by Sajad on 9/8/17.
 */

public class XCalendar {

    public static final String GregorianType = "GRE";
    public static final String JalaliType = "JLL";
    public static final String ArabicGerigorianType = "ARG";

    private static String type;

    private Calendar calendar;

    private HashMap<String, CalendarRepoInterface> calendarRepository;

    public static void setDefaultType(String defaultType) {
        type = defaultType;
    }

    public static String getDefaultType() {
        return type;
    }

    public XCalendar() {
        calendar = Calendar.getInstance();
    }

    public static XCalendar fromJalali(int year, int month, int day) {
        return new XCalendar(JalaliType, year, month, day);
    }

    public static XCalendar fromGerigorian(int year, int month, int day) {
        return new XCalendar(GregorianType, year, month, day);
    }

    public static XCalendar fromArabicGerigorian(int year, int month, int day) {
        return new XCalendar(ArabicGerigorianType, year, month, day);
    }

    public XCalendar(long epoch) {
        calendar = Calendar.getInstance();
        calendar.setTimeInMillis(epoch);
    }

    public XCalendar(String type, int year, int month, int day) {
        switch (type) {
            case GregorianType:
                initFromGerigorian(year, month, day);
                break;

            case JalaliType:
                initFromJalali(year, month, day);
                break;

            case ArabicGerigorianType:
                initFromArabicGerigorian(year, month, day);
                break;
        }
    }

    private void initFromJalali(int year, int month, int day) {
        JalaliCalendar.YearMonthDate t = JalaliCalendar.jalaliToGregorian(new JalaliCalendar.YearMonthDate(year, month, day));
        calendar = Calendar.getInstance();
        calendar.set(t.getYear(), t.getMonth(), t.getDate());
    }

    private void initFromGerigorian(int year, int month, int day) {
        calendar = Calendar.getInstance();
        calendar.set(year, month, day);
    }

    private void initFromArabicGerigorian(int year, int month, int day) {
        calendar = Calendar.getInstance();
        calendar.set(year, month, day);
    }

    public CalendarRepoInterface getCalendar(String type) {
        if (calendarRepository == null) initRepository();
        return calendarRepository.get(type);
    }

    public CalendarRepoInterface getCalendar() {
        if (calendarRepository == null) initRepository();
        if (type == null) type = JalaliType;
        return calendarRepository.get(type);
    }

    private void initRepository() {
        calendarRepository = new HashMap<>();
        calendarRepository.put(JalaliType, new CalJalali(calendar));
        calendarRepository.put(GregorianType, new CalGregorian(calendar));
        calendarRepository.put(ArabicGerigorianType, new CalGregorianArabic(calendar));
    }

    public static class Utils {

        public static boolean isExpiredDate(XCalendar xCalendar) {
            XCalendar nowCalendar = new XCalendar();
            int y, m, d;
            y = nowCalendar.getCalendar().getYear();
            m = nowCalendar.getCalendar().getMonth();
            d = nowCalendar.getCalendar().getDay();
            int yy, mm, dd;
            yy = xCalendar.getCalendar().getYear();
            mm = xCalendar.getCalendar().getMonth();
            dd = xCalendar.getCalendar().getDay();
            long now = (y * 10000) + (m * 100) + d;
            long date = (yy * 10000) + (mm * 100) + dd;
            if (date >= now) {
                if (BuildConfig.DEBUG)
                    Log.i("expired: " + xCalendar.getCalendar().getYYYYMMDD("-") + "|" + nowCalendar.getCalendar().getYYYYMMDD("-"), "false");
                return false;
            } else {
                if (BuildConfig.DEBUG)
                    Log.i("expired: " + xCalendar.getCalendar().getYYYYMMDD("-") + "|" + nowCalendar.getCalendar().getYYYYMMDD("-"), "true");
                return true;
            }
        }

        public static boolean isToday(XCalendar xCalendar) {
            String nowDt = new XCalendar().getCalendar().getYYYYMMDD("-");
            String day = xCalendar.getCalendar().getYYYYMMDD("-");
            if (nowDt.contentEquals(day)) {
                return true;
            } else {
                return false;
            }
        }

        public static XCalendar increaseOneDay(XCalendar xCalendar) {
            Calendar c = Calendar.getInstance();
            c.setTimeInMillis(xCalendar.getCalendar().getEpoch());
            c.add(Calendar.DATE, 1);
            return new XCalendar(c.getTimeInMillis());
        }

        public static XCalendar decreaseOneDay(XCalendar xCalendar) {
            Calendar c = Calendar.getInstance();
            c.setTimeInMillis(xCalendar.getCalendar().getEpoch());
            c.add(Calendar.DATE, -1);
            return new XCalendar(c.getTimeInMillis());
        }

    }

}
