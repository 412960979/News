package com.study.news.homepage;

import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.transition.Transition;
import android.transition.TransitionInflater;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.view.WindowInsets;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;

import com.study.news.R;
import com.study.news.about.AboutActivity;
import com.study.news.homepage.adapter.ChannelListAdapter;
import com.study.news.homepage.adapter.FeedAdapter;
import com.study.news.imagenews.ImageActivity;
import com.study.news.login.LoginActivity;
import com.study.news.model.NewsDataBean;
import com.study.news.ui.LoadMoreWrapper;
import com.study.news.ui.listener.OnItemClickListener;
import com.study.news.ui.listener.RecyclerItemClickListener;
import com.study.news.ui.transitions.FabTransform;
import com.study.news.utils.TransitionUtils;
import com.study.news.utils.ViewUtils;

import java.util.ArrayList;
import java.util.List;

import io.vov.vitamio.LibsChecker;

import static com.google.gson.internal.$Gson$Preconditions.checkNotNull;

public class HomeActivity extends Activity implements NewsListContract.View {
    private Context mContxt;
    private Toolbar toolbar;
    private DrawerLayout drawer;
    private RecyclerView grid;
    private RecyclerView filtersList;
    private final static int NEW_LOGIN = 1;
    private final static int NEW_PIC = 2;
    private FloatingActionButton fab;
    private NewsListContract.Presenter mPresenter;
    private ChannelListAdapter mChannelsListAdapter;
    private FeedAdapter mFeedAdapter;
    private List<String> mChannels = new ArrayList<>();
    private List<NewsDataBean.ContentListBean> mFeeds = new ArrayList<>();
    private Menu mMenu;
    private MenuItem titleMenu;
    private SwipeRefreshLayout swipeRefreshLayout;
    private LoadMoreWrapper newsAdapterWrapper;
    private ImageView img;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContxt =  this;
        setContentView(R.layout.home_activity_main);

        findViewAndSetAttr();

        setActionBar(toolbar);

        setDrawer();

        new NewsListPresenter(this);

        mPresenter.start();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        mPresenter.closeSubScription();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        mMenu = menu;
        titleMenu = mMenu.findItem(R.id.menu_title);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_filter:
                drawer.openDrawer(GravityCompat.START);
                return true;
            case R.id.menu_about:
                startActivity(new Intent(HomeActivity.this, AboutActivity.class),
                        ActivityOptions.makeSceneTransitionAnimation(this).toBundle());
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void findViewAndSetAttr() {
        img = (ImageView) findViewById(R.id.img);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        drawer = (DrawerLayout) findViewById(R.id.drawer);
        grid = (RecyclerView) findViewById(R.id.grid);
        filtersList = (RecyclerView) findViewById(R.id.filters);
        fab = (FloatingActionButton) findViewById(R.id.fab);
        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipeLayout);
        mChannelsListAdapter = new ChannelListAdapter(this, mChannels);
        mFeedAdapter = new FeedAdapter(this, mFeeds);
        newsAdapterWrapper = new LoadMoreWrapper(mFeedAdapter);

        newsAdapterWrapper.setLoadMoreView(LayoutInflater.from(this).inflate(R.layout.loadmore_footview, filtersList, false));
        swipeRefreshLayout.setRefreshing(true);
        swipeRefreshLayout.setColorSchemeResources(R.color.accent);
        swipeRefreshLayout.setProgressBackgroundColor(R.color.text_primary_light);

        filtersList.setLayoutManager(new LinearLayoutManager(this));
        filtersList.setAdapter(mChannelsListAdapter);

        GridLayoutManager layoutManager = new GridLayoutManager(this, 2);
//        layoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
//            @Override
//            public int getSpanSize(int position) {
//                return mFeedAdapter.getItemColumnSpan(position);
//            }
//        });
        grid.setLayoutManager(layoutManager);
        grid.setHasFixedSize(true);
        grid.setAdapter(newsAdapterWrapper);

        fab.setOnClickListener((view) -> showLoginActivity());

        filtersList.addOnItemTouchListener(new RecyclerItemClickListener(
                this,
                filtersList,
                (OnItemClickListener) this::onChannelsRecyleViewItemClick));

//        grid.addOnItemTouchListener(new RecyclerItemClickListener(
//                this,
//                grid,
//                (OnItemClickListener) this::onFeedsRecyleViewItemClick));

        // 下拉刷新
        swipeRefreshLayout.setOnRefreshListener(() -> mPresenter.getHomePageNewsListData());
        // 加载更多
        newsAdapterWrapper.setOnLoadMoreListener(() -> mPresenter.getHomePageNewsListDataMore());
    }

    /**
     * 设置插入的toolbar在系统状态栏的下面
     *
     * @param insets
     */
    private void setToolbarPos(WindowInsets insets) {
        ViewGroup.MarginLayoutParams lpToolbar = (ViewGroup.MarginLayoutParams) toolbar
                .getLayoutParams();
        lpToolbar.topMargin += insets.getSystemWindowInsetTop();
        lpToolbar.leftMargin += insets.getSystemWindowInsetLeft();
        lpToolbar.rightMargin += insets.getSystemWindowInsetRight();
        toolbar.setLayoutParams(lpToolbar);
    }

    /**
     * 设置RecyleView的位置
     */
    private void setRecyleViewPos(WindowInsets insets) {
        grid.setPadding(
                grid.getPaddingLeft() + insets.getSystemWindowInsetLeft(),
                insets.getSystemWindowInsetTop()
                        + ViewUtils.getActionBarSize(HomeActivity.this),
                grid.getPaddingRight() + insets.getSystemWindowInsetRight(),
                grid.getPaddingBottom() + insets.getSystemWindowInsetBottom());
    }

    /**
     * 设置fab的位置
     */
    private void setFabPos(WindowInsets insets) {
        // 设置悬浮按钮的位置
        ViewGroup.MarginLayoutParams lpFab = (ViewGroup.MarginLayoutParams) fab
                .getLayoutParams();
        lpFab.bottomMargin += insets.getSystemWindowInsetBottom(); // portrait
        lpFab.rightMargin += insets.getSystemWindowInsetRight(); // landscape
        fab.setLayoutParams(lpFab);
    }

    /**
     * 设置view的颜色与状态栏结合，以获得所需的外观。
     *
     * @param insets
     */
    private void setViewPos(WindowInsets insets) {
        View statusBarBackground = findViewById(R.id.status_bar_background);
        FrameLayout.LayoutParams lpStatus = (FrameLayout.LayoutParams)
                statusBarBackground.getLayoutParams();
        lpStatus.height = insets.getSystemWindowInsetTop();
        statusBarBackground.setLayoutParams(lpStatus);
    }

    /**
     * 设置抽屉列表位置
     *
     * @param insets
     */
    private void setDrawerRecyleView(WindowInsets insets) {
        final boolean ltr = filtersList.getLayoutDirection() == View.LAYOUT_DIRECTION_LTR;
        filtersList.setPaddingRelative(filtersList.getPaddingStart(),
                filtersList.getPaddingTop() + insets.getSystemWindowInsetTop(),
                filtersList.getPaddingEnd() + (ltr ? insets.getSystemWindowInsetRight() :
                        0),
                filtersList.getPaddingBottom() + insets.getSystemWindowInsetBottom());
    }

    /**
     * 抽屉布局适用于SystemWindows，所以我们自己必须处理插入的东西
     */
    private void setDrawer() {
        drawer.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION);

        drawer.setOnApplyWindowInsetsListener((v, insets) -> {
            setToolbarPos(insets);
            setRecyleViewPos(insets);
            setFabPos(insets);
            setViewPos(insets);
            setDrawerRecyleView(insets);
            // 清除监听，以便插件不重用
            drawer.setOnApplyWindowInsetsListener(null);

            return insets.consumeSystemWindowInsets();
        });
    }

    private void showLoginActivity() {
        Intent intent = new Intent(this, LoginActivity.class);
        FabTransform.addExtras(intent,
                ContextCompat.getColor(this, R.color.accent), R.drawable.ic_add_dark);
        ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(this, fab,
                getString(R.string.news_login));
        startActivityForResult(intent, NEW_LOGIN, options.toBundle());
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case NEW_LOGIN:
                break;
            case NEW_PIC: {
                break;
            }
        }
    }

    @Override
    public void setPresenter(NewsListContract.Presenter presenter) {
        mPresenter = checkNotNull(presenter);
    }

    @Override
    public void upDateChaneelRecyecleView(List<String> data) {
        mChannels.clear();
        mChannels.addAll(data);
        mChannelsListAdapter.notifyDataSetChanged();
    }

    @Override
    public void upDateFeedRecyleView(List<NewsDataBean.ContentListBean> data) {
        mFeeds.clear();
        ;
        mFeeds.addAll(data);
        newsAdapterWrapper.notifyDataSetChanged();
    }

    @Override
    public void hideSwipeRefreshLayout() {
        swipeRefreshLayout.setRefreshing(false);
    }

    /**
     * 频道的item点击事件
     *
     * @param view
     * @param pos
     */
    private void onChannelsRecyleViewItemClick(View view, int pos) {
        switch (pos) {
            case 0:
            case 1:
            case 2:
            case 3:
            case 4:
            default:
                titleMenu.setTitle(((Button) view).getText());
                drawer.closeDrawer(GravityCompat.START);
                break;
        }
    }

    private ImageView imageView;

    /**
     * 首页的grid点击事件
     *
     * @param view
     * @param pos
     */
    private void onFeedsRecyleViewItemClick(View view, int pos) {
    }

    @Override
    protected void onPause() {
        super.onPause();
    }
}
