/*
wsminimal.js
*/

    var socket;

    function sendMessage(message) {
        var jsonMsg = JSON.stringify( {'name': message});
        socket.send(jsonMsg);
        console.log("Sent Message: " + jsonMsg);
    }

    function connect(){
        var host     =  document.location.host;
        var pathname =  "/"                   //document.location.pathname;
        var addr     = "ws://" +host  + pathname + "accessgui"  ;
        //alert("connect addr=" + addr   );

        // Assicura che sia aperta un unica connessione
        if(socket !== undefined && socket.readyState !== WebSocket.CLOSED){
             alert("WARNING: Connessione WebSocket già stabilita");
        }
        socket = new WebSocket(addr);

        socket.onopen = function (event) {
            //console.log("Connected to " + addr);
            setMessageToWindow(infoDisplay,"socket | Connected to " + addr);
        };

//Sulla WS scrive WSHandler
        socket.onmessage = function (event) {
             msg = event.data;
            //alert(`Got Message: ${msg}`);
            console.log("ws-status:" + msg);
            if( msg.includes("plan") ) addMessageToWindow(planexecDisplay,msg); //in ioutils
            //else if( msg.includes("RobotPos") ) setMessageToWindow(robotDisplay,msg);
            else //setMessageToWindow(robotDisplay,msg); //""+`${event.data}`*/
            addToMessageArea( msg );
         };
    }//connect

