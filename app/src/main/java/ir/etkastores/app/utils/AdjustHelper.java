package ir.etkastores.app.utils;

import com.adjust.sdk.Adjust;
import com.adjust.sdk.AdjustEvent;

/**
 * Created by garshasbi on 4/21/18.
 */

public class AdjustHelper {

    //1
    public final static String AddToNextShoppingList = "2unh79";
    //2
    public final static String CallPhoneStore = "8fgrcu";
    //3
    public final static String CallToSupport = "novq6t";
    //4
    public final static String ClickBannerHome = "t53ztl";
    //5
    public final static String ClickHekmatHome = "5om2ae";
    //6
    public final static String ClickProductHome = "sd8gkd";
    //7
    public final static String DeleteFromNextShoppingList = "vkjiv4";
    //8
    public final static String DisableHekmatNotifications = "37ah6y";
    //9
    public final static String EditProfile = "frulkh";
    //10
    public final static String EmailToSupport = "phm5up";
    //11
    public final static String EnableHekmatNotifications = "a7n3l8";
    //12
    public final static String FailureChangePassword = "7ecg95";
    //13
    public final static String FilterCategory = "nzhik4";
    //14
    public final static String FilterSort = "bxuzhy";
    //15
    public final static String FindMyLocationInMap = "w5ka1n";
    //16
    public final static String HekmatShowStores = "wiuqog";
    //17
    public final static String HomeTab = "i6s120";
    //18
    public final static String InStoreMode = "tfl61f";
    //19
    public final static String OpenInviteFriend = "k1b86n";
    //20
    public final static String Login = "6c7p9o";
    //21
    public final static String Logout = "d4xo4r";
    //22
    public final static String MapTab = "iquknf";
    //23
    public final static String NewsButton = "sj9kgk";
    //24
    public final static String OpenAboutEtka = "upgbue";
    //25
    public final static String OpenBarcodeScanner = "7u9ad7";
    //26
    public final static String OpenChangePassword = "10bz9a";
    //27
    public final static String OpenEditProfile = "97fxu9";
    //28
    public final static String OpenFAQ = "7s9b1h";
    //29
    public final static String OpenHekmatCard = "g29rs1";
    //30
    public final static String OpenHekmatFromStore = "5deu78";
    //31
    public final static String OpenNewsDetail = "rn9ss8";
    //32
    public final static String OpenNewTicket = "3fx4d4";
    //33
    public final static String OpenNextShoppingList = "hs9e94";
    //34
    public final static String OpenPoints = "tk29ia";
    //35
    public final static String OpenProductFromNextShoppingList = "hakyin";
    //36
    public final static String OpenProductFromRelateds = "fectwk";
    //37
    public final static String OpenProductImage = "6vqtd3";
    //38
    public final static String OpenProfileOtherPages = "ekm8ya";
    //39
    public final static String OpenProfileSetting = "8229mm";
    //40
    public final static String OpenRules = "1cg4zr";
    //41
    public final static String OpenStoreFromList = "fvw70p";
    //42
    public final static String OpenStoreFromMap = "2c2lpy";
    //43
    public final static String OpenSupport = "sxn6rn";
    //44
    public final static String OpenSurvey = "974v5n";
    //45
    public final static String OpenUserPrivacy = "n1hqbd";
    //46
    public final static String ProfileTab = "67j2ns";
    //47
    public final static String Register = "kweskj";
    //48
    public final static String ScanBarcode = "vnvtry";
    //49
    public final static String SearchKeyword = "i7fthr";
    //50
    public final static String SearchTab = "3dzqxa";
    //51
    public final static String SelectCategoryFromSearch = "781l8d";
    //52
    public final static String SelectProductFromSearch = "dadfm0";
    //53
    public final static String SelectStoreFromSearchSuggestion = "1cvkdu";
    //54
    public final static String ShareStore = "d8gi9m";
    //55
    public final static String ShowFAQDetail = "xm2r0h";
    //56
    public final static String SplashOpen = "knk3du";
    //57
    public final static String SubmitNewTicket = "qy5kh1";
    //58
    public final static String SubmitSurvey = "c4kljz";
    //59
    public final static String SuccessChangePassword = "as649e";
    //60
    public final static String TraceRoutesStores = "h8u3le";
    //61
    public final static String WalkthroughEnter = "elnh5m";
    //62
    public final static String OpenFactors = "urae4z";
    //63
    public final static String OpenFactorList = "1ckm9r";

    public static void sendAdjustEvent(String name){
        try {
            AdjustEvent event = new AdjustEvent(name.toLowerCase());
            Adjust.trackEvent(event);
        }catch (Exception err){
            err.printStackTrace();
        }
    }


}
