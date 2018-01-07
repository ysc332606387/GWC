package com.example.mygroup.bean;

/**
 * Created by 杨洪 on 2017/12/20.
 */

public class Car_shop_Bean {
    private String name;
    private boolean flag;

    public Car_shop_Bean(String name, Boolean flag) {
        this.name = name;
        this.flag = flag;
    }



    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Boolean getFlag() {
        return flag;
    }

    public void setFlag(Boolean flag) {
        this.flag = flag;
    }
}
