package unibo.basicomm23.ws;
import unibo.basicomm23.utils.CommUtils;

import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;

/*
This class level annotation declares that the class it decorates
is a web socket endpoint that will be deployed and made available
in the URI-space of a web socket server.
The annotation allows the developer to define the URL (or URI template)
which this endpoint will be published, and other important properties
of the endpoint to the websocket runtime,
such as the encoders it uses to send messages.

Occorre un WebApplication

The annotated class must have a public no-arg constructor.
*/

@ServerEndpoint(value = "/test")
public class WsServer   {

    public WsServer(int port) {
        CommUtils.outblue("WsServer STARTS on port=" + port);
    }

    @OnOpen
    public void onOpen(Session session) {
        CommUtils.outblue("WsServer open session=" + session);
    }
    @OnMessage
    public String onMessage(String message, Session session) {
        // Metodo eseguito alla ricezione di un messaggio
        // La stringa 'message' rappresenta il messaggio
        // Il valore di ritorno di questo metodo sara' automaticamente
        // inviato come risposta al client. Ad esempio:
        CommUtils.outblue("WsServer onMessage message=" + message);
        return "WsServer received [" + message + "]";
    }
    @OnClose
    public void onClose(Session session) {
        CommUtils.outblue("WsServer onClose session=" + session);
    }
    @OnError
    public void onError(Throwable exception, Session session) {
        CommUtils.outred("WsServer onError session=" + session + " " + exception.getMessage() );
    }

    public static void main(String[] args) {
        int port = 8080; // Porta del WsServer
        //CommUtils.outblue("WsServer STARTS on port=" + port);
        WsServer server = new WsServer(port);
        try {
            Thread.sleep(1200000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        CommUtils.outblue("WsServer ENDS "  );
        //server.start();
     }

    /*
    Usage: var socket = new WebSocket("ws:/X.X.X.X:8080/MyServer/test/");
     */
}
