package ir.etkastores.app.adapters.viewPagerAdapters;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import java.util.List;

import ir.etkastores.app.R;

/**
 * Created by garshasbi on 4/4/18.
 */

public class GalleryPagerAdapter extends PagerAdapter {

    Context context;
    LayoutInflater inflater;
    private List<String> items;

    public GalleryPagerAdapter(Context context) {
        this.context = context;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == ((LinearLayout) object);
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {

//        View itemView = inflater.inflate(R.layout.pager_item, container, false);
//
//        ImageView imageView = (ImageView) itemView.findViewById(R.id.imageView);
//        imageView.setImageResource(mResources[position]);

//        container.addView(itemView);
//
//        return itemView;

        return null;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((LinearLayout) object);
    }

}
