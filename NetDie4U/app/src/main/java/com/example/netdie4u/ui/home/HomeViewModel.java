package com.example.netdie4u.ui.home;

import android.app.Application;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import com.example.netdie4u.data.Feed;
import com.example.netdie4u.repository.SnakeRepository;

import java.util.List;

public class HomeViewModel extends AndroidViewModel {
    private final MutableLiveData<String> mText;
    private final SnakeRepository repository;
    private final LiveData<List<Feed>> feeds;

    public HomeViewModel(Application application) {
        super(application);
        mText = new MutableLiveData<>();
        mText.setValue("Eva, psyyche & The rock cat+");
        repository = SnakeRepository.getInstance(application);
        feeds = repository.getAllFeeds();
    }

    public LiveData<String> getText() {
        return mText;
    }

    public LiveData<List<Feed>> getFeeds() {
        return feeds;
    }

    public void addFeed(Feed feed) {
        repository.insert(feed);
    }
    public void deleteFeed(Feed feed) {
        repository.delete(feed);
    }

}

