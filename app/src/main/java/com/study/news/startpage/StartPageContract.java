package com.study.news.startpage;

import com.study.news.base.BasePresenter;
import com.study.news.base.BaseView;
import com.study.news.model.NewsDataBean;

import java.util.List;

public interface StartPageContract {
    interface View extends BaseView<Presenter> {
    }

    interface Presenter extends BasePresenter {
        void getIdListData();
        void closeSubScription();
    }
}
