package com.study.news.homepage;

import android.content.Context;
import android.content.res.Resources;
import android.util.Log;
import com.study.news.R;
import com.study.news.db.BaseSQLiteOpenHelper;
import com.study.news.model.NewsDataBean;
import com.study.news.retrofit.HttpQuest;
import com.study.news.retrofit.HttpResultSubscriber;
import java.util.ArrayList;
import java.util.List;
import rx.Observable;
import rx.Subscription;
import static com.google.gson.internal.$Gson$Preconditions.checkNotNull;

public class NewsListPresenter implements NewsListContract.Presenter {
    private final NewsListContract.View mHomeView;
    private Subscription subScription;
    private List<String> mChannel = new ArrayList<>();
    private List<NewsDataBean.ContentListBean> mFeed = new ArrayList<>();
    private int start = 0;
    private final static int num = 1;

    public NewsListPresenter(Context context) {
        mHomeView = checkNotNull((HomeActivity) context);
        mHomeView.setPresenter(this);
    }

    @Override
    public void start() {
        getChannelData();
        getHomePageNewsListData();
    }

    @Override
    public void getChannelData() {
        mChannel.clear();
        Resources res = ((HomeActivity) mHomeView).getResources();
        subScription = Observable.
                from(res.getStringArray(R.array.channels)).
                subscribe(s -> {
                    mChannel.add(s);
                    mHomeView.upDateChaneelRecyecleView(mChannel);
                });
    }

    @Override
    public void getHomePageNewsListData() {
        mFeed.clear();
        start = 0;
        subScription = HttpQuest.getOneListData(new HttpResultSubscriber<NewsDataBean>() {
            @Override
            public void onSuccess(NewsDataBean data) {
                mHomeView.hideSwipeRefreshLayout();
                mFeed.addAll(data.getContent_list());
                mHomeView.upDateFeedRecyleView(mFeed);
            }

            @Override
            public void onFailure(Throwable e) {
                Log.i("test", "出错了=" + e.getMessage());
            }
        }, BaseSQLiteOpenHelper.newInstance((HomeActivity) mHomeView).queryIdLimit("id_list", "id", start, num));
    }

    @Override
    public void getHomePageNewsListDataMore() {
        start += 1;
        subScription = HttpQuest.getOneListData(new HttpResultSubscriber<NewsDataBean>() {
            @Override
            public void onSuccess(NewsDataBean data) {
                mHomeView.hideSwipeRefreshLayout();
                mFeed.addAll(data.getContent_list());
                mHomeView.upDateFeedRecyleView(mFeed);
            }

            @Override
            public void onFailure(Throwable e) {
                Log.i("test", "出错了=" + e.getMessage());
            }
        }, BaseSQLiteOpenHelper.newInstance((HomeActivity) mHomeView).queryIdLimit("id_list", "id", start, num));
    }

    @Override
    public void closeSubScription() {
        if (!subScription.isUnsubscribed())
            subScription.unsubscribe();
    }
}
