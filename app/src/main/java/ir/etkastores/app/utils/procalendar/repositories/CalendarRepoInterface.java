package ir.etkastores.app.utils.procalendar.repositories;

/**
 * Created by Sajad on 9/9/17.
 */

public interface CalendarRepoInterface {

    int ITEM_YEAR = 1, ITEM_MONTH = 2, ITEM_DAY = 3;
    int START_WEEK_SAT=0,START_WEEK_SUN=1;
    int firstDayOfMonthInWeek();
    int firstDayOfWeek();
    int daysOfMonth(int month);
    int daysOfMonth();
    boolean isLeapYear();
    String[] monthNamesOfYear();
    String[] shortMonthNamesOfYear();
    String[] dayNamesOfWeek();
    String[] shortDayNamesOfWeek();
    String getDateByMonthName(String splitter);
    String getDateByMonthName();
    String getDateByShortMonthName(String splitter);
    String getDateByShortMonthName();
    String getDateNumerical(String splitter);
    String getDateNumerical();
    String getMonthNameAndDay(String splitter);
    String getMonthNameAndDay();
    String getShortMonthNameAndDay(String splitter);
    String getShortMonthNameAndDay();
    String getYearAndMonthName(String splitter);
    String getYearAndMonthName();
    String getYearAndShortMonthName(String splitter);
    String getYearAndShortMonthName();
    String getMonthName();
    String getShortMonthName();
    String getDayNameInWeek();
    String getShortDayNameInWeek();
    String[] getMonthHeaderDayNames();
    int getYear();
    int getMonth();
    int getDay();
    long getEpoch();
    String getYYYYMMDD(String splitter);

}
