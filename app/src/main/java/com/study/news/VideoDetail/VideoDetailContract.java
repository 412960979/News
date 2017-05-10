package com.study.news.VideoDetail;

import com.study.news.base.BasePresenter;
import com.study.news.base.BaseView;
import com.study.news.model.NewsDataBean;
import com.study.news.model.VideoDataBean;

public class VideoDetailContract {
    interface View extends BaseView<VideoDetailContract.Presenter> {
        void updateVideoView(VideoDataBean data);
    }

    interface Presenter extends BasePresenter {
        void getData();
        void closeSubScription();
        void getShare(NewsDataBean.ContentListBean.ShareInfoBean data);
    }
}
