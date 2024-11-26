package com.example.netdie4u.ui.gallery;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.netdie4u.basic.XFragment;
import com.example.netdie4u.data.Feed;
import com.example.netdie4u.data.Ghost_List;
import com.example.netdie4u.data.Item_;
import com.example.netdie4u.data.data_itself;
import com.example.netdie4u.repository.SnakeRepository;
import com.example.netdie4u.service.network.SheWithNet;
import com.example.netdie4u.service.network.TheNet;
import com.example.netdie4u.service.network.interfaces.FetchCallBack;
import com.example.netdie4u.service.kits.Sugar_kit;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;
public class GalleryViewModel extends AndroidViewModel {
    private final MutableLiveData<Boolean> isLoading = new MutableLiveData<>(false);
    private final MutableLiveData<Ghost_List> previewData = new MutableLiveData<>();
    private final MutableLiveData<String> error = new MutableLiveData<>();
    private SheWithNet sheWithNet;
    private final MutableLiveData<Boolean> navigateToHome = new MutableLiveData<>(false);
    private final SnakeRepository repository;

    public GalleryViewModel(Application application) {
        super(application);
        repository = SnakeRepository.getInstance(application);
        try {
            XmlPullParser parser = XmlPullParserFactory.newInstance().newPullParser();
            sheWithNet = new SheWithNet(parser);
            sheWithNet.setTheNet(new TheNet(parser));
        } catch (Exception e) {
            error.setValue("初始化解析器失败: " + e.getMessage());
        }
    }

    public void parseRssUrl(String url) {
        isLoading.setValue(true);
        sheWithNet.fetchData(url, new FetchCallBack<Ghost_List>() {
            @Override
            public void onSuccess(Ghost_List result) {
                isLoading.postValue(false);
                previewData.postValue(result);
            }

            @Override
            public void onFailure(Exception e) {
                isLoading.postValue(false);
                error.postValue("解析失败: " + e.getMessage());
            }
        });
    }

    public void saveFeed(String url, Ghost_List ghostList) {
        try {
            Feed feed = new Feed();
            feed.titleShow = ghostList.getTitle_show();
            feed.titleOfArticle = ghostList.getFirstItem().getTitle();
            feed.author = ghostList.getFirstItem().getAuthor();
            feed.link = ghostList.getFirstItem().getLink();
            feed.pubDate = ghostList.getFirstItem().getPubDate();
            feed.description = ghostList.getFirstItem().getDescription();

            repository.insert(feed);
            navigateToHome.setValue(true);
        } catch (Exception e) {
            error.postValue("保存失败: " + e.getMessage());
        }
    }

    public LiveData<Boolean> getIsLoading() {
        return isLoading;
    }

    public LiveData<Ghost_List> getPreviewData() {
        return previewData;
    }

    public LiveData<String> getError() {
        return error;
    }

    public LiveData<Boolean> getNavigateToHome() {
        return navigateToHome;
    }

    public void navigationComplete() {
        navigateToHome.setValue(false);
    }
}
