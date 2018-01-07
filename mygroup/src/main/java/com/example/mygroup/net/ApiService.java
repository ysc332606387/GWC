package com.example.mygroup.net;

import com.example.mygroup.bean.GwcBean;

import java.util.Map;

import io.reactivex.Flowable;
import retrofit2.http.GET;
import retrofit2.http.QueryMap;

/**
 * Created by 杨洪 on 2018/1/5.
 */
public interface ApiService {
    //http://120.27.23.105/product/getProductCatagory?cid=1
    @GET("product/getCarts")//?uid=3416&source=android//?source=android
    Flowable<GwcBean> get(@QueryMap Map<String,String> map);


    @GET("product/deleteCart")//?uid=3416&source=android//?source=android
    Flowable<GwcBean> getdel(@QueryMap Map<String,String> map);


}
