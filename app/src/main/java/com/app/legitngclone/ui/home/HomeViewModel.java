package com.app.legitngclone.ui.home;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.app.legitngclone.pojo.PostModel;

import java.util.List;

public class HomeViewModel extends ViewModel {

    MutableLiveData<List<PostModel>> postMutableLiveData = new MutableLiveData<>();

    public void getPosts(){}
}