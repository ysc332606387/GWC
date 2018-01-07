package com.example.mygroup.customview;


import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.mygroup.R;

/**
 * Created by 杨洪 on 2017/12/20.
 */

public class CarTitleView extends LinearLayout implements View.OnClickListener{

    private CarTitleInterface carTitleInterface;
    private ImageView title_address;
    private TextView tv_compile;
    private ImageView iv_message;

    public void setCarTitleView(CarTitleInterface carTitleInterface){
        this.carTitleInterface = carTitleInterface;
    }

    public CarTitleView(Context context) {
        this(context,null);
    }

    public CarTitleView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public CarTitleView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        View.inflate(context, R.layout.car_title_fragment,this);
        initView();
    }

    private void initView() {
        title_address = (ImageView)findViewById(R.id.title_address);
        tv_compile = (TextView)findViewById(R.id.tv_compile);
        iv_message = (ImageView)findViewById(R.id.iv_message);
        title_address.setOnClickListener(this);
        tv_compile.setOnClickListener(this);
        iv_message.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.title_address:
                carTitleInterface.addressClick(view);
                break;
            case R.id.tv_compile:
                carTitleInterface.compileClick(view);
                break;
            case R.id.iv_message:
               carTitleInterface.messageClick(view);
                break;
        }
    }
}
