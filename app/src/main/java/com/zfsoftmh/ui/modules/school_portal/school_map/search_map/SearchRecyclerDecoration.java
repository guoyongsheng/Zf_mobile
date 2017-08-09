package com.zfsoftmh.ui.modules.school_portal.school_map.search_map;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by li on 2017/7/5.
 */

public class SearchRecyclerDecoration extends RecyclerView.ItemDecoration{


    private Paint paint;


    public SearchRecyclerDecoration(Context context,int Color){
        paint=new Paint();
        paint.setColor(Color);

    }



    @Override
    public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
        super.onDraw(c, parent, state);
        DrawHorizontal(c,parent);
    }



    public void DrawHorizontal(Canvas c, RecyclerView parent){
        int childcount=parent.getChildCount();
        final int left=parent.getPaddingLeft();
        final int right=parent.getWidth()+parent.getPaddingRight();
        for(int i=0;i<childcount;i++){
            final View childview=parent.getChildAt(i);
            RecyclerView.LayoutParams params= (RecyclerView.LayoutParams) childview.getLayoutParams();
            int top=childview.getBottom()+params.bottomMargin;
            int bottom=top+1;
            c.drawRect(left,top,right,bottom,paint);
        }










    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);
    }
}
