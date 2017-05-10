package com.study.news.VideoDetail;

import android.content.Context;
import android.util.Log;

import com.study.news.MusicDetail.MusicDetailActivity;
import com.study.news.model.MusicDataBean;
import com.study.news.model.NewsDataBean;
import com.study.news.model.VideoDataBean;
import com.study.news.retrofit.HttpQuest;
import com.study.news.retrofit.HttpResultSubscriber;
import com.study.news.ui.ShareNewsTask;

import rx.Subscription;

import static com.google.gson.internal.$Gson$Preconditions.checkNotNull;

public class VideoDetailPresenter implements VideoDetailContract.Presenter {
    private final VideoDetailContract.View mVideoDetail;
    private Subscription subScription;
    private String mItemId;

    public VideoDetailPresenter(Context context, String itemId) {
        mVideoDetail = checkNotNull((VideoDetailActivity) context);
        mVideoDetail.setPresenter(this);
        this.mItemId = itemId;
    }

    @Override
    public void start() {
        getData();
    }

    @Override
    public void getData() {
        subScription = HttpQuest.getVideoData(new HttpResultSubscriber<VideoDataBean>() {
            @Override
            public void onSuccess(VideoDataBean data) {
                mVideoDetail.updateVideoView(data);
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
        new ShareNewsTask((VideoDetailActivity)mVideoDetail, data).execute();
    }
}
