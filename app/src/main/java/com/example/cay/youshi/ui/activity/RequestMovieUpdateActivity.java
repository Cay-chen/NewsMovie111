package com.example.cay.youshi.ui.activity;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.cay.youshi.R;
import com.example.cay.youshi.base.BaseActivity;
import com.example.cay.youshi.bean.UpDdtaBackBean;
import com.example.cay.youshi.databinding.ActivityRequestMovieBinding;
import com.example.cay.youshi.http.HttpUtils;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class RequestMovieUpdateActivity extends BaseActivity<ActivityRequestMovieBinding> {
    private EditText mEditText;
    private TextView mContent;
    private TextView mQQ;
    private TextView mWeixin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request_movie);
        // showLoading();
        showContentView();
        setTitle("求片专区");
        mContent = (TextView) findViewById(R.id.tv_shuoming);
        mQQ = (TextView) findViewById(R.id.tv_qqq);
        mWeixin = (TextView) findViewById(R.id.tv_weixin);
        mEditText = (EditText) findViewById(R.id.et_get_movie_name);
        SpannableString qq = new SpannableString("QQ群：377183104 (推荐)点击QQ号码添加群");
        qq.setSpan(new RelativeSizeSpan(0.7f), 18, 27, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);  //0.5f表示默认字体大小的一半
        qq.setSpan(new ForegroundColorSpan(Color.RED),15,17, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        qq.setSpan(new ClickableSpan() {
            @Override
            public void onClick(View widget) {
                joinQQGroup("SXx-Ksn5VrXYPQVHRYueo5k7OpDubPbb");
            }
        },4,13,Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        mQQ.setMovementMethod(LinkMovementMethod.getInstance());
        mQQ.setText(qq);
        Button mButton = (Button) findViewById(R.id.btn_movie_submit);
        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                if (mEditText.getText().toString().trim().isEmpty()) {
                    Toast.makeText(RequestMovieUpdateActivity.this, "请输入电影名或电视名", Toast.LENGTH_SHORT).show();
                } else {
                    HttpUtils.getInstance().getYouShiData(false).updateAndInvail("1",mEditText.getText().toString().trim())
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(new Observer<UpDdtaBackBean>() {
                                @Override
                                public void onSubscribe(Disposable d) {
                                }

                                @Override
                                public void onNext(UpDdtaBackBean value) {
                                    if (value.getResMsg().equals("1")) {
                                        Toast.makeText(RequestMovieUpdateActivity.this, "提交成功，我们将会尽快更新", Toast.LENGTH_LONG).show();
                                        mEditText.setText("");
                                    } else {
                                        Toast.makeText(RequestMovieUpdateActivity.this, "提交失败", Toast.LENGTH_LONG).show();

                                    }
                                }

                                @Override
                                public void onError(Throwable e) {
                                    Toast.makeText(RequestMovieUpdateActivity.this, "提交失败", Toast.LENGTH_LONG).show();
                                }

                                @Override
                                public void onComplete() {

                                }
                            });
                }
            }
        });
    }

    /****************
     * 发起添加群流程。群号：425465(377183104) 的 key 为： SXx-Ksn5VrXYPQVHRYueo5k7OpDubPbb
     * 调用 joinQQGroup(SXx-Ksn5VrXYPQVHRYueo5k7OpDubPbb) 即可发起手Q客户端申请加群 425465(377183104)
     * @param key 由官网生成的key
     * @return 返回true表示呼起手Q成功，返回fals表示呼起失败
     ******************/
    public boolean joinQQGroup(String key) {
        Intent intent = new Intent();
        intent.setData(Uri.parse("mqqopensdkapi://bizAgent/qm/qr?url=http%3A%2F%2Fqm.qq.com%2Fcgi-bin%2Fqm%2Fqr%3Ffrom%3Dapp%26p%3Dandroid%26k%3D" + key));
        // 此Flag可根据具体产品需要自定义，如设置，则在加群界面按返回，返回手Q主界面，不设置，按返回会返回到呼起产品界面    //intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        try {
            startActivity(intent);
            return true;
        } catch (Exception e) {
            // 未安装手Q或安装的版本不支持
            Toast.makeText(RequestMovieUpdateActivity.this, "未安装手Q或安装的版本不支持", Toast.LENGTH_SHORT).show();
            return false;
        }
    }


}
