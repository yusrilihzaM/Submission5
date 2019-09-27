package com.yusril.submission5.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;
import com.yusril.submission5.R;
import com.yusril.submission5.activity.TvDetailActivity;
import com.yusril.submission5.model.TvShow;

import java.util.ArrayList;

public class TvAdapter extends RecyclerView.Adapter<TvAdapter.MyViewHolder> {
    private Context mContext;
    private ArrayList<TvShow> TvList=new ArrayList<>();

    public TvAdapter(Context mContext) {
        this.mContext = mContext;
    }

    public void setData(ArrayList<TvShow> items) {
        TvList.clear();
        TvList.addAll(items);
        //TvList=items;
        notifyDataSetChanged();
    }
    @NonNull
    @Override
    public TvAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_tv,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final TvAdapter.MyViewHolder holder, final int position) {
        final TvShow tvItems = TvList.get(position);
        holder.tvName.setText(tvItems.getName());
        holder.tvName.setText(tvItems.getName());
        holder.rating_tv.setText(Double.toString(tvItems.getRate()));
        Glide.with(mContext)
                .load(tvItems.getPoster())
                .apply(new RequestOptions().override(350,550))
                .listener(new RequestListener<Drawable>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                        holder.progressBarItemTv.setVisibility(View.GONE);
                        return false;
                    }
                })
                .into(holder.imgPhoto_tv);

        holder.Llayout_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent moveWithDataIntent = new Intent(mContext,TvDetailActivity.class);
                moveWithDataIntent.putExtra(TvDetailActivity.EXTRA_TV, TvList.get(position));
                mContext.startActivity(moveWithDataIntent);
            }
        });


    }

    @Override
    public int getItemCount() {
        return TvList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView imgPhoto_tv;
        TextView tvName;
        TextView rating_tv;
        ConstraintLayout Llayout_tv;
        ProgressBar progressBarItemTv;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            imgPhoto_tv=itemView.findViewById(R.id.img_tv);
            tvName=itemView.findViewById(R.id.tv_name);
            Llayout_tv=itemView.findViewById(R.id.linear_tv);
            rating_tv=itemView.findViewById(R.id.item_scoreAngkaHome_tv);
            progressBarItemTv = itemView.findViewById(R.id.progressBar_itemTv);
        }
    }
}
