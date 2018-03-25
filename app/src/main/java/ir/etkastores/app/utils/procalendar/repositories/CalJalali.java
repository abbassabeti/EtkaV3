package ir.etkastores.app.utils.procalendar.repositories;

import java.util.Calendar;

import ir.etkastores.app.utils.procalendar.XCalendar;
import ir.etkastores.app.utils.procalendar.core.JalaliCalendar;

/**
 * Created by Sajad on 9/19/17.
 */

public class CalJalali implements CalendarRepoInterface {

    private static String[] monthNames = new String[]{"","فروردین","اردیبهشت","خرداد","تیر","مرداد","شهریور","مهر","آبان","آذر","دی","بهمن","اسفند"};
    private static String[] dayNames = new String[]{"","یکشنبه","دوشنبه","سه‌شنبه","چهارشنبه","پنجشنبه","جمعه","شنبه"};
    private static String[] shortDayNames = new String[]{"","ی","د","س","چ","پ","ج","ش"};
    private static int[] firstDayOfWeek =new int[]{2,3,4,5,6,7,1};

    private Calendar calendar;

    public CalJalali(Calendar calendar) {
        this.calendar = calendar;
    }


    /**
     * by this method we can recognize the position of day in first week of selected month
     * we use this method for calculate offset of first day of month in week in calendar
     * we first find the month and year of selected date, then get first day of that month and year
     * @return
     */
    @Override
    public int firstDayOfMonthInWeek() {
        int y = getYear();
        int m = getMonth()-1;
        XCalendar xCalendar = XCalendar.fromJalali(y,m,1);
        Calendar cc = Calendar.getInstance();
        int yy = xCalendar.getCalendar(XCalendar.GregorianType).getYear();
        int mm = xCalendar.getCalendar(XCalendar.GregorianType).getMonth()-1;
        int dd = xCalendar.getCalendar(XCalendar.GregorianType).getDay();
        cc.set(yy,mm,dd);
        int d = firstDayOfWeek[cc.get(Calendar.DAY_OF_WEEK)-1];
        return d;
    }

    @Override
    public int firstDayOfWeek() {
        return START_WEEK_SAT;
    }

    @Override
    public int daysOfMonth(int month) {
        if (month<7){
            return 31;
        }else if (month<12){
            return 30;
        }else{
            if (isLeapYear())
                return 30;
            else
                return 29;
        }
    }

    @Override
    public int daysOfMonth() {
        return daysOfMonth(getMonth());
    }

    @Override
    public boolean isLeapYear() {
        return JalaliCalendar.isLeepYear(getYMD().getYear());
    }

    @Override
    public String[] monthNamesOfYear() {
        return monthNames;
    }

    @Override
    public String[] shortMonthNamesOfYear() {
        return monthNames;
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
        return getDay()+splitter+getShortMonthName();
    }

    @Override
    public String getDateByShortMonthName() {
        return getDateByShortMonthName(" ");
    }

    @Override
    public String getDateNumerical(String splitter) {
        return getYear()+splitter+getMonth()+splitter+getDay();
    }

    @Override
    public String getDateNumerical() {
        return getDateNumerical(" ");
    }

    @Override
    public String getMonthNameAndDay(String splitter) {
        return getMonthName()+splitter+getDay();
    }

    @Override
    public String getMonthNameAndDay() {
        return getMonthNameAndDay(" ");
    }

    @Override
    public String getShortMonthNameAndDay(String splitter) {
        return getShortMonthName()+splitter+getDay();
    }

    @Override
    public String getShortMonthNameAndDay() {
        return getShortMonthNameAndDay(" ");
    }

    @Override
    public String getYearAndMonthName(String splitter) {
        return getMonthName() + splitter + getYear();
    }

    @Override
    public String getYearAndMonthName() {
        return getYearAndMonthName(" ");
    }

    @Override
    public String getYearAndShortMonthName(String splitter) {
        return getYear() +splitter+getShortMonthName();
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
    public String[] getMonthHeaderDayNames() {
        return new String[]{"ش","ی","د","س","چ","پ","ج"};
    }

    @Override
    public int getYear() {
        return getYMD().getYear();
    }

    @Override
    public int getMonth() {
        return getYMD().getMonth()+1;
    }

    @Override
    public int getDay() {
        return getYMD().getDate();
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

    private JalaliCalendar.YearMonthDate getYMD(){
        return JalaliCalendar.gregorianToJalali(new JalaliCalendar.YearMonthDate(calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)));
    }

}
