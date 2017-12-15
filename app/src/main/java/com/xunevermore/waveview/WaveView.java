package com.xunevermore.waveview;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.animation.LinearInterpolator;

/**
 * Created by Administrator on 2017/12/12 0012.
 */

public class WaveView extends View {

    private Path mPath;
    private float halfWaveLength = 500f;
    private float waveHeight = 100f;
    private Paint mPaint;

    public WaveView(Context context) {
        this(context, null);
    }

    public WaveView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public WaveView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mPath = new Path();
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setColor(Color.BLACK);
    }

    private static final String TAG = "WaveView";

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        halfWaveLength = w/2;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);



        int y = getMeasuredHeight() / 2;
        mPath.reset();
        mPath.moveTo(-fraction, y);


        float waveCount = (fraction+getMeasuredWidth())/halfWaveLength*2;
        int round = Math.round(waveCount);
        Log.i(TAG, "count: "+round);
        Log.i(TAG, "getMeasuredWidth: "+getMeasuredWidth());
        Log.i(TAG, "getMeasuredHeight: "+getMeasuredHeight());

        for (int i = 0; i < round; i++) {
            mPath.rQuadTo(halfWaveLength / 2, -waveHeight, halfWaveLength, 0);
            mPath.rQuadTo(halfWaveLength/2,waveHeight,halfWaveLength,0);
        }

        mPath.lineTo(getMeasuredWidth(),getMeasuredHeight());
        mPath.lineTo(0,getMeasuredHeight());
        mPath.close();
        canvas.drawPath(mPath,mPaint);

    }
    private float fraction = 0f;

    public void start(){
        ValueAnimator valueAnimator = ValueAnimator.ofFloat(halfWaveLength*2, 0);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                fraction = (float) animation.getAnimatedValue();
                postInvalidate();

            }
        });
        valueAnimator.setDuration(1500);
        valueAnimator.setInterpolator(new LinearInterpolator());
        valueAnimator.setRepeatCount(ValueAnimator.INFINITE);
        valueAnimator.start();
    }
}
