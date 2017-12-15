package com.xunevermore.waveview;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;

import com.xunevermore.waveview.drawable.TextDrawable;

public class DrawActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_draw);
        ImageView iv = (ImageView) findViewById(R.id.imageView);
        TextDrawable drawable = new TextDrawable("徐影魔");
        iv.setImageDrawable(drawable);

        drawable.start();

        ImageView iv2 = (ImageView) findViewById(R.id.imageView2);

        TextDrawable android = new TextDrawable("Android");
        iv2.setImageDrawable(android);
        android.start();
    }
}
