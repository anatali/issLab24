package unibo.servicefacade24;

import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.AbstractWebSocketHandler;
import unibo.basicomm23.utils.CommUtils;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/*
Gestisce la websocket avendo come riferimento applicativo ApplguiCore
 */


public class WSHandler extends AbstractWebSocketHandler {
    private final List<WebSocketSession> sessions               = new ArrayList<>();
    private final Map<String, WebSocketSession> pendingRequests = new HashMap<>();

    private ApplguiCore guiCore;

    //Injiection
    public void setGuiCore(ApplguiCore gui) {
        guiCore = gui;
    }

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        sessions.add(session);
        CommUtils.outyellow("WSH | Added client session: " + session);
        super.afterConnectionEstablished(session);
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session,
                                      CloseStatus status) throws Exception {
        sessions.remove(session);
        pendingRequests.remove(session);
        System.out.println("WSH | Removed " + session);
        super.afterConnectionClosed(session, status);
    }

    // messaggi in arrivo da client
    @Override
    protected void handleTextMessage(WebSocketSession session,
                                     TextMessage message) {
        String msg = message.getPayload();
        CommUtils.outgreen("WSH | Received: " + msg);
        guiCore.handleWsMsg( msg ); //gestisce dorequest e docmd
    }

    protected synchronized  void sendToAll(String message) { //synchronized JAN24
        //Appena si collega alla appl remota, il CoAP observer riceve dati vecchi
        //e chiama  sendToAll prima amcora che un utente
        //abbia aperto la pagina con il browser e che quindi ci sia una WS
        CommUtils.outcyan("WSH | Sending to all " + message);
        try {
            if( sessions.size() > 0 ){
                for (WebSocketSession session : sessions) {
                    session.sendMessage(new TextMessage(message));
                    //CommUtils.outcyan("WSH | sent on current session " + session.getRemoteAddress());
                }
            }
            else{
                CommUtils.outred("WSH | Sorry: no session yet ...");
            }
            //qualcuno ha chiamato troppo presto
            /*
            new Thread() {
                public void run() {
                    try {
                            while (sessions.size() == 0) {
                                CommUtils.outcyan("WSH | waiting for session ... ");
                                Thread.sleep(1500);
                            }

                            for (WebSocketSession session : sessions) {
                                session.sendMessage(new TextMessage(message));
                                //CommUtils.outcyan("WSH | sent on current session " + session.getRemoteAddress());
                            }
                            CommUtils.outcyan("WSH | session done ");
                    } catch (Exception e) {
                            e.printStackTrace();
                    }
                }
            }.start();
           */
        } catch (Exception e) {
                CommUtils.outred("WSH | sendToAll " + message + " ERROR " + e.getMessage() );
        }
    }

    /*
    protected void sendToClient(String message, String requestId, Boolean requestComplete) {
        System.out.println("WSH | Sending to client " + message);
        WebSocketSession session = this.pendingRequests.get(requestId);
        try {
            session.sendMessage(new TextMessage(message));
        } catch (Exception e) {
            System.out.println("WSH | There was an error while sending the response " + message + " to " + session);
        }
        if (requestComplete) {
            this.pendingRequests.remove(requestId);
        }
    }

    protected String newRequest(WebSocketSession session) {
        String requestId = "req" + session.getId().substring(session.getId().lastIndexOf('-') + 1);
        pendingRequests.put(requestId, session);
        return requestId;
    }*/
}
