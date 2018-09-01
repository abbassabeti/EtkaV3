package ir.etkastores.app.utils;

import android.annotation.SuppressLint;
import android.util.Base64;

public class StringXORer {

    public String encode(String s, String key) {
        return base64Encode(xorWithKey(s.getBytes(), key.getBytes()));
    }

    public String decode(String s, String key) {
        return new String(xorWithKey(base64Decode(s), key.getBytes()));
    }

    private byte[] xorWithKey(byte[] a, byte[] key) {
        byte[] out = new byte[a.length];
        for (int i = 0; i < a.length; i++) {
            out[i] = (byte) (a[i] ^ key[i % key.length]);
        }
        return out;
    }

    @SuppressLint("NewApi")
    private byte[] base64Decode(String s) {
        try {
            return Base64.decode(s, Base64.DEFAULT);
        } catch (IllegalArgumentException e) {
            throw new RuntimeException(e);
        }
    }

    @SuppressLint("NewApi")
    private String base64Encode(byte[] bytes) {
        return Base64.encodeToString(bytes, Base64.DEFAULT).replaceAll("\\s", "");
    }

}

