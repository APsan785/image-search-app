package com.apsan.imagesearchapp.ui;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.paging.LoadState;
import androidx.paging.LoadStateAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.apsan.imagesearchapp.R;
import com.manojbhadane.QButton;

import org.jetbrains.annotations.NotNull;

public class PhotoLoadStateAdapter extends LoadStateAdapter<PhotoLoadStateAdapter.PhotoViewHolder> {

    private PhotoAdapter adapter;

    public PhotoLoadStateAdapter(PhotoAdapter adapter) {
        this.adapter = adapter;
    }


    @NotNull
    @Override
    public PhotoViewHolder onCreateViewHolder(@NotNull ViewGroup viewGroup, @NotNull LoadState loadState) {
        return new PhotoViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.header_footer_load_layout,
                viewGroup, false), adapter);
    }

    @Override
    public void onBindViewHolder(@NotNull PhotoViewHolder photoViewHolder, @NotNull LoadState loadState) {
        photoViewHolder.bindview(loadState);
    }


    public static class PhotoViewHolder extends RecyclerView.ViewHolder {
        private TextView textview;
        private QButton button;
        private ProgressBar progressBar;

        public PhotoViewHolder(@NonNull View itemView, PhotoAdapter adapter) {
            super(itemView);
            textview = itemView.findViewById(R.id.textViewheader);
            button = itemView.findViewById(R.id.retry_btn_header);
            progressBar = itemView.findViewById(R.id.progressBarHeader);
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    adapter.retry();
                }
            });
        }

        public void bindview(LoadState loadState) {
            progressBar.setVisibility(loadState instanceof LoadState.Loading ? View.VISIBLE : View.GONE);
            button.setVisibility(!(loadState instanceof LoadState.Loading) ? View.VISIBLE : View.GONE);
            textview.setVisibility(!(loadState instanceof LoadState.Loading) ? View.VISIBLE : View.GONE);
        }
    }
}

