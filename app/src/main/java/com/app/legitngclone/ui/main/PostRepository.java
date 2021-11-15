package com.app.legitngclone.ui.main;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import java.util.List;

public class PostRepository {
    private PostDao postDao;
    private LiveData<List<SavedPost>> allPosts;

    public PostRepository(Application application){
        PostsDatabase database = PostsDatabase.getInstance(application);
        postDao = database.postDao();
        allPosts = postDao.getAllPosts();
    }

    public void insert(SavedPost post){
        new InsertPostAsyncTask(postDao).execute(post);
    }

    public void delete(String title){
        new DeletePostAsyncTask(postDao).execute(title);
    }

    public LiveData<List<SavedPost>> getAllPosts(){
        return allPosts;
    }

    private static class InsertPostAsyncTask extends AsyncTask<SavedPost, Void, Void>{
        private  PostDao postDao;

        private InsertPostAsyncTask(PostDao postDao){
            this.postDao = postDao;
        }

        @Override
        protected Void doInBackground(SavedPost... savedPosts) {
            postDao.insert(savedPosts[0]);
            return null;
        }
    }

    private static class DeletePostAsyncTask extends AsyncTask<String, Void, Void>{
        private  PostDao postDao;

        private DeletePostAsyncTask(PostDao postDao){
            this.postDao = postDao;
        }

        @Override
        protected Void doInBackground(String... strings) {
            postDao.delete(strings[0]);
            return null;
        }
    }

}
