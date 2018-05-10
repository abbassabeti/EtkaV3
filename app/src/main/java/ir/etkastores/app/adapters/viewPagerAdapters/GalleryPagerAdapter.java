package ir.etkastores.app.adapters.viewPagerAdapters;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v4.view.PagerAdapter;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.target.SizeReadyCallback;
import com.bumptech.glide.request.transition.Transition;
import com.github.chrisbanes.photoview.PhotoView;

import java.util.List;

import ir.etkastores.app.R;
import ir.etkastores.app.utils.image.ImageLoader;

/**
 * Created by garshasbi on 4/4/18.
 */

public class GalleryPagerAdapter extends PagerAdapter {

    Context context;
    LayoutInflater inflater;
    private List<String> items;

    public GalleryPagerAdapter(Context context,List<String> items) {
        this.context = context;
        this.items = items;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == ((CardView) object);
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View itemView = inflater.inflate(R.layout.gallery_pager_slide, container, false);
        final PhotoView photoView = itemView.findViewById(R.id.photoView);
        photoView.setImageDrawable(null);
        ImageLoader.loadImage(context, items.get(position), new SimpleTarget<Drawable>() {
            @Override
            public void onResourceReady(Drawable drawable, Transition<? super Drawable> transition) {
                try{
                    photoView.setImageDrawable(drawable);
                }catch (Exception err){
                    err.printStackTrace();
                }
            }

            @Override
            public void removeCallback(SizeReadyCallback sizeReadyCallback) {

            }
        });

        container.addView(itemView);
        return itemView;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((CardView) object);
    }

}
