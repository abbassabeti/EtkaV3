package ir.etkastores.app.data;

import android.text.TextUtils;

import io.michaelrocks.paranoid.Obfuscate;
import ir.etkastores.app.utils.DiskDataHelper;

/**
 * Created by garshasbi on 4/3/18.
 */

@Obfuscate
public class ContactUsManager {

    private final static String PHONE = "PHONE";
    private final static String EMAIL = "EMAIL";

    private static ContactUsManager instance;

    public static ContactUsManager getInstance() {
        if (instance == null) instance = new ContactUsManager();
        return instance;
    }

    private ContactUsManager() {

    }

    public void savePhone(String phone) {
        DiskDataHelper.putString(PHONE, phone);
    }

    public void saveEmail(String email) {
        DiskDataHelper.putString(EMAIL, email);
    }

    public String getPhone() {
        String phone = DiskDataHelper.getString(PHONE);
        if (TextUtils.isEmpty(phone)) {
            return "";
        } else {
            return phone;
        }
    }

    public String getEmail() {
        String email = DiskDataHelper.getString(EMAIL);
        if (TextUtils.isEmpty(email)) {
            return "";
        } else {
            return email;
        }
    }


}
