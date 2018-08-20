package ir.etkastores.app.utils.image;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.AppCompatImageView;
import android.text.TextUtils;
import android.util.Log;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;
import java.util.List;

import ir.etkastores.app.R;

/**
 * Created by Sajad on 1/2/18.
 */

public class ImageLoader {

    public static void loadImage(Context context, AppCompatImageView imageView, String url){
        try {
            Glide.with(context).load(url.trim()).into(imageView);
        }catch (Exception err){
            err.printStackTrace();
            Log.e("load image crash",""+err.getLocalizedMessage());
        }
    }

    public static void loadImage(Context context, String url, Target<Drawable> target){
        try {
            Glide.with(context).load(url.trim()).into(target);
        }catch (Exception err){
            err.printStackTrace();
            Log.e("load image crash2",""+err.getLocalizedMessage());
        }
    }

    public static void loadProductImage(Context context, AppCompatImageView imageView, String url) {
        try {
            imageView.setImageResource(R.drawable.product_place_holder);
            if (TextUtils.isEmpty(url)){
                imageView.setImageResource(R.drawable.product_place_holder);
            }else{
                Glide.with(context).load(url.trim()).apply(getRequestOptions()).into(imageView);
            }
        } catch (Exception err) {
            err.printStackTrace();
            Log.e("load image crash3",""+err.getLocalizedMessage());
        }
    }

    public static void loadProductImage(Context context, AppCompatImageView imageView, List<String> urls) {
        if (urls == null || urls.size() == 0) {
            loadProductImage(context, imageView, "");
        } else {
            loadProductImage(context, imageView, urls.get(0));
        }
    }

    private static RequestOptions requestOptionsProducts;

    private static RequestOptions getRequestOptions() {
        if (requestOptionsProducts == null) {
            requestOptionsProducts = new RequestOptions();
            requestOptionsProducts = requestOptionsProducts.placeholder(R.drawable.product_place_holder);
            requestOptionsProducts = requestOptionsProducts.error(R.drawable.product_place_holder);
        }
        return requestOptionsProducts;
    }

}
