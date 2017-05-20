package org.spartabots.restart;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.ViewGroup;

public class MainActivity extends AppCompatActivity {

    private DrawingView drawingView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        DisplayMetrics metrics = getApplicationContext().getResources().getDisplayMetrics();
        int width = metrics.widthPixels;

        drawingView = new DrawingView(this, width);

        drawingView.setLayoutParams(new ViewGroup.LayoutParams(width, width));

        setContentView(drawingView);
    }
}