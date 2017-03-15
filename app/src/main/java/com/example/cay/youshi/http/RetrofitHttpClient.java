package com.example.cay.youshi.http;

import com.example.cay.youshi.bean.UpDdtaBackBean;
import com.example.cay.youshi.bean.VersionUpdataBean;
import com.example.cay.youshi.bean.YouShiFirstDataBean;
import com.example.cay.youshi.bean.YouShiMovieDealisBean;
import com.example.cay.youshi.bean.YouShiSingleLookupResultBean;
import com.example.cay.youshi.bean.YouShiTodayBackResultBean;
import com.example.cay.youshi.bean.YouShiTopbarResultBean;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * 请求数据接口
 * Created by Cay on 2017/2/23.
 */

public interface RetrofitHttpClient {

    /**
     * 上传登录统计
     *
     * @return 等等
     */
    @POST("VMovie/ServerCountLogin")
    Observable<UpDdtaBackBean> upCountLogin();


    /**
     * 版本更新检测
     * @return
     */
    @GET("VMovie/VersionUpdataServer")
    Observable<List<VersionUpdataBean>> verJianCe();

    /**
     * 获取首页数据
     * @return xx
     */
    @GET("/YouShiServer/FirstData")
    Observable<YouShiFirstDataBean> getYouShiFirstData();

    /**
     * 获取电影详情
     * @param id s
     * @return s
     */
    @GET("/YouShiServer/MovieDetail")
    Observable<YouShiMovieDealisBean> getYouShiMovieDetail(@Query("id") String id);

    /**
     * 优视 单个条件模糊查询
     * @param type 类型
     * @param value 类型值
     * @param position 起始位置
     * @param num  查询 数量
     * @return 返回类型
     */
    @GET("/YouShiServer/SingleVagueLookup")
    Observable<YouShiSingleLookupResultBean> singleLookupResult(@Query("type") String type, @Query("value") String value, @Query("position") String position, @Query("num") String num);

    /**
     * 优视 单个条件准确查询
     * @param type 类型
     * @param position 起始位置
     * @param num  查询 数量
     * @return 返回类型
     */
    @GET("/YouShiServer/SingleLookupData")
    Observable<YouShiSingleLookupResultBean> oneLookupResult(@Query("type") String type, @Query("position") String position, @Query("num") String num);

    /**
     * 优视 单个条件准确查询
     * @param type 类型
     * @return 返回类型
     */
    @GET("/YouShiServer/movieTopBarData")
    Observable<YouShiTopbarResultBean> getTopbar(@Query("type") String type);

    /**
     * 优视 获取今天更新数据  只能回去7天的
     * @return 返回类型
     */
    @GET("/YouShiServer/ToadyUpdate")
    Observable<YouShiTodayBackResultBean> getToday();
    /**
     * 优视 获取今天更新数据  只能回去7天的
     * @return 返回类型
     *  type = 0表示链接失效  1表示更新电视
     *
     */
    @GET("/YouShiServer/UpUpdateAndInvail")
    Observable<UpDdtaBackBean> updateAndInvail(@Query("type") String type,@Query("issue") String name);
}

