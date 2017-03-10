package com.example.cay.youshi.adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.cay.youshi.R;
import com.example.cay.youshi.bean.MovieBean;
import com.example.cay.youshi.bean.PlayeRecordBean;
import com.example.cay.youshi.bean.UpDdtaBackBean;
import com.example.cay.youshi.http.HttpUtils;
import com.example.cay.youshi.player.MyPlayerActivity;
import com.example.cay.youshi.utils.TimeChange;

import org.litepal.crud.DataSupport;

import java.util.List;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**shu
 * Created by Cay on 2017/2/5.
 */

public class MovieCountItemAdapter extends BaseQuickAdapter<MovieBean,BaseViewHolder> {
    private Context context;

    public MovieCountItemAdapter(int layoutResId, List<MovieBean> data, Context context) {
        super(layoutResId, data);
        this.context = context;
    }
    @Override
    protected void convert(final BaseViewHolder helper, final MovieBean item) {
        helper.setText(R.id.movie_count_btn, item.getItemName());
        helper.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                upCountMoviePlayernum(item.getMovieId(),item.getAllName(),item.getImg_url());
                savePlayeRecord(item.getAllName(),item.getImg_url(),item.getType(),item.getMovieId());
                if (item.getType() == 1) {
                    MyPlayerActivity.star(context,item.getMovieUrl(),item.getMovieName());
                } else {
                    Uri issuesUrl = Uri.parse(item.getMovieUrl().trim());
                    Intent intent = new Intent(Intent.ACTION_VIEW, issuesUrl);
                    context.startActivity(intent);
                }
            }
        });
    }

    private void upCountMoviePlayernum(String id, String name, String img_url) {
        HttpUtils.getInstance().getMyObservableClient().upMoviePlayerData(name,id,img_url)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<UpDdtaBackBean>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(UpDdtaBackBean value) {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });

    }

    private  void savePlayeRecord(final String name, final String url, final int type, final String id) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                DataSupport.deleteAll(PlayeRecordBean.class, "movie_id = ?", id);
                PlayeRecordBean playeRecordBean = new PlayeRecordBean();
                playeRecordBean.setImg_url(url);
                playeRecordBean.setName(name);
                playeRecordBean.setType(type);
                playeRecordBean.setMovie_id(id);
                playeRecordBean.setPlay_time(TimeChange.getCurrentTime(System.currentTimeMillis()));
                playeRecordBean.save();
            }
        }).start();
    }
}
