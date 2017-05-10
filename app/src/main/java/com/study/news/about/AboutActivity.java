package com.study.news.about;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Resources;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.transition.TransitionInflater;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.study.news.R;
import com.study.news.ui.ElasticDragDismissFrameLayout;
import com.study.news.ui.InkPageIndicator;

import java.security.InvalidParameterException;

public class AboutActivity extends AppCompatActivity {
    private ElasticDragDismissFrameLayout draggableFrame;
    private ViewPager pager;
    private InkPageIndicator pageIndicator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);

        draggableFrame = (ElasticDragDismissFrameLayout) findViewById(R.id.draggable_frame);
        pager = (ViewPager) findViewById(R.id.pager);
        pageIndicator = (InkPageIndicator) findViewById(R.id.indicator);

        pager.setAdapter(new AboutPagerAdapter(AboutActivity.this));
        pager.setPageMargin(8);
        pageIndicator.setViewPager(pager);

        draggableFrame.addListener(
                new ElasticDragDismissFrameLayout.SystemChromeFader(this) {
                    @Override
                    public void onDragDismissed() {
                        if (draggableFrame.getTranslationY() > 0) {
                            getWindow().setReturnTransition(
                                    TransitionInflater.from(AboutActivity.this)
                                            .inflateTransition(R.transition.about_return_downward));
                        }
                        finishAfterTransition();
                    }
                });
    }

    private static class AboutPagerAdapter extends PagerAdapter {
        private final LayoutInflater layoutInflater;
        private final Activity host;
        private final Resources resources;
        private TextView github, gmail;
        private ScrollView scrollView;

        private View aboutNews;
        private View libs;

        AboutPagerAdapter(@NonNull Activity host) {
            this.host = host;
            resources = host.getResources();
            layoutInflater = LayoutInflater.from(host);
        }

        @Override
        public Object instantiateItem(ViewGroup collection, int position) {
            View layout = getPage(position, collection);
            collection.addView(layout);
            return layout;
        }

        @Override
        public void destroyItem(ViewGroup collection, int position, Object view) {
            collection.removeView((View) view);
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public int getCount() {
            return 2;
        }

        private View getPage(int position, ViewGroup parent) {
            switch (position) {
                case 0:
                    if (aboutNews == null) {
                        aboutNews = layoutInflater.inflate(R.layout.about_news, parent, false);
                        github = (TextView) aboutNews.findViewById(R.id.github);
                        gmail = (TextView) aboutNews.findViewById(R.id.gmail);
                        scrollView = (ScrollView) aboutNews.findViewById(R.id.scrollView);
                    }

                    gmail.setOnClickListener(v -> Snackbar.make(scrollView, "weining412960979@gmail.com", Snackbar.LENGTH_LONG).show());
                    github.setOnClickListener(v -> {
                        Intent it = new Intent(Intent.ACTION_VIEW);
                        it.setData(Uri.parse("https://github.com/412960979/News.git"));
                        it = Intent.createChooser(it, null);
                        host.startActivity(it);
                    });
                    return aboutNews;
                case 1:
                    if (libs == null) {
                        libs = layoutInflater.inflate(R.layout.about_libs, parent, false);
                    }
                    return libs;
            }
            throw new InvalidParameterException();
        }
    }
}
