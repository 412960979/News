package com.study.news.glide;

import android.content.Context;

import com.bumptech.glide.Glide;
import com.bumptech.glide.GlideBuilder;
import com.bumptech.glide.load.DecodeFormat;
import com.bumptech.glide.load.engine.cache.DiskLruCacheWrapper;
import com.bumptech.glide.load.engine.cache.LruResourceCache;
import com.bumptech.glide.module.GlideModule;

import java.io.File;

/**
 * 配置图片的缓存路径，缓存大小等。
 */
public class GlideConfiguration implements GlideModule {
    public static final int MAX_MEMORY_CACHE_SIZE = 10 * 1024 * 1024;
    private static final int DISK_CACHE_SIZE = 100 * 1024 * 1024;
    private static final String  PATH = "sdcard/news/img/";

    @Override
    public void applyOptions(Context context, GlideBuilder builder) {
        // 保存图片的格式由RGB_575改成ARGB_8888
        builder.setDecodeFormat(DecodeFormat.PREFER_ARGB_8888);

        //设置磁盘缓存的路径 path
        final File cacheDir = new File(PATH);
        builder.setDiskCache(() -> DiskLruCacheWrapper.get(cacheDir, DISK_CACHE_SIZE));
        //设置内存缓存大小，一般默认使用glide内部的默认值
        builder.setMemoryCache(new LruResourceCache(MAX_MEMORY_CACHE_SIZE));
    }

    @Override
    public void registerComponents(Context context, Glide glide) {

    }
}
