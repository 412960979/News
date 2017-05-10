package com.study.news.MusicDetail;

import com.study.news.base.BasePresenter;
import com.study.news.base.BaseView;
import com.study.news.model.EssayDataBean;
import com.study.news.model.MusicDataBean;
import com.study.news.model.NewsDataBean;

public class MusicDetailContract {
    interface View extends BaseView<MusicDetailContract.Presenter> {
        void updateMusicView(MusicDataBean data);
    }

    interface Presenter extends BasePresenter {
        void getData();
        void closeSubScription();
        void getShare(NewsDataBean.ContentListBean.ShareInfoBean data);
    }
}
