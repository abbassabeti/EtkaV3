package ir.etkastores.app.adapters.recyclerViewAdapters;

import android.content.Context;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import ir.etkastores.app.R;
import ir.etkastores.app.utils.image.ImageLoader;

/**
 * Created by garshasbi on 4/3/18.
 */

public class GalleryRecyclerHorizontalListAdapter extends RecyclerView.Adapter<GalleryRecyclerHorizontalListAdapter.ViewHolder> {

    private Context context;
    private LayoutInflater inflater;
    private List<String> items;
    private int selectedItem = 0;

    private OnImageSelectListener onImageSelectListener;

    public GalleryRecyclerHorizontalListAdapter(Context context, List<String> items) {
        this.context = context;
        this.inflater = LayoutInflater.from(context);
        this.items = items;
    }

    @Override
    public GalleryRecyclerHorizontalListAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        return new ViewHolder(inflater.inflate(R.layout.cell_gallery_thumb, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(GalleryRecyclerHorizontalListAdapter.ViewHolder viewHolder, int i) {
        viewHolder.bind(items.get(i));
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public void setSelectedItem(int index){
        selectedItem = index;
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.image)
        AppCompatImageView imageView;

        public ViewHolder(final View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onImageSelectListener != null)
                        onImageSelectListener.onImageSelect(items.get(getAdapterPosition()), getAdapterPosition());
                }
            });
        }

        public void bind(String url) {
            ImageLoader.loadImage(context, imageView, url);
            int position = getAdapterPosition();
            if (position == selectedItem){
                itemView.setAlpha(1f);
            }else{
                itemView.setAlpha(0.3f);
            }
        }

    }

    public void setOnImageSelectListener(OnImageSelectListener callback) {
        this.onImageSelectListener = callback;
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false));
    }

    public interface OnImageSelectListener {
        void onImageSelect(String img, int index);
    }

}
