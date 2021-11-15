package com.app.legitngclone.ui.bookmarks;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.app.legitngclone.ui.main.PostRepository;
import com.app.legitngclone.ui.main.SavedPost;

import java.util.List;

public class BookmarksViewModel extends AndroidViewModel {

    private PostRepository repository;
    private LiveData<List<SavedPost>> allPosts;

    public BookmarksViewModel(@NonNull Application application) {
        super(application);
        repository = new PostRepository(application);
        allPosts = repository.getAllPosts();
    }

    public void insert(String title, String description, String content, String image, String author, String date, String url){
        SavedPost post
                = new SavedPost(title,image, description, content, date, author, url);
        repository.insert(post);
    }

    public void delete(String title){
        repository.delete(title);
    }

    public LiveData<List<SavedPost>> getAllPosts(){
        return allPosts;
    }

}