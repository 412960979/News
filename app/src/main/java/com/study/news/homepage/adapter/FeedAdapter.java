package com.study.news.homepage.adapter;

import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.util.Pair;
import android.support.v7.widget.RecyclerView;
import android.transition.Transition;
import android.transition.TransitionInflater;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.study.news.EssayDetail.EssayActivity;
import com.study.news.MusicDetail.MusicDetailActivity;
import com.study.news.R;
import com.study.news.VideoDetail.VideoDetailActivity;
import com.study.news.homepage.HomeActivity;
import com.study.news.imagenews.ImageActivity;
import com.study.news.model.NewsDataBean;
import com.study.news.utils.TransitionUtils;
import com.study.news.utils.ViewUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * 首页列表适配器
 */
public class FeedAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private final static int TYPE_NEWS_PIC = 0;
    private final static int TYPE_NEWS_OTHERS = -1;
    private final static int TYPE_NEWS_MUSIC = 4;
    private final static int TYPE_NEWS_VEDIO = 5;
    private final LayoutInflater layoutInflater;
    private List<NewsDataBean.ContentListBean> mData;
    private Context mContext;
    private List<Integer> spanPos = new ArrayList<>();

    public FeedAdapter(Context context, List<NewsDataBean.ContentListBean> data) {
        mContext = context;
        this.mData = data;
        this.layoutInflater = LayoutInflater.from(context);
//        LibsChecker.checkVitamioLibs((HomeActivity)context);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType) {
            case TYPE_NEWS_PIC:
                return createPicNewsViewHolder(parent);
            case TYPE_NEWS_MUSIC:
                return createMusicNewsViewHolder(parent);
            case TYPE_NEWS_VEDIO:
                return createVedioNewsViewHolder(parent);
            case TYPE_NEWS_OTHERS:
                return createOthersNewsViewHolder(parent);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        switch (getItemViewType(position)) {
            case TYPE_NEWS_PIC:
                bindPicNews((PicNewsViewHolder) holder, position);
                break;
            case TYPE_NEWS_OTHERS:
                bindOthersNews((OthersNewsViewHolder) holder, position);
                break;
            case TYPE_NEWS_MUSIC:
                bindMusicNews((MusicNewsViewHolder) holder, position);
                break;
            case TYPE_NEWS_VEDIO:
                bindVedioNews((VedioNewsViewHolder) holder, position);
                break;
        }
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    @Override
    public int getItemViewType(int position) {
        if (getItemCount() > 0) {
            if (mData.get(position).getCategory().equals("0")) {
                return TYPE_NEWS_PIC;
            } else if (mData.get(position).getCategory().equals("4")) {
                return TYPE_NEWS_MUSIC;
            } else if (mData.get(position).getCategory().equals("5")) {
                return TYPE_NEWS_VEDIO;
            } else {
                return TYPE_NEWS_OTHERS;
            }
        }
        return 0;
    }

    public int getItemColumnSpan(int position) {
//        if ((((getItemCount() % 2 == 0 && (position == getItemCount() - 1 || position == getItemCount() - 2))
//                || (getItemCount() % 2 != 0 && position == getItemCount() - 1)) || isPan(position))) {
//            if (isPan(position)) {
//
//            } else {
//                spanPos.add(position);
//            }
//            return 2;
//        }
        return 1;
    }

    private boolean isPan(int pos) {
        return spanPos.contains(pos);
    }

    static class PicNewsViewHolder extends RecyclerView.ViewHolder {
        ImageView imgPic;
        TextView title;

        public PicNewsViewHolder(View itemView) {
            super(itemView);

            imgPic = (ImageView) itemView.findViewById(R.id.pic);
            title = (TextView) itemView.findViewById(R.id.title);
        }
    }

    private PicNewsViewHolder createPicNewsViewHolder(ViewGroup parent) {
        final PicNewsViewHolder holder = new PicNewsViewHolder(layoutInflater.inflate(
                R.layout.pic_item, parent, false));

        holder.imgPic.setOnClickListener(v -> {
            HomeActivity homeActivity = (HomeActivity)mContext;
            Intent intent = new Intent(homeActivity, ImageActivity.class);
            intent.putExtra("img", mData.get(holder.getAdapterPosition()).getImg_url());
            intent.putExtra("time", mData.get(holder.getAdapterPosition()).getPost_date());
            intent.putExtra("title", mData.get(holder.getAdapterPosition()).getTitle());
            intent.putExtra("forward", mData.get(holder.getAdapterPosition()).getForward());
            intent.putExtra("share", mData.get(holder.getAdapterPosition()).getShare_info());
            setGridItemContentTransitions(holder.imgPic, homeActivity);
            ActivityOptions options =
                    ActivityOptions.makeSceneTransitionAnimation(homeActivity,
                            Pair.create(v, "pic"),
                            Pair.create(v, "essay_background"),
                            Pair.create(v, "content"));
            homeActivity.startActivity(intent, options.toBundle());
        });
        return holder;
    }

    private void bindPicNews(final PicNewsViewHolder holder, int position) {
        Glide
                .with(mContext)
                .load(mData.get(position).getImg_url())
                .asBitmap()
                .override(600, 400)
                .into(holder.imgPic);
        holder.title.setText(mData.get(position).getTitle() + "  ·  " + mData.get(position).getForward());
    }

    static class OthersNewsViewHolder extends RecyclerView.ViewHolder {
        ImageView imgPic;
        TextView title;

        public OthersNewsViewHolder(View itemView) {
            super(itemView);

            imgPic = (ImageView) itemView.findViewById(R.id.pic);
            title = (TextView) itemView.findViewById(R.id.title);
        }
    }

    private OthersNewsViewHolder createOthersNewsViewHolder(ViewGroup parent) {
        final OthersNewsViewHolder holder = new OthersNewsViewHolder(layoutInflater.inflate(
                R.layout.others_item, parent, false));

        holder.imgPic.setOnClickListener(v -> {
            HomeActivity homeActivity = (HomeActivity)mContext;
            Intent intent = new Intent(homeActivity, EssayActivity.class);
            intent.putExtra("img", mData.get(holder.getAdapterPosition()).getImg_url());
            intent.putExtra("item_id", mData.get(holder.getAdapterPosition()).getItem_id());
            intent.putExtra("share", mData.get(holder.getAdapterPosition()).getShare_info());
            setGridItemContentTransitions(holder.imgPic, homeActivity);
            ActivityOptions options =
                    ActivityOptions.makeSceneTransitionAnimation(homeActivity,
                            Pair.create(v, "pic"),
                            Pair.create(v, "essay_background"),
                            Pair.create(v, "scrollView"));
            homeActivity.startActivity(intent, options.toBundle());
        });
        return holder;
    }

    private void setGridItemContentTransitions(View gridItem, Activity host) {
        final View fab = host.findViewById(R.id.fab);
        if (!ViewUtils.viewsIntersect(gridItem, fab)) return;

        Transition reenter = TransitionInflater.from(host)
                .inflateTransition(R.transition.fab_reenter);
        reenter.addListener(new TransitionUtils.TransitionListenerAdapter() {
            @Override
            public void onTransitionEnd(Transition transition) {
                ((HomeActivity)mContext).getWindow().setReenterTransition(null);
            }
        });
        host.getWindow().setReenterTransition(reenter);
    }

    private void bindOthersNews(final OthersNewsViewHolder holder, int position) {
        Glide
                .with(mContext)
                .load(mData.get(position).getImg_url())
                .asBitmap()
                .override(600, 400)
                .into(holder.imgPic);
        holder.title.setText(mData.get(position).getTitle() + "  ·  " + mData.get(position).getForward());
    }


    static class MusicNewsViewHolder extends RecyclerView.ViewHolder {
        ImageView imgPic;
        TextView title;

        public MusicNewsViewHolder(View itemView) {
            super(itemView);

            imgPic = (ImageView) itemView.findViewById(R.id.pic);
            title = (TextView) itemView.findViewById(R.id.title);
        }
    }

    private MusicNewsViewHolder createMusicNewsViewHolder(ViewGroup parent) {
        final MusicNewsViewHolder holder = new MusicNewsViewHolder(layoutInflater.inflate(
                R.layout.music_item, parent, false));
        holder.imgPic.setOnClickListener(v -> {
            HomeActivity homeActivity = (HomeActivity)mContext;
            Intent intent = new Intent(homeActivity, MusicDetailActivity.class);
            intent.putExtra("img", mData.get(holder.getAdapterPosition()).getImg_url());
            intent.putExtra("item_id", mData.get(holder.getAdapterPosition()).getItem_id());
            intent.putExtra("share", mData.get(holder.getAdapterPosition()).getShare_info());
            intent.putExtra("audio_url", mData.get(holder.getAdapterPosition()).getAudio_url());
            setGridItemContentTransitions(holder.imgPic, homeActivity);
            ActivityOptions options =
                    ActivityOptions.makeSceneTransitionAnimation(homeActivity,
                            Pair.create(v, "pic"),
                            Pair.create(v, "essay_background")
                    );
            homeActivity.startActivity(intent, options.toBundle());
        });
        return holder;
    }

    private void bindMusicNews(final MusicNewsViewHolder holder, int position) {
        Glide
                .with(mContext)
                .load(mData.get(position).getImg_url())
                .asBitmap()
                .override(600, 400)
                .into(holder.imgPic);
        holder.title.setText("音乐" + " · " + mData.get(position).getTitle());
    }

    static class VedioNewsViewHolder extends RecyclerView.ViewHolder {
        ImageView imgPic;
        TextView title;

        public VedioNewsViewHolder(View itemView) {
            super(itemView);

            imgPic = (ImageView) itemView.findViewById(R.id.pic);
            title = (TextView) itemView.findViewById(R.id.title);
        }
    }

    private VedioNewsViewHolder createVedioNewsViewHolder(ViewGroup parent) {
        final VedioNewsViewHolder holder = new VedioNewsViewHolder(layoutInflater.inflate(
                R.layout.vedio_item, parent, false));
        holder.imgPic.setOnClickListener(v -> {
            HomeActivity homeActivity = (HomeActivity)mContext;
            Intent intent = new Intent(homeActivity, VideoDetailActivity.class);
            intent.putExtra("img", mData.get(holder.getAdapterPosition()).getImg_url());
            intent.putExtra("item_id", mData.get(holder.getAdapterPosition()).getItem_id());
            intent.putExtra("share", mData.get(holder.getAdapterPosition()).getShare_info());
            intent.putExtra("video_url", mData.get(holder.getAdapterPosition()).getVideo_url());
            setGridItemContentTransitions(holder.imgPic, homeActivity);
            ActivityOptions options =
                    ActivityOptions.makeSceneTransitionAnimation(homeActivity,
                            Pair.create(v, "pic"),
                            Pair.create(v, "essay_background")
                    );
            homeActivity.startActivity(intent, options.toBundle());
        });
        return holder;
    }

    private void bindVedioNews(final VedioNewsViewHolder holder, int position) {
        Glide
                .with(mContext)
                .load(mData.get(position).getImg_url())
                .asBitmap()
                .override(600, 400)
                .into(holder.imgPic);
        holder.title.setText("影视" + " · " + mData.get(position).getTitle());
    }
}
