package org.spartabots.restart;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;

/**
 * TODO: document your custom view class.
 */
public class DrawingView extends View {
    private Paint paint;
    private Button aButton, bButton, upButton, downButton, leftButton, rightButton;
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
        Buttons();
        invalidate();
    }
    public void Buttons(){
        //a
        aButton = (Button) findViewById(R.id.aButton);

        aButton.setOnTouchListener(new View.OnTouchListener(){
            @Override
            public boolean onTouch(View v, MotionEvent event){

                return false;
            }
        });
        //b
        bButton = (Button) findViewById(R.id.bButton);

        bButton.setOnTouchListener(new View.OnTouchListener(){
            @Override
            public boolean onTouch(View v, MotionEvent event){

                return false;
            }
        });
        //up
        upButton = (Button) findViewById(R.id.upButton);

        upButton.setOnTouchListener(new View.OnTouchListener(){
            @Override
            public boolean onTouch(View v, MotionEvent event){

                return false;
            }
        });
        //down
        downButton = (Button) findViewById(R.id.downButton);

        downButton.setOnTouchListener(new View.OnTouchListener(){
            @Override
            public boolean onTouch(View v, MotionEvent event){

                return false;
            }
        });
        //right
        rightButton = (Button) findViewById(R.id.rightButton);

        rightButton.setOnTouchListener(new View.OnTouchListener(){
            @Override
            public boolean onTouch(View v, MotionEvent event){

                return false;
            }
        });
        //left
        leftButton = (Button) findViewById(R.id.leftButton);

        leftButton.setOnTouchListener(new View.OnTouchListener(){
            @Override
            public boolean onTouch(View v, MotionEvent event){

                return false;
            }
        });
    }
}
