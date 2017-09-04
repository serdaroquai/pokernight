var stompClient = null;
var name = getURLParameter("name"); // playerName

function connect() {
    var socket = new SockJS('/pokerNight');
    stompClient = Stomp.over(socket);
    stompClient.connect({}, function (frame) {
        console.log('Connected: ' + frame);
        stompClient.subscribe('/topic/greetings', function (greeting) {
        	updateGame(JSON.parse(greeting.body));
        });
//        stompClient.subscribe('/topic/greetings', function (greeting) {
//        	updateGame(JSON.parse(greeting.body));
//        });
    });
}

function disconnect() {
    if (stompClient != null) {
        stompClient.disconnect();
    }
    console.log("Disconnected");
}


function sendAction(action) {
//    stompClient.send("/app/hello", {}, JSON.stringify({'name': $("#name").val()}));
	stompClient.send("/app/hello", {}, action);
}

function getURLParameter(name) {
	return decodeURIComponent((new RegExp('[?|&]' + name + '=' + '([^&;]+?)(&|#|;|$)').exec(location.search) || [null, ''])[1].replace(/\+/g, '%20')) || null;
}


