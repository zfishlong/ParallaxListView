package com.ilmare.parallaxlistview.Animation;

import android.animation.IntEvaluator;
import android.app.DownloadManager;
import android.view.animation.Animation;
import android.view.animation.Transformation;
import android.widget.ImageView;

/**
 * Created by zhangchenggeng
 * Time 2016/5/3 18:52.
 * Descripton:
 * History:
 * 版权所有
 */
public class ResetAnimation extends Animation {

    private ImageView imageView;
    private int startHeight;
    private int endHeight;

    public ResetAnimation(ImageView imageView,int startHeight,int endHeight){
        this.imageView=imageView;
        this.startHeight=startHeight;
        this.endHeight=endHeight;

    }


    @Override
    protected void applyTransformation(float interpolatedTime, Transformation t) {


        IntEvaluator evaluator=new IntEvaluator();
        Integer newHeight = evaluator.evaluate(interpolatedTime, startHeight, endHeight);

        imageView.getLayoutParams().height=newHeight;
        imageView.requestLayout();

        super.applyTransformation(interpolatedTime, t);
    }



}
