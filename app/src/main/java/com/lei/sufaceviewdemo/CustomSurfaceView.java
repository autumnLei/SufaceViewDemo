package com.lei.sufaceviewdemo;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.Surface;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

/**
 * Created by lei on 2019-08-14.
 */

public class CustomSurfaceView extends SurfaceView implements Runnable, SurfaceHolder.Callback {

    private static final String TAG = "surface_";
    private SurfaceHolder mSurfaceHolder;
    // 绘图的Canvas
    private Canvas mCanvas;
    // 子线程标志位
    private boolean mIsDrawing;
    // 画笔
    private Paint mPaint;
    // 路径
    private Path mPath;

    public CustomSurfaceView(Context context) {
        this(context, null);
    }

    public CustomSurfaceView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CustomSurfaceView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    public void initView() {
        mPaint = new Paint();
        mPaint.setColor(Color.BLACK);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(4);
        mPaint.setAntiAlias(true);
        mPath = new Path();
        mPath.moveTo(0, 0);
        mSurfaceHolder = getHolder();
        mSurfaceHolder.addCallback(this);
//        mSurfaceHolder.addCallback(new SurfaceHolder.Callback() {
//            @Override
//            public void surfaceCreated(SurfaceHolder surfaceHolder) {
//                Log.i(TAG, "surfaceCreated: ");
//                mIsDrawing = true;
//                new Thread(CustomSurfaceView.this).start();
//            }
//
//            @Override
//            public void surfaceChanged(SurfaceHolder surfaceHolder, int i, int i1, int i2) {
//                Log.i(TAG, "surfaceChanged: ");
//                mIsDrawing = false;
//            }
//
//            @Override
//            public void surfaceDestroyed(SurfaceHolder surfaceHolder) {
//                Log.i(TAG, "surfaceDestroyed: ");
//            }
//        });
        setFocusable(true);
        setKeepScreenOn(true);
        setFocusableInTouchMode(true);
    }

    @Override
    public void run() {
        while (mIsDrawing) {
            drawSomething();
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int x = (int) event.getX();
        int y = (int) event.getY();
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mPath.moveTo(x, y);
                break;
            case MotionEvent.ACTION_MOVE:
                mPath.lineTo(x, y);
                break;
            case MotionEvent.ACTION_UP:
                break;
        }
        return true;
    }


    public void drawSomething() {
        try {
            mCanvas = mSurfaceHolder.lockCanvas();
            mCanvas.drawColor(Color.WHITE);
            mCanvas.drawPath(mPath, mPaint);
        } catch (Exception e) {

        } finally {
            if (mCanvas != null) {
                mSurfaceHolder.unlockCanvasAndPost(mCanvas);
            }
        }
    }

    @Override
    public void surfaceCreated(SurfaceHolder surfaceHolder) {
        mIsDrawing = true;
        new Thread(this).start();
    }

    @Override
    public void surfaceChanged(SurfaceHolder surfaceHolder, int i, int i1, int i2) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder surfaceHolder) {
        mIsDrawing = false;
    }
}























