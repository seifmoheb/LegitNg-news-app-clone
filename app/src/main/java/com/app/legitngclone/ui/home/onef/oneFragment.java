package com.app.legitngclone.ui.home.onef;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;

import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.app.legitngclone.R;
import com.app.legitngclone.pojo.PostModel;
import com.app.legitngclone.ui.main.PostsAdapter;
import com.facebook.shimmer.ShimmerFrameLayout;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Cache;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import static android.view.View.VISIBLE;

public class oneFragment extends Fragment {

    private OneFragmentViewModel oneFragmentViewModel;
    RecyclerView recyclerView;
    PostsAdapter postsAdapter;
    private ShimmerFrameLayout shimmerFrameLayout;
    SwipeRefreshLayout swipeRefreshLayout;
    ProgressBar progressBar;
    NestedScrollView scrollView;
    int page = 1, limit = 20;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View root = inflater.inflate(R.layout.fragment_one, container, false);
        oneFragmentViewModel =
                ViewModelProviders.of(this).get(OneFragmentViewModel.class);
        oneFragmentViewModel.getPosts(page, limit);
        recyclerView = root.findViewById(R.id.recycler);
        postsAdapter = new PostsAdapter();
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(postsAdapter);
        scrollView = (NestedScrollView) root.findViewById(R.id.scrollView);
        progressBar = root.findViewById(R.id.progress_bar);

        swipeRefreshLayout = root.findViewById(R.id.swipeContainer);
        shimmerFrameLayout = root.findViewById(R.id.shimmer);
        shimmerFrameLayout.startShimmer();
        swipeRefreshLayout.setEnabled(false);


        oneFragmentViewModel.postMutableLiveData.observeForever(new Observer<List<PostModel>>() {
            @Override
            public void onChanged(List<PostModel> postModels) {
                progressBar.setVisibility(View.GONE);
                postsAdapter.setmContext(getContext());
                postsAdapter.setPostsList(postModels);
                shimmerFrameLayout.stopShimmer();
                shimmerFrameLayout.setVisibility(View.INVISIBLE);
                swipeRefreshLayout.setEnabled(true);
            }
        });
        postsAdapter.notifyDataSetChanged();
        scrollView.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
            @Override
            public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                if (scrollY == v.getChildAt(0).getMeasuredHeight() - v.getMeasuredHeight()) {
                    if (page < 4) {
                        progressBar.setVisibility(VISIBLE);
                        page++;
                        oneFragmentViewModel.getPosts(page, limit);
                        postsAdapter.notifyDataSetChanged();
                    }
                }
            }
        });

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

                scrollView.setVisibility(View.INVISIBLE);
                shimmerFrameLayout.setVisibility(View.VISIBLE);
                shimmerFrameLayout.startShimmer();
                swipeRefreshLayout.setRefreshing(false);
                oneFragmentViewModel.postMutableLiveData.observe((LifecycleOwner) getContext(), new Observer<List<PostModel>>() {
                    @Override
                    public void onChanged(List<PostModel> postModels) {
                        progressBar.setVisibility(View.GONE);
                        postsAdapter.setmContext(getContext());
                        postsAdapter.setPostsList(postModels);
                        shimmerFrameLayout.stopShimmer();
                        shimmerFrameLayout.setVisibility(View.INVISIBLE);
                        scrollView.setVisibility(VISIBLE);
                        swipeRefreshLayout.setEnabled(true);
                    }
                });
                postsAdapter.notifyDataSetChanged();
            }
        });
        return root;
    }


}