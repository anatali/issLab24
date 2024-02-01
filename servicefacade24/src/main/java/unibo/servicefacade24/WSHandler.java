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

    private ApplguiCore applGuiCore;

    protected void setManager(ApplguiCore gui) {
        this.applGuiCore = gui;
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
        this.applGuiCore.handleWsMsg( msg ); //gestisce dorequest e docmd
        /*
        if( msg.startsWith("dorequest")) {
            this.applGuiCore.handleWsMsg(msg, newRequest(session));
        }else if( msg.startsWith("docmd")) {
            this.applGuiCore.clientCmd(msg, newRequest(session));
        }*/
    }

    protected synchronized  void sendToAll(String message) { //synchronized JAN24
        CommUtils.outcyan("WSH | Sending to all " + message);
        for (WebSocketSession session : sessions) {
            try {
                session.sendMessage(new TextMessage(message));
            } catch (Exception e) {
                CommUtils.outred("WSH | sendToAll " + message + " ERROR " + e.getMessage() );
            }
        }
    }

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
    }
}
