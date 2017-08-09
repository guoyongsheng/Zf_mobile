package com.zfsoftmh.ui.widget.divider;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;

import com.zfsoftmh.entity.FoodInfo;

import java.util.List;

/**
 * Created by ljq
 * on 2017/7/26.
 */

public class EateryDatailTitleDecoration extends RecyclerView.ItemDecoration{

    LayoutInflater mlayoutInflater;
    Context context;
    Paint paint;
    Rect rect;
    List<FoodInfo> poslist;
    private int titleHeight;
    private String currentTag = "0";

private static  int COLOR_BACK= Color.parseColor("#FFDFDFDF");
    private  static  int COLOR_FONT=Color.parseColor("#FF000000");


    public EateryDatailTitleDecoration(Context context,List<FoodInfo> poslist){
        super();
        this.poslist=poslist;
        paint=new Paint();
        rect=new Rect();
        titleHeight = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 30, context.getResources().getDisplayMetrics());
        int titleFontSize = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 16, context.getResources().getDisplayMetrics());
        paint.setTextSize(titleFontSize);
        paint.setAntiAlias(true);
        mlayoutInflater = LayoutInflater.from(context);
    }

    @Override
    public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
        super.onDraw(c, parent, state);
        int left=parent.getPaddingLeft();
        int right=parent.getWidth()-parent.getPaddingLeft();
        int childcount=parent.getChildCount();
        for(int i=0;i<childcount;i++){
            View v=parent.getChildAt(i);
            RecyclerView.LayoutParams params= (RecyclerView.LayoutParams) v.getLayoutParams();
            int pos=params.getViewLayoutPosition();

            if(pos>-1){
                if(pos==0){
                    DreawArea(c,left,right,params,pos,v);
                }else {//绘制条件
                    /*
                  if(){

                  }*/


                }

            }


        }
    }





    @Override
    public void onDrawOver(Canvas c, RecyclerView parent, RecyclerView.State state) {
        super.onDrawOver(c, parent, state);
        LinearLayoutManager manager= (LinearLayoutManager) parent.getLayoutManager();
        int pos=manager.findFirstVisibleItemPosition();
        if(pos>-1){
        //if(){ //条件
        String title=poslist.get(pos).getType_title();
        View  child=parent.findViewHolderForLayoutPosition(pos).itemView;
        paint.setColor(COLOR_BACK);
        c.drawRect(parent.getPaddingLeft(),parent.getPaddingTop(),
                parent.getRight()-parent.getPaddingRight(),parent.getPaddingTop()+titleHeight,paint);
        paint.setColor(COLOR_FONT);
        paint.getTextBounds(title,0,title.length(),rect);
        c.drawText(title,child.getPaddingLeft(),parent.getPaddingTop()+titleHeight-(titleHeight/2-rect.height()/2),paint);

        }
       // }
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);
        RecyclerView.LayoutParams params= (RecyclerView.LayoutParams) view.getLayoutParams();
        int pos=params.getViewLayoutPosition();
        if(pos>-1){
        }

        if(pos==0){
            outRect.set(0,titleHeight,0,0);
        }else{//其他符合的条件
            /*if(){
                outRect.set(0,titleHeight,0,0);
            }else{
                outRect.set(0,0,0,0);
            }*/
        }

    }


    private void DreawArea(Canvas c, int left, int right, RecyclerView.LayoutParams params, int pos, View child){
        paint.setColor(COLOR_BACK);
        c.drawRect(left,child.getTop()-params.topMargin-titleHeight,right,child.getTop()-params.topMargin,paint);
        paint.setColor(COLOR_FONT);
        paint.getTextBounds(poslist.get(pos).getType_title(),0,poslist.get(pos).getType_title().length(),rect);
        c.drawText(poslist.get(pos).getType_title(),child.getLeft(),child.getTop()-params.topMargin-(titleHeight/2-rect.height()/2),paint);
    }
}
