package com.xunevermore.waveview.drawable;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.Rect;
import android.graphics.Shader;
import android.graphics.drawable.Animatable;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.animation.LinearOutSlowInInterpolator;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.BounceInterpolator;
import android.view.animation.LinearInterpolator;

/**
 * Created by Administrator on 2017/12/15 0015.
 */

public class TextDrawable extends Drawable implements Animatable {


    private String text;

    private Paint mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private ObjectAnimator fraction1;

    public TextDrawable(String text) {
        this.text = text;

        mPaint.setColor(Color.BLACK);
    }

    @Override
    public void draw(@NonNull Canvas canvas) {
        Rect bounds = getBounds();

        int i = bounds.centerY();



        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setShader(null);
        canvas.drawRect(bounds,mPaint);
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setStrokeCap(Paint.Cap.ROUND);
        mPaint.setFakeBoldText(true);

        mPaint.setTextSize(bounds.width()/(text.length()+1));
        float width = mPaint.measureText(text);


        Paint.FontMetrics fontMetrics = mPaint.getFontMetrics();
        float v = fontMetrics.descent + bounds.centerY();

        float textHeight = fontMetrics.ascent - fontMetrics.descent;

        float padding = (bounds.width() - width) / 2;

        LinearGradient gradient ;
        gradient = new LinearGradient(0+padding,bounds.centerY()-textHeight/2,bounds.width()-padding,
                bounds.centerY()+textHeight/2,new int[]{Color.BLACK,Color.YELLOW,Color.BLACK},new float[]{0,fraction,1}, Shader.TileMode.CLAMP);
        mPaint.setShader(gradient);
        canvas.drawText(text,bounds.centerX()-width/2,v,mPaint);

    }
    private float fraction = 0f;

    public float getFraction() {
        return fraction;
    }

    public void setFraction(float fraction) {
        this.fraction = fraction;
        invalidateSelf();
    }

    public void start(){
        fraction1 = ObjectAnimator.ofFloat(this, "fraction", 0f, 1f);
        fraction1.setRepeatCount(ValueAnimator.INFINITE);
        fraction1.setInterpolator(new LinearInterpolator());
        fraction1.setDuration(1500);
        fraction1.start();
    }

    @Override
    public void stop() {
        if(fraction1!=null&&fraction1.isRunning()){
            fraction1.cancel();
        }
    }

    @Override
    public boolean isRunning() {
        return false;
    }

    @Override
    public void setAlpha(int alpha) {

    }

    @Override
    public void setColorFilter(@Nullable ColorFilter colorFilter) {

    }

    @Override
    public int getOpacity() {
        return PixelFormat.OPAQUE;
    }
}
