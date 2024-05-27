//guimain.js

const msgArea = document.getElementById("messageArea");
console.log("msgArea=" + msgArea)

var entryName = "accessgui"  //Lo stesso di WebSocketConfiguration.wsPath
var socket ;

function connect() {
    const host     = document.location.host;

    const pathname = ""; //document.location.pathname;

    const addr     = "ws://" + host + pathname + "/"+entryName;
    console.log("connect addr="+addr)
    // Assicura che sia aperta un unica connessione
    if (socket !== undefined && socket.readyState !== WebSocket.CLOSED) {
        alert("WARNING: Connessione WebSocket già stabilita");
    }
    socket = new WebSocket(addr); // CONNESSIONE

    socket.onopen = function (event) {
        showMsg("Connessione " + addr + " ok");
    };
    socket.onerror = function (event) {
        showMsg("ERROR " + event.data);
    };
    socket.onmessage = function (event) { // RICEZIONE
    try{
        //let [type, payload, info] = event.data.split("/");
        v = event.data
       console.log("onmessage event=" + v)
       if( v.includes("nonews") ) return
       if( v.includes("confirm") ) showConfirmation(v)
       else showMsg( `${v}`)
    }catch( e){
        alert(e);
    }
    };
    //return socket;
}

function showConfirmation(s) {
    // Mostra il popup con il messaggio e i pulsanti Yes/No
    const confirmation = window.confirm(s);

    // Verifica la risposta
    if (confirmation) {
      //alert("Hai cliccato Yes!");
      showMsg( `confirm yes`)
      submitReply("yes|"+s)
    } else {
      //alert("Hai cliccato No!");
      showMsg( `confirm no`)
      submitReply("no|"+s)
    }
 }

function submitRequest() {
     console.log("submitRequest "+ requestInput.value);
    sendMessage("request/" + requestInput.value); //arriva a ApplGuiCore via WSHandler
    requestInput.value = "";
}

function submitReply(v) { //vedi ApplGuiCore
     console.log("submitReply "+ v);
    sendMessage("reply/" + v); //arriva a ApplGuiCore 62 via WSHandler
}

function submitRequestInfo() {
     console.log("submitRequestInfo " );
    sendMessage("requestInfo/actors"  ); //arriva a ApplGuiCore via WSHandler

}

function submitTheCmd() {
      //console.log("submitCmd "+ cmdInput.value);
      sendMessage("cmd/" + cmdInput.value);
      cmdInput.value = "";
}

function submitTheExit() {
      sendMessage("exit/ok"  );
      cmdInput.value = "";
}

function sendMessage(message) {
    console.log("sendMessage "+ message);
    socket.send(message);
}

function showMsg(message) {
  msgArea.innerHTML +=  message + "\n"
}

function clear_messageArea(){
    msgArea.innerHTML = ""
}
function exit(){

}

$(function () {
	$( "#lsocket" ).click(function() { doMove("cmd(l)", 300);    })
	$( "#rsocket" ).click(function() { doMove("cmd(r)", 300);    })
	$( "#wsocket" ).click(function() { doMove("cmd(w)", 350);  })
	$( "#ssocket" ).click(function() { doMove("cmd(s)", 350);  })
	$( "#hsocket" ).click(function() { doMove("cmd(h)", 100);         })
});

    function doMove(move, time) {
        const moveJson = '{"cmd":"'+ move + '", "time":'+time+'}'
        console.log("doMove moveJson:" + moveJson + " socket=" + socket);
        if (socket && socket.bufferedAmount == 0) {
            socket.send(moveJson)
            addMessageToWindow(move + " done\n")
        }
    }

    function addMessageToWindow(message) {
        messageArea.innerHTML += `${message}`
        //messageWindow.innerHTML = `<div>${message}</div>`
    }
//connect() //con la WS della GUI