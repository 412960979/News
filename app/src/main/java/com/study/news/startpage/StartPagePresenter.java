package com.study.news.startpage;

import android.content.Context;
import android.util.Log;
import com.study.news.db.BaseSQLiteOpenHelper;
import com.study.news.retrofit.HttpQuest;
import com.study.news.retrofit.HttpResultSubscriber;
import java.util.List;
import rx.Observable;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

import static com.google.gson.internal.$Gson$Preconditions.checkNotNull;

public class StartPagePresenter implements StartPageContract.Presenter {
    private final StartPageContract.View mStartPageView;
    private Subscription subScription;

    public StartPagePresenter(Context context) {
        mStartPageView = checkNotNull((StartActivity) context);
        mStartPageView.setPresenter(this);
    }

    @Override
    public void start() {
        getIdListData();
    }

    @Override
    public void getIdListData() {
        subScription = HttpQuest.getIdListData(new HttpResultSubscriber<List<String>>() {
            @Override
            public void onSuccess(List<String> data) {
                // 把id插入数据库
                insertIdToDatabase(data);
            }

            @Override
            public void onFailure(Throwable e) {

            }
        });
    }

    @Override
    public void closeSubScription() {
        if (!subScription.isUnsubscribed())
            subScription.unsubscribe();
    }

    /**
     * 插入id到数据库里面
     * @param data
     */
    private void insertIdToDatabase(List<String> data) {
        try {
            subScription = Observable.
                    from(data).
                    map(s -> BaseSQLiteOpenHelper.newInstance((StartActivity) mStartPageView).insertId("id_list", "id", s)).
                    subscribeOn(Schedulers.io()).
                    observeOn(AndroidSchedulers.mainThread()).
                    subscribe(result -> Log.i("test", "插入结果： " + result));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
