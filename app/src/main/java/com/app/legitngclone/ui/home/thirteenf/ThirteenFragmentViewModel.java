package com.app.legitngclone.ui.home.thirteenf;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.app.legitngclone.data.PostsClient;
import com.app.legitngclone.pojo.Articles;
import com.app.legitngclone.pojo.PostModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ThirteenFragmentViewModel extends ViewModel {
    public MutableLiveData<List<PostModel>> postMutableLiveData = new MutableLiveData<>();
    public MutableLiveData <String> posts = new MutableLiveData<>();
    public void getPosts(int page, int limit){

        PostsClient.getINSTANCE().getGlobalPosts(page, limit).enqueue(new Callback<Articles>() {
            @Override
            public void onResponse(Call<Articles> call, Response<Articles> response) {
                postMutableLiveData.setValue(response.body().getArticles());
            }

            @Override
            public void onFailure(Call<Articles> call, Throwable t) {

            }

        });
    }
}
