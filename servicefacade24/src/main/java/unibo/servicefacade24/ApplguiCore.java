package unibo.servicefacade24;
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
        CommUtils.outred("AGC | handleReplyMsg " + msg  ) ;
        updateMsg( msg  );
    }

    public void updateMsg( String msg ) {
        CommUtils.outblue("AGC updateMsg " + msg);
        outinadapter.sendToAll(msg);
    }

    public void handleWsMsg(String msg ) {
        CommUtils.outcyan("AGC | handleWsMsg $msg"  );
        String[] parts = msg.split("/");
        String message = parts[0];
            String payload = parts[1];
        CommUtils.outcyan("AGC | handleWsMsg " + message  );

        switch (message) {
             case "request":
                dorequest(payload );
                break;
            case "cmd":
                docmd(payload );
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
 

    private void dorequest(String payload ) {
        outinadapter.dorequest(
        "gui",destActor,reqid,reqcontent.replace("X",payload) );
    }
    private void docmd(String payload ) {
        //outinadapter.docmd(payload );
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
