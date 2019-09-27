package com.yusril.submission5.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.github.ivbaranov.mfb.MaterialFavoriteButton;
import com.google.android.material.snackbar.Snackbar;
import com.yusril.submission5.R;
import com.yusril.submission5.database.DatabaseHelper;
import com.yusril.submission5.database.MovieHelper;
import com.yusril.submission5.model.TvShow;

import static com.yusril.submission5.database.DatabaseContract.CONTENT_URI_TV;
import static com.yusril.submission5.database.DatabaseContract.TABLE_TV;
import static com.yusril.submission5.database.DatabaseContract.TvColumns.DESCRIPTION_TV;
import static com.yusril.submission5.database.DatabaseContract.TvColumns.ID_TV;
import static com.yusril.submission5.database.DatabaseContract.TvColumns.IMAGE_TV;
import static com.yusril.submission5.database.DatabaseContract.TvColumns.RATE_TV;
import static com.yusril.submission5.database.DatabaseContract.TvColumns.TITLE_TV;

public class TvDetailActivity extends AppCompatActivity {
    public static final String EXTRA_TV = "extra_tv";
    private TextView name,plotSynopsis,userrating;
    private ImageView photo;
    private TvShow tvShow;
    private ImageButton back;
    private MovieHelper movieHelper;
    private MaterialFavoriteButton materialFavoriteButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tv_detail);
        getSupportActionBar().hide();
        photo=findViewById(R.id.img_tv_detail);
        plotSynopsis=findViewById(R.id.d_description_tv);
        name= findViewById(R.id.d_name_tv);
        userrating= findViewById(R.id.item_scoreAngkaHometv);
        materialFavoriteButton = findViewById(R.id.favorite);
        back=findViewById(R.id.btn_back);

        tvShow =getIntent().getParcelableExtra(EXTRA_TV);

        name.setText(tvShow.getName());
        plotSynopsis.setText(tvShow.getOverview());
        userrating.setText(Double.toString(tvShow.getRate()));
        Glide.with(this)
                .load(tvShow.getPoster())
                .apply(new RequestOptions().override(350,550))
                .into(photo);
        if (Exist(tvShow.getName())){
            materialFavoriteButton.setFavorite(true);
            materialFavoriteButton.setOnFavoriteChangeListener(new MaterialFavoriteButton.OnFavoriteChangeListener() {
                @Override
                public void onFavoriteChanged(MaterialFavoriteButton buttonView, boolean favorite) {
                    if (favorite){
                        Save();
                        Snackbar.make(buttonView,"Added to Favorite",Snackbar.LENGTH_SHORT).show();
                    }else {
                        Uri uri= Uri.parse(CONTENT_URI_TV+"/"+tvShow.getId());
                        getContentResolver().delete(uri,null, null );
                        Snackbar.make(buttonView,"Removed from Favorite",Snackbar.LENGTH_SHORT).show();
                    }
                }
            });
        }else {
            materialFavoriteButton.setOnFavoriteChangeListener(new MaterialFavoriteButton.OnFavoriteChangeListener() {
                @Override
                public void onFavoriteChanged(MaterialFavoriteButton buttonView, boolean favorite) {
                    if (favorite) {
                        Save();
                        Snackbar.make(buttonView, "Added to Favorite", Snackbar.LENGTH_SHORT).show();
                    } else {
                        Uri uri= Uri.parse(CONTENT_URI_TV+"/"+tvShow.getId());
                        getContentResolver().delete(uri,null, null );
                        Snackbar.make(buttonView, "Removed from Favorite", Snackbar.LENGTH_SHORT).show();
                    }
                }
            });
        }
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private boolean Exist(String name) {
        String pilih= TITLE_TV+" =?";
        String[] pilihArg={name};
        String limit="1";
        movieHelper= new MovieHelper(this);
        movieHelper.open();
        DatabaseHelper dataBaseHelper= new DatabaseHelper(TvDetailActivity.this);
        SQLiteDatabase database = dataBaseHelper.getWritableDatabase();
        Cursor cursor= database.query(TABLE_TV,null,pilih,pilihArg,null,null,null,limit);
        boolean exists;
        /*if (cursor==null){
            exists=false;
        }*/
        exists=(cursor.getCount() > 0);
        cursor.close();
        movieHelper.close();
        return exists;
    }
    private void Save(){
        ContentValues args = new ContentValues();
        args.put(ID_TV, tvShow.getId());
        args.put(TITLE_TV, tvShow.getName());
        args.put(DESCRIPTION_TV, tvShow.getOverview());
        args.put(RATE_TV, tvShow.getRate());
        args.put(IMAGE_TV, tvShow.getPoster());
        getContentResolver().insert(CONTENT_URI_TV, args);
    }
}
