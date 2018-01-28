package ir.etkastores.app;

/**
 * Created by Sajad on 1/28/18.
 */

public class DummyProvider {

    private static int[] imgs = new int[]{R.drawable.t1,
            R.drawable.t2,
            R.drawable.t3,
            R.drawable.t4,
            R.drawable.t5,
            R.drawable.t6,
            R.drawable.t7,
            R.drawable.t8,
            R.drawable.t9,
            R.drawable.t10,
            R.drawable.t11,
            R.drawable.t12,
            R.drawable.t13,
            R.drawable.t14};

    private static int imgCounter = 0;
    public static int getRandomImgId(){
        int selectedImage = imgs[imgCounter];
        imgCounter +=1;
        if (imgCounter == 14) imgCounter = 0;
        return selectedImage;
    }

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


    private static int[] sliders = new int[]{R.drawable.s1,
            R.drawable.s2,
            R.drawable.s3};

    private static int sliderCounter = 0;
    public static int getRandomSlider(){
        int selectedImage = sliders[sliderCounter];
        sliderCounter +=1;
        if (sliderCounter == 3) sliderCounter = 0;
        return selectedImage;
    }


}
