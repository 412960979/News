package com.study.news.ui;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

public class LoadMoreWrapper<T> extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    public static final int ITEM_TYPE_LOAD_MORE = 1;
    private RecyclerView.Adapter mAdapter;
    private View mLoadMoreView;
    public LoadMoreWrapper(RecyclerView.Adapter adapter) {
        mAdapter = adapter;
    }

    private boolean hasLoadMore() {
        return mLoadMoreView != null;
    }

    private boolean isShowLoadMore(int position) {
        return hasLoadMore() && (position >= mAdapter.getItemCount());
    }

    @Override
    public int getItemViewType(int position) {
        if (isShowLoadMore(position)) {
            return ITEM_TYPE_LOAD_MORE;
        }
        return mAdapter.getItemViewType(position);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == ITEM_TYPE_LOAD_MORE) {
            return new RecyclerView.ViewHolder(mLoadMoreView) {
            };
        }
        return mAdapter.onCreateViewHolder(parent, viewType);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (isShowLoadMore(position)) {
            if (mOnLoadMoreListener != null) {
                mOnLoadMoreListener.onLoadMoreRequested();
            }
        } else {
            mAdapter.onBindViewHolder(holder, position);
        }
    }

    @Override
    public int getItemCount() {
        return mAdapter.getItemCount() == 0 ? mAdapter.getItemCount() : mAdapter.getItemCount() + 1;
    }

    public interface OnLoadMoreListener {
        void onLoadMoreRequested();
    }

    private OnLoadMoreListener mOnLoadMoreListener;

    public LoadMoreWrapper setOnLoadMoreListener(OnLoadMoreListener loadMoreListener) {
        if (loadMoreListener != null) {
            mOnLoadMoreListener = loadMoreListener;
        }
        return this;
    }

    public LoadMoreWrapper setLoadMoreView(View loadMoreView) {
        mLoadMoreView = loadMoreView;
        return this;
    }
}