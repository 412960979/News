package com.study.news.homepage;

import com.study.news.base.BasePresenter;
import com.study.news.base.BaseView;
import com.study.news.model.NewsDataBean;

import java.util.List;

public interface NewsListContract {
    interface View extends BaseView<Presenter> {
        void upDateChaneelRecyecleView(List<String> data);
        void upDateFeedRecyleView(List<NewsDataBean.ContentListBean> data);
        void hideSwipeRefreshLayout();
    }

    interface Presenter extends BasePresenter {
        void getChannelData();
        void getHomePageNewsListData();
        void getHomePageNewsListDataMore();
        void closeSubScription();
    }
}
