package com.study.news.retrofit;

import retrofit2.adapter.rxjava.HttpException;
import rx.Subscriber;

public abstract class HttpResultSubscriber<T> extends Subscriber<HttpResult<T>> {
    @Override
    public void onError(Throwable e) {
        e.printStackTrace();
        //在这里做全局的错误处理
        if (e instanceof HttpException) {
            // ToastUtils.getInstance().showToast(e.getMessage());
        }
        onFailure(e);
    }

    @Override
    public void onNext(HttpResult<T> t) {
        if (t.res == 0)
            onSuccess(t.data);
        else
            onFailure(new Throwable());
    }

    @Override
    public void onCompleted() {
    }

    public abstract void onSuccess(T data);

    public abstract void onFailure(Throwable e);
}
