package com.study.news.EssayDetail;

import android.content.Context;
import android.util.Log;

import com.study.news.db.BaseSQLiteOpenHelper;
import com.study.news.homepage.HomeActivity;
import com.study.news.model.EssayDataBean;
import com.study.news.model.NewsDataBean;
import com.study.news.retrofit.HttpQuest;
import com.study.news.retrofit.HttpResultSubscriber;
import com.study.news.ui.ShareNewsTask;

import rx.Subscription;

import static com.google.gson.internal.$Gson$Preconditions.checkNotNull;

public class EssayDetailPresenter implements EssayDetailContract.Presenter {
    private final EssayDetailContract.View mEssayDetail;
    private Subscription subScription;
    private String mItemId;

    public EssayDetailPresenter(Context context, String itemId) {
        mEssayDetail = checkNotNull((EssayActivity) context);
        mEssayDetail.setPresenter(this);
        this.mItemId = itemId;
    }

    @Override
    public void start() {
        getData();
    }

    @Override
    public void getData() {
        subScription = HttpQuest.getEssayData(new HttpResultSubscriber<EssayDataBean>() {
            @Override
            public void onSuccess(EssayDataBean data) {
                mEssayDetail.updateEssayView(data);
            }

            @Override
            public void onFailure(Throwable e) {
                Log.i("test", "出错了=" + e.getMessage());
            }
        }, mItemId);
    }

    @Override
    public void getShare(NewsDataBean.ContentListBean.ShareInfoBean data) {
        new ShareNewsTask((EssayActivity)mEssayDetail, data).execute();
    }

    @Override
    public void closeSubScription() {
        if (!subScription.isUnsubscribed())
            subScription.unsubscribe();
    }
}
