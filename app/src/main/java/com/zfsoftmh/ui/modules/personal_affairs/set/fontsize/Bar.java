package com.zfsoftmh.ui.modules.personal_affairs.set.fontsize;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.text.TextUtils;

/**
 * Created by 王世美 on 2016/11/4 0004.
 * 设置字体中的刻度条
 * 其中方法getNearestTickCoordinate()方法表示的是找到距离thumb最近刻度的X坐标，
 * getNearestTickIndex()表示的是找到距离thumb最近的刻度的下标
 */
public class Bar {
    private Paint mBarPaint;
    private Paint mTextPaint;

    private final float mLeftX;
    private final float mRightX;
    private final float mY;
    private final float mPadding;

    private int mSegments;
    private float mTickDistance;
    private final float mTickHeight;
    private final float mTickStartY;
    private final float mTickEndY;

    public Bar(float x, float y, float width, int tickCount, float tickHeight,
               float barWidth, int barColor, int textColor, int textSize, int padding,float mThumbRadius) {

        mLeftX = x;
        mRightX = x + width;
        mY = y;
        this.mPadding = padding;
//        mThumbRadius = nearestTickCoordinate;
        mSegments = tickCount - 1;
        mTickDistance = width / mSegments;
        mTickHeight = tickHeight;
        mTickStartY = mY - mTickHeight / 2f;
        mTickEndY = mY + mTickHeight / 2f;

        mBarPaint = new Paint();
        mBarPaint.setColor(barColor);
        mBarPaint.setStrokeWidth(barWidth);
        mBarPaint.setAntiAlias(true);

        mTextPaint = new Paint();
        mTextPaint.setColor(textColor);
        mTextPaint.setTextSize(textSize);
        mTextPaint.setAntiAlias(true);
    }

    public void draw(Canvas canvas) {
        drawLine(canvas);
        drawTicks(canvas);
    }

    public float getLeftX() {
        return mLeftX;
    }

    public float getRightX() {
        return mRightX;
    }

    /*方法getNearestTickCoordinate()方法表示的是找到距离thumb最近刻度的X坐标*/
    public float getNearestTickCoordinate(Thumb thumb) {
        final int nearestTickIndex = getNearestTickIndex(thumb);
        final float nearestTickCoordinate = mLeftX + (nearestTickIndex * mTickDistance);
        return nearestTickCoordinate;
    }

    /*getNearestTickIndex()表示的是找到距离thumb最近的刻度的下标*/
    public int getNearestTickIndex(Thumb thumb) {
        return getNearestTickIndex(thumb.getX());
    }

    public int getNearestTickIndex(float x) {
        return (int) ((x - mLeftX + mTickDistance / 2f) / mTickDistance);
    }

    private void drawLine(Canvas canvas) {
        canvas.drawLine(mLeftX, mY, mRightX, mY, mBarPaint);
    }

    private void drawTicks(Canvas canvas) {
        for (int i = 0; i <= mSegments; i++) {
            final float x = i * mTickDistance + mLeftX;
            canvas.drawLine(x, mTickStartY, x, mTickEndY, mBarPaint);
            String text = 0 == i ? "小" : mSegments == i ? "大" : "";
            if (!TextUtils.isEmpty(text)) {
                canvas.drawText(text, x - getTextWidth(text) / 2, mTickStartY - mPadding, mTextPaint);
            }
        }
    }

    float getTextWidth(String text) {
        return mTextPaint.measureText(text);
    }

    public void destroyResources() {
        if (null != mBarPaint) {
            mBarPaint = null;
        }
        if (null != mTextPaint) {
            mTextPaint = null;
        }
    }
}
