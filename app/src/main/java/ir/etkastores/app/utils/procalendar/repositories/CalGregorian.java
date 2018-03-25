package ir.etkastores.app.utils.procalendar.repositories;

import java.util.Calendar;

/**
 * Created by Sajad on 9/19/17.
 */

public class CalGregorian implements CalendarRepoInterface {

    private static String[] monthNames = new String[]{"", "January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"};
    private static String[] shortMonthNames = new String[]{"", "Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"};
    private static String[] dayNames = new String[]{"", "Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday"};
    private static String[] shortDayNames = new String[]{"", "S", "M", "T", "W", "T", "F", "S"};

    private Calendar calendar;

    public CalGregorian(Calendar calendar) {
        this.calendar = calendar;
    }

    @Override
    public int firstDayOfMonthInWeek() {
        Calendar cc = Calendar.getInstance();
        cc.set(calendar.get(Calendar.YEAR),calendar.get(Calendar.MONTH),1);
        return cc.get(Calendar.DAY_OF_WEEK);
    }

    @Override
    public int firstDayOfWeek() {
        return START_WEEK_SUN;
    }

    @Override
    public int daysOfMonth(int month) {
        int days = 0;
        switch (month) {

            case 1:
                days = 31;
                break;

            case 2:
                if (isLeapYear()) {
                    days = 29;
                } else {
                    days = 28;
                }
                break;

            case 3:
                days = 31;
                break;

            case 4:
                days = 30;
                break;

            case 5:
                days = 31;
                break;

            case 6:
                days = 30;
                break;

            case 7:
                days = 31;
                break;

            case 8:
                days = 31;
                break;

            case 9:
                days = 30;
                break;

            case 10:
                days = 31;
                break;

            case 11:
                days = 30;
                break;

            case 12:
                days = 31;
                break;

        }
        return days;
    }

    @Override
    public int daysOfMonth() {
        return daysOfMonth(getMonth());
    }

    @Override
    public boolean isLeapYear() {
        if (calendar.getActualMaximum(Calendar.DAY_OF_YEAR) > 365) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public String[] monthNamesOfYear() {
        return monthNames;
    }

    @Override
    public String[] shortMonthNamesOfYear() {
        return shortMonthNames;
    }

    @Override
    public String[] dayNamesOfWeek() {
        return dayNames;
    }

    @Override
    public String[] shortDayNamesOfWeek() {
        return shortDayNames;
    }


    @Override
    public String getDateByMonthName(String splitter) {
        return String.format("%d%s%s%s%d",getDay(),splitter,getMonthName(),splitter,getYear());
    }

    @Override
    public String getDateByMonthName() {
        return getDateByMonthName(" ");
    }

    @Override
    public String getDateByShortMonthName(String splitter) {
        return String.format("%d%s%s%s%d",getDay(),splitter,getShortMonthName(),splitter,getYear());
    }

    @Override
    public String getDateByShortMonthName() {
        return getDateByShortMonthName(" ");
    }

    @Override
    public String getDateNumerical(String splitter) {
        return String.format("%d%s%d%s%d",getMonth(),splitter,getDay(),splitter,getYear());
    }

    @Override
    public String getDateNumerical() {
        return getDateNumerical(" ");
    }

    @Override
    public String getMonthNameAndDay(String splitter) {
        return String.format("%d%s%s",getDay(),splitter,getMonthName());
    }

    @Override
    public String getMonthNameAndDay() {
        return getMonthNameAndDay(" ");
    }

    @Override
    public String getShortMonthNameAndDay(String splitter) {
        return String.format("%d%s%s",getDay(),splitter,getShortMonthName());
    }

    @Override
    public String getShortMonthNameAndDay() {
        return getShortMonthNameAndDay(" ");
    }

    @Override
    public String getYearAndMonthName(String splitter) {
        return String.format("%s%s%d", getMonthName(), splitter, getYear());
    }

    @Override
    public String getYearAndMonthName() {
        return getYearAndMonthName(" ");
    }

    @Override
    public String getYearAndShortMonthName(String splitter) {
        return String.format("%s%s%d", getShortMonthName(), splitter, getYear());
    }

    @Override
    public String getYearAndShortMonthName() {
        return getYearAndShortMonthName(" ");
    }

    @Override
    public String getMonthName() {
        return monthNamesOfYear()[getMonth()];
    }

    @Override
    public String getShortMonthName() {
        return shortMonthNamesOfYear()[getMonth()];
    }

    @Override
    public String getDayNameInWeek() {
        return dayNamesOfWeek()[calendar.get(Calendar.DAY_OF_WEEK)];
    }

    @Override
    public String getShortDayNameInWeek() {
        return shortDayNamesOfWeek()[calendar.get(Calendar.DAY_OF_WEEK)];
    }

    @Override
    public int getYear() {
        return calendar.get(Calendar.YEAR);
    }

    @Override
    public int getMonth() {
        return calendar.get(Calendar.MONTH) + 1;
    }

    @Override
    public int getDay() {
        return calendar.get(Calendar.DAY_OF_MONTH);
    }

    @Override
    public long getEpoch() {
        return calendar.getTimeInMillis();
    }

    @Override
    public String getYYYYMMDD(String splitter) {
        String d = String.valueOf(getDay());
        if (d.length()==1) d = "0"+d;
        String m = String.valueOf(getMonth());
        if (m.length()==1) m = "0"+m;
        String y = String.valueOf(getYear());
        return y+splitter+m+splitter+d;
    }

    @Override
    public String[] getMonthHeaderDayNames() {
        return new String[]{ "S", "M", "T", "W", "T", "F", "S"};
    }

}
