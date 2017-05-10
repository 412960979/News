package com.study.news.MusicDetail;

import android.content.Context;
import android.util.Log;
import com.study.news.model.MusicDataBean;
import com.study.news.model.NewsDataBean;
import com.study.news.retrofit.HttpQuest;
import com.study.news.retrofit.HttpResultSubscriber;
import com.study.news.ui.ShareNewsTask;
import rx.Subscription;
import static com.google.gson.internal.$Gson$Preconditions.checkNotNull;

public class MusicDetailPresenter implements MusicDetailContract.Presenter {
    private final MusicDetailContract.View mMusicDetail;
    private Subscription subScription;
    private String mItemId;

    public MusicDetailPresenter(Context context, String itemId) {
        mMusicDetail = checkNotNull((MusicDetailActivity) context);
        mMusicDetail.setPresenter(this);
        this.mItemId = itemId;
    }

    @Override
    public void start() {
        getData();
    }

    @Override
    public void getData() {
        subScription = HttpQuest.getMusicData(new HttpResultSubscriber<MusicDataBean>() {
            @Override
            public void onSuccess(MusicDataBean data) {
                mMusicDetail.updateMusicView(data);
            }

            @Override
            public void onFailure(Throwable e) {
                Log.i("test", "出错了=" + e.getMessage());
            }
        }, mItemId);
    }


    @Override
    public void closeSubScription() {
        if (!subScription.isUnsubscribed())
            subScription.unsubscribe();
    }

    @Override
    public void getShare(NewsDataBean.ContentListBean.ShareInfoBean data) {
        new ShareNewsTask((MusicDetailActivity)mMusicDetail, data).execute();
    }
}
