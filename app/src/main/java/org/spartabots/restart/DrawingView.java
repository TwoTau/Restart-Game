package org.spartabots.restart;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.util.zip.Inflater;

public class DrawingView extends View {
    private Paint paint;
    private int[][] board = new int[21][21];

    private int cellWidth;

    private int playerX = 10;
    private int playerY = 20;

    private int levelNumber = 0;

    private boolean revealEverything = true;

    private final int EMPTY_CODE = 0;
    private final int DOOR_CODE = 1;
    private final int KILLBOX_CODE = 2;
    private final int TELEPORTER_CODE = 3;
    private final int BACK_CODE = 4;
    private final int LOCKDOOR_CODE = 5;
    private final int KEY_CODE = 6;

    private boolean key = false;

    private Context c;
    public DrawingView(Context context, int width) {
        super(context);
        c = context;
        cellWidth = width / 21;

        loadLevel();

        paint = new Paint();
        paint.setColor(0xFFDDDDDD);
        paint.setAntiAlias(true);


    }
    @Override
    public void onDraw(Canvas canvas) {

        canvas.drawColor(Color.rgb(8, 8, 8));

        paint.setColor(0xFFFFFFFF);
        canvas.drawCircle((int) (cellWidth * (playerX + 0.5)), (int) (cellWidth * (playerY + 0.5)), cellWidth / 2, paint);
        for(int i = 0; i < board.length; i++) {
            for(int j = 0; j < board[i].length; j++) {
                if(board[i][j] == DOOR_CODE) { // door
                    paint.setColor(0xFF43DD53);
                    canvas.drawCircle((int) (cellWidth * (j+0.5)), (int) (cellWidth * (i+0.5)), cellWidth / 2, paint);
                }
                else if(revealEverything) {
                    if(board[i][j] == EMPTY_CODE) { // empty
                        paint.setColor(0xFF333333);
                        canvas.drawCircle((int) (cellWidth * (j+0.5)), (int) (cellWidth * (i+0.5)), cellWidth / 2, paint);
                    }
                    else if(board[i][j] == KILLBOX_CODE) {
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

        paint.setColor(0xFFEEEEEE);
        canvas.drawCircle((int) (cellWidth * (playerX+0.5)), (int) (cellWidth * (playerY+0.5)), cellWidth / 2, paint);
        playerTrack();
        invalidate();
    }
    public void movePlayerLeft() {
        if(playerX > 0) {
            --playerX;
        }
    }
    public void movePlayerRight() {
        if(playerX < board[0].length - 1) {
            ++playerX;
        }
    }
    public void movePlayerDown() {
        if(playerY < board.length - 1) {
            ++playerY;
        }
    }
    public void movePlayerUp() {
        if(playerY > 0) {
            --playerY;
        }
    }
    public void setPlayerPos(int newX, int newY) {
        playerX = newX;
        playerY = newY;
    }
    private void resetBoard() {
        for(int i = 0; i < board.length; i++) {
            for(int j = 0; j < board[i].length; j++) {
                board[i][j] = EMPTY_CODE;
            }
        }

        setPlayerPos(3, 6);

        board[0][3] = DOOR_CODE; // door
    }

    private void loadLevel() {
        resetBoard();
        String levelName = "Level:" + levelNumber;
        Toast.makeText(c, levelName, Toast.LENGTH_SHORT).show();
        if(levelNumber == 1) {
            // do nothing
        }
        else if(levelNumber == 2) { // cell in front of player is killer
            board[0][3] = LOCKDOOR_CODE;
            int randomX = (int) Math.random()*board[0].length;
            int randomY = (int) Math.random()*board.length;
            board[randomX][randomY] = KEY_CODE;
        }
        else if(levelNumber == 3) { // cell in front of player is teleporter
            board[5][3] = BACK_CODE;
        }
    }
    private void playerTrack(){
        System.out.println("hallo");
        if(board[playerY][playerX] == KILLBOX_CODE){
            loadLevel();
        } else if(board[playerY][playerX] == DOOR_CODE){
            levelNumber++;
            loadLevel();
        } else if(board[playerY][playerX] == BACK_CODE) {

        } else if (board[playerY][playerX] == TELEPORTER_CODE) {

        } else if(board[playerY][playerX] == LOCKDOOR_CODE) {
            Toast.makeText(c, "Door is locked. Find the key.", Toast.LENGTH_SHORT).show();
            if(key){
                key = false;
                levelNumber++;
                loadLevel();
            }
        } else if(board[playerY][playerX] == KEY_CODE){
            Toast.makeText(c, "Key Acquired", Toast.LENGTH_SHORT).show();
            key = true;
            board[playerY][playerX] = EMPTY_CODE;
        }
    }
}