package com.app.tychodemo.fragment;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.app.tychodemo.R;
import com.app.tychodemo.adapter.GridAdapter;
import com.app.tychodemo.utils.RecyclerViewDivider;
import com.app.tychodemo.utils.ToastUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by xingjikang on 2017/3/27.
 */

public class FoundFragment extends BaseFragment {
    @BindView(R.id.found_recyclerView)
    RecyclerView foundRecyclerView;
    @BindView(R.id.swipeRefreshLayout)
    SwipeRefreshLayout swipeRefreshLayout;
    private List<Map<String, String>> data = new ArrayList<>();
    boolean isLoading;
    private MyGridAdapter adapter;
    private Handler handler = new Handler();
    GridLayoutManager  mGridViewLayoutManger;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View foundLayout = inflater.inflate(R.layout.fragment_found,
                container, false);
        ButterKnife.bind(this, foundLayout);
        return foundLayout;
    }


    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        swipeRefreshLayout.setColorSchemeColors(Color.RED,Color.BLUE,Color.GREEN);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Log.d("onrefresh","onrefresh");
                        data.clear();
                        getData();

                    }
                }, 2000);
            }
        });
        //设置布局管理器 --GridLayout效果--2行
        mGridViewLayoutManger = new GridLayoutManager(getActivity(), 3);

        foundRecyclerView.setLayoutManager(mGridViewLayoutManger);
        foundRecyclerView.addItemDecoration(new RecyclerViewDivider(getActivity(), GridLayoutManager.HORIZONTAL));
        adapter=new MyGridAdapter(getActivity(),data);
        foundRecyclerView.setAdapter(adapter);
        data.clear();
        getData();

        //上拉刷新
        initListener();
    }

    private void initListener() {
        foundRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                Log.d("test", "StateChanged = " + newState);

            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                Log.d("test", "onScrolled");
                int lastVisibleItemPosition = (mGridViewLayoutManger).findLastVisibleItemPosition();
                int totalItemCount = mGridViewLayoutManger.getItemCount();
                if (lastVisibleItemPosition >= totalItemCount - 3&& dy > 0) {
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
    /**
     * 获取测试数据
     */
    private void getData() {
        for(int i=0;i<12;i++){
            Map<String, String> map = new HashMap<>();
            map.put("img",R.mipmap.found_one+"");
            map.put("name","咔哧鸡腿堡套餐");
            map.put("price","20");
            data.add(map);
        }
        adapter.notifyDataSetChanged();
        swipeRefreshLayout.setRefreshing(false);
        adapter.notifyItemRemoved(adapter.getItemCount());
    }

    @Override
    public void onResume() {
        super.onResume();


    }
    private class  MyGridAdapter extends GridAdapter{

        public MyGridAdapter(Context context, List<Map<String, String>> list) {
            super(context, list);
        }

        @Override
        public void onClickItem(int index) {
            Toast.makeText(getActivity(),"position"+index,Toast.LENGTH_LONG).show();
        }
    }

}
