package com.study.news.homepage.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import com.study.news.R;
import java.util.List;

public class ChannelListAdapter extends RecyclerView.Adapter<ChannelListAdapter.ChannelViewHolder>{
    private List<String> mData;
    private Context mContext;
    private LayoutInflater inflater;

    public ChannelListAdapter(Context context, List<String> data) {
        this.mContext = context;
        this.mData = data;
        inflater = LayoutInflater.from(mContext);
    }

    @Override
    public ChannelListAdapter.ChannelViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.filter_item, parent, false);
        ChannelViewHolder holder = new ChannelViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(ChannelListAdapter.ChannelViewHolder holder, int position) {
        holder.channel.setText(mData.get(position));
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    static class ChannelViewHolder extends RecyclerView.ViewHolder {
        Button channel;
        public ChannelViewHolder(View itemView) {
            super(itemView);
            channel = (Button) itemView.findViewById(R.id.channel);
        }
    }
}
