package com.app.tychodemo.activity;

import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.app.tychodemo.R;
import com.app.tychodemo.nohttp.HttpListener;
import com.app.tychodemo.nohttp.HttpResponseListener;
import com.yanzhenjie.nohttp.Logger;
import com.yanzhenjie.nohttp.NoHttp;
import com.yanzhenjie.nohttp.rest.CacheMode;
import com.yanzhenjie.nohttp.rest.Request;
import com.yanzhenjie.nohttp.rest.RequestQueue;

import org.w3c.dom.Text;

import butterknife.ButterKnife;

public abstract class BaseActivity extends AppCompatActivity {
    private CoordinatorLayout mCoordinatorLayout;
    private AppBarLayout mAppBarLayout;
    private Toolbar mToolbar;
    private FrameLayout mContentLayout;
    private TextView titleText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Logger.e(getClass().getName());
        getDelegate().setContentView(R.layout.activity_base);

        // 初始化请求队列，传入的参数是请求并发值。
        mQueue = NoHttp.newRequestQueue(1);

        mCoordinatorLayout = ButterKnife.findById(this, R.id.coordinatorlayout);
        mAppBarLayout = ButterKnife.findById(this, R.id.appbarlayout);
        mToolbar = ButterKnife.findById(this, R.id.toolbar);
        mContentLayout = ButterKnife.findById(this, R.id.content);
        titleText=ButterKnife.findById(this,R.id.base_title);
        setSupportActionBar(mToolbar);
        setBackBar(true);
        onActivityCreate(savedInstanceState);
    }

    //-------------- NoHttp -----------//

    /**
     * 用来标记取消。
     */
    private Object object = new Object();

    /**
     * 请求队列。
     */
    private RequestQueue mQueue;

    /**
     * 发起请求。
     *
     * @param what      what.
     * @param request   请求对象。
     * @param callback  回调函数。
     * @param canCancel 是否能被用户取消。
     * @param isLoading 实现显示加载框。
     * @param <T>       想请求到的数据类型。
     */
    public <T> void request(int what, Request<T> request, HttpListener<T> callback, boolean canCancel, boolean
            isLoading) {
        request.setCancelSign(object);
        mQueue.add(what, request, new HttpResponseListener<>(this, request, callback, canCancel, isLoading));
    }
    /**
     * 发起请求。
     *
     * @param what      what.
     *  @param iscache      是否缓存
     * @param request   请求对象。
     * @param callback  回调函数。
     * @param canCancel 是否能被用户取消。
     * @param isLoading 实现显示加载框。
     * @param <T>       想请求到的数据类型。
     */
    public <T> void request(int what,String iscache, Request<T> request, HttpListener<T> callback, boolean canCancel, boolean
            isLoading) {
        if(iscache.equals("1")){
            request.setCacheMode(CacheMode.REQUEST_NETWORK_FAILED_READ_CACHE);
        }
        request.setCancelSign(object);
        mQueue.add(what, request, new HttpResponseListener<>(this, request, callback, canCancel, isLoading));
    }

    @Override
    protected void onDestroy() {
        // 和声明周期绑定，退出时取消这个队列中的所有请求，当然可以在你想取消的时候取消也可以，不一定和声明周期绑定。
        mQueue.cancelBySign(object);

        // 因为回调函数持有了activity，所以退出activity时请停止队列。
        mQueue.stop();
        //解除绑定

        super.onDestroy();
    }

    protected void cancelAll() {
        mQueue.cancelAll();
    }

    protected void cancelBySign(Object object) {
        mQueue.cancelBySign(object);
    }
    // -------------------- BaseActivity的辅助封装 --------------------- //
    protected abstract void onActivityCreate(Bundle savedInstanceState);
    public void setBackBar(boolean isShow) {
        getSupportActionBar().setDisplayHomeAsUpEnabled(isShow);
    }
    public TextView getTitleText(){
        return titleText;
    }
    public Toolbar getmToolbar() {
        return mToolbar;
    }
}
