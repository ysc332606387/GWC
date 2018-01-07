package com.example.mygroup.presenter;

import com.example.mygroup.view.IView;
import com.example.mygroup.model.Model;

import java.util.Map;

import io.reactivex.Flowable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subscribers.DisposableSubscriber;

/**
 * Created by 杨洪 on 2018/1/5.
 */

public class Presenter implements BasePresenter {

    private IView iv;
    private DisposableSubscriber subscriber;
    Model model;
    public Presenter(IView iv) {
        this.iv = iv;
    }

    @Override
    public void get(String baseurl,Map<String, String> map) {
        model = new Model(this);
        model.get(baseurl,map);
    }

    @Override
    public void getdel(String baseurl,Map<String, String> map) {
        model = new Model(this);
        model.getdel(baseurl,map);
    }

  public void getData(Flowable flowable){
        subscriber = (DisposableSubscriber) flowable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSubscriber() {
                    @Override
                    public void onNext(Object o) {
                        iv.onSuccess(o);
                    }

                    @Override
                    public void onError(Throwable t) {
                        iv.onFailed((Exception) t);
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    //防止内存泄漏
    public void detatch(){
        if (iv != null) {
            iv = null;
        }
        if(subscriber!=null){
            if(!subscriber.isDisposed()){
                subscriber.dispose();
            }
        }
    }

}
