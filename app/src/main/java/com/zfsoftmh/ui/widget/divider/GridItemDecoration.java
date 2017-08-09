package com.zfsoftmh.ui.widget.divider;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.State;
import android.view.View;

/**
 * @author sy
 * @since 2016/9/22
 */
public class GridItemDecoration extends RecyclerView.ItemDecoration {
	
	private Paint paint;
	private int horizontalPadding,verticalPadding;
	public GridItemDecoration(int color){
		paint = new Paint();
		paint.setColor(color);
	}
	
	/**
	 * 设置不封闭分割线的留白步长
	 * @param horizontalPadding   水平留白量
	 * @param verticalPadding   垂直留白量
	 */
	public void setItemPadding(int horizontalPadding, int verticalPadding){
		this.horizontalPadding = horizontalPadding;
		this.verticalPadding = verticalPadding;
	}
	
	@Override
	public void onDrawOver(Canvas c, RecyclerView parent, State state) {
		super.onDrawOver(c, parent, state);
		drawHorizontal(c, parent);
		drawVertical(c, parent);
	}
	
	public void drawHorizontal(Canvas c, RecyclerView parent){
      int childCount = parent.getChildCount();
      for (int i = 0; i < childCount; i++) {
          final View child = parent.getChildAt(i);
          final RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child.getLayoutParams();
          final int left = child.getLeft() - params.leftMargin;
          final int right = child.getRight() + params.rightMargin + 2;
          final int top = child.getBottom() + params.bottomMargin;
          final int bottom = top + 2;
          c.drawRect(left + horizontalPadding, top, right - horizontalPadding, bottom, paint);
      }
	}
	
	public void drawVertical(Canvas c, RecyclerView parent){
      final int childCount = parent.getChildCount();
      for (int i = 0; i < childCount; i++) {
          final View child = parent.getChildAt(i);
          final RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child.getLayoutParams();
          final int top = child.getTop() - params.topMargin;
          final int bottom = child.getBottom() + params.bottomMargin;
          final int left = child.getRight() + params.rightMargin;
          final int right = left + 2;
          c.drawRect(left, top + verticalPadding, right, bottom - verticalPadding, paint);
      }
	}

}
