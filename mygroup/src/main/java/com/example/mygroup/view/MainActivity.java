package com.example.mygroup.view;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ExpandableListView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.mygroup.Adapter.CarShopAdapter;
import com.example.mygroup.R;
import com.example.mygroup.bean.Car_child_shop_Bean;
import com.example.mygroup.bean.Car_shop_Bean;
import com.example.mygroup.bean.GwcBean;
import com.example.mygroup.customview.CarShopCheckedInterface;
import com.example.mygroup.customview.CarTitleInterface;
import com.example.mygroup.customview.CarTitleView;
import com.example.mygroup.customview.NumberAndPrice;
import com.example.mygroup.presenter.Presenter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class MainActivity extends AppCompatActivity implements IView, View.OnClickListener {

    private Presenter presenter;
    private CarTitleView ctv_title;
    private ExpandableListView pt_lv;
    private Button btn_toBuy;
    private LinearLayout ll_toBuy;
    private CheckBox cb_all;
    private TextView tv_rental;
    private TextView tv_num;
    private Button btn_goto;
    private LinearLayout lll_one;
    private CheckBox cb_alldel;
    private Button btn_delete_all;
    private LinearLayout l_two;


    private ArrayList<Car_shop_Bean> list_shop;
    private ArrayList<List<Car_child_shop_Bean>> list_shop_chiled;
    private CarShopAdapter carShopAdapter;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        initView();
        initTitle();


        //http://120.27.23.105/product/getProductCatagory?cid=1

    }

    @Override
    public void onSuccess(Object o) {






        if (o != null) {
            GwcBean gwcBean= (GwcBean) o;
            List<GwcBean.DataBean> data = gwcBean.getData();
            if (data != null) {
                list_shop = new ArrayList<Car_shop_Bean>();
                list_shop_chiled = new ArrayList<List<Car_child_shop_Bean>>();
                //设置选择状态
                for (int i = 0; i < data.size(); i++) {
                    String sellerName = data.get(i).getSellerName();
                    list_shop.add(new Car_shop_Bean(sellerName, false));

                    List<GwcBean.DataBean.ListBean> list = data.get(i).getList();

                    ArrayList<Car_child_shop_Bean> car_shop_been = new ArrayList<>();

                    for (GwcBean.DataBean.ListBean childList : list) {
                        int num = childList.getNum();
                        double price = childList.getPrice();
                        String images = childList.getImages();
                        String title = childList.getTitle();
                        int pid = childList.getPid();
                        String[] split = images.toString().split("\\|");
                        car_shop_been.add(new Car_child_shop_Bean(pid, title, num, price, split[0], false));

                    }
                    list_shop_chiled.add(car_shop_been);
                }
                //initAllData(list_shop);
                //设置适配器
                carShopAdapter = new CarShopAdapter(this, list_shop, list_shop_chiled);

                cb_all.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        boolean checked = cb_all.isChecked();
                        for (int i = 0; i < list_shop.size(); i++) {
                            list_shop.get(i).setFlag(checked);
                        }

                        for (List<Car_child_shop_Bean> i : list_shop_chiled) {
                            for (int j = 0; j < i.size(); j++) {
                                i.get(j).setChildCheck(checked);
                            }
                        }
                        carShopAdapter.notifyDataSetChanged();
                        foreach(list_shop_chiled);
                    }
                });


                cb_alldel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        boolean checked = cb_alldel.isChecked();
                        for (int i = 0; i < list_shop.size(); i++) {
                            list_shop.get(i).setFlag(checked);
                        }

                        for (List<Car_child_shop_Bean> i : list_shop_chiled) {
                            for (int j = 0; j < i.size(); j++) {
                                i.get(j).setChildCheck(checked);
                            }
                        }
                        carShopAdapter.notifyDataSetChanged();
                    }
                });


                carShopAdapter.setCarShop(new CarShopCheckedInterface() {
                    @Override
                    public void AllChecked(boolean ischeck) {
                        if (!ischeck) {
                            cb_all.setChecked(false);
                            cb_alldel.setChecked(false);
                        } else {
                            cb_all.setChecked(true);
                            cb_alldel.setChecked(true);
                        }
                    }
                });

                carShopAdapter.setNumberAndPrice(new NumberAndPrice() {
                    @Override
                    public void AllNumAndPriceClick(final ArrayList<List<Car_child_shop_Bean>> list_shop_child) {
                        int max = 0;
                        int nums = 0;
                        for (List<Car_child_shop_Bean> list_child : list_shop_child) {
                            for (int i = 0; i < list_child.size(); i++) {
                                boolean childCheck = list_child.get(i).isChildCheck();
                                Car_child_shop_Bean car_child_shop_bean = list_child.get(i);
                                if (childCheck) {
                                    int num = car_child_shop_bean.getNum();
                                    double price = car_child_shop_bean.getPrice();
                                    int prices = (int) price;
                                    max += prices * num;
                                    nums++;
                                }
                            }
                        }
                        tv_rental.setText("合计:￥" + max);
                        tv_num.setText("数量:" + nums);
                    }
                });

                pt_lv.setAdapter(carShopAdapter);
                //让他默认收回
                int count = pt_lv.getCount();
                for (int i = 0; i < count; i++) {
                    pt_lv.expandGroup(i);
                }
                pt_lv.setGroupIndicator(null);


                //全选点击事件
                cb_alldel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        boolean checked = cb_alldel.isChecked();
                        for (int i = 0; i < list_shop.size(); i++) {
                            list_shop.get(i).setFlag(checked);
                        }

                        for (List<Car_child_shop_Bean> i : list_shop_chiled) {
                            for (int j = 0; j < i.size(); j++) {
                                i.get(j).setChildCheck(checked);
                            }
                        }
                        carShopAdapter.notifyDataSetChanged();
                    }
                });


                carShopAdapter.setCarShop(new CarShopCheckedInterface() {
                    @Override
                    public void AllChecked(boolean ischeck) {
                        if (!ischeck) {
                            cb_all.setChecked(false);
                            cb_alldel.setChecked(false);
                        } else {
                            cb_all.setChecked(true);
                            cb_alldel.setChecked(true);
                        }
                    }
                });

                carShopAdapter.setNumberAndPrice(new NumberAndPrice() {
                    @Override
                    public void AllNumAndPriceClick(final ArrayList<List<Car_child_shop_Bean>> list_shop_child) {
                        int max = 0;
                        int nums = 0;
                        for (List<Car_child_shop_Bean> list_child : list_shop_child) {
                            for (int i = 0; i < list_child.size(); i++) {
                                boolean childCheck = list_child.get(i).isChildCheck();
                                Car_child_shop_Bean car_child_shop_bean = list_child.get(i);
                                if (childCheck) {
                                    int num = car_child_shop_bean.getNum();
                                    double price = car_child_shop_bean.getPrice();
                                    int prices = (int) price;
                                    max += prices * num;
                                    nums++;
                                }
                            }
                        }
                        tv_rental.setText("合计:￥" + max);
                        tv_num.setText("数量:" + nums);
                    }
                });

                pt_lv.setAdapter(carShopAdapter);
                //让他默认收回
                int count1 = pt_lv.getCount();
                for (int i = 0; i < count1; i++) {
                    pt_lv.expandGroup(i);
                }
                pt_lv.setGroupIndicator(null);

            }
        }












    }

    @Override
    public void onFailed(Exception e) {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.detatch();
    }

    private void initView() {
        ctv_title = (CarTitleView) findViewById(R.id.ctv_title);
        pt_lv = (ExpandableListView) findViewById(R.id.pt_lv);
        btn_toBuy = (Button) findViewById(R.id.btn_toBuy);
        ll_toBuy = (LinearLayout) findViewById(R.id.ll_toBuy);
        cb_all = (CheckBox) findViewById(R.id.cb_all);
        tv_rental = (TextView) findViewById(R.id.tv_rental);
        tv_num = (TextView) findViewById(R.id.tv_num);
        btn_goto = (Button) findViewById(R.id.btn_goto);
        lll_one = (LinearLayout) findViewById(R.id.lll_one);
        cb_alldel = (CheckBox) findViewById(R.id.cb_alldel);
        btn_delete_all = (Button) findViewById(R.id.btn_delete_all);
        l_two = (LinearLayout) findViewById(R.id.l_two);

        btn_toBuy.setOnClickListener(this);
        btn_goto.setOnClickListener(this);
        btn_delete_all.setOnClickListener(this);
    }





    //头布局的点击事件
    private void initTitle() {
        ctv_title.setCarTitleView(new CarTitleInterface() {
            boolean b = true;
            public TextView tv_compile;

            @Override
            public void addressClick(View v) {

            }

            //点击编辑----
            @Override
            public void compileClick(View v) {
                b = !b;
                tv_compile = (TextView) v.findViewById(R.id.tv_compile);
                if (b) {
                    tv_compile.setText("编辑");
                    lll_one.setVisibility(View.VISIBLE);
                    l_two.setVisibility(View.GONE);
                    carShopAdapter.notifyDataSetChanged();

                } else {
                    tv_compile.setText("完成");
                    lll_one.setVisibility(View.GONE);
                    l_two.setVisibility(View.VISIBLE);
                    carShopAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void messageClick(View v) {

            }
        });
    }






    //计算总价和数量s
    public void foreach(ArrayList<List<Car_child_shop_Bean>> list_shop_child) {
        carShopAdapter.notifyDataSetChanged();
        int max = 0;
        int nums = 0;
        for (List<Car_child_shop_Bean> list_child : list_shop_child) {
            for (int i = 0; i < list_child.size(); i++) {
                boolean childCheck = list_child.get(i).isChildCheck();
                Car_child_shop_Bean car_child_shop_bean = list_child.get(i);
                if (childCheck) {
                    int num = car_child_shop_bean.getNum();
                    double price = car_child_shop_bean.getPrice();
                    int prices = (int) price;
                    max += prices * num;
                    nums++;
                }
            }
        }
        tv_rental.setText("合计:￥" + max);
        tv_num.setText("数量:" + nums);
    }





    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_toBuy:

                break;
            case R.id.btn_goto:

                break;
            case R.id.btn_delete_all:

                ArrayList<Car_shop_Bean> list_new_shop = new ArrayList<Car_shop_Bean>();
                ArrayList<List<Car_child_shop_Bean>> list_new_child = new ArrayList<List<Car_child_shop_Bean>>();
                for (int i = 0; i < list_shop.size(); i++) {
                    Car_shop_Bean car_shop_bean = list_shop.get(i);
                    List<Car_child_shop_Bean> car_child_shop_been = list_shop_chiled.get(i);
                    Boolean flag = list_shop.get(i).getFlag();
                    if (flag) {
                        list_new_shop.add(car_shop_bean);
                        list_new_child.add(car_child_shop_been);
                    }
                    ArrayList<Car_child_shop_Bean> list_child_new = new ArrayList<Car_child_shop_Bean>();
                    for (int j = 0; j < list_shop_chiled.get(i).size(); j++) {
                        Car_child_shop_Bean car_child_shop_bean = list_shop_chiled.get(i).get(j);
                        boolean childCheck = list_shop_chiled.get(i).get(j).isChildCheck();
                        if (childCheck) {
                            list_child_new.add(car_child_shop_bean);
                            int pid = car_child_shop_bean.getPid();
                            HashMap<String, String> map = new HashMap<String, String>();
                            map.put("pid", pid + "");
                            // String uid = MyApp.yhsp.getString("uid", "");
                            map.put("uid", "" + 3416);
                            // Persent persent = new Persent(getActivity());
                            //persent.getData(map, "http://120.27.23.105/product/", 1);
                            //：https://www.zhaoapi.cn/?uid=72&pid=1
                             presenter.getdel("https://www.zhaoapi.cn/", map);

                        }
                    }
                    list_shop_chiled.get(i).removeAll(list_child_new);
                }
                tv_rental.setText("合计:￥" + 0);
                tv_num.setText("数量:" + 0);
                list_shop.removeAll(list_new_shop);
                list_shop_chiled.removeAll(list_new_child);
                carShopAdapter.notifyDataSetChanged();
                //  initAllData(list_shop);

                break;
        }
    }


    @Override
    public void onResume() {
        super.onResume();
        initSelCar();

    }

    private void initSelCar() {

        presenter = new Presenter(this);
        Map<String, String> map = new HashMap<>();
        map.put("uid", "71");
        presenter.get("https://www.zhaoapi.cn/", map);

    }


}
