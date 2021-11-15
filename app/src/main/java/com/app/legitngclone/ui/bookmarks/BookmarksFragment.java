package com.app.legitngclone.ui.bookmarks;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.app.legitngclone.R;
import com.app.legitngclone.pojo.PostModel;
import com.app.legitngclone.ui.home.onef.OneFragmentViewModel;
import com.app.legitngclone.ui.main.PostsAdapter;
import com.app.legitngclone.ui.main.SavedPost;
import com.app.legitngclone.ui.main.SavedPostsAdapter;

import java.util.List;

public class BookmarksFragment extends Fragment {

    private BookmarksViewModel bookmarksViewModel;
    RecyclerView recyclerView;
    SavedPostsAdapter savedPostsAdapter;
    LinearLayoutManager linearLayoutManager;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        bookmarksViewModel =
                ViewModelProviders.of(this).get(BookmarksViewModel.class);
        View root = inflater.inflate(R.layout.fragment_bookmarks, container, false);
        ActionBar actionBar = ((AppCompatActivity) getActivity()).getSupportActionBar();
        actionBar.setDisplayUseLogoEnabled(false);
        actionBar.setTitle("Bookmarks");
        recyclerView = root.findViewById(R.id.recycler);
        savedPostsAdapter = new SavedPostsAdapter();
        linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setReverseLayout(true);
        linearLayoutManager.setStackFromEnd(true);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(savedPostsAdapter);
        bookmarksViewModel.getAllPosts().observe((LifecycleOwner) getContext(), new Observer<List<SavedPost>>() {
            @Override
            public void onChanged(List<SavedPost> savedPosts) {
                savedPostsAdapter.setPostsList(savedPosts);
                savedPostsAdapter.setmContext(getContext());
            }
        });
        savedPostsAdapter.notifyDataSetChanged();

        return root;
    }
}