package com.xunevermore.waveview;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class LaunchActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launch);
    }

    public void wave(View view) {
        goActivity(MainActivity.class);
    }

    private void goActivity(Class<? extends Activity> clazz){
        startActivity(new Intent(this,clazz));
    }

    public void word(View view) {
        goActivity(DrawActivity.class);
    }
}
