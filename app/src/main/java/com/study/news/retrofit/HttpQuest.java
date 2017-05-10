package com.study.news.retrofit;

import com.study.news.model.EssayDataBean;
import com.study.news.model.MusicDataBean;
import com.study.news.model.NewsDataBean;
import com.study.news.model.VideoDataBean;
import com.study.news.utils.DeviceInfoYtils;

import java.util.HashMap;
import java.util.List;

import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * 每个网络请求需要设置的参数方法类.
 */
public class HttpQuest {
    /**
     * 订阅的公共属性设置
     *
     * @param o
     * @param s
     * @return
     */
    private static Subscription toSubscribe(Observable o, Subscriber s) {
        return o.subscribeOn(Schedulers.io())
                .retryWhen(new RetryWhenNetworkException())
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(s);
    }

    /**
     * 请求首页信息
     *
     * @param subscriber
     * @return
     */
    public static Subscription getIdListData(Subscriber<HttpResult<List<String>>> subscriber) {
        HashMap<String, String> map = new HashMap<>();
        map.put("channel", "wdj");
        map.put("version", "4.0.2");
        map.put("uuid", DeviceInfoYtils.getUniquePsuedoID());
        map.put("platform", "android");
        Observable observable = ServiceFactory.getInstance()
                .createService(HttpRequestService.class)
                .getIdListData(map);
        return toSubscribe(observable, subscriber);
    }

    /**
     * 请求首页信息
     *
     * @param subscriber
     * @return
     */
    public static Subscription getOneListData(Subscriber<HttpResult<NewsDataBean>> subscriber, String id) {
        HashMap<String, String> map = new HashMap<>();
        map.put("channel", "wdj");
        map.put("version", "4.0.2");
        map.put("uuid", DeviceInfoYtils.getUniquePsuedoID());
        map.put("platform", "android");
        Observable observable = ServiceFactory.getInstance()
                .createService(HttpRequestService.class)
                .getOneListData(id, map);
        return toSubscribe(observable, subscriber);
    }

    /**
     * 请求文章详情
     * @param subscriber
     * @param id
     * @return
     */
    public static Subscription getEssayData(Subscriber<HttpResult<EssayDataBean>> subscriber, String id) {
        HashMap<String, String> map = new HashMap<>();
        map.put("channel", "wdj");
        map.put("source", "summary");
        map.put("source_id", "9261");
        map.put("version", "4.0.2");
        map.put("uuid", DeviceInfoYtils.getUniquePsuedoID());
        map.put("platform", "android");
        Observable observable = ServiceFactory.getInstance()
                .createService(HttpRequestService.class)
                .getEssayData(id, map);
        return toSubscribe(observable, subscriber);
    }

    /**
     * 请求音乐详情
     * @param subscriber
     * @param id
     * @return
     */
    public static Subscription getMusicData(Subscriber<HttpResult<MusicDataBean>> subscriber, String id) {
        HashMap<String, String> map = new HashMap<>();
        map.put("channel", "wdj");
        map.put("version", "4.0.2");
        map.put("uuid", DeviceInfoYtils.getUniquePsuedoID());
        map.put("platform", "android");
        Observable observable = ServiceFactory.getInstance()
                .createService(HttpRequestService.class)
                .getMusicData(id, map);
        return toSubscribe(observable, subscriber);
    }

    /**
     * 请求影视详情
     * @param subscriber
     * @param id
     * @return
     */
    public static Subscription getVideoData(Subscriber<HttpResult<VideoDataBean>> subscriber, String id) {
        HashMap<String, String> map = new HashMap<>();
        map.put("channel", "wdj");
        map.put("version", "4.0.2");
        map.put("uuid", DeviceInfoYtils.getUniquePsuedoID());
        map.put("platform", "android");
        Observable observable = ServiceFactory.getInstance()
                .createService(HttpRequestService.class)
                .getVideoData(id, map);
        return toSubscribe(observable, subscriber);
    }
}
