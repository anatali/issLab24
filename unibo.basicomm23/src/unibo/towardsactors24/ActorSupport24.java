package unibo.towardsactors24;
import unibo.basicomm23.interfaces.IApplMessage;
import unibo.basicomm23.interfaces.Interaction;
import unibo.basicomm23.utils.CommUtils;
 

public class ActorSupport24 {

    public static void sendMsg( IApplMessage msg, ActorContext24 ctx  )   {
        try { 
            String destActorName = msg.msgReceiver();
            CommUtils.outyellow(  "		ActorSupport24 | sendMsg to destActorName=" + destActorName + " msg.getConn()=" + msg.getConn());
            ActorBasic24 dest    = ctx.getActor(destActorName);
            if (dest != null) {
                CommUtils.outyellow(  "		ActorSupport24 | sendMsg to " + dest.name  );
               //CommUtils.outyellow( "ActorSupport24 | sendMsg to " + dest.name);
                dest.msgQueue.put( msg ); //attore locale
            } 
            else {
                //sendMsgToRemoteActor(msg, ctx);   
            	Interaction callerConn =  msg.getConn();
            	if( callerConn != null) {
            		callerConn.forward(msg);
            	}
            	else CommUtils.outred(  "		ActorSupport24 | sorry, sendMsg to remote not implemented  "   );
            }
        }catch(Exception e){
            CommUtils.outred(  "		ActorSupport24 | sendMsg ERROR  " + e.getMessage() );
        }
    }



}
