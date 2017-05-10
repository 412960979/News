package com.study.news.imagenews;

import android.animation.ValueAnimator;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.AnimatedVectorDrawable;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.graphics.Palette;
import android.util.TypedValue;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.study.news.R;
import com.study.news.model.NewsDataBean;
import com.study.news.ui.ParallaxScrimageView;
import com.study.news.ui.ShareNewsTask;
import com.study.news.utils.ColorUtils;
import com.study.news.utils.GlideUtils;
import com.study.news.utils.ViewUtils;

import static com.study.news.utils.AnimUtils.getFastOutSlowInInterpolator;

public class ImageActivity extends AppCompatActivity {
    private ParallaxScrimageView pic;
    private TextView mTitle, mForward, mTime;
    private ImageButton back;
    private Button essay_share_count;
    private NewsDataBean.ContentListBean.ShareInfoBean shareInfoBean;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image);

        pic = (ParallaxScrimageView) findViewById(R.id.pic);
        mTitle = (TextView) findViewById(R.id.title);
        mForward = (TextView) findViewById(R.id.forward);
        back = (ImageButton) findViewById(R.id.back);
        mTime = (TextView) findViewById(R.id.time);
        essay_share_count = (Button) findViewById(R.id.essay_share_count);

        Bundle bundle = getIntent().getExtras();
        String image = bundle.getString("img");
        String titleImg = bundle.getString("title");
        String forwardImg = bundle.getString("forward");
        String time = bundle.getString("time");
        shareInfoBean = (NewsDataBean.ContentListBean.ShareInfoBean) bundle.get("share");

        mTitle.setText(titleImg);
        mForward.setText(forwardImg);
        mTime.setText(time);

        Glide
                .with(this)
                .load(image)
                .override(800, 600)
                .listener(headViewListener)
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .into(pic);

        back.setOnClickListener(v -> {
            finishAfterTransition();
        });

        essay_share_count.setOnClickListener(v -> {
            ((AnimatedVectorDrawable) essay_share_count.getCompoundDrawables()[1]).start();
            new ShareNewsTask(this, shareInfoBean).execute();
        });

    }

    @Override
    public void onBackPressed() {
        //取消activity动画
        finishAfterTransition();
    }

    private RequestListener headViewListener = new RequestListener<String, GlideDrawable>() {
        @Override
        public boolean onResourceReady(GlideDrawable resource, String model,
                                       Target<GlideDrawable> target, boolean isFromMemoryCache,
                                       boolean isFirstResource) {
            final Bitmap bitmap = GlideUtils.getBitmap(resource);
            final int twentyFourDip = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                    24, ImageActivity.this.getResources().getDisplayMetrics());
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

                        // 如果图片是搞亮的，就现实一个暗一点的返回按钮的颜色
                        if (!isDark) {
                            back.setColorFilter(ContextCompat.getColor(
                                    ImageActivity.this, R.color.dark_icon));
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
                                ViewUtils.setLightStatusBar(pic);
                            }
                        }

                        if (statusBarColor != getWindow().getStatusBarColor()) {
                            pic.setScrimColor(statusBarColor);
                            ValueAnimator statusBarColorAnim = ValueAnimator.ofArgb(
                                    getWindow().getStatusBarColor(), statusBarColor);
                            statusBarColorAnim.addUpdateListener(animation -> getWindow().setStatusBarColor(
                                    (int) animation.getAnimatedValue()));
                            statusBarColorAnim.setDuration(1000L);
                            statusBarColorAnim.setInterpolator(
                                    getFastOutSlowInInterpolator(ImageActivity.this));
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
}
