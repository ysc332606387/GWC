package com.example.mygroup.net;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by 杨洪 on 2018/1/5.
 */

public class RetrofitUtils {
    private static volatile RetrofitUtils instance;
    private final Retrofit retrofit;

    private RetrofitUtils(String baseurl) {

        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(new Logger())
                .build();

        retrofit = new Retrofit.Builder()
                .baseUrl(baseurl)
               .client(client)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    public static RetrofitUtils getInstance(String baseurl) {
        if (instance == null) {
            synchronized (RetrofitUtils.class) {
                if (instance == null) {
                    instance = new RetrofitUtils(baseurl);
                }
            }
        }
        return instance;
    }

    public Retrofit getretrofit(){
        return retrofit;
    }

}
