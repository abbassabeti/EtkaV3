package ir.etkastores.app.utils.image;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.AppCompatImageView;
import android.text.TextUtils;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;
import com.franmontiel.persistentcookiejar.PersistentCookieJar;
import com.franmontiel.persistentcookiejar.cache.SetCookieCache;
import com.franmontiel.persistentcookiejar.persistence.SharedPrefsCookiePersistor;
import com.squareup.picasso.OkHttp3Downloader;
import com.squareup.picasso.Picasso;

import java.net.CookieHandler;
import java.net.CookieManager;
import java.net.CookiePolicy;
import java.util.List;

import ir.etkastores.app.R;
import okhttp3.CookieJar;
import okhttp3.OkHttpClient;
import okhttp3.internal.JavaNetCookieJar;

/**
 * Created by Sajad on 1/2/18.
 */

public class ImageLoader {

    public static void loadImage(Context context, AppCompatImageView imageView, String url){
        try {
//            Glide.with(context).load(url.trim()).into(imageView);
            Picasso.get().setLoggingEnabled(true);
            Picasso.get().load(url).into(imageView);
        }catch (Exception err){
            err.printStackTrace();
        }
    }

    public static void loadImage(Context context, String url, Target<Drawable> target){
        try {
            Glide.with(context).load(url.trim()).into(target);
//            Picasso.get().load(url).into(imageView);
        }catch (Exception err){
            err.printStackTrace();
        }
    }

    public static void loadProductImage(Context context, AppCompatImageView imageView, String url) {
        try {
            if (TextUtils.isEmpty(url)){
                imageView.setImageResource(R.drawable.product_place_holder);
            }else{
//                Glide.with(context).load(url.trim()).apply(getRequestOptions()).into(imageView);
                Picasso.get().setLoggingEnabled(true);
                Picasso.get().load(url).into(imageView);
            }
        } catch (Exception err) {
            err.printStackTrace();
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
            requestOptionsProducts.placeholder(R.drawable.product_place_holder);
            requestOptionsProducts.error(R.drawable.product_place_holder);
        }
        return requestOptionsProducts;
    }

//    private static OkHttp3Downloader getPicassoDownloader(Context context){
//        CookieJar cookieJar =
//                new PersistentCookieJar(new SetCookieCache(), new SharedPrefsCookiePersistor(context));
//        OkHttpClient defaultHttpClient = new OkHttpClient.Builder()
//                .cookieJar(cookieJar)
//                .build();
//        return new OkHttp3Downloader(defaultHttpClient);
//    }
//
//    public static Picasso getPicasso(Context context){
//        return new Picasso.Builder(context).downloader(getPicassoDownloader(context)).build();
//    }

}
