package com.study.news.retrofit;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * 避免每次请求网络设置订阅切换线程时代码重复
 */
public class TransformUtils {
    /**
     * 让订阅发生在io，让订阅的回调（eg:onNext())发生在主线程
     * @param <T>
     * @return
     */
    public static <T> Observable.Transformer<T, T> defaultSchedulers() {
        return new Observable.Transformer<T, T>() {
            @Override
            public Observable<T> call(Observable<T> tObservable) {
                return tObservable.observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.io());
            }
        };
    }

    /**
     * 让订阅和订阅的回调都发生在io
     * @param <T>
     * @return
     */
    public static <T> Observable.Transformer<T, T> all_io() {
        return new Observable.Transformer<T, T>() {
            @Override
            public Observable<T> call(Observable<T> tObservable) {
                return tObservable.observeOn(Schedulers.io()).subscribeOn(Schedulers.io());
            }
        };
    }
}
