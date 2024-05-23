package main.java.facade24;
import unibo.basicomm23.interfaces.IApplMessage;
import unibo.basicomm23.utils.CommUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/*
Logica applicativa (domain core) della gui
Creata da ServiceFacadeController usando FacadeBuilder
 */
public class ApplguiCore {
    private   ActorOutIn outinadapter;
    private String reqid      = "dofibo";           //config.get(6); CHE NE SA?
    private String reqcontent = "dofibo(X)";       //config.get(7);
    private  String destActor = "";

    public ApplguiCore( ActorOutIn outinadapter ) {
        this.outinadapter = outinadapter;
        ApplSystemInfo.setup();
        destActor         = ApplSystemInfo.applActorName;
    }

    //Chiamato da CoapObserver
    public void handleMsgFromActor(String msg, String requestId) {
        CommUtils.outcyan("AGC | hanldeMsgFromActor " + msg + " requestId=" + requestId) ;
        updateMsg( msg );
    }
    public void handleReplyMsg( String msg) { //IApplMessage msg
        CommUtils.outcyan("AGC | handleReplyMsg " + msg  ) ;
        //Mando la risppsta alla ws-conn del browser che ha fatto la richiesta
        //updateMsg( msg  );
        outinadapter.sendToOne(msg);
    }
    public void handleReplyMsg( IApplMessage msg) {
        CommUtils.outcyan("AGC | handleReplyMsg " + msg  ) ;
        //Mando la risppsta alla ws-conn del browser che ha fatto la richiesta
        outinadapter.sendToOne(msg);
    }
    public void updateMsg( String msg ) {
        CommUtils.outblue("AGC updateMsg " + msg);
        outinadapter.sendToAll(msg);
        //potrei mandare a M2M ... che poi manda la risposta a REST POST
        //M2MController.m2mCtrl.setAnswer(msg);
    }

    public void handleWsMsg(String id, String msg) {
        CommUtils.outcyan("AGC | handleWsMsg msg " + msg  );
        String[] parts = msg.split("/");
        String message = parts[0];
        String payload = parts[1];
        CommUtils.outcyan("AGC | handleWsMsg " + message + " from " + id );

        switch (message) {
             case "request":
                dorequest(id,payload );
                break;
            case "cmd":
                docmd(id,payload );
                break;
            case "reply":
                doreply(id,payload);
                 break;
            case "requestInfo":
                dorequestInfo();
                break;
            case "exit":
                System.exit(0);
                break;
            default:
                break;
        }
    }
 

    public void dorequest(String id,String payload ) { //public per M2MController
        if( payload.startsWith("dofibo")){ //payload from M2M
            outinadapter.dorequest(id,destActor,reqid,payload );
        }else { //payload from GUI
            outinadapter.dorequest(
               id, destActor, reqid, reqcontent.replace("X", payload));
        }
    }
    private void docmd(String id,String payload ) {
        //outinadapter.docmd(payload );
    }
    private void doreply(String id,String payload ) {
        CommUtils.outred("AGC | doreply id=" + id + " payload=" + payload );
        //payload=yes|confirm(DYNAMICACTORNAME,N)
        String[] pa = payload.split("\\|");
        CommUtils.outcyan("AGC | pa[1] " + pa[0] + " " + pa[1] );
       String destName =  pa[1].replace("confirm(","").split(",")[0];
        CommUtils.outcyan("AGC | doreply " + destName );
        //Non destActor, ma il nome del dynamic che ha fatto la ask Got response
        outinadapter.doreply(id,destName,"confirmed", "confirmed(P)".replace("P",pa[0]) );
    }
    private void dorequestInfo(  ) {

        //ApplSystemInfo.setup();
        List<String> actorNames = ApplSystemInfo.getActorNamesInApplCtx();
        CommUtils.outgreen (" --- ServiceFacadeController | actorNames= "+actorNames  );

        FacadeBuilder.wsHandler.sendToAll(
                "ACTORS:" + actorNames.toString() +
                " interacting with:" + ApplSystemInfo.applActorName);

    }
}
