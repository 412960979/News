package com.study.news.ui;

import android.app.Activity;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v4.app.ShareCompat;

import com.bumptech.glide.Glide;
import com.study.news.model.NewsDataBean;

import java.io.File;

/**
 * 调用系统分享
 */
public class ShareNewsTask extends AsyncTask<Void, Void, File> {
    private final Activity activity;
    private final NewsDataBean.ContentListBean.ShareInfoBean data;

    public ShareNewsTask(Activity activity, NewsDataBean.ContentListBean.ShareInfoBean data) {
        this.activity = activity;
        this.data = data;
    }

    @Override
    protected File doInBackground(Void... params) {
        try {
            return Glide
                    .with(activity)
                    .load(data.getImage())
                    .downloadOnly(600, 400)
                    .get();
        } catch (Exception ex) {
            return null;
        }
    }

    @Override
    protected void onPostExecute(File result) {
        if (result == null) {
            return;
        }
        // TODO 之后这里要添加分享图片
        ShareCompat.IntentBuilder.from(activity).
                setText(data.getContent() + "\n" + data.getUrl()).
                setType("text/plain").
                setSubject(data.getTitle()).
                startChooser();
    }
}
