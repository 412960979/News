package com.study.news.EssayDetail;

import com.study.news.base.BasePresenter;
import com.study.news.base.BaseView;
import com.study.news.model.EssayDataBean;
import com.study.news.model.NewsDataBean;

public class EssayDetailContract {
    interface View extends BaseView<EssayDetailContract.Presenter> {
        void updateEssayView(EssayDataBean data);
    }

    interface Presenter extends BasePresenter {
        void closeSubScription();
        void getData();
        void getShare(NewsDataBean.ContentListBean.ShareInfoBean data);
    }
}
