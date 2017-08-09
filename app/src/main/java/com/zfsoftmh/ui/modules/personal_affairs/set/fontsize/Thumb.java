package com.zfsoftmh.ui.modules.personal_affairs.set.fontsize;

import android.graphics.Canvas;
import android.graphics.Paint;

/**
 * Created by wsm on 2016/11/4 0004.
 * 设置字体拖动条的圆球
 * 当圆的半径太小的时候可能手指点击不住，
 * 这样会影响用户体验，所以就设置了mTouchDelete属性，它表示手指触摸的最小范围
 */
public class Thumb {
    private static final float MINIMUM_TARGET_RADIUS = 50;

    private final float mTouchZone;
    private boolean mIsPressed;

    private final float mY;
    private static float mX;

    private Paint mPaintNormal;
    private Paint mPaintPressed;

    private float mRadius;
    private int mColorNormal;
    private int mColorPressed;

    public Thumb(float x, float y, int colorNormal, int colorPressed, float radius) {

        mRadius = radius;
        mColorNormal = colorNormal;
        mColorPressed = colorPressed;

        mPaintNormal = new Paint();
        mPaintNormal.setColor(mColorNormal);
        mPaintNormal.setAntiAlias(true);

        mPaintPressed = new Paint();
        mPaintPressed.setColor(mColorPressed);
        mPaintPressed.setAntiAlias(true);

        mTouchZone = (int) Math.max(MINIMUM_TARGET_RADIUS, radius);

        mX = x;
        mY = y;
    }

    public static void setX(float x) {
        mX = x;
    }

    public float getX() {
        return mX;
    }

    public boolean isPressed() {
        return mIsPressed;
    }

    public void press() {
        mIsPressed = true;
    }

    public void release() {
        mIsPressed = false;
    }

    public boolean isInTargetZone(float x, float y) {
        if (Math.abs(x - mX) <= mTouchZone && Math.abs(y - mY) <= mTouchZone) {
            return true;
        }
        return false;
    }

    public void draw(Canvas canvas) {
        if (mIsPressed) {
            canvas.drawCircle(mX, mY, mRadius, mPaintPressed);
        } else {
            canvas.drawCircle(mX, mY, mRadius, mPaintNormal);
        }
    }

    public void destroyResources() {
        if(null != mPaintNormal) {
            mPaintNormal = null;
        }
        if(null != mPaintPressed) {
            mPaintPressed = null;
        }
    }
}
