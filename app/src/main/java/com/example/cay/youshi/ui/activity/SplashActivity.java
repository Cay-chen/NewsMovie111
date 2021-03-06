package com.example.cay.youshi.ui.activity;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;

import com.example.cay.youshi.MainActivity;
import com.example.cay.youshi.R;
import com.example.cay.youshi.databinding.ActivitySplashBinding;
import com.example.cay.youshi.utils.CommonUtils;
import com.example.cay.youshi.utils.PerfectClickListener;

import java.util.Random;

public class SplashActivity extends AppCompatActivity {
    private ActivitySplashBinding mBinding;
    private boolean animationEnd;
    private int[] mDrawables = new int[]{R.drawable.b_1, R.drawable.b_2,
            R.drawable.b_3, R.drawable.b_4, R.drawable.b_5, R.drawable.b_6,
    };
    private boolean isIn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_splash);

        int i = new Random().nextInt(mDrawables.length);
//        Glide.with(this).load(mDrawables[i]).into(mBinding.ivPic);

        mBinding.ivPic.setImageDrawable(CommonUtils.getDrawable(mDrawables[i]));

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                toMainActivity();
            }
        }, 2000);

//        Animation animation = AnimationUtils.loadAnimation(this, R.anim.transition_anim);
//        animation.setAnimationListener(animationListener);
//        mBinding.ivPic.startAnimation(animation);

        mBinding.tvJump.setOnClickListener(new PerfectClickListener() {
            @Override
            protected void onNoDoubleClick(View v) {
                toMainActivity();
//                animationEnd();
            }
        });
    }

    /**
     * 实现监听跳转效果
     */
    private Animation.AnimationListener animationListener = new Animation.AnimationListener() {
        @Override
        public void onAnimationEnd(Animation animation) {
            animationEnd();
        }

        @Override
        public void onAnimationStart(Animation animation) {
        }

        @Override
        public void onAnimationRepeat(Animation animation) {
        }
    };


    private void animationEnd() {
        synchronized (SplashActivity.this) {
            if (!animationEnd) {
                animationEnd = true;
                mBinding.ivPic.clearAnimation();
                toMainActivity();
            }
        }
    }

    private void toMainActivity() {
        if (isIn) {
            return;
        }
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        overridePendingTransition(R.anim.screen_zoom_in, R.anim.screen_zoom_out);
        finish();
        isIn = true;
    }
}
