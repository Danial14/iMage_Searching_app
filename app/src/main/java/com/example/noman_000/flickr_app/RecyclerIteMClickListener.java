package com.example.noman_000.flickr_app;

import android.content.Context;
import android.support.v4.view.GestureDetectorCompat;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;


class RecyclerIteMClickListener extends RecyclerView.SimpleOnItemTouchListener {
    private static final String TAG = "RecyclerIteMClickListen";
    private final onRecyclerClickListener recyclerClickListener;
    private final GestureDetectorCompat gestureDetector;
    private final RecyclerView recyclerView;

    public RecyclerIteMClickListener(Context context, final RecyclerView recyclerView, final onRecyclerClickListener recyclerClickListener) {
        this.recyclerClickListener = recyclerClickListener;
        this.recyclerView = recyclerView;
        gestureDetector = new GestureDetectorCompat(context, new GestureDetector.SimpleOnGestureListener(){
            @Override
            public boolean onSingleTapUp(MotionEvent e) {
                Log.d(TAG, "onSingleTapUp:");
                if(recyclerView != null && recyclerClickListener != null){
                    View childView = recyclerView.findChildViewUnder(e.getX(), e.getY());
                    if(childView != null){
                        Log.d(TAG, "onSingleTapUp: listener's onItemClick() called");
                        recyclerClickListener.onItemClick(childView, recyclerView.getChildAdapterPosition(childView));
                    }
                }
                return true;
            }

            @Override
            public void onLongPress(MotionEvent e) {
                Log.d(TAG, "onLongPress:");
                if(recyclerView !=  null && recyclerClickListener != null){
                    View childView = recyclerView.findChildViewUnder(e.getX(), e.getY());
                    if(childView != null){
                        recyclerClickListener.onItemLongClick(childView, recyclerView.getChildAdapterPosition(childView));
                    }
                }
                super.onLongPress(e);
            }

            @Override
            public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
                Log.d(TAG, "onScroll:");
                return super.onScroll(e1, e2, distanceX, distanceY);
            }

            @Override
            public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
                Log.d(TAG, "onFling:");
                return super.onFling(e1, e2, velocityX, velocityY);
            }

            @Override
            public void onShowPress(MotionEvent e) {
                Log.d(TAG, "onShowPress:");
                super.onShowPress(e);
            }

            @Override
            public boolean onDown(MotionEvent e) {
                Log.d(TAG, "onDown:");
                return super.onDown(e);
            }

            @Override
            public boolean onDoubleTap(MotionEvent e) {
                Log.d(TAG, "onDoubleTap:");
                return super.onDoubleTap(e);
            }

            @Override
            public boolean onDoubleTapEvent(MotionEvent e) {
                Log.d(TAG, "onDoubleTapEvent:");
                return super.onDoubleTapEvent(e);
            }

            @Override
            public boolean onSingleTapConfirmed(MotionEvent e) {
                Log.d(TAG, "onSingleTapConfirmed:");
                return super.onSingleTapConfirmed(e);
            }

            @Override
            public boolean onContextClick(MotionEvent e) {
                Log.d(TAG, "onContextClick:");
                return super.onContextClick(e);
            }
        });
    }

    interface onRecyclerClickListener{
        void onItemClick(View view, int position);
        void onItemLongClick(View view, int position);
    }

    @Override
    public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {
        Log.d(TAG, "onInterceptTouchEvent: starts");
        // return true Means that we have handle all the events now systeM doesnot need to handle events
        // return false Means that we have not handle all the events now systeM does need to handle events
        // how we decide that with which event we want to deal with?
        // that is where GestureDetector it tells what type of gesture(event) is happened
        if(gestureDetector != null){
            boolean result = gestureDetector.onTouchEvent(e);
            Log.d(TAG, "onInterceptTouchEvent: " + result);
            return result;
        }
        else {
            Log.d(TAG, "onInterceptTouchEvent: false");
            return false;
        }
    }
}
