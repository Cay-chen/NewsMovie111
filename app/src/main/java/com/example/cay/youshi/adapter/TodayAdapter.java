package com.example.cay.youshi.adapter;

import android.content.Context;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.cay.youshi.R;
import com.example.cay.youshi.bean.YouShiTodayUpdateBean;

import java.util.Calendar;
import java.util.List;

/**
 * Created by Cay on 2017/3/15.
 */

public class TodayAdapter extends BaseQuickAdapter<YouShiTodayUpdateBean,BaseViewHolder> {
    private Context context;
    public TodayAdapter(Context context,int layoutResId, List<YouShiTodayUpdateBean> data) {
        super(layoutResId, data);
        this.context = context;
    }

    @Override
    protected void convert(BaseViewHolder helper, YouShiTodayUpdateBean item) {
        // 取得系统日期:
        Calendar c = Calendar.getInstance();
        final int month = c.get(Calendar.MONTH)+1;
        final int day = c.get(Calendar.DAY_OF_MONTH);
        String date = month+"月"+day+"日";
        if (item.getDate().trim().equals(date)) {
            helper.setText(R.id.tv_today_update_date, "今日更新")
                    .setTextColor(R.id.tv_today_update_date,context.getResources().getColor(R.color.colorTheme));
        } else {
            helper.setText(R.id.tv_today_update_date, item.getDate().trim()+"更新");
        }
        if (!item.getCha_tv().trim().equals("")) {
            helper.setText(R.id.et_cha, item.getCha_tv().trim())
                    .setVisible(R.id.ll_today_update_date_chatv, true);
        }
        if (!item.getMovie().trim().equals("")) {
            helper.setText(R.id.et_movie, item.getMovie().trim())
                    .setVisible(R.id.ll_today_update_date_movie, true);
        }
        if (!item.getRihan_tv().trim().equals("")) {
            helper.setText(R.id.et_rihan, item.getRihan_tv().trim())
                    .setVisible(R.id.ll_today_update_date_rihangtv, true);
        }
        if (!item.getUsa_tv().trim().equals("")) {
            helper.setText(R.id.et_usa, item.getUsa_tv().trim())
                    .setVisible(R.id.ll_today_update_date_usatv, true);
        }
    }
}
