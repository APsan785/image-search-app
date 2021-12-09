package com.apsan.imagesearchapp.ui.details;

import android.content.Intent;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.apsan.imagesearchapp.MainActivity;
import com.apsan.imagesearchapp.R;
import com.apsan.imagesearchapp.api.models.UnsplashPhotos;
import com.apsan.imagesearchapp.databinding.DetailFragmentBinding;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;

public class DetailsFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.detail_fragment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ImageView receivedPhoto = view.findViewById(R.id.received_photo);
        TextView textViewDescription = view.findViewById(R.id.textView_description);
        TextView textViewCreator = view.findViewById(R.id.textView_creator);
        ProgressBar progressBarDetail = view.findViewById(R.id.progressBar_detail);
        UnsplashPhotos photo_received = DetailsFragmentArgs.fromBundle(getArguments()).getUnsplashPhoto();
        Glide.with(DetailsFragment.this)
                .load(photo_received.getUrls().getFull())
                .error(R.drawable.ic_baseline_error_24)
                .listener(new RequestListener<Drawable>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                        progressBarDetail.setVisibility(View.GONE);
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                        progressBarDetail.setVisibility(View.GONE);
                        textViewCreator.setVisibility(View.VISIBLE);
                        textViewDescription.setVisibility(photo_received.getDescription() != null ? View.VISIBLE : View.GONE);
                        return false;
                    }
                })
                .into(receivedPhoto);

        textViewDescription.setText(photo_received.getDescription());
        textViewCreator.setText("Photo by: "+photo_received.getUser().getName());
        if (photo_received.getUser().getPortfolio_url() != null) {
            Uri uri = Uri.parse(photo_received.getUser().getPortfolio_url());
            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
            textViewCreator.setOnClickListener(v -> startActivity(intent));
            textViewCreator.setPaintFlags(Paint.UNDERLINE_TEXT_FLAG);
        }
    }
}
