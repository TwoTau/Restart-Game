package org.spartabots.restart;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.view.View;

import java.util.LinkedList;


public class DrawingView extends View {
    private Paint paint;

    private int width = 15;
    private int height = width;

    private int cellWidth;

    public int playerNumber = 0;

    public LinkedList<Point> player1 = new LinkedList<Point>();
    public LinkedList<Point> player2 = new LinkedList<Point>();

    private Context c;
    public DrawingView(Context context, int width) {
        super(context);
        c = context;
        cellWidth = width / 15;

        player1.add(new Point(0, 4));
        player1.add(new Point(0, 3));
        player1.add(new Point(0, 2));
        player1.add(new Point(0, 1));
        player1.add(new Point(0, 0));

        player2.add(new Point(14, 10));
        player2.add(new Point(14, 11));
        player2.add(new Point(14, 12));
        player2.add(new Point(14, 13));
        player2.add(new Point(14, 14));

        paint = new Paint();
        paint.setColor(0xFFDDDDDD);
        paint.setAntiAlias(true);
    }
    @Override
    public void onDraw(Canvas canvas) {

        canvas.drawColor(Color.rgb(8, 8, 8));

        for(int i = 0; i < height; i++) {
            for(int j = 0; j < width; j++) {
                paint.setColor(0xFF333333);
                canvas.drawCircle((int) (cellWidth * (j+0.5)), (int) (cellWidth * (i+0.5)), cellWidth / 2, paint);
            }
        }

        for(int i = 0; i < player1.size(); i++) {
            Point p = player1.get(i);
            if(i == 0) {
                paint.setColor(0xFFEE0044);
            } else {
                paint.setColor(0xFFAA2266);
            }
            canvas.drawCircle((int) (cellWidth * (p.x+0.5)), (int) (cellWidth * (p.y+0.5)), cellWidth / 2, paint);
        }
        for(int i = 0; i < player2.size(); i++) {
            Point p = player2.get(i);
            if(i == 0) {
                paint.setColor(0xFF4411EE);
            } else {
                paint.setColor(0xFF7733AA);
            }
            canvas.drawCircle((int) (cellWidth * (p.x+0.5)), (int) (cellWidth * (p.y+0.5)), cellWidth / 2, paint);
        }

        invalidate();
    }

    private boolean isIntersecting(LinkedList<Point> ll1, LinkedList<Point> ll2) {
        for(int i = 0; i < ll1.size(); i++) {
            for(int j = 0; j < ll2.size(); j++) {
                if(ll1.get(i) == ll2.get(j)) {
                    return true;
                }
            }
        }
        return false;
    }

    private boolean isIntersecting(Point head, LinkedList<Point> ll1) {
        for(int i = 1; i < ll1.size(); i++) {
            if(head == ll1.get(i)) {
                return true;
            }
        }
        return false;
    }

    public LinkedList<Point> getThisPlayer() {
        return (playerNumber == 1) ? player1 : player2;
    }

    public boolean movePlayerLeft() {
        Point head = getThisPlayer().getFirst();
        if(head.x > 0 && !isIntersecting(new Point(head.x - 1, head.y), getThisPlayer())) {
            Point last = getThisPlayer().removeLast();
            last.set(head.x, head.y);
            getThisPlayer().addFirst(last);
            --last.x;
            return true;
        }
        return false;
    }
    public boolean movePlayerRight() {
        Point head = getThisPlayer().getFirst();
        if(head.x < width - 1 && !isIntersecting(new Point(head.x + 1, head.y), getThisPlayer())) {
            Point last = getThisPlayer().removeLast();
            last.set(head.x, head.y);
            getThisPlayer().addFirst(last);
            ++last.x;
            return true;
        }
        return false;
    }
    public boolean movePlayerDown() {
        Point head = getThisPlayer().getFirst();
        if(head.y < height - 1 && !isIntersecting(new Point(head.x, head.y + 1), getThisPlayer())) {
            Point last = getThisPlayer().removeLast();
            last.set(head.x, head.y);
            getThisPlayer().addFirst(last);
            ++last.y;
            return true;
        }
        return false;
    }
    public boolean movePlayerUp() {
        Point head = getThisPlayer().getFirst();
        if(head.y > 0  && !isIntersecting(new Point(head.x, head.y - 1), getThisPlayer())) {
            Point last = getThisPlayer().removeLast();
            last.set(head.x, head.y);
            getThisPlayer().addFirst(last);
            --last.y;
            return true;
        }
        return false;
    }

    public String getJSONPosition() {
        LinkedList<Point> player =  getThisPlayer();
        String json = "{\"position\":[";
        for(int i = 0; i < player.size(); i++) {
            json += "[" + player.get(i).x + "," + player.get(i).y + "]";
            if(i < player.size() - 1) {
                json += ",";
            }
        }
        return json + "]}";
    }
}