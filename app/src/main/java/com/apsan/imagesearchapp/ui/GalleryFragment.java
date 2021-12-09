package com.apsan.imagesearchapp.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.paging.LoadState;
import androidx.paging.PagedList;
import androidx.paging.PagingData;
import androidx.recyclerview.widget.RecyclerView;

import com.apsan.imagesearchapp.R;
import com.apsan.imagesearchapp.api.models.UnsplashPhotos;
import com.manojbhadane.QButton;

import dagger.hilt.android.AndroidEntryPoint;
import kotlin.Unit;

@AndroidEntryPoint
public class GalleryFragment extends Fragment {

    RecyclerView recyclerView;
    GalleryViewModel viewModel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        return inflater.inflate(R.layout.gallery_fragment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ProgressBar progressBar = view.findViewById(R.id.progress_bar);
        TextView errormsg = view.findViewById(R.id.error_msg);
        QButton retrybtn = view.findViewById(R.id.retry_btn);
        TextView noresult = view.findViewById(R.id.no_result_found);
        PhotoAdapter adapter = new PhotoAdapter(new PhotoAdapter.ItemClick() {
            @Override
            public void onItemClick(UnsplashPhotos photo) {
                GalleryFragmentDirections.ActionGalleryFragmentToDetailsFragment action = GalleryFragmentDirections.actionGalleryFragmentToDetailsFragment(photo);
                Navigation.findNavController(view).navigate(action);
            }
        });

        recyclerView = view.findViewById(R.id.recycler_view);
        recyclerView.setAdapter(adapter.withLoadStateHeaderAndFooter(
                new PhotoLoadStateAdapter(adapter),
                new PhotoLoadStateAdapter(adapter)
        ));
        viewModel = new ViewModelProvider(this).get(GalleryViewModel.class);


        viewModel.getPhotos().observe(getViewLifecycleOwner(), unsplashPhotosPagingData
                -> adapter.submitData(getViewLifecycleOwner().getLifecycle(), unsplashPhotosPagingData));
//        setHasOptionsMenu(true);

        adapter.addLoadStateListener(combinedLoadStates -> {
            progressBar.setVisibility(combinedLoadStates.getSource().getRefresh() instanceof LoadState.Loading ? View.VISIBLE:View.GONE);
            recyclerView.setVisibility(combinedLoadStates.getSource().getRefresh() instanceof LoadState.NotLoading ? View.VISIBLE:View.GONE);
            retrybtn.setVisibility(combinedLoadStates.getSource().getRefresh() instanceof LoadState.Error ? View.VISIBLE:View.GONE);
            errormsg.setVisibility(combinedLoadStates.getSource().getRefresh() instanceof LoadState.Error ? View.VISIBLE:View.GONE);

            // empty view
            if (combinedLoadStates.getSource().getRefresh() instanceof LoadState.NotLoading &&
                    combinedLoadStates.getAppend().getEndOfPaginationReached() &&
                    adapter.getItemCount() < 1) {
                recyclerView.setVisibility(View.GONE);
                noresult.setVisibility(View.VISIBLE);
            } else {
                noresult.setVisibility(View.GONE);
            }

            return null;
        });
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);

        inflater.inflate(R.menu.menu_gallery, menu);

        SearchView searchView = (SearchView) menu.findItem(R.id.action_search).getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                if (query != null) {
                    recyclerView.smoothScrollToPosition(0);
                    viewModel.searchPhotos(query);
                    searchView.clearFocus();
                }
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return true;
            }
        });
    }

}
