var express = require("express");
var app = express();

// configure express
// app.use(express.static(__dirname)); // static files are in the www folder

var server = app.listen(2976, function() {
    console.log("Listening on port 2976.");
});

var io = require("socket.io")(server);

function log(identifier, message) {
    var time = (new Date).toLocaleTimeString();
    console.log("[" + time + "] " + identifier + " " + message);
}

var numPlayers = 0;

var player1 = [0, 0, 0, 0, 0];
var player2 = [0, 0, 0, 0, 0];

var turn = 1;

io.on("connection", function(socket) {
    var ipAddress = socket.request.connection.remoteAddress.slice(2);
    var username = ipAddress; // username is ip address until changed
    var id;
	
	if(numPlayers === 0) {
		player1 = [[0, 4], [0, 3], [0, 2], [0, 1], [0, 0]];
		numPlayers = 1;
		socket.emit("setup", JSON.stringify({
			playernumber: 1
		}));
		id = 1;
	}
	else if(numPlayers === 1) {
		player2 = [[14, 10], [14, 11], [14, 12], [14, 13], [14, 14]];
		numPlayers = 2;
		
		socket.emit("setup", JSON.stringify({
			playernumber: 2
		}));
		
		id = 2;
		
		io.emit("gamestart", JSON.stringify({
			player1: player1,
			player2: player2,
			turn: turn
		}));		
	} else {
		socket.disconnect(0);
	}

    log(username, "connected");

    socket.on("position", function(pos) {
        console.log(pos);
		
		if(id === 1) {
			player1 = JSON.parse(pos).position;
		} else if(id === 2) {
			player2 = JSON.parse(pos).position;
		}
		
		turn += 1;
		
		io.emit("updatepos", JSON.stringify({
			player1: player1,
			player2: player2,
			turn: turn
		}));
    });

    socket.on("disconnect", function() {
        log(username, "disconnected");
    });
});