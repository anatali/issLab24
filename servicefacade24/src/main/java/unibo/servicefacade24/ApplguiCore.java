package unibo.servicefacade24;
import unibo.basicomm23.utils.CommUtils;

import java.util.HashMap;
import java.util.Map;

/*
Logica applicativa (domain core) della gui
 */
public class ApplguiCore {
    //private   WSHandler wsHandler;
    private   ActorOutIn outinadapter;

    public ApplguiCore( ActorOutIn outinadapter ) {
        this.outinadapter = outinadapter;
        //public ApplguiCore(WSHandler clientHandler) {
         //wsHandler  = clientHandler;
         //outinadapter = new ActorOutIn(this );
         //wsHandler.setManager(this);
    }

    public void hanldeMsgFromActor(String msg, String requestId) {
        CommUtils.outcyan("AGC | hanldeMsgFromActor " + msg + " requestId=" + requestId) ;
        updateMsg( msg );
    }

    public void updateMsg( String msg ) {
        CommUtils.outblue("AGC updateMsg " + msg);
        //this.wsHandler.sendToAll( msg  );
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
            case "exit":
                System.exit(0);
                break;
            default:
                break;
        }
    }
 

    private void dorequest(String payload ) {
        outinadapter.dorequest(payload );
    }
    private void docmd(String payload ) {
        outinadapter.docmd(payload );
    }
}