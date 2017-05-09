package com.app.tychodemo.fragment;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.app.tychodemo.R;
import com.app.tychodemo.adapter.ListAdapter;
import com.app.tychodemo.utils.RecyclerViewDivider;
import com.app.tychodemo.utils.ToastUtils;
import com.yanzhenjie.nohttp.Logger;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by xingjikang on 2017/3/27.
 */

public class ClassifyFragment extends BaseFragment {
    @BindView(R.id.classify_recyclerView)
    RecyclerView classifyRecyclerView;
    @BindView(R.id.swipeRefreshLayout)
    SwipeRefreshLayout swipeRefreshLayout;
    private List<Map<String, String>> data = new ArrayList<>();
    boolean isLoading;
    private MyListAdapter adapter;
    private Handler handler = new Handler();
    LinearLayoutManager mLinearLayoutManager;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View contactsLayout = inflater.inflate(R.layout.fragment_classify,
                container, false);
        ButterKnife.bind(this, contactsLayout);
        return contactsLayout;
    }


    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        swipeRefreshLayout.setColorSchemeColors(Color.RED, Color.BLUE, Color.GREEN);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Log.d("onrefresh", "onrefresh");
                        data.clear();
                        getData();

                    }
                }, 2000);
            }
        });
        //设置布局管理器 --GridLayout效果--2行
        mLinearLayoutManager = new LinearLayoutManager(getActivity());

        classifyRecyclerView.setLayoutManager(mLinearLayoutManager);
        classifyRecyclerView.addItemDecoration(new RecyclerViewDivider(getActivity(), LinearLayoutManager.HORIZONTAL));
        adapter = new MyListAdapter(getActivity(), data);
        classifyRecyclerView.setAdapter(adapter);
        data.clear();
        getData();

        //上拉刷新
        initListener();


    }


    private void initListener() {
        classifyRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                Log.d("test", "StateChanged = " + newState);

            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                Log.d("test", "onScrolled");
                int lastVisibleItemPosition = (mLinearLayoutManager).findLastVisibleItemPosition();
                int totalItemCount = mLinearLayoutManager.getItemCount();
                if (lastVisibleItemPosition >= totalItemCount - 1 && dy > 0) {
                    Log.d("test", "loading executed");

                    boolean isRefreshing = swipeRefreshLayout.isRefreshing();
                    if (isRefreshing) {
                        adapter.notifyItemRemoved(adapter.getItemCount());
                        return;
                    }
                    if (!isLoading) {
                        isLoading = true;
                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                getData();
                                Log.d("test", "load more completed");
                                isLoading = false;
                            }
                        }, 1000);
                    }
                }
            }
        });
    }

    @Override
    public void onResume() {
        // TODO Auto-generated method stub
        super.onResume();


    }

    /**
     * 获取测试数据
     */
    private void getData() {
        for (int i = 0; i < 12; i++) {
            Map<String, String> map = new HashMap<>();
            map.put("img", R.mipmap.found_two + "");
            map.put("name", "美心西饼  75折");
            map.put("label", "面包 蛋糕 芝士");
            map.put("num", "0.9");
            data.add(map);
        }
        adapter.notifyDataSetChanged();
        swipeRefreshLayout.setRefreshing(false);
        adapter.notifyItemRemoved(adapter.getItemCount());
    }

    private class MyListAdapter extends ListAdapter {

        public MyListAdapter(Context context, List<Map<String, String>> list) {
            super(context, list);
        }

        @Override
        public void onClickItem(int index) {
            Toast.makeText(getActivity(), "position" + index, Toast.LENGTH_SHORT).show();
        }
    }

}
