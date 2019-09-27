package com.yusril.submission5.fragment;


import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;

import com.yusril.submission5.R;
import com.yusril.submission5.adapter.MoviesAdapter;
import com.yusril.submission5.database.MovieHelper;
import com.yusril.submission5.model.Movie;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class FavoriteMovieFragment extends Fragment {
    private RecyclerView recyclerView;
    private MoviesAdapter adapter;
    private ArrayList<Movie> movieList;

    public FavoriteMovieFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_favorit_movie, container, false);
        setHasOptionsMenu(true);
        recyclerView = view.findViewById(R.id.rvf_movie);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(),2));
        adapter= new MoviesAdapter(getContext());
        MovieHelper item =new MovieHelper(getContext());
        item.open();
        movieList=new ArrayList<>();
        movieList=item.getAllMovies();
        adapter.setData(movieList);
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
        return view;
    }


    @Override
    public void onStart(){
        super.onStart();
        MovieHelper item =new MovieHelper(getContext());
        item.open();
        movieList=item.getAllMovies();
        adapter.setData(movieList);
        recyclerView.setAdapter(adapter);
    }

}
