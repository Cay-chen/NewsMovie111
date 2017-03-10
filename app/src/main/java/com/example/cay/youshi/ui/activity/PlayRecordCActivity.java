package com.example.cay.youshi.ui.activity;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseViewHolder;
import com.chad.library.adapter.base.callback.ItemDragAndSwipeCallback;
import com.chad.library.adapter.base.listener.OnItemSwipeListener;
import com.example.cay.youshi.R;
import com.example.cay.youshi.adapter.PlayerRecordAdapter;
import com.example.cay.youshi.base.BaseActivity;
import com.example.cay.youshi.bean.PlayeRecordBean;
import com.example.cay.youshi.databinding.ActivityLocalsVideoBinding;
import com.example.cay.youshi.utils.DpPxSpTransformUtil;

import org.litepal.crud.DataSupport;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;


public class PlayRecordCActivity extends BaseActivity<ActivityLocalsVideoBinding> {
    private static final String TAG = "Cay";
    private RecyclerView mRecyclerView;
    private OnItemSwipeListener onItemSwipeListener;
    private ItemDragAndSwipeCallback mItemDragAndSwipeCallback;
    private ItemTouchHelper mItemTouchHelper;
    private List<PlayeRecordBean> mList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_locals_video);
        setTitle("最近播放");
        LinearLayoutManager manager = new LinearLayoutManager(this);
        mRecyclerView = bindingView.rvLocals;
        mRecyclerView.setLayoutManager(manager);


        final Paint paint = new Paint();
        paint.setAntiAlias(true);
        paint.setTextSize(80);
        paint.setColor(Color.WHITE);
         onItemSwipeListener = new OnItemSwipeListener() {
            @Override
            public void onItemSwipeStart(RecyclerView.ViewHolder viewHolder, int pos) {
                BaseViewHolder holder = ((BaseViewHolder) viewHolder);
//                holder.setTextColor(R.id.tv, Color.WHITE);
            }

            @Override
            public void clearView(RecyclerView.ViewHolder viewHolder, int pos) {
                BaseViewHolder holder = ((BaseViewHolder) viewHolder);
//                holder.setTextColor(R.id.tv, Color.BLACK);
            }

            @Override
            public void onItemSwiped(RecyclerView.ViewHolder viewHolder, int pos) {
                BaseViewHolder holder = ((BaseViewHolder) viewHolder);
                TextView textView=holder.getView(R.id.tv_record_name);
                if (textView.getText().toString() != null) {
                    DataSupport.deleteAll(PlayeRecordBean.class, "name = ?", textView.getText().toString());

                }
            }

            @Override
            public void onItemSwipeMoving(Canvas canvas, RecyclerView.ViewHolder viewHolder, float dX, float dY, boolean isCurrentlyActive) {
                canvas.drawColor(ContextCompat.getColor(PlayRecordCActivity.this, R.color.colorAccent));
                DpPxSpTransformUtil.init(getResources().getDisplayMetrics().density);
                canvas.drawText("删除", 20, DpPxSpTransformUtil.dip2px(50), paint);

                // canvas.drawBitmap();
            }
        };
        Observable.create(new ObservableOnSubscribe<List<PlayeRecordBean>>() {
            @Override
            public void subscribe(ObservableEmitter<List<PlayeRecordBean>> e) throws Exception {
                List<PlayeRecordBean> aaa222 = DataSupport.order("id desc").find(PlayeRecordBean.class);
                e.onNext(aaa222);
            }
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<List<PlayeRecordBean>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(List<PlayeRecordBean> value) {
                        showContentView();
                        mList = value;
                        PlayerRecordAdapter playerRecordAdapter = new PlayerRecordAdapter(PlayRecordCActivity.this, R.layout.play_record_item, value);
                        mItemDragAndSwipeCallback = new ItemDragAndSwipeCallback(playerRecordAdapter);
                        mItemTouchHelper = new ItemTouchHelper(mItemDragAndSwipeCallback);
                        mItemTouchHelper.attachToRecyclerView(mRecyclerView);
                        playerRecordAdapter.enableSwipeItem();

                        playerRecordAdapter.setOnItemSwipeListener(onItemSwipeListener);
                        mRecyclerView.setAdapter(playerRecordAdapter);

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });

    }

}
