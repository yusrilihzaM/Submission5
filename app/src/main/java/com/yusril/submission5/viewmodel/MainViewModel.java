package com.yusril.submission5.viewmodel;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.yusril.submission5.BuildConfig;
import com.yusril.submission5.model.Movie;
import com.yusril.submission5.model.TvShow;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Locale;

import cz.msebera.android.httpclient.Header;

public class MainViewModel extends ViewModel {
    private MutableLiveData<ArrayList<Movie>> listMovies = new MutableLiveData<>();
    private MutableLiveData<ArrayList<TvShow>> listTv = new MutableLiveData<>();
    private String lang;

    public void setListMovies(final String type){
        AsyncHttpClient client = new AsyncHttpClient();
        final ArrayList<Movie> listItems = new ArrayList<>();
        String locale = Locale.getDefault().getDisplayLanguage();
        if (locale.contains("English")) {
            lang = "en-US";
        } else if (locale.contains("Indonesia")) {
            lang = "id";
        }

        String url = "https://api.themoviedb.org/3/discover/movie?api_key=" + BuildConfig.API_KEY + "&language=" + lang;
        client.get(url, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                try {

                    String result = new String(responseBody);
                    Log.d("API Result", result);
                    JSONObject responseObject = new JSONObject(result);
                    JSONArray list = responseObject.getJSONArray("results");
                    for (int i = 0; i < list.length(); i++) {
                        JSONObject movie = list.getJSONObject(i);
                        Movie movieItems = new Movie(movie);
                        listItems.add(movieItems);
                    }
                    listMovies.postValue(listItems);

                }
                catch (Exception e) {
                    Log.d("Error",e.getMessage());
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                Log.d("Error",error.getMessage());
            }
        });
    }
    public LiveData<ArrayList<Movie>> getMovie() {
        return listMovies;
    }

    public void setListTv(final String type){
        AsyncHttpClient client = new AsyncHttpClient();
        final ArrayList<TvShow> listItemsTv = new ArrayList<>();
        String locale = Locale.getDefault().getDisplayLanguage();
        if (locale.contains("English")) {
            lang = "en-US";
        } else if (locale.contains("Indonesia")) {
            lang = "id";
        }

        String url = "https://api.themoviedb.org/3/discover/tv?api_key=" + BuildConfig.API_KEY + "&language=" + lang;
        client.get(url, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                try {
                    String result = new String(responseBody);
                    Log.d("API Result", result);
                    JSONObject responseObject = new JSONObject(result);
                    JSONArray list = responseObject.getJSONArray("results");
                    for (int i = 0; i < list.length(); i++) {
                        JSONObject tv = list.getJSONObject(i);
                        TvShow tvItems = new TvShow(tv);
                        listItemsTv.add(tvItems);
                    }
                    listTv.postValue(listItemsTv);
                } catch (Exception e) {
                    Log.d("Error",e.getMessage());
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                Log.d("Error",error.getMessage());
            }
        });
    }
    public LiveData<ArrayList<TvShow>> getTv(){
        return listTv;
    }
    public void setSearchMovie(String query) {
        AsyncHttpClient client = new AsyncHttpClient();
        final ArrayList<Movie> listItems = new ArrayList<>();
        String url = " https://api.themoviedb.org/3/search/movie?api_key="+BuildConfig.API_KEY+"&language=en-US&query="+query;
        client.get(url, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                try {

                    String result = new String(responseBody);
                    Log.d("API Result", result);
                    JSONObject responseObject = new JSONObject(result);
                    JSONArray list = responseObject.getJSONArray("results");
                    for (int i = 0; i < list.length(); i++) {
                        JSONObject movie = list.getJSONObject(i);
                        Movie movieItems = new Movie(movie);
                        listItems.add(movieItems);
                    }
                    listMovies.postValue(listItems);

                }
                catch (Exception e) {
                    Log.d("Error",e.getMessage());
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                Log.d("Error",error.getMessage());
            }

        });


    }
    public LiveData<ArrayList<Movie>> getSearchMovies() {
        return listMovies;
    }
    public void setSearchTelevision(String query){
        AsyncHttpClient client = new AsyncHttpClient();
        final ArrayList<TvShow> listItemsTv = new ArrayList<>();
        String url = " https://api.themoviedb.org/3/search/tv?api_key="+BuildConfig.API_KEY+"&language=en-US&query="+query;
        client.get(url, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                try {
                    String result = new String(responseBody);
                    Log.d("API Result", result);
                    JSONObject responseObject = new JSONObject(result);
                    JSONArray list = responseObject.getJSONArray("results");
                    for (int i = 0; i < list.length(); i++) {
                        JSONObject tv = list.getJSONObject(i);
                        TvShow tvItems = new TvShow(tv);
                        listItemsTv.add(tvItems);
                    }
                    listTv.postValue(listItemsTv);
                } catch (Exception e) {
                    Log.d("Error",e.getMessage());
                }
            }
            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                Log.d("Error",error.getMessage());
            }
        });
    }
    public LiveData<ArrayList<TvShow>> getSearchTv() {
        return listTv;
    }
}
