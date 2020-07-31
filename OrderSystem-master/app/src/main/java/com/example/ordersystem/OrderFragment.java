package com.example.ordersystem;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.ordersystem.adapter.OrderAdapter;
import com.example.ordersystem.entity.Data;
import com.example.ordersystem.entity.Food;
import com.example.ordersystem.entity.Order;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class OrderFragment extends Fragment {

    private RecyclerView recyclerView;
    private static OrderAdapter orderAdapter;
    private static List<Order> orderList = listOrder();

    public OrderFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_order, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerView = view.findViewById(R.id.frag_order_recyclerview);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager); // 指定一个默认的布局管理器
        recyclerView.addItemDecoration(new DividerItemDecoration(getActivity(),LinearLayoutManager.VERTICAL));// 指定item分割线

        orderAdapter = new OrderAdapter(orderList);
        recyclerView.setAdapter(orderAdapter);
    }

//    public static void refresh() {
//        orderList.add(new Order());
//        Log.i("why","???");
//        orderAdapter.notifyDataSetChanged();
//        Log.i("why","!!!");
//
//    }

    private static List<Order> listOrder() {
        List<Food> foods = new ArrayList<>();
        Food f1 = new Food(1, "山芋圆子    ", "非常好吃 非常好吃\n非常好吃 非常好吃\n", 20);
        Food f2 = new Food(2, "红烧肉        ", "非常好吃 非常好吃\n非常好吃 非常好吃\n", 30);
        Food f3 = new Food(3, "炒面            ", "非常好吃 非常好吃\n非常好吃 非常好吃\n", 18);
        Food f4 = new Food(4, "牛肉拉面    ", "非常好吃 非常好吃\n非常好吃 非常好吃\n", 10);
        Food f5 = new Food(5, "大盘鸡        ", "非常好吃 非常好吃\n非常好吃 非常好吃\n", 25);
        Food f6 = new Food(6, "番茄鸡蛋汤", "非常好吃 非常好吃\n非常好吃 非常好吃\n", 15);
        foods.add(f1); foods.add(f2); foods.add(f3); foods.add(f4);foods.add(f5); foods.add(f6);
        Order o1 = new Order(1, foods, 68);
        Order o2 = new Order(2, foods, 68);
        Order o3 = new Order(3, foods, 68);
        Order o4 = new Order(4, foods, 68);
        List<Order> orderList = new ArrayList<>();
        orderList.add(o1);orderList.add(o2);orderList.add(o3);orderList.add(o4);
        return orderList;
    }

}
