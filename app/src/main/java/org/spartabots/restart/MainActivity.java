package org.spartabots.restart;

import android.graphics.Point;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.ViewGroup;
import android.widget.Toast;

import com.github.nkzawa.emitter.Emitter;
import com.github.nkzawa.socketio.client.IO;
import com.github.nkzawa.socketio.client.Socket;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.URISyntaxException;
import java.util.LinkedList;

public class MainActivity extends AppCompatActivity {

    private DrawingView drawingView;

    public boolean myTurn = false;

    private Socket mSocket;
    {
        try {
            mSocket = IO.socket("http://192.168.129.8:2976");
        } catch (URISyntaxException e) {}
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        int width = getApplicationContext().getResources().getDisplayMetrics().widthPixels;

        drawingView = new DrawingView(this, width);

        drawingView.setLayoutParams(new ViewGroup.LayoutParams(width, width));

        setContentView(drawingView);

        connectWebSocket();

        drawingView.setOnTouchListener(new OnSwipeTouchListener(MainActivity.this) {
            public void onSwipeTop() {
                if(myTurn) {
                    drawingView.movePlayerUp();
                    endTurn();
                }
            }
            public void onSwipeRight() {
                if(myTurn) {
                    drawingView.movePlayerRight();
                    endTurn();
                }
            }
            public void onSwipeLeft() {
                if(myTurn) {
                    drawingView.movePlayerLeft();
                    endTurn();
                }
            }
            public void onSwipeBottom() {
                if(myTurn) {
                    drawingView.movePlayerDown();
                    endTurn();
                }
            }

        });
    }

    private void connectWebSocket() {
        mSocket.on("setup", onSetup);
        mSocket.on("gamestart", onGamestart);
        mSocket.on("updatepos", onUpdate);
        mSocket.connect();
    }

    private void endTurn() {
        mSocket.emit("position", drawingView.getJSONPosition());
        myTurn = false;
    }

    private Emitter.Listener onSetup = new Emitter.Listener() {
        @Override
        public void call(Object... args) {
            int data = 0;
            try {
                JSONObject data2 = new JSONObject(args[0].toString());
                data = data2.getInt("playernumber");
            } catch (JSONException e) {
                e.printStackTrace();
            }
            drawingView.playerNumber = data;
            System.out.println("Socket: SETUP COMPLETE. I am #" + data);
        }
    };

    private Emitter.Listener onGamestart = new Emitter.Listener() {
        @Override
        public void call(Object... args) {
            JSONArray data = new JSONArray();
            try {
                JSONObject data2 = new JSONObject(args[0].toString());
                data = data2.getJSONArray("player" + ((drawingView.playerNumber == 1) ? "2": "1"));
            } catch (JSONException e) {
                e.printStackTrace();
            }

            if(drawingView.playerNumber == 1) {
                myTurn = true;
            }

            System.out.println("Socket: GAME STARTED: " + data);
        }
    };

    private Emitter.Listener onUpdate = new Emitter.Listener() {
        @Override
        public void call(Object... args) {
            JSONArray data = new JSONArray();
            int turnNumber = 0;
            try {
                JSONObject data2 = new JSONObject(args[0].toString());
                data = data2.getJSONArray("player" + ((drawingView.playerNumber == 1) ? "2": "1"));
                turnNumber = data2.getInt("turn");
            } catch (JSONException e) {
                e.printStackTrace();
            }

            if((turnNumber % 2 == 0 && drawingView.playerNumber == 2) || (turnNumber % 2 == 1 && drawingView.playerNumber == 1)) {
                myTurn = true;
            }

            LinkedList<Point> updatedPlayer = new LinkedList<Point>();

            for(int i = 0; i < data.length(); i++) {
                try {
                    String[] arrayThing = data.get(i).toString().split(",");
                    int x = Integer.parseInt(arrayThing[0].substring(1, arrayThing[0].length()));
                    int y = Integer.parseInt(arrayThing[1].substring(0, arrayThing[1].length() - 1));
                    updatedPlayer.add(new Point(x, y));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            if(drawingView.playerNumber == 1) {
                drawingView.player2 = updatedPlayer;
            } else {
                drawingView.player1 = updatedPlayer;
            }

            System.out.println("Socket: UPDATED: " + data);
        }
    };


}