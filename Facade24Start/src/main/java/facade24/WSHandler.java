package main.java.facade24;

import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.AbstractWebSocketHandler;
import unibo.basicomm23.interfaces.IApplMessage;
import unibo.basicomm23.msg.ApplMessage;
import unibo.basicomm23.utils.CommUtils;

import java.io.IOException;
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
    private final Map<String, WebSocketSession> curSessions     = new HashMap<>();
    private ApplguiCore guiCore;

    private static int namecounter = 1;  

    //Injiection
    public void setGuiCore(ApplguiCore gui) {
        guiCore = gui;
    }

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        sessions.add(session);
        CommUtils.outyellow("WSH | Added client session id=" + session.getId() + " " + session.getRemoteAddress());
        //curSessions.put(session.getId(),session);
        super.afterConnectionEstablished(session);
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session,
                                      CloseStatus status) throws Exception {
        sessions.remove(session);
        pendingRequests.remove(session);
        curSessions.remove( session.getId() );
        System.out.println("WSH | Removed " + session);
        super.afterConnectionClosed(session, status);
    }

    // messaggi in arrivo da client
    @Override
    protected void handleTextMessage(WebSocketSession session,
                                     TextMessage message) {
        String msg = message.getPayload();
        CommUtils.outgreen("WSH | Received: " + msg + " " + session.getId()) ;
        String sendermockname  = "gui" + namecounter++;
        CommUtils.outgreen("WSH | sendermockname: " + sendermockname) ;
        //Memorizzo la sessione del sendermockname
        curSessions.put(sendermockname,session);
        guiCore.handleWsMsg( sendermockname,  msg ); //gestisce dorequest e docmd e reply ad ask
    }

    protected  void sendToOne(String msg) {
        //CommUtils.outcyan("WSH | sendToOne " + msg  );
        IApplMessage msgqak      = new ApplMessage(msg);
        CommUtils.outcyan("WSH | sendToOne " + msgqak  );
        WebSocketSession session = curSessions.get( msgqak.msgReceiver() );
        if( session != null ) {
            try {
                session.sendMessage( new TextMessage(msgqak.msgContent()) );
            } catch (IOException e) {
                CommUtils.outred("WSH | sendToOne " + msg + " ERROR " + e.getMessage());
            }
        }else{
            CommUtils.outred("WSH | sendToOne " + msg + " session not found "  );
        }
    }

    protected  void sendToOne(IApplMessage msg) {
        CommUtils.outred("WSH | sendToOne " + msg  + " SENDER=" + msg.msgSender()  );
        WebSocketSession session = curSessions.get( msg.msgReceiver() );
        if( session != null ) {
            try {
                session.sendMessage( new TextMessage(msg.msgContent()) );
            } catch (IOException e) {
                CommUtils.outred("WSH | sendToOne " + msg + " ERROR " + e.getMessage());
            }
        }else{
            CommUtils.outred("WSH | sendToOne " + msg + " session not found "  );
        }

    }


    protected synchronized  void sendToAll(String message) { //synchronized JAN24
        //Appena si collega alla appl remota, il CoAP observer riceve dati vecchi
        //e chiama  sendToAll prima amcora che un utente
        //abbia aperto la pagina con il browser e che quindi ci sia una WS
        CommUtils.outcyan("WSH | Sending to all " + message);
        try {
            if( sessions.size() > 0 ){
                for (WebSocketSession session : sessions) {
                    session.sendMessage(new TextMessage("WSH> "+message));
                    //CommUtils.outcyan("WSH | sent on current session " + session.getRemoteAddress());
                }
            }
            else{
                //CommUtils.outred("WSH | Sorry: no session yet ...");
            }
 
        } catch (Exception e) {
                CommUtils.outred("WSH | sendToAll " + message + " ERROR " + e.getMessage() );
        }
    }
 
}
