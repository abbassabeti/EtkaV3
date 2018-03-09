package ir.etkastores.app;

/**
 * Created by Sajad on 1/28/18.
 */

public class DummyProvider {

    private static String[] dates = new String[]{"۱۰ مرداد ۱۳۹۶",
            "۲۱ شهریور ۱۳۹۶",
            "۲۷ مهر ۱۳۹۶",
            "۲۲ آبان ۱۳۹۶",
            "۲۲ آذر ۱۳۹۶",
            "۳۰ دی ۱۳۹۶",
            "۵ بهمن ۱۳۹۶"};

    private static int dateCounter = 0;

    public static String getRandomDate(){
        String dt= dates[dateCounter];
        dateCounter +=1;
        if (dateCounter == 7) dateCounter = 0;
        return dt;
    }

    public static int getRandomSlider(){
        return R.drawable.etka_logo_vector;
    }


}
