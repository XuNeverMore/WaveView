package com.xunevermore.waveview;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathMeasure;
import android.graphics.Rect;
import android.graphics.Region;
import android.graphics.Shader;
import android.graphics.SweepGradient;
import android.graphics.drawable.GradientDrawable;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.util.AttributeSet;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.animation.LinearInterpolator;

/**
 * Created by Administrator on 2017/12/14 0014.
 */

public class SWaveView extends SurfaceView implements SurfaceHolder.Callback{
    private ValueAnimator valueAnimator;
    private float fraction;


    private Path mPath;
    private float halfWaveLength = 500f;
    private float waveHeight = 100f;
    private Paint mPaint;
    private Region region;
    private int left;
    private int height;

    public SWaveView(Context context) {
        this(context,null);
    }

    public SWaveView(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public SWaveView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        getHolder().addCallback(this);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        height = h;
        halfWaveLength = w/2;
        mPath = new Path();
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
//        left = (int) (halfWaveLength*2-fraction);
        left=w/2;

        region = new Region(left,0, left +1,height);

    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        start();
    }

    private static final String TAG = "SWaveView";
    public void start(){

//        HandlerThread handlerThread = new HandlerThread("surcace_draw");
        Looper looper = Looper.myLooper();
        Log.i(TAG, "start: "+looper);
        Handler handler = new Handler();
        handler.post(new Runnable() {
            @Override
            public void run() {
                valueAnimator = ValueAnimator.ofFloat(halfWaveLength*2, 0);
                valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                    @Override
                    public void onAnimationUpdate(ValueAnimator animation) {
                        fraction = (float) animation.getAnimatedValue();


                        drawWave();
                    }
                });
                valueAnimator.setDuration(1500);
                valueAnimator.setInterpolator(new LinearInterpolator());
                valueAnimator.setRepeatCount(ValueAnimator.INFINITE);
                valueAnimator.start();
            }
        });

    }

    private void drawWave() {

        int y = getMeasuredHeight() / 2;
        mPath.reset();
        mPath.moveTo(-fraction, y);

        Canvas canvas = getHolder().lockCanvas();
        canvas.drawColor(Color.WHITE);
        float waveCount = (fraction+getMeasuredWidth())/halfWaveLength*2;
        int round = Math.round(waveCount);

        for (int i = 0; i < round; i++) {
            mPath.rQuadTo(halfWaveLength / 2, -waveHeight, halfWaveLength, 0);
            mPath.rQuadTo(halfWaveLength/2,waveHeight,halfWaveLength,0);
        }

        mPath.lineTo(getMeasuredWidth(),getMeasuredHeight());
        mPath.lineTo(0,getMeasuredHeight());
        mPath.close();

//        LinearGradient linearGradient = new LinearGradient(0,0,0,h,Color.GREEN,Color.BLUE, Shader.TileMode.CLAMP);
//        mPaint.setShader(linearGradient);
        mPaint.setColor(Color.BLACK);
        canvas.drawPath(mPath,mPaint);

        Region region = new Region();
        region.setPath(mPath,this.region);

        Rect bounds = region.getBounds();

        mPaint.setColor(Color.RED);
        canvas.drawCircle(left,bounds.top-20,20,mPaint);
        getHolder().unlockCanvasAndPost(canvas);
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {


    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {

        valueAnimator.cancel();
    }
}
