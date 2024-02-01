//guimain.js

const msgArea = document.getElementById("messageArea");
console.log("msgArea=" + msgArea)

var socket = connect();


function connect() {
    const host     = document.location.host;
    const pathname = document.location.pathname;
    const addr     = "ws://" + host + pathname + "accessgui";
    console.log("connect addr="+addr)
    // Assicura che sia aperta un unica connessione
    if (socket !== undefined && socket.readyState !== WebSocket.CLOSED) {
        alert("WARNING: Connessione WebSocket già stabilita");
    }
    socket = new WebSocket(addr); // CONNESSIONE

    socket.onopen = function (event) {
        //addTo_msgArea("Connessione " + addr + " ok");
        showMsg("Connessione " + addr + " ok");
    };
    socket.onerror = function (event) {
        //addTo_msgArea("ERROR " + event.data);
        showMsg("ERROR " + event.data);
    };
    socket.onmessage = function (event) { // RICEZIONE
        //let [type, payload, info] = event.data.split("/");
        console.log("main onmessage event=" + event.data)
        showMsg( `${event.data}`)
    };
    return socket;
}


function submitRequest() {
     console.log("submitRequest "+ requestInput.value);
    sendMessage("request/" + requestInput.value); //arriva a ActorOutIn
    requestInput.value = "";

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