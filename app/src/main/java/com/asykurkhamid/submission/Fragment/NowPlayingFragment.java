package com.asykurkhamid.submission.Fragment;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.asykurkhamid.submission.Activity.LoadDataListener;
import com.asykurkhamid.submission.Activity.SearchListener;
import com.asykurkhamid.submission.Adapter.MoviesAdapter;
import com.asykurkhamid.submission.BuildConfig;
import com.asykurkhamid.submission.Http.APIResponse;
import com.asykurkhamid.submission.Http.Services;
import com.asykurkhamid.submission.Http.ServicesFactory;
import com.asykurkhamid.submission.Pojo.DataMovieJSON;
import com.asykurkhamid.submission.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class NowPlayingFragment extends Fragment implements SearchListener,LoadDataListener{
    ArrayList<DataMovieJSON> nowPlayingData = new ArrayList<>();

    @BindView(R.id.pgNowPlaying)
    ProgressBar pgNowPlaying;
    @BindView(R.id.rvNowPlaying)
    RecyclerView rvNowPlaying;

    private final String API = BuildConfig.API_KEY;
    private final String page ="1";
    private String lang;
    public SearchListener getSearchIntance(){
        return this;
    }
    public LoadDataListener getLoadDataInstance(){
        return this;
    }

    public NowPlayingFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getNowPlayingData();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_now_playing, container, false);
        ButterKnife.bind(this,view);
        Locale locale = getResources().getConfiguration().locale;
        lang = locale.getLanguage();

//        getNowPlayingData();
        initRecycler();
        return view;
    }

    @Override
    public void onPrepareOptionsMenu(Menu menu) {
        super.onPrepareOptionsMenu(menu);
    }

    public void getNowPlayingData() {
        retrofit2.Call<APIResponse<List<DataMovieJSON>>> call = ServicesFactory.getService().getNowPlaying(API,page,lang);
        call.enqueue(new Callback<APIResponse<List<DataMovieJSON>>>() {
            @Override
            public void onResponse(retrofit2.Call<APIResponse<List<DataMovieJSON>>> call, Response<APIResponse<List<DataMovieJSON>>> response) {
                if (response.isSuccessful()&&response.body().isSuccessful()){
                    List<DataMovieJSON> datas = response.body().results;
                    if (datas !=null){
                        nowPlayingData.clear();
                        nowPlayingData.addAll(datas);
                        rvNowPlaying.getAdapter().notifyDataSetChanged();
                        pgNowPlaying.setVisibility(View.GONE);
                        rvNowPlaying.setVisibility(View.VISIBLE);
                    }
                }
            }

            @Override
            public void onFailure(retrofit2.Call<APIResponse<List<DataMovieJSON>>> call, Throwable t) {

            }
        });
    }

    private void initRecycler() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        int col =2;
        rvNowPlaying.setLayoutManager(new GridLayoutManager(getContext(),col));
        MoviesAdapter adapter = new MoviesAdapter(getContext(),nowPlayingData);
        rvNowPlaying.setAdapter(adapter);
    }

    @Override
    public void doSearch(String text) {
        Log.e("SEARCH", "NOW PLAYING");
        nowPlayingData.removeAll(nowPlayingData);
        getSearchDataNowPlaying(text);
    }

    private void getSearchDataNowPlaying(String query) {
        pgNowPlaying.setVisibility(View.VISIBLE);

        Call<APIResponse<List<DataMovieJSON>>> call = ServicesFactory.getService().getSearchMovie(API,lang,query,page,"false");
        call.enqueue(new Callback<APIResponse<List<DataMovieJSON>>>() {
            @Override
            public void onResponse(Call<APIResponse<List<DataMovieJSON>>> call, Response<APIResponse<List<DataMovieJSON>>> response) {
                if (response.isSuccessful()&&response.body().isSuccessful()){
                    List<DataMovieJSON> datasSearch = response.body().results;
                    if (datasSearch != null);
                    nowPlayingData.clear();
                    nowPlayingData.addAll(datasSearch);
                    rvNowPlaying.getAdapter().notifyDataSetChanged();
                    pgNowPlaying.setVisibility(View.GONE);
                    rvNowPlaying.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onFailure(Call<APIResponse<List<DataMovieJSON>>> call, Throwable t) {

            }
        });
    }

    @Override
    public void loadData() {
        getNowPlayingData();
    }
}
