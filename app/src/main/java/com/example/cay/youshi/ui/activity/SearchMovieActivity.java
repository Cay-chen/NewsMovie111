package com.example.cay.youshi.ui.activity;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;
import android.widget.Toast;

import com.example.cay.youshi.R;
import com.example.cay.youshi.adapter.MovieDetailsAdapter;
import com.example.cay.youshi.bean.SingleLookupResultBean;
import com.example.cay.youshi.bean.YouShiMovieDealisBean;
import com.example.cay.youshi.databinding.ActivitySearchMovieBinding;
import com.example.cay.youshi.http.HttpUtils;

import java.util.List;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

public class SearchMovieActivity extends AppCompatActivity implements SearchView.OnQueryTextListener, View.OnTouchListener {
    private ActivitySearchMovieBinding movieBinding;
    private SearchView mSearchView;
    private InputMethodManager mImm;
    private RecyclerView mRecyclerView;
    private MovieDetailsAdapter mMovieDetailsAdapter;
    private View notDataView;
    private View errorView;
    private View headView;
    private TextView title;

    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        movieBinding = DataBindingUtil.setContentView(this, R.layout.activity_search_movie);
        Toolbar mToolbar = movieBinding.searchMovieToolbar;
        setSupportActionBar(mToolbar);
        getSupportActionBar().setLogo(R.drawable.icon_back);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mImm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        initRecyclerView();
        title = (TextView) headView.findViewById(R.id.header_title);
        notDataView = getLayoutInflater().inflate(R.layout.empty_view, (ViewGroup) mRecyclerView.getParent(), false);
        notDataView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mSearchView.getQuery().toString().trim().equals("")) {
                    Toast.makeText(SearchMovieActivity.this, R.string.search_err, Toast.LENGTH_SHORT).show();
                } else {
                    searchMovieDataHttp(mSearchView.getQuery().toString().trim());
                }

            }
        });
        errorView = getLayoutInflater().inflate(R.layout.error_view, (ViewGroup) mRecyclerView.getParent(), false);
        errorView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mSearchView.getQuery().toString().trim().equals("")) {
                    Toast.makeText(SearchMovieActivity.this, R.string.search_err, Toast.LENGTH_SHORT).show();
                } else {
                    searchMovieDataHttp(mSearchView.getQuery().toString().trim());

                }

            }
        });
    }

    private void initRecyclerView() {
        mRecyclerView = movieBinding.searchMovieRv;
        mRecyclerView.setVisibility(View.GONE);
        LinearLayoutManager manager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(manager);
        mMovieDetailsAdapter = new MovieDetailsAdapter(R.layout.item_one1, null, SearchMovieActivity.this);
        mRecyclerView.setAdapter(mMovieDetailsAdapter);
        headView = getLayoutInflater().inflate(R.layout.header_item_one, (ViewGroup) mRecyclerView.getParent(), false);
        mMovieDetailsAdapter.addHeaderView(headView);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_search, menu);

        mSearchView = (SearchView) MenuItemCompat.getActionView(menu.findItem(R.id.menu_search));

        mSearchView.setOnQueryTextListener(this);
        mSearchView.setQueryHint(getResources().getString(R.string.search_net_music));
        mSearchView.setIconifiedByDefault(false);
        mSearchView.setIconified(false);
        mSearchView.setSubmitButtonEnabled(true);

        MenuItemCompat.setOnActionExpandListener(menu.findItem(R.id.menu_search), new MenuItemCompat.OnActionExpandListener() {
            @Override
            public boolean onMenuItemActionExpand(MenuItem item) {
                return true;
            }

            @Override
            public boolean onMenuItemActionCollapse(MenuItem item) {
                finish();
                return false;
            }
        });
        menu.findItem(R.id.menu_search).expandActionView();
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        searchMovieDataHttp(mSearchView.getQuery().toString().trim());
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        return false;
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        hideInputManager();
        return false;
    }

    public void hideInputManager() {
        if (mSearchView != null) {
            if (mImm != null) {
                mImm.hideSoftInputFromWindow(mSearchView.getWindowToken(), 0);
            }
            mSearchView.clearFocus();
        }
    }

    public void searchMovieDataHttp(final String name) {
        mMovieDetailsAdapter.setNewData(null);
        mMovieDetailsAdapter.setEmptyView(R.layout.loading_view, (ViewGroup) mRecyclerView.getParent());
        mRecyclerView.setVisibility(View.VISIBLE);
        mRecyclerView.postDelayed(new Runnable() {
            @Override
            public void run() {
                HttpUtils.getInstance().getYouShiData(false).singleLookupResult("name", name, "10000000000", "30")
                        .map(new Function<SingleLookupResultBean, List<YouShiMovieDealisBean>>() {
                            @Override
                            public List<YouShiMovieDealisBean> apply(SingleLookupResultBean singleLookupResultBean) throws Exception {
                                return singleLookupResultBean.getResult();
                            }
                        }).subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Observer<List<YouShiMovieDealisBean>>() {
                            @Override
                            public void onSubscribe(Disposable d) {

                            }

                            @Override
                            public void onNext(List<YouShiMovieDealisBean> list) {
                                if (list.size() == 0) {
                                    mMovieDetailsAdapter.setEmptyView(notDataView);
                                } else {
                                    title.setText("搜索到与“" + name + "”相关视频" + list.size() + "个");
                                    mMovieDetailsAdapter.setNewData(list);
                                }
                            }

                            @Override
                            public void onError(Throwable e) {
                                mMovieDetailsAdapter.setEmptyView(errorView);
                            }

                            @Override
                            public void onComplete() {

                            }
                        });
            }
        }, 1000);
    }

}
