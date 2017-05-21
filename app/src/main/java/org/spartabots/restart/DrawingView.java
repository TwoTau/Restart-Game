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
    private int[][] board = new int[7][7];

    private int cellWidth;

    private int playerX = 3;
    private int playerY = 6;

    private boolean revealEverything = true;

    private final int EMPTY_CODE = 0;
    private final int DOOR_CODE = 1;
    private final int KILLBOX_CODE = 2;
    private final int TELEPORTER_CODE = 3;

    public DrawingView(Context context, int width) {
        super(context);

        setWillNotDraw(false);

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

        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                if (board[i][j] == EMPTY_CODE) {
                    paint.setColor(0xFF333333);
                    canvas.drawCircle((int) (cellWidth * (j + 0.5)), (int) (cellWidth * (i + 0.5)), cellWidth / 2, paint);
                } else if (board[i][j] == DOOR_CODE) {
                    paint.setColor(0xFF43DD53);
                    canvas.drawCircle((int) (cellWidth * (j + 0.5)), (int) (cellWidth * (i + 0.5)), cellWidth / 2, paint);
                } else if (revealEverything) {
                    if (board[i][j] == KILLBOX_CODE) {
                        paint.setColor(0xFFEE1133);
                        canvas.drawCircle((int) (cellWidth * (j + 0.5)), (int) (cellWidth * (i + 0.5)), cellWidth / 2, paint);
                    } else if (board[i][j] == TELEPORTER_CODE) {
                        paint.setColor(0xFF4466EE);
                        canvas.drawCircle((int) (cellWidth * (j + 0.5)), (int) (cellWidth * (i + 0.5)), cellWidth / 2, paint);
                    }
                }
            }

            // draw the player
            paint.setColor(0xFFEEEEEE);
            canvas.drawCircle((int) (cellWidth * (playerX + 0.5)), (int) (cellWidth * (playerY + 0.5)), cellWidth / 2, paint);
        }

        invalidate();
    }

    public String getPlayerPos() {
        return "(" + playerX + "," + playerY + ")";
    }

    public void movePlayerLeft() {
        if (playerX > 0) {
            --playerX;
        }
    }

    public void movePlayerRight() {
        if (playerX < 6) {
            ++playerX;
        }
    }

    public void movePlayerDown() {
        if (playerY < 6) {
            ++playerY;
        }
    }

    public void movePlayerUp() {
        if (playerY > 0) {
            --playerY;
        }
    }

    private void setPlayerPos(int newX, int newY) {
        playerX = newX;
        playerY = newY;
    }

    private void resetBoard() {
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                board[i][j] = EMPTY_CODE;
            }
        }

        board[0][3] = DOOR_CODE; // door
    }

    private void loadLevel(int levelNumber) {
        resetBoard();
        if (levelNumber == 1) {
            // do nothing
        } else if (levelNumber == 2) { // cell in front of player is killer
            board[5][3] = KILLBOX_CODE;
        } else if (levelNumber == 3) { // cell in front of player is teleporter
            board[5][3] = TELEPORTER_CODE;
        }
    }
}