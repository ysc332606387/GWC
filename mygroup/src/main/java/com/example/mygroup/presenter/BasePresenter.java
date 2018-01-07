package com.example.mygroup.presenter;

import java.util.Map;

/**
 * Created by 杨洪 on 2018/1/5.
 */

public interface BasePresenter {
    void get(String baseurl,Map<String,String> map);

    void getdel(String baseurl,Map<String,String> map);
}
