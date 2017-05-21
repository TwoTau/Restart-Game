package org.spartabots.restart;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.ViewGroup;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private DrawingView drawingView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        DisplayMetrics metrics = getApplicationContext().getResources().getDisplayMetrics();
        int width = metrics.widthPixels;
        int height = metrics.heightPixels;

        drawingView = new DrawingView(this, width);

        drawingView.setLayoutParams(new ViewGroup.LayoutParams(width, width));

        setContentView(drawingView);

        drawingView.setOnTouchListener(new OnSwipeTouchListener(MainActivity.this) {
            public void onSwipeTop() {
                drawingView.movePlayerUp();
            }
            public void onSwipeRight() {
                drawingView.movePlayerRight();
            }
            public void onSwipeLeft() {
                drawingView.movePlayerLeft();
            }
            public void onSwipeBottom() {
                drawingView.movePlayerDown();
            }

        });
    }
}