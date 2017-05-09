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
import android.widget.Toast;

import com.app.tychodemo.R;
import com.app.tychodemo.adapter.HomeGridAdapter;
import com.app.tychodemo.adapter.HomeTwoGridAdapter;
import com.app.tychodemo.adapter.ListAdapter;
import com.app.tychodemo.listenter.ObservableScrollView;
import com.app.tychodemo.utils.RecyclerViewDivider;
import com.app.tychodemo.utils.ToastUtils;
import com.app.tychodemo.view.CustomLinearLayoutManager;
import com.app.tychodemo.view.FullyLinearLayoutManager;
import com.app.tychodemo.view.MyScrollView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by xingjikang on 2017/3/27.
 */

public class HomeFragment extends BaseFragment {
    @BindView(R.id.home_one_recyclerView)
    RecyclerView homeOneRecyclerView;
    @BindView(R.id.home_two_recyclerView)
    RecyclerView homeTwoRecyclerView;
    @BindView(R.id.home_swipe)
    SwipeRefreshLayout homeSwipe;

    MyHomeGridAdapter myHomeGridAdapter;
    MyHomeTwoGridAdapter myHomeTwoGridAdapter;
    @BindView(R.id.scrollView)
    ObservableScrollView scrollView;
    @BindView(R.id.home_middle)
    RecyclerView homeMiddleRecyclerView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View contactsLayout = inflater.inflate(R.layout.fragment_home,
                container, false);
        ButterKnife.bind(this, contactsLayout);
        return contactsLayout;
    }


    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

//        上界面数据展示
        initTopView();
//        中界面数据展示
        initMiddleView();
//        底界面数据展示
        initBottomView();

//        initBottomView2();
    }

    private void initBottomView2() {
        getTopData();
        myHomeGridAdapter = new MyHomeGridAdapter(getActivity(), topList);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(), 1);
        homeTwoRecyclerView.setLayoutManager(gridLayoutManager);
        homeTwoRecyclerView.setAdapter(myHomeGridAdapter);

    }

    private void initBottomView() {


        homeSwipe.setColorSchemeColors(Color.RED, Color.BLUE, Color.GREEN);
        homeSwipe.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        bottomList.clear();
                        getBottomData();
                    }
                }, 2000);

            }
        });


        custommanager = new LinearLayoutManager(getActivity()) {
//            @Override
//            public boolean canScrollVertically() {
//                return false;
//            }
        };
//        custommanager.setScrollEnabled(false);//禁用滑动
        bottomList.clear();
        adapter = new MyListAdapter(getActivity(), bottomList);
        homeTwoRecyclerView.setAdapter(adapter);
        homeTwoRecyclerView.setLayoutManager(custommanager);
        homeTwoRecyclerView.addItemDecoration(new RecyclerViewDivider(getActivity(), LinearLayoutManager.HORIZONTAL));
        homeTwoRecyclerView.setNestedScrollingEnabled(false);
        getBottomData();

//        上拉刷新
        initListener();
    }

    private void initListener() {
//        scrollView.setOnScrollToBottomLintener(new MyScrollView.OnScrollToBottomListener() {
//            @Override
//            public void onScrollBottomListener(boolean isBottom) {
//                if (isBottom&&scrollView.isTop()) {
//                    boolean isRefreshing = homeSwipe.isRefreshing();
//                    if (isRefreshing) {
//                        adapter.notifyItemRemoved(adapter.getItemCount());
//                        return;
//                    }
//                    if (!isLoading) {
//                        isLoading = true;
//                        handler.postDelayed(new Runnable() {
//                            @Override
//                            public void run() {
//                                getBottomData();
//                                Log.d("test", "load more completed");
//                                isLoading = false;
//                            }
//                        }, 1000);
//                    }
//                }
//            }
//        });


        scrollView.setScrollViewListener(new ObservableScrollView.ScrollViewListener() {
            @Override
            public void onScrollChanged(ObservableScrollView scrollView, int x, int y, int oldx, int oldy) {

                if (scrollView.getScrollY() == 0) {
                    Log.i("顶部", "顶部");
                }
                if (scrollView.getScrollY() + scrollView.getHeight() - scrollView.getPaddingTop() -
                        scrollView.getPaddingBottom() == scrollView.getChildAt(0).getHeight()) {
//                    System.out.println("底部");

                    boolean isRefreshing = homeSwipe.isRefreshing();
                    if (isRefreshing) {
                        adapter.notifyItemRemoved(adapter.getItemCount());
                        return;
                    }
                    if (!isLoading) {
                        isLoading = true;
                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                getBottomData();
                                Log.d("test", "load more completed");
                                isLoading = false;
                            }
                        }, 1000);
                    }


                }

            }
        });
//
//        homeTwoRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
//            @Override
//            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
//                super.onScrollStateChanged(recyclerView, newState);
//                Log.d("test", "StateChanged = " + newState);
//
//            }
//
//            @Override
//            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
//                super.onScrolled(recyclerView, dx, dy);
//                Log.d("test", "onScrolled");
//                int lastVisibleItemPosition = (fullyLinearLayoutManager).findLastVisibleItemPosition();
//                int totalItemCount = fullyLinearLayoutManager.getItemCount();
//                if (lastVisibleItemPosition >= totalItemCount - 1 && dy > 0) {
//                    Log.d("test", "loading executed");
//
//                    boolean isRefreshing = homeSwipe.isRefreshing();
//                    if (isRefreshing) {
//                        adapter.notifyItemRemoved(adapter.getItemCount());
//                        return;
//                    }
//                    if (!isLoading) {
//                        isLoading = true;
//                        handler.postDelayed(new Runnable() {
//                            @Override
//                            public void run() {
//                                getBottomData();
//                                Log.d("test", "load more completed");
//                                isLoading = false;
//                            }
//                        }, 1000);
//                    }
//                }
//            }
//        });

    }

    private void initMiddleView() {
        getMiddleData();
        myHomeTwoGridAdapter = new MyHomeTwoGridAdapter(getActivity(), middleList);
        GridLayoutManager fullyGridLayoutManager = new GridLayoutManager(getActivity(), 3);
        homeMiddleRecyclerView.setLayoutManager(fullyGridLayoutManager);
        homeMiddleRecyclerView.setAdapter(myHomeTwoGridAdapter);
    }

    private void initTopView() {
        getTopData();
        myHomeGridAdapter = new MyHomeGridAdapter(getActivity(), topList);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(), 4);
        homeOneRecyclerView.setLayoutManager(gridLayoutManager);
        homeOneRecyclerView.setAdapter(myHomeGridAdapter);

    }

    @Override
    public void onResume() {
        super.onResume();

    }

    private class MyHomeGridAdapter extends HomeGridAdapter {

        public MyHomeGridAdapter(Context context, List<Map<String, String>> list) {
            super(context, list);
        }

        @Override
        public void onClickItem(int index) {
            ToastUtils.show(getActivity(), "position" + index);

        }
    }

    private class MyHomeTwoGridAdapter extends HomeTwoGridAdapter {

        public MyHomeTwoGridAdapter(Context context, List<Map<String, String>> list) {
            super(context, list);
        }

        @Override
        public void onClickItem(int index) {
            ToastUtils.show(getActivity(), "position" + index);
        }
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

    /**
     * 获取上层数据
     */
    private List<Map<String, String>> topList = new ArrayList<>();

    private void getTopData() {
//        topList.clear();
        for (int i = 0; i < 8; i++) {
            Map<String, String> map = new HashMap<>();
            map.put("img", R.mipmap.icon1 + "");
            map.put("name", "美食");
            topList.add(map);
        }

    }

    /**
     * 获取中层数据
     */
    private List<Map<String, String>> middleList = new ArrayList<>();

    private void getMiddleData() {
//        middleList.clear();
        for (int i = 0; i < 6; i++) {
            Map<String, String> map = new HashMap<>();
            map.put("img", R.mipmap.found_one + "");
            map.put("name", "咔哧鸡腿堡套餐");
            map.put("price", "20");
            middleList.add(map);
        }

    }

    /**
     * 获取下层数据
     */
    boolean isLoading;
    private Handler handler = new Handler();
    private LinearLayoutManager custommanager;
    private List<Map<String, String>> bottomList = new ArrayList<>();
    private MyListAdapter adapter;

    private void getBottomData() {
        for (int i = 0; i < 4; i++) {
            Map<String, String> map = new HashMap<>();
            map.put("img", R.mipmap.found_two + "");
            map.put("name", "美心西饼  75折");
            map.put("label", "面包 蛋糕 芝士");
            map.put("num", "0.9");
            bottomList.add(map);
        }


        adapter.notifyDataSetChanged();
        homeSwipe.setRefreshing(false);
        adapter.notifyItemRemoved(adapter.getItemCount());
        System.out.println("获取数据---------" + adapter.getItemCount());
    }

}
