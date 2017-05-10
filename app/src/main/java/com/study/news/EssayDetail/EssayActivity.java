package com.study.news.EssayDetail;

import android.animation.ValueAnimator;
import android.graphics.Bitmap;
import android.graphics.drawable.AnimatedVectorDrawable;
import android.os.Build;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.graphics.Palette;
import android.util.TypedValue;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.study.news.R;
import com.study.news.model.EssayDataBean;
import com.study.news.model.NewsDataBean;
import com.study.news.ui.ParallaxScrimageView;
import com.study.news.ui.SlideScrollView;
import com.study.news.utils.ColorUtils;
import com.study.news.utils.GlideUtils;
import com.study.news.utils.ViewUtils;
import static com.google.gson.internal.$Gson$Preconditions.checkNotNull;
import static com.study.news.utils.AnimUtils.getFastOutSlowInInterpolator;

public class EssayActivity extends AppCompatActivity implements EssayDetailContract.View {
    private ParallaxScrimageView headView;
    private ImageButton back;
    private SlideScrollView scrollView;
    private EssayDetailContract.Presenter mPresenter;
    private TextView mContent, mTime;
    private WebView mWebView;
    private Button mEssay_like_count, mEssay_comment_count, mEssay_share_count;
    private NewsDataBean.ContentListBean.ShareInfoBean shareInfoBean;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_essay);

        headView = (ParallaxScrimageView) findViewById(R.id.pic);
        back = (ImageButton) findViewById(R.id.back);
        scrollView = (SlideScrollView) findViewById(R.id.scrollView);
        mContent = (TextView) findViewById(R.id.content);
        mWebView = (WebView) findViewById(R.id.webView);
        mTime = (TextView) findViewById(R.id.time);
        mEssay_like_count = (Button) findViewById(R.id.essay_like_count);
        mEssay_comment_count = (Button) findViewById(R.id.essay_comment_count);
        mEssay_share_count = (Button) findViewById(R.id.essay_share_count);

        //启用支持javascript
        WebSettings settings = mWebView.getSettings();
        settings.setJavaScriptEnabled(true);
//        settings.setTextZoom(200);
        settings.setUseWideViewPort(true);
        settings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NARROW_COLUMNS);
        settings.setLoadWithOverviewMode(true);

        Bundle bundle = getIntent().getExtras();
        String image = bundle.getString("img");
        String itemId = bundle.getString("item_id");
        shareInfoBean = (NewsDataBean.ContentListBean.ShareInfoBean) bundle.get("share");

        Glide
                .with(this)
                .load(image)
                .listener(headViewListener)
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .override(600, 400)
                .into(headView);

        back.setOnClickListener(v -> finishAfterTransition());

        new EssayDetailPresenter(this, itemId);

        mEssay_share_count.setOnClickListener(v -> {
            ((AnimatedVectorDrawable) mEssay_share_count.getCompoundDrawables()[1]).start();
            mPresenter.getShare(shareInfoBean);
        });
    }

    private RequestListener headViewListener = new RequestListener<String, GlideDrawable>() {
        @Override
        public boolean onResourceReady(GlideDrawable resource, String model,
                                       Target<GlideDrawable> target, boolean isFromMemoryCache,
                                       boolean isFirstResource) {
            final Bitmap bitmap = GlideUtils.getBitmap(resource);
            final int twentyFourDip = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                    24, EssayActivity.this.getResources().getDisplayMetrics());
            Palette.from(bitmap)
                    .maximumColorCount(3)
                    .clearFilters()
                    .setRegion(0, 0, bitmap.getWidth() - 1, twentyFourDip)
                    .generate(palette -> {
                        boolean isDark;
                        @ColorUtils.Lightness int lightness = ColorUtils.isDark(palette);
                        if (lightness == ColorUtils.LIGHTNESS_UNKNOWN) {
                            isDark = ColorUtils.isDark(bitmap, bitmap.getWidth() / 2, 0);
                        } else {
                            isDark = lightness == ColorUtils.IS_DARK;
                        }

                        // 如果图片是搞亮的，就显示一个暗一点的返回按钮的颜色
                        if (!isDark) {
                            back.setColorFilter(ContextCompat.getColor(
                                    EssayActivity.this, R.color.dark_icon));
                        }

                        // 设置状态栏的颜色跟图片的颜色一致
                        int statusBarColor = getWindow().getStatusBarColor();
                        final Palette.Swatch topColor =
                                ColorUtils.getMostPopulousSwatch(palette);
                        if (topColor != null &&
                                (isDark || Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)) {
                            statusBarColor = ColorUtils.scrimify(topColor.getRgb(),
                                    isDark, 0.075f);
                            // 如果sdk大于等于M就设置高亮状态栏
                            if (!isDark && Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                                ViewUtils.setLightStatusBar(headView);
                            }
                        }

                        if (statusBarColor != getWindow().getStatusBarColor()) {
                            headView.setScrimColor(statusBarColor);
                            ValueAnimator statusBarColorAnim = ValueAnimator.ofArgb(
                                    getWindow().getStatusBarColor(), statusBarColor);
                            statusBarColorAnim.addUpdateListener(animation -> getWindow().setStatusBarColor(
                                    (int) animation.getAnimatedValue()));
                            statusBarColorAnim.setDuration(1000L);
                            statusBarColorAnim.setInterpolator(
                                    getFastOutSlowInInterpolator(EssayActivity.this));
                            statusBarColorAnim.start();
                        }
                    });
            return false;
        }


        @Override
        public boolean onException(Exception e, String model, Target<GlideDrawable> target,
                                   boolean isFirstResource) {
            return false;
        }
    };

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finishAfterTransition();
    }

    @Override
    public void setPresenter(EssayDetailContract.Presenter presenter) {
        mPresenter = checkNotNull(presenter);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mPresenter.start();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPresenter.closeSubScription();
    }

    @Override
    public void updateEssayView(EssayDataBean data) {
        mContent.setText(data.getHp_title());
        mTime.setText(data.getLast_update_date());
        mEssay_like_count.setText(data.getPraisenum() + " LIKES");
        mEssay_comment_count.setText(data.getCommentnum() + " COMMENTS");
        mEssay_share_count.setText(data.getSharenum() + " SHARE");
        String htmlData = "<html> \n" +
                "<head> \n" +
                "<style type=\"text/css\"> \n" +
                "body {text-align:justify; font-size: " + 39 + "px; line-height: " + (65) + "px; letter-spacing: " + (8) + "px; padding: " + 20 + " " + 10 + " " + 20 + " " + 20 +
                ";background-color: " + "#40808080" + "}\n" +
                "</style> \n" +
                "</head> \n" +
                "<body>" + data.getHp_content() + "</body> \n";
        mWebView.loadDataWithBaseURL(null, htmlData, "text/html", "utf-8", null);
    }
}