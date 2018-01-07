package com.example.mygroup.bean;

/**
 * Created by 杨洪 on 2017/12/20.
 */

public class Car_child_shop_Bean {
    private int pid;
    private String title;
    private int num;
    private double price;
    private String images;
    private boolean childCheck;

    public Car_child_shop_Bean(int pid, String title, int num, double price, String images, boolean childCheck) {
        this.pid = pid;
        this.title = title;
        this.num = num;
        this.price = price;
        this.images = images;
        this.childCheck = childCheck;
    }

    public int getPid() {
        return pid;
    }

    public void setPid(int pid) {
        this.pid = pid;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getImages() {
        return images;
    }

    public void setImages(String images) {
        this.images = images;
    }

    public boolean isChildCheck() {
        return childCheck;
    }

    public void setChildCheck(boolean childCheck) {
        this.childCheck = childCheck;
    }
}
