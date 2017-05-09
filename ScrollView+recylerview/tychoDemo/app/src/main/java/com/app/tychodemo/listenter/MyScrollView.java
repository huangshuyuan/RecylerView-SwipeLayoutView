package com.app.tychodemo.listenter;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.ViewConfiguration;
import android.widget.ScrollView;

/**
 * Created by xingjikang on 2017/3/28.
 */

public class MyScrollView extends ScrollView {
    private int downX;
    private int downY;
    private int mTouchSlop;
    private OnScrollToBottomListener onBottomListener;

    public OnScrollToBottomListener getOnBottomListener() {
        return onBottomListener;
    }

    public void setOnBottomListener(OnScrollToBottomListener onBottomListener) {
        this.onBottomListener = onBottomListener;
    }

    public boolean isButtom() {
        return isButtom;
    }

    public void setButtom(boolean top) {
        isButtom = top;
    }

    private boolean isButtom = false;//是不是滑动到了最低端 ；使用这个方法，解决了上拉加载的问题


    public MyScrollView(Context context) {
        super(context);
        mTouchSlop = ViewConfiguration.get(context).getScaledTouchSlop();
    }

    public MyScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mTouchSlop = ViewConfiguration.get(context).getScaledTouchSlop();
    }

    public MyScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mTouchSlop = ViewConfiguration.get(context).getScaledTouchSlop();
    }



    public interface OnScrollToBottomListener {
        public void onScrollBottomListener(boolean isBottom);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent e) {
        int action = e.getAction();
        switch (action) {
            case MotionEvent.ACTION_DOWN:
                setButtom(false);
                downX = (int) e.getRawX();
                downY = (int) e.getRawY();
                Log.i("-----::----downY-----::", downY + "");
                break;
            case MotionEvent.ACTION_MOVE:
                int moveY = (int) e.getRawY();
                Log.i("-----::----moveY-----::", moveY + "");
             /****判断是向下滑动，才设置为true****/
                if (downY - moveY > 0) {
                    setButtom(true);
                } else {
                    setButtom(false);
                }
                if (Math.abs(moveY - downY) > mTouchSlop) {
                    return true;
                }
        }
        return super.onInterceptTouchEvent(e);
    }
}
