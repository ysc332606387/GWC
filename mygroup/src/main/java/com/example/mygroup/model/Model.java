package com.example.mygroup.model;


import com.example.mygroup.bean.GwcBean;
import com.example.mygroup.net.ApiService;
import com.example.mygroup.presenter.Presenter;
import com.example.mygroup.net.RetrofitUtils;

import java.util.Map;

import io.reactivex.Flowable;

/**
 * Created by 杨洪 on 2018/1/5.
 */

public class Model implements IModel {

    private Presenter presenter;

    public Model(Presenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public void get(String baseurl, Map<String, String> map) {
        Flowable<GwcBean> flowable = RetrofitUtils.getInstance(baseurl).getretrofit().create(ApiService.class).get(map);
        presenter.getData(flowable);
    }
//getdel
@Override
    public void getdel(String baseurl, Map<String, String> map) {
    Flowable<GwcBean> flowable = RetrofitUtils.getInstance(baseurl).getretrofit().create(ApiService.class).getdel(map);
    presenter.getData(flowable);
}

}
