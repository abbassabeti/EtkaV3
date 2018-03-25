package ir.etkastores.app.utils;

/**
 * Created by garshasbi on 2/24/18.
 */

public class StringUtils {

    public static String toEnglishDigit(String str){
        String s = str;
        s = s.replace("۰","0");
        s = s.replace("۱","1");
        s = s.replace("۲","2");
        s = s.replace("۳","3");
        s = s.replace("۴","4");
        s = s.replace("۵","5");
        s = s.replace("۶","6");
        s = s.replace("۷","7");
        s = s.replace("۸","8");
        s = s.replace("۹","9");
        return s;
    }

    public static String toPersianDigit(String str){
        String s = str;
        s = s.replace("0","۰");
        s = s.replace("1","۱");
        s = s.replace("2","۲");
        s = s.replace("3","۳");
        s = s.replace("4","۴");
        s = s.replace("5","۵");
        s = s.replace("6","۶");
        s = s.replace("7","۷");
        s = s.replace("8","۸");
        s = s.replace("9","۹");
        return s;
    }

}
