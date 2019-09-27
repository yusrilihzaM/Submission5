package com.yusril.favorit.adapter;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.yusril.favorit.R;

import static com.yusril.favorit.database.DatabaseContract.MovieColumns.DESCRIPTION;
import static com.yusril.favorit.database.DatabaseContract.MovieColumns.IMAGE;
import static com.yusril.favorit.database.DatabaseContract.MovieColumns.TITLE;
import static com.yusril.favorit.database.DatabaseContract.getColumnString;

public class MovieAdapter extends CursorAdapter {
    public MovieAdapter(Context context, Cursor c, boolean autoRequery) {
        super(context, c, autoRequery);
    }
    @Override
    public View newView(Context context, Cursor cursor, ViewGroup viewGroup) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_movie, viewGroup, false);
        return view;
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        if (cursor != null){
            TextView movieName = view.findViewById(R.id.title_movie);
            ImageView gambar = view.findViewById(R.id.img_movie);
            TextView description=view.findViewById(R.id.movie_description);
            description.setText(getColumnString(cursor,DESCRIPTION));
            movieName.setText(getColumnString(cursor,TITLE));
            Glide.with(context)
                    .load(getColumnString(cursor,IMAGE))
                    .apply(new RequestOptions().override(350,550))
                    .into(gambar);
        }
    }
    @Override
    public Cursor getCursor() {
        return super.getCursor();
    }
}
