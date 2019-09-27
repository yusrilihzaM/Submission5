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
import com.yusril.submission5.activity.MovieDetailActivity;
import com.yusril.submission5.R;
import com.yusril.submission5.model.Movie;

import java.util.ArrayList;

public class MoviesAdapter extends RecyclerView.Adapter<MoviesAdapter.MyViewHolder> {
    private Context mContext;
    private ArrayList<Movie> movieList=new ArrayList<>();

    public MoviesAdapter(Context mContext) {
        this.mContext = mContext;
    }

    public void setData(ArrayList<Movie> items) {
        movieList.clear();
        movieList.addAll(items);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public MoviesAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_movie,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final MoviesAdapter.MyViewHolder holder, final int position) {
        final Movie movieItems = movieList.get(position);
        holder.movieName.setText(movieItems.getName());
        holder.rating.setText(Double.toString(movieItems.getRate()));
        Glide.with(mContext)
                .load(movieItems.getPoster())
                .apply(new RequestOptions().override(350,550))
                .listener(new RequestListener<Drawable>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                        holder.progressBarItemMovie.setVisibility(View.GONE);
                        return false;
                    }
                })
                .into(holder.imgPhoto);

        holder.Llayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent moveWithDataIntent = new Intent(mContext,MovieDetailActivity.class);
                moveWithDataIntent.putExtra(MovieDetailActivity.EXTRA_MOVIE, movieList.get(position));
                mContext.startActivity(moveWithDataIntent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return movieList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView imgPhoto;
        TextView movieName;
        TextView rating;
        ConstraintLayout Llayout;
        ProgressBar progressBarItemMovie;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            imgPhoto=itemView.findViewById(R.id.img_movie);
            movieName=itemView.findViewById(R.id.movie_name);
            Llayout=itemView.findViewById(R.id.linear);
            rating=itemView.findViewById(R.id.item_scoreAngkaHome);
            progressBarItemMovie = itemView.findViewById(R.id.progressBar_itemMovie);
        }
    }
}
