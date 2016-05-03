package com.ilmare.parallaxlistview.View;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.animation.Animation;
import android.view.animation.OvershootInterpolator;
import android.widget.ImageView;
import android.widget.ListView;

import com.ilmare.parallaxlistview.Animation.ResetAnimation;
import com.nineoldandroids.animation.ValueAnimator;

/**
 * Created by zhangchenggeng
 * Time 2016/4/28 12:46.
 * Descripton:
 * History:
 * 版权所有
 */
public class ParallaxListView extends ListView {

    public   ImageView imageView;
    public  int originHeight;

    int drawableHeight;  //图片的高度
    public static final String TAG = "TAG";
    public ParallaxListView(Context context) {
        super(context,null);
    }



    public ParallaxListView(Context context, AttributeSet attrs) {
        super(context, attrs,0);
    }

    public ParallaxListView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);


    }

    public void setHeaderImageView(ImageView imageView){
        this.imageView=imageView;

        //获取原始的高度
        originHeight = imageView.getHeight();

        //获取图片的原始高度
        drawableHeight = imageView.getDrawable().getIntrinsicHeight();


    }


    //往下拉，拉不动的时候
    @Override
    public  boolean overScrollBy(int deltaX, int deltaY, int scrollX, int scrollY, int scrollRangeX, int scrollRangeY, int maxOverScrollX, int maxOverScrollY, boolean isTouchEvent) {

        //deltaY ：瞬时值
        //scrollY ： 下滑量
        //scrollRangeY ：下滑的范围
        //isTouchEvent ：是否是手滑动

        int height=imageView.getHeight();

        if(isTouchEvent&&deltaY<0){
            if(height<=drawableHeight){
                int newHeight= (int) (height+Math.abs(deltaY/3.0f));
                imageView.getLayoutParams().height=newHeight;
                imageView.requestLayout();
            }
        }

        return super.overScrollBy(deltaX, deltaY, scrollX, scrollY, scrollRangeX, scrollRangeY, maxOverScrollX, maxOverScrollY, isTouchEvent);

    }


    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        switch (ev.getAction()){

            case MotionEvent.ACTION_UP:   //手指抬起的时候-->回弹
                final int startHeight = imageView.getHeight();
                final int endHeight = originHeight;
                //方法一
                //animator(startHeight, endHeight);

                //方法二
                ResetAnimation animation=new ResetAnimation(imageView,startHeight,endHeight);
                animation.setDuration(500);
                animation.setInterpolator(new OvershootInterpolator());
                startAnimation(animation);
                break;
        }

        return super.onTouchEvent(ev);
    }

    private void animator(final int startHeight, final int endHeight) {
        ValueAnimator valueAnimator=ValueAnimator.ofInt(1);

        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {

            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float animatedFraction = animation.getAnimatedFraction();

                int newheight= evaluate(animatedFraction,startHeight,endHeight);
                imageView.getLayoutParams().height=newheight;
                imageView.requestLayout();
            }
        });

        valueAnimator.setDuration(500);
        valueAnimator.start();
    }


    public Integer evaluate(float fraction, Integer startValue, Integer endValue) {
        int startInt = startValue;
        return (int)(startInt + fraction * (endValue - startInt));
    }

}
