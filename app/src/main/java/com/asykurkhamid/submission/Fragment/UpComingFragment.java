package com.asykurkhamid.submission.Fragment;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.asykurkhamid.submission.Activity.LoadDataListener;
import com.asykurkhamid.submission.Activity.MainActivity;
import com.asykurkhamid.submission.Activity.SearchListener;
import com.asykurkhamid.submission.Adapter.MoviesAdapter;
import com.asykurkhamid.submission.BuildConfig;
import com.asykurkhamid.submission.Http.APIResponse;
import com.asykurkhamid.submission.Http.ServicesFactory;
import com.asykurkhamid.submission.Pojo.DataMovieJSON;
import com.asykurkhamid.submission.R;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.Random;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class UpComingFragment extends Fragment implements SearchListener,LoadDataListener{
    ArrayList<DataMovieJSON> upComingData = new ArrayList<>();

    @BindView(R.id.pgUpComing)
    ProgressBar pgUpComing;
    @BindView(R.id.rvUpComing)
    RecyclerView rvUpComing;

    private final String API = BuildConfig.API_KEY;
    private final String page ="1";
    private String lang;

    public SearchListener getSearchInstance(){
        return this;
    }
    public LoadDataListener getLoadDataInstance(){
        return this;
    }

    public UpComingFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getUpcomingData();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_up_coming, container, false);
        ButterKnife.bind(this,view);
        Locale locale = getResources().getConfiguration().locale;
        lang = locale.getLanguage();
//        getUpcomingData();
        initRecycler();
        return view;
    }


    public void getUpcomingData() {
        Call<APIResponse<List<DataMovieJSON>>> call = ServicesFactory.getService().getUpComing(API,page,lang);
        call.enqueue(new Callback<APIResponse<List<DataMovieJSON>>>() {
            @Override
            public void onResponse(Call<APIResponse<List<DataMovieJSON>>> call, Response<APIResponse<List<DataMovieJSON>>> response) {
                if (response.isSuccessful()&&response.body().isSuccessful()){
                    List<DataMovieJSON> datas = response.body().results;
//                    Random random = new Random();
//                    Collections.shuffle(datas, random);

                    if (datas != null){
                        upComingData.clear();
                        upComingData.addAll(datas);
                        rvUpComing.getAdapter().notifyDataSetChanged();
                        pgUpComing.setVisibility(View.GONE);
                        rvUpComing.setVisibility(View.VISIBLE);
                    }
                }
            }

            @Override
            public void onFailure(Call<APIResponse<List<DataMovieJSON>>> call, Throwable t) {

            }
        });
    }

    private void initRecycler() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        int col =2;
        rvUpComing.setLayoutManager(new GridLayoutManager(getContext(),col));
        MoviesAdapter adapter = new MoviesAdapter(getContext(),upComingData);
        rvUpComing.setAdapter(adapter);
    }

    @Override
    public void doSearch(String text) {
        Log.e("SEARCH", "UPCOMMING");
        upComingData.removeAll(upComingData);
        getSearchUpcomingData(text);
    }
    private void getSearchUpcomingData(String query) {
        pgUpComing.setVisibility(View.VISIBLE);

        Call<APIResponse<List<DataMovieJSON>>> call = ServicesFactory.getService().getSearchMovie(API,lang,query,page,"false");
        call.enqueue(new Callback<APIResponse<List<DataMovieJSON>>>() {
            @Override
            public void onResponse(Call<APIResponse<List<DataMovieJSON>>> call, Response<APIResponse<List<DataMovieJSON>>> response) {
                if (response.isSuccessful()&&response.body().isSuccessful()){
                    List<DataMovieJSON> datasSearch = response.body().results;
                    if (datasSearch != null);
                    upComingData.clear();
                    upComingData.addAll(datasSearch);
                    rvUpComing.getAdapter().notifyDataSetChanged();
                    pgUpComing.setVisibility(View.GONE);
                    rvUpComing.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onFailure(Call<APIResponse<List<DataMovieJSON>>> call, Throwable t) {

            }
        });
    }

    @Override
    public void loadData() {
        getUpcomingData();
    }
}
