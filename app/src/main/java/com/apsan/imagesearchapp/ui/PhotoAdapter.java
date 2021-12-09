package com.apsan.imagesearchapp.ui;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.paging.PagedListAdapter;
import androidx.paging.PagingDataAdapter;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.apsan.imagesearchapp.R;
import com.apsan.imagesearchapp.api.models.UnsplashPhotos;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;

public class PhotoAdapter extends PagingDataAdapter<UnsplashPhotos, PhotoAdapter.PhotoHolder> {

    protected PhotoAdapter(ItemClick itemClick) {
        super(diffCallback);
        this.itemClick = itemClick;
    }

    Context context;
    ItemClick itemClick;

    @NonNull
    @Override
    public PhotoHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        return new PhotoHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.gallery_views, parent, false),
                itemClick);
    }

    @Override
    public void onBindViewHolder(@NonNull PhotoHolder holder, int position) {
        UnsplashPhotos photo = getItem(position);
        holder.username.setText(photo.getUser().getUsername());
        Glide.with(context)
                .load(photo.getUrls().getRegular())
                .centerCrop()
                .transition(DrawableTransitionOptions.withCrossFade())
                .error(R.drawable.ic_baseline_error_24)
                .into(holder.imageView);
    }

    public class PhotoHolder extends RecyclerView.ViewHolder {
        TextView username;
        ImageView imageView;

        public PhotoHolder(@NonNull View itemView, ItemClick itemClick) {
            super(itemView);
            int position = getBindingAdapterPosition();
            username = itemView.findViewById(R.id.text_view_username);
            imageView = itemView.findViewById(R.id.image_view);
 /*           imageView.setOnClickListener(v -> {
                if(position!=RecyclerView.NO_POSITION){
                    UnsplashPhotos photo = getItem(position);
                    if(photo!=null) itemClick.onItemClick(photo);
                }
            });
   */
            imageView.setOnClickListener(v-> itemClick.onItemClick(getItem(getBindingAdapterPosition())));
            }
    }

    public static DiffUtil.ItemCallback<UnsplashPhotos> diffCallback = new DiffUtil.ItemCallback<UnsplashPhotos>() {
        @Override
        public boolean areItemsTheSame(@NonNull UnsplashPhotos oldItem, @NonNull UnsplashPhotos newItem) {
            return oldItem.getId().equals(newItem.getId());
        }

        @Override
        public boolean areContentsTheSame(@NonNull UnsplashPhotos oldItem, @NonNull UnsplashPhotos newItem) {
            return oldItem.getUser().getUsername().equals(newItem.getUser().getUsername()) &&
                    oldItem.getUrls().getRegular().equals(newItem.getUrls().getRegular());
        }
    };
    interface ItemClick{
        void onItemClick(UnsplashPhotos photo);
    }
}
