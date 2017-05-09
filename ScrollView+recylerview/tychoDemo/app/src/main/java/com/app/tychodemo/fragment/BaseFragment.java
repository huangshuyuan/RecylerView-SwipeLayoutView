package com.app.tychodemo.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.app.tychodemo.nohttp.HttpListener;
import com.app.tychodemo.nohttp.HttpResponseListener;
import com.yanzhenjie.nohttp.NoHttp;
import com.yanzhenjie.nohttp.rest.Request;
import com.yanzhenjie.nohttp.rest.RequestQueue;


public class BaseFragment extends Fragment {
	private static final String TAG = "BaseFragment";

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
		mQueue.add(what, request, new HttpResponseListener<>(getActivity(), request, callback, canCancel, isLoading));
	}

	@Override
	public void onDestroy() {
		// 和声明周期绑定，退出时取消这个队列中的所有请求，当然可以在你想取消的时候取消也可以，不一定和声明周期绑定。
		mQueue.cancelBySign(object);

		// 因为回调函数持有了activity，所以退出activity时请停止队列。
		mQueue.stop();

		super.onDestroy();
	}

	protected void cancelAll() {
		mQueue.cancelAll();
	}

	protected void cancelBySign(Object object) {
		mQueue.cancelBySign(object);
	}



	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		Log.i(TAG, "onCreate...");
		// 初始化请求队列，传入的参数是请求并发值。
		mQueue= NoHttp.newRequestQueue(1);

	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
							 Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		Log.i(TAG, "onCreateView...");



		return 	super.onCreateView(inflater, container, savedInstanceState);
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		Log.i(TAG, "onActivityCreated...");
		super.onActivityCreated(savedInstanceState);
	}

	@Override
	public void onStart() {
		// TODO Auto-generated method stub
		Log.i(TAG, "onStart...");
		super.onStart();
	}

	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		Log.i(TAG, "onResume...");
		super.onResume();
	}

	@Override
	public void onPause() {
		// TODO Auto-generated method stub
		Log.i(TAG, "onPause...");
		super.onPause();
	}

	@Override
	public void onStop() {
		// TODO Auto-generated method stub
		Log.i(TAG, "onStop...");
		super.onStop();
	}

	@Override
	public void onDestroyView() {
		// TODO Auto-generated method stub
		Log.i(TAG, "onDestroyView...");
		super.onDestroyView();
	}





}
