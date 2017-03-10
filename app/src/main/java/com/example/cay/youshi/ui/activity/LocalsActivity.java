package com.example.cay.youshi.ui.activity;

import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;

import com.example.cay.youshi.R;
import com.example.cay.youshi.adapter.LocalAdapter;
import com.example.cay.youshi.base.BaseActivity;
import com.example.cay.youshi.bean.BitmapEntity;
import com.example.cay.youshi.databinding.ActivityLocalsVideoBinding;

import java.util.ArrayList;
import java.util.List;

import fm.jiecao.jcvideoplayer_lib.JCVideoPlayer;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;


public class LocalsActivity extends BaseActivity<ActivityLocalsVideoBinding> {
    private View mView;
    private LocalAdapter localAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_locals_video);
        setTitle("本地视频");
        LinearLayoutManager manager = new LinearLayoutManager(this);
        bindingView.rvLocals.setLayoutManager(manager);
        mView = LayoutInflater.from(this).inflate(R.layout.empty_view, null, false);
        showContentView();
       Observable.create(new ObservableOnSubscribe<List<BitmapEntity>>() {
            @Override
            public void subscribe(ObservableEmitter<List<BitmapEntity>> e) throws Exception {
                e.onNext(Search_photo());
            }
        }).subscribeOn(Schedulers.io())
          .observeOn(AndroidSchedulers.mainThread())
           .subscribe(new Observer<List<BitmapEntity>>() {
                   @Override
                   public void onSubscribe(Disposable d) {

                   }

                   @Override
                   public void onNext(List<BitmapEntity> value) {
                        localAdapter = new LocalAdapter(LocalsActivity.this, R.layout.local_rv_item, value);
                       bindingView.rvLocals.setAdapter(localAdapter);
                       if (value.size() == 0) {
                           localAdapter.setEmptyView(mView);
                       }

                   }

                   @Override
                   public void onError(Throwable e) {

                   }

                   @Override
                   public void onComplete() {

                   }
               });



    }
    public List<BitmapEntity> Search_photo() {
        List<BitmapEntity> list = new ArrayList<>();
        if (android.os.Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            Uri originalUri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
            ContentResolver cr = LocalsActivity.this.getApplicationContext().getContentResolver();
            Cursor cursor = cr.query(originalUri, null, null, null, null);
           /* if (cursor == null) {
                return;
            }*/

            for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()) {
                String title = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Video.Media.TITLE));
                String path = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Video.Media.DATA));
                long size = cursor.getLong(cursor.getColumnIndexOrThrow(MediaStore.Video.Media.SIZE));
                long duration = cursor.getInt(cursor.getColumnIndexOrThrow(MediaStore.Video.Media.DURATION));
                //获取当前Video对应的Id，然后根据该ID获取其缩略图的uri
                int id = cursor.getInt(cursor.getColumnIndexOrThrow(MediaStore.Video.Media._ID));
                String[] selectionArgs = new String[] { id + "" };
                String[] thumbColumns = new String[] { MediaStore.Video.Thumbnails.DATA,
                        MediaStore.Video.Thumbnails.VIDEO_ID };
                String selection = MediaStore.Video.Thumbnails.VIDEO_ID + "=?";

                String uri_thumb = "";
                Cursor thumbCursor = (LocalsActivity.this.getApplicationContext().getContentResolver()).query(
                        MediaStore.Video.Thumbnails.EXTERNAL_CONTENT_URI, thumbColumns, selection, selectionArgs,
                        null);

                if (thumbCursor != null && thumbCursor.moveToFirst()) {
                    uri_thumb = thumbCursor
                            .getString(thumbCursor.getColumnIndexOrThrow(MediaStore.Video.Thumbnails.DATA));

                }

                BitmapEntity bitmapEntity = new BitmapEntity(title, path, size, uri_thumb, duration);

                list.add(bitmapEntity);

            }
            if (cursor != null) {
                cursor.close();
                //TODO
                //  return list;
            }
        }
        return list;

    }
    public static void start(Context mContext) {
        Intent intent = new Intent(mContext, LocalsActivity.class);
        mContext.startActivity(intent);
    }
    @Override
    public void onBackPressed() {
        if (JCVideoPlayer.backPress()) {
            return;
        }
        super.onBackPressed();
    }
    @Override
    protected void onPause() {
        super.onPause();
        JCVideoPlayer.releaseAllVideos();

    }
    @Override
    protected void onRestart() {
        super.onRestart();
      /*  if (JCMediaManager.instance().mediaPlayer!=null) {
            JCMediaManager.instance().mediaPlayer.start();
        }*/
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        JCVideoPlayer.releaseAllVideos();

    }
}
