package com.app.legitngclone.ui.home.onef;

import android.app.Application;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.app.legitngclone.data.PostsClient;
import com.app.legitngclone.pojo.Articles;
import com.app.legitngclone.pojo.PostModel;
import com.app.legitngclone.ui.main.MainActivity;
import com.app.legitngclone.ui.main.PostRepository;
import com.app.legitngclone.ui.main.SavedPost;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Cache;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.facebook.FacebookSdk.getCacheDir;

public class OneFragmentViewModel extends AndroidViewModel {
   public MutableLiveData<List<PostModel>> postMutableLiveData = new MutableLiveData<>();
   public MutableLiveData <String> posts = new MutableLiveData<>();
   public List<List<PostModel>> oldData = new ArrayList<>();
    private PostRepository repository;
    private LiveData<List<SavedPost>> allSavedPosts;

    public OneFragmentViewModel(@NonNull Application application) {
        super(application);
        repository = new PostRepository(application);
        allSavedPosts = repository.getAllPosts();
    }

    public void getPosts(final int page, int limit){

        PostsClient.getINSTANCE().getGeneralPosts(page,limit).enqueue(new Callback<Articles>() {
            @Override
            public void onResponse(Call<Articles> call, Response<Articles> response) {
                if(response.raw().cacheResponse() != null){
                    Log.i("Network","response from cache");
                }
                if(response.raw().networkResponse() != null){
                    Log.i("Network","response from server");
                }
                if(response.body().getArticles() != null)
                     postMutableLiveData.setValue(response.body().getArticles());
            }

            @Override
            public void onFailure(Call<Articles> call, Throwable t) {

            }

        });
    }

}
