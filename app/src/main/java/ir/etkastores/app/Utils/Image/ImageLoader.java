package ir.etkastores.app.Utils.Image;

import android.content.Context;
import android.support.v7.widget.AppCompatImageView;

import com.bumptech.glide.Glide;

/**
 * Created by Sajad on 1/2/18.
 */

public class ImageLoader {

    public static void load(Context context, AppCompatImageView imageView, String url){
        try{
            Glide.with(context).load(url).into(imageView);
        }catch (Exception err){
            err.printStackTrace();
        }
    }

}
