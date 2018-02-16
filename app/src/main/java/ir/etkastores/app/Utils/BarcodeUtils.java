package ir.etkastores.app.Utils;

import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.support.v7.widget.AppCompatImageView;
import android.text.TextUtils;
import android.view.ViewTreeObserver;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;

import java.util.EnumMap;
import java.util.Map;

/**
 * Created by Sajad on 2/16/18.
 */

public class BarcodeUtils {

    private static int w = 0;
    private static int h = 0;

    public synchronized static void generateBarcodeBitmap(final String data, final BarcodeFormat format, final AppCompatImageView imageView) {
        if (!TextUtils.isEmpty(data) && format != null && imageView != null) {

            w = imageView.getMeasuredWidth();
            h = imageView.getMeasuredHeight();

            if (w < 1 || h < 1) {
                ViewTreeObserver vto = imageView.getViewTreeObserver();
                vto.addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
                    public boolean onPreDraw() {
                        imageView.getViewTreeObserver().removeOnPreDrawListener(this);
                        h = imageView.getMeasuredHeight();
                        w = imageView.getMeasuredWidth();
                        generateBarcodeBitmap(data, format, w, h, new BarcodeGenerateCallback() {
                            @Override
                            public void onGenerateBitMap(Bitmap bitmap) {
                                if (imageView != null) {
                                    imageView.setImageBitmap(bitmap);
                                }
                            }
                        });
                        return true;
                    }
                });
            } else {
                generateBarcodeBitmap(data, format, imageView.getMeasuredWidth(), imageView.getMeasuredHeight(), new BarcodeGenerateCallback() {
                    @Override
                    public void onGenerateBitMap(Bitmap bitmap) {
                        if (imageView != null) {
                            imageView.setImageBitmap(bitmap);
                        }
                    }
                });
            }
        }
    }

    public synchronized static void generateBarcodeBitmap(final String data, final BarcodeFormat format, final int width, final int height, final BarcodeGenerateCallback callback) {

        if (TextUtils.isEmpty(data) || format == null || width == 0 || height == 0 || callback == null) {
            return;
        }

        new AsyncTask<Void, Void, Bitmap>() {

            @Override
            protected Bitmap doInBackground(Void... voids) {
                try {
                    return encodeAsBitmap(data, format, width, height);
                } catch (Exception err) {
                    return null;
                }
            }

            @Override
            protected void onPostExecute(Bitmap bitmap) {
                super.onPostExecute(bitmap);
                if (callback != null) callback.onGenerateBitMap(bitmap);
            }
        }.execute();
    }

    public interface BarcodeGenerateCallback {
        void onGenerateBitMap(Bitmap bitmap);
    }

    /**************************************************************
     * getting from com.google.zxing.client.android.encode.QRCodeEncoder
     *
     * See the sites below
     * http://code.google.com/p/zxing/
     * http://code.google.com/p/zxing/source/browse/trunk/android/src/com/google/zxing/client/android/encode/EncodeActivity.java
     * http://code.google.com/p/zxing/source/browse/trunk/android/src/com/google/zxing/client/android/encode/QRCodeEncoder.java
     */

    private static final int WHITE = 0xFFFFFFFF;
    private static final int BLACK = 0xFF000000;

    private synchronized static Bitmap encodeAsBitmap(String contents, BarcodeFormat format, int img_width, int img_height) throws WriterException {
        String contentsToEncode = contents;
        if (contentsToEncode == null) {
            return null;
        }
        Map<EncodeHintType, Object> hints = null;
        String encoding = guessAppropriateEncoding(contentsToEncode);
        if (encoding != null) {
            hints = new EnumMap<EncodeHintType, Object>(EncodeHintType.class);
            hints.put(EncodeHintType.CHARACTER_SET, encoding);
        }
        MultiFormatWriter writer = new MultiFormatWriter();
        BitMatrix result;
        try {
            result = writer.encode(contentsToEncode, format, img_width, img_height, hints);
        } catch (IllegalArgumentException iae) {
            // Unsupported format
            return null;
        }
        int width = result.getWidth();
        int height = result.getHeight();
        int[] pixels = new int[width * height];
        for (int y = 0; y < height; y++) {
            int offset = y * width;
            for (int x = 0; x < width; x++) {
                pixels[offset + x] = result.get(x, y) ? BLACK : WHITE;
            }
        }

        Bitmap bitmap = Bitmap.createBitmap(width, height,
                Bitmap.Config.ARGB_8888);
        bitmap.setPixels(pixels, 0, width, 0, 0, width, height);
        return bitmap;
    }

    private synchronized static String guessAppropriateEncoding(CharSequence contents) {
        // Very crude at the moment
        for (int i = 0; i < contents.length(); i++) {
            if (contents.charAt(i) > 0xFF) {
                return "UTF-8";
            }
        }
        return null;
    }


}
