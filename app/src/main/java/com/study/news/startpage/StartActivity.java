package com.study.news.startpage;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.content.Intent;
import android.graphics.Color;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.AccelerateInterpolator;
import android.widget.TextView;

import com.study.news.R;
import com.study.news.homepage.HomeActivity;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

import static android.animation.Animator.*;
import static com.google.gson.internal.$Gson$Preconditions.checkNotNull;

public class StartActivity extends AppCompatActivity implements StartPageContract.View{
    private ConstraintLayout constraint;
    private StartPageContract.Presenter mPresenter;
    private TextView textViewName;
    private TextView textView;
    private TextView textView2;
    private TextView textView3;
    private TextView textView4;
    private TextView textView5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        textViewName = (TextView) findViewById(R.id.textViewName);
        textView = (TextView) findViewById(R.id.textView);
        textView2 = (TextView) findViewById(R.id.textView2);
        textView3 = (TextView) findViewById(R.id.textView3);
        textView4 = (TextView) findViewById(R.id.textView4);
        textView5 = (TextView) findViewById(R.id.textView5);

        constraint = (ConstraintLayout) findViewById(R.id.constraint);

        setAnimation();

        Observable.
                timer(4, TimeUnit.SECONDS).
                subscribeOn(Schedulers.io()).
                observeOn(AndroidSchedulers.mainThread()).
                subscribe(integer -> {
                    Intent intent = new Intent(StartActivity.this, HomeActivity.class);
                    startActivity(intent);
                    StartActivity.this.finish();
                });

        new StartPagePresenter(this);
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

    /**
     * 设置开场动画过渡效果
     */
    private void setAnimation(){
        ObjectAnimator animator = ObjectAnimator.ofInt(constraint, "BackgroundColor", Color.parseColor("#ffffff"), Color.parseColor("#333333"));
        animator.setEvaluator(new android.animation.ArgbEvaluator());// 设置类型估值器
        animator.setInterpolator(new AccelerateDecelerateInterpolator());// 设置时间插值器，这里设置先加速后减速
        animator.setDuration(1500);
        animator.start();

        animator.addListener(new AnimatorListener(){
            @Override
            public void onAnimationStart(Animator animation) {
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                textViewName.setVisibility(View.VISIBLE);
                textView.setVisibility(View.VISIBLE);
                textView2.setVisibility(View.VISIBLE);
                textView3.setVisibility(View.VISIBLE);
                textView4.setVisibility(View.VISIBLE);
                textView5.setVisibility(View.VISIBLE);
                ObjectAnimator animatorName = ObjectAnimator.ofFloat(textViewName, "TranslationY", 0, 300);
                animatorName.setInterpolator(new AccelerateInterpolator());// 设置时间插值器，这里设置先加速后减速
                animatorName.setDuration(500);
                animatorName.start();

                ObjectAnimator animatorDay = ObjectAnimator.ofFloat(textView, "TranslationX", 0, 300);
                animatorDay.setInterpolator(new AccelerateInterpolator());// 设置时间插值器，这里设置先加速后减速
                animatorDay.setDuration(500);
                animatorDay.start();

                ObjectAnimator animator3 = ObjectAnimator.ofFloat(textView3, "TranslationX", 0, 200);
                animator3.setInterpolator(new AccelerateInterpolator());// 设置时间插值器，这里设置先加速后减速
                animator3.setDuration(500);
                animator3.start();

                ObjectAnimator animator5 = ObjectAnimator.ofFloat(textView5, "TranslationX", 0, 400);
                animator5.setInterpolator(new AccelerateInterpolator());// 设置时间插值器，这里设置先加速后减速
                animator5.setDuration(500);
                animator5.start();

                ObjectAnimator animator2 = ObjectAnimator.ofFloat(textView2, "TranslationX", 800, -250);
                animator2.setInterpolator(new AccelerateInterpolator());// 设置时间插值器，这里设置先加速后减速
                animator2.setDuration(500);
                animator2.start();

                ObjectAnimator animator4 = ObjectAnimator.ofFloat(textView4, "TranslationX", 800, -200);
                animator4.setInterpolator(new AccelerateInterpolator());// 设置时间插值器，这里设置先加速后减速
                animator4.setDuration(500);
                animator4.start();
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }


            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
    }

    @Override
    public void setPresenter(StartPageContract.Presenter presenter) {
        mPresenter = checkNotNull(presenter);
    }
}
