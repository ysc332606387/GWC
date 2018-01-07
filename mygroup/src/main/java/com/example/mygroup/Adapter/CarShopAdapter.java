package com.example.mygroup.Adapter;

import android.content.Context;
import android.net.Uri;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import com.bumptech.glide.Glide;
import com.example.mygroup.R;
import com.example.mygroup.bean.Car_child_shop_Bean;
import com.example.mygroup.bean.Car_shop_Bean;
import com.example.mygroup.customview.CarShopCheckedInterface;
import com.example.mygroup.customview.NumberAndPrice;
import com.example.mygroup.customview.ShopNumInterface;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 杨洪 on 2017/12/20.
 */

public class CarShopAdapter extends BaseExpandableListAdapter {
    private Context context;
    private ArrayList<Car_shop_Bean> list_shop;
    private ArrayList<List<Car_child_shop_Bean>> list_shop_child;
    private CarShopCheckedInterface carShopCheckedInterface;
    private ShopNumInterface shopNumInterface;
    private NumberAndPrice numberAndPrice;
    private TextView tv_shopName;
    private SimpleDraweeView iv_commodityImg;
    private TextView tv_commodityName;
    private TextView tv_commodityPrice;
    private Button btn_delete;
    private Button btn_add;
    private EditText et_num;
    private int shopNum = 0;
    private int allNum = 0;
    private int allPrice = 0;
    private Button btn_delete_only;

    public CarShopAdapter(Context context, ArrayList<Car_shop_Bean> list_shop, ArrayList<List<Car_child_shop_Bean>> list_shop_child) {
        this.context = context;
        this.list_shop = list_shop;
        this.list_shop_child = list_shop_child;
    }
    public void setCarShop(CarShopCheckedInterface carShopCheckedInterface){
        this.carShopCheckedInterface = carShopCheckedInterface;
    }
    public void setShopList(ShopNumInterface shopNumInterface){
        this.shopNumInterface = shopNumInterface;
    }
    public void setNumberAndPrice(NumberAndPrice numberAndPrice){
        this.numberAndPrice = numberAndPrice;
    }
    @Override
    public int getGroupCount() {
        return list_shop.size();
    }

    @Override
    public int getChildrenCount(int i) {
        return list_shop_child.get(i).size();
    }

    @Override
    public Object getGroup(int i) {
        return list_shop.get(i);
    }

    @Override
    public Object getChild(int i, int i1) {
        return list_shop_child.get(i).get(i1);
    }

    @Override
    public long getGroupId(int i) {
        return i;
    }

    @Override
    public long getChildId(int i, int i1) {
        return i1;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    @Override
    public View getGroupView(final int i, boolean b, View view, ViewGroup viewGroup) {
        final Car_shop_Bean car_shop_bean1 = list_shop.get(i);
        final List<Car_child_shop_Bean> car_child_shop_been = list_shop_child.get(i);
        view = View.inflate(context, R.layout.car_shop_one_fragment, null);
        final CheckBox rb_shop = (CheckBox)view.findViewById(R.id.rb_shop);
        tv_shopName = (TextView)view.findViewById(R.id.tv_shopName);
        rb_shop.setChecked(car_shop_bean1.getFlag());
        tv_shopName.setText(car_shop_bean1.getName());
        rb_shop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.e("---------点击:",rb_shop.isChecked()+"");
                list_shop.get(i).setFlag(rb_shop.isChecked());
                boolean checked = rb_shop.isChecked();
                car_shop_bean1.setFlag(checked);
                boolean a = true;
                for (int j = 0; j < list_shop.size(); j++) {
                    Boolean flag = list_shop.get(j).getFlag();
                    if(flag==false){
                        a = false;
                        carShopCheckedInterface.AllChecked(a);
                        break;
                    }else{
                        a = true;
                        carShopCheckedInterface.AllChecked(a);
                    }
                }
                for (int j = 0; j < car_child_shop_been.size(); j++) {
                    car_child_shop_been.get(j).setChildCheck(rb_shop.isChecked());
                }
                notifyDataSetChanged();
                for (Car_child_shop_Bean list_child:car_child_shop_been){
                    numberAndPrice.AllNumAndPriceClick(list_shop_child);
                }
            }
        });
        return view;
    }

    @Override
    public View getChildView(final int i, final int i1, final boolean b, View view, ViewGroup viewGroup) {
        final Car_shop_Bean car_shop_bean = list_shop.get(i);
        final Car_child_shop_Bean car_child_shop_bean = list_shop_child.get(i).get(i1);
        view = View.inflate(context, R.layout.car_shop_fragment, null);
        final CheckBox rb_commodity = (CheckBox)view.findViewById(R.id.rb_commodity);
        iv_commodityImg = (SimpleDraweeView)view.findViewById(R.id.iv_commodityImg);
        tv_commodityName = (TextView)view.findViewById(R.id.tv_commodityName);
        tv_commodityPrice = (TextView)view.findViewById(R.id.tv_commodityPrice);
        btn_delete = (Button)view.findViewById(R.id.btn_delete);
        btn_add = (Button)view.findViewById(R.id.btn_add);
        et_num = (EditText)view.findViewById(R.id.et_num);
        et_num.setText(car_child_shop_bean.getNum()+"");
        rb_commodity.setChecked(car_child_shop_bean.isChildCheck());
        tv_commodityName.setText(car_child_shop_bean.getTitle());



        //Glide.with(context).load(car_child_shop_bean.getImages()).into(iv_commodityImg);
        iv_commodityImg.setImageURI(Uri.parse(car_child_shop_bean.getImages()));


        tv_commodityPrice.setText("￥:"+car_child_shop_bean.getPrice());
        btn_delete.setText("—");
        btn_add.setText("+");
        notifyDataSetChanged();


        rb_commodity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                car_child_shop_bean.setChildCheck(rb_commodity.isChecked());
                boolean flag = true;
                for (int j = 0; j < list_shop_child.get(i).size(); j++) {
                    if(list_shop_child.get(i).get(j).isChildCheck()==false) {
                        flag = false;
                        break;
                    }
                }
                if(flag){
                    list_shop.get(i).setFlag(true);
                }else{
                    list_shop.get(i).setFlag(false);
                }
                boolean c = true;
                for (int j = 0; j < list_shop.size(); j++) {
                    if(list_shop.get(j).getFlag() == false){
                        c = false;
                        break;
                    }else{
                        c = true;
                    }
                }
                if(c){
                    carShopCheckedInterface.AllChecked(true);
                }else{
                    carShopCheckedInterface.AllChecked(false);
                }

                Log.e("-----取到的索引:",i1+"");
                numberAndPrice.AllNumAndPriceClick(list_shop_child);
                notifyDataSetChanged();
            }
        });

        btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean childCheck = car_child_shop_bean.isChildCheck();
                int num1 = car_child_shop_bean.getNum();
                num1++;
                car_child_shop_bean.setNum(num1);
                numberAndPrice.AllNumAndPriceClick(list_shop_child);
                notifyDataSetChanged();
            }
        });
        final boolean childCheck = car_child_shop_bean.isChildCheck();
        btn_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int num1 = car_child_shop_bean.getNum();
                num1--;
                if(num1<1){
                    car_child_shop_bean.setNum(1);
                    Toast.makeText(context,"不能再少了···",Toast.LENGTH_SHORT).show();
                }else{
                    car_child_shop_bean.setNum(num1);
                }
                numberAndPrice.AllNumAndPriceClick(list_shop_child);
                notifyDataSetChanged();
            }
        });
        return view;
    }

    @Override
    public boolean isChildSelectable(int i, int i1) {
        return true;
    }
}
