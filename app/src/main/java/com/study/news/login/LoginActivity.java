package com.study.news.login;

import android.app.Activity;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import com.study.news.R;
import com.study.news.ui.transitions.FabTransform;
import com.study.news.ui.transitions.MorphTransform;

public class LoginActivity extends AppCompatActivity {
    private LinearLayout container;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // 让fab的动画效果更平滑
        if (!FabTransform.setup(this, container)) {
            MorphTransform.setup(this, container,
                    ContextCompat.getColor(this, R.color.background_light),
                    getResources().getDimensionPixelSize(R.dimen.dialog_corners));
        }
        container = (LinearLayout) findViewById(R.id.container);
    }

    public void dismiss(View view) {
        setResult(Activity.RESULT_CANCELED);
        finishAfterTransition();
    }

    @Override
    public void onBackPressed() {
        dismiss(null);
    }

}
