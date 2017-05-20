package org.spartabots.restart;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.drawable.Drawable;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.View;

import java.lang.reflect.Array;
import java.util.ArrayList;

/**
 * TODO: document your custom view class.
 */
public class DrawingView extends View {
    private Paint paint;

    int[][] board = new int[7][7];

    int cellWidth;

    int playerX = 3;
    int playerY = 6;

    public DrawingView(Context context, int width) {
        super(context);

        cellWidth = width / 7;

        for(int i = 0; i < board.length; i++) {
            for(int j = 0; j < board[i].length; j++) {
                board[i][j] = 0;
            }
        }

        board[6][3] = 1; // player
        board[0][3] = 2; // door

        paint = new Paint();
        paint.setColor(0xFFDDDDDD);
        paint.setAntiAlias(true);
    }

    @Override
    public void onDraw(Canvas canvas) {

        canvas.drawColor(Color.rgb(8, 8, 8));

        paint.setColor(0xFFFFFFFF);
        canvas.drawCircle((int) (cellWidth * (playerX + 0.5)), (int) (cellWidth * (playerY + 0.5)), cellWidth / 2, paint);

        invalidate();
    }
}
