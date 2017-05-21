package org.spartabots.restart;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;

public class DrawingView extends View {
    private Paint paint;
    private Button aButton, bButton, upButton, downButton, leftButton, rightButton;
    private int[][] board = new int[7][7];

    private int cellWidth;

    private int playerX = 3;
    private int playerY = 6;

    private boolean revealEverything = true;

    private final int EMPTY_CODE = 0;
    private final int PLAYER_CODE = 1;
    private final int DOOR_CODE = 2;
    private final int KILLBOX_CODE = 3;
    private final int TELEPORTER_CODE = 4;

    public DrawingView(Context context, int width) {
        super(context);

        cellWidth = width / 7;

        loadLevel(2);

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
        for(int i = 0; i < board.length; i++) {
            for(int j = 0; j < board[i].length; j++) {
                if(board[i][j] == EMPTY_CODE) { // empty
                    paint.setColor(0xFF333333);
                    canvas.drawCircle((int) (cellWidth * (j+0.5)), (int) (cellWidth * (i+0.5)), cellWidth / 2, paint);
                }
                else if(board[i][j] == PLAYER_CODE) { // player
                    paint.setColor(0xFFEEEEEE);
                    canvas.drawCircle((int) (cellWidth * (j+0.5)), (int) (cellWidth * (i+0.5)), cellWidth / 2, paint);
                }
                else if(board[i][j] == DOOR_CODE) { // door
                    paint.setColor(0xFF43DD53);
                    canvas.drawCircle((int) (cellWidth * (j+0.5)), (int) (cellWidth * (i+0.5)), cellWidth / 2, paint);
                }
                else if(revealEverything) {
                    if(board[i][j] == KILLBOX_CODE) {
                        paint.setColor(0xFFEE1133);
                        canvas.drawCircle((int) (cellWidth * (j+0.5)), (int) (cellWidth * (i+0.5)), cellWidth / 2, paint);
                    }
                    else if(board[i][j] == TELEPORTER_CODE) {
                        paint.setColor(0xFF4466EE);
                        canvas.drawCircle((int) (cellWidth * (j+0.5)), (int) (cellWidth * (i+0.5)), cellWidth / 2, paint);
                    }
                }
            }
        }

        invalidate();
    }

    private void movePlayerLeft() {
        if(playerX > 0) {
            --playerX;
        }
    }
    private void movePlayerRight() {
        if(playerX < 7) {
            ++playerX;
        }
    }
    private void movePlayerDown() {
        if(playerY < 7) {
            ++playerY;
        }
    }
    private void movePlayerUp() {
        if(playerY > 0) {
            --playerY;
        }
    }

    private void setPlayerPos(int newX, int newY) {

    }

    private void resetBoard() {
        for(int i = 0; i < board.length; i++) {
            for(int j = 0; j < board[i].length; j++) {
                board[i][j] = 0;
            }
        }

        board[6][3] = PLAYER_CODE; // player
        board[0][3] = DOOR_CODE; // door
    }

    private void loadLevel(int levelNumber) {
        resetBoard();
        if(levelNumber == 1) {
            // do nothing
        }
        else if(levelNumber == 2) { // cell in front of player is killer
            board[5][3] = KILLBOX_CODE;
        }
        else if(levelNumber == 3) { // cell in front of player is teleporter
            board[5][3] = TELEPORTER_CODE;
        }
    }
    public void Buttons() {
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