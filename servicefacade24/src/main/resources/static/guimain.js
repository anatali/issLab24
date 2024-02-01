//guimain.js

const msgArea = document.getElementById("messageArea");
console.log("msgArea=" + msgArea)

const outdevArea = document.getElementById("outdevArea");
console.log("outdevArea=" + outdevArea)

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
        addTo_msgArea("Connessione " + addr + " ok");
    };
    socket.onerror = function (event) {
        addTo_msgArea("ERROR " + event.data);
    };
    socket.onmessage = function (event) { // RICEZIONE
        //let [type, payload, info] = event.data.split("/");
        console.log("main onmessage event=" + event.data)
        showMsg( `${event.data}`)
    };
    return socket;
}


function submitRequest() {

/*
    if (requestInput.value.length === 0) {
        addTo_msgArea("Inserisci un dato")
    } else { */
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
      //console.log("submitCmd "+ cmdInput.value);
      sendMessage("exit/ok"  );
      cmdInput.value = "";
}

function sendMessage(message) {
    console.log("sendMessage "+ message);
    socket.send(message);
}

function showMsg(message) {
    if( message.startsWith("show")){
        var msg = message.replace("show(","")
        msg     = msg.substring(0, msg.lastIndexOf(")")) + ""
        addTo_outdevArea(msg)
    }
    else  if( message.startsWith("out")){
        var msg = message.replace("out(","")
        msg     = msg.substring(0, msg.lastIndexOf(")")) + ""
        addTo_msgArea(msg)
    }
    else addTo_msgArea(message)
}

function addTo_msgArea(message) {
    //msgArea.innerHTML += "<div class=\"testo\">" + message + "</div>"
    msgArea.innerHTML +=  message + "\n"
}

function addTo_outdevArea(message) {
    //outdevArea.innerHTML += "<div class=\"testo\">" + message + "</div>"
     outdevArea.innerHTML +=  message + "\n"
}

function clear_outdevArea(){
    outdevArea.innerHTML = ""
}

function clear_messageArea(){
    msgArea.innerHTML = ""
}
function exit(){

}