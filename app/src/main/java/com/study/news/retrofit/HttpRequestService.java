package com.study.news.retrofit;

import com.study.news.model.EssayDataBean;
import com.study.news.model.MusicDataBean;
import com.study.news.model.NewsDataBean;
import com.study.news.model.VideoDataBean;

import java.util.HashMap;
import java.util.List;

import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.QueryMap;
import rx.Observable;

public interface HttpRequestService {
    // base url
    String BASE_URL = "http://v3.wufazhuce.com:8000/api/";

    @GET("onelist/idlist/")
    Observable<HttpResult<List<String>>> getIdListData(@QueryMap HashMap<String, String > map);

    @GET("onelist/{id}/0")
    Observable<HttpResult<NewsDataBean>> getOneListData(@Path("id") String id, @QueryMap HashMap<String, String > map);

    @GET("essay/{id}")
    Observable<HttpResult<EssayDataBean>> getEssayData(@Path("id") String id, @QueryMap HashMap<String, String > map);

    @GET("music/detail/{id}")
    Observable<HttpResult<MusicDataBean>> getMusicData(@Path("id") String id, @QueryMap HashMap<String, String > map);

    @GET("movie/{id}/story/1/0")
    Observable<HttpResult<VideoDataBean>> getVideoData(@Path("id") String id, @QueryMap HashMap<String, String > map);
}
