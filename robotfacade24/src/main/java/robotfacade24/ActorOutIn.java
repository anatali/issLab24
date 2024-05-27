package main.java.robotfacade24;


import unibo.basicomm23.interfaces.IApplMessage;
import unibo.basicomm23.interfaces.Interaction;
import unibo.basicomm23.msg.ApplMessage;
import unibo.basicomm23.utils.CommUtils;


/*
Adapter verso il QActor che fa da facade
Usa il file facadeConfig.json
 */
public class ActorOutIn {
    private ApplguiCore guiCore;
    private WSHandler wsHandler;
    private  Interaction tcpConn;


    public ActorOutIn( WSHandler wsHandler ) {
        try {
            this.wsHandler        = wsHandler;
        } catch (Exception e) {
            tcpConn = null;
            CommUtils.outred("OUTIN | creation WARNING: " + e.getMessage());
        }
     }

     //Injection
     public void setGuiCore(ApplguiCore guiCore){
         this.guiCore = guiCore;
     }

     
     private void sendToActor(IApplMessage message ) {
    	 String response = RobotUtils.sendApplMsg(message);
    	 guiCore.handleReplyMsg( response.toString()   );
     }
     

     
     
/*
    private void sendToActorOld(IApplMessage message ) {
        IApplMessage response = null;
        try {
            CommUtils.outmagenta("OUTIN | Sending " + message);
            if( message.isRequest() ) {
                response = tcpConn.request(message);
                CommUtils.outmagenta("OUTIN | Got response " + response);
                if (response != null) {
                    if(response.isRequest()){
                        //String s =  "Sorry, I'm not able to answer to request " + response.msgId() + ":"+ response.msgContent();
                        //CommUtils.outmagenta("OUTIN |  " + s);
                        //IApplMessage sorry = MsgUtil.buildReply("gui", response.msgId(), s, response.msgSender());
                        //wsHandler.sendToAll( s );
                        guiCore.handleReplyMsg( response   );
                        //Mi attendo una risposta che deve essere gestita da
                    }else {
                        //wsHandler.sendToAll( response );
                        guiCore.handleReplyMsg( response.toString()   );
                    }
                }
            }else tcpConn.forward(message);
        } catch (Exception e) {
            CommUtils.outred("OUTIN | ERROR " + e.getMessage() +" while sending the request");
        }
    }
*/
    //
    public void dorequest(  String senderId, String destActor, String reqid, String reqarg ) {
        IApplMessage message = CommUtils.buildRequest(senderId, reqid, reqarg  , destActor);
        CommUtils.outmagenta("OUTIN | dorequest " + message );
        sendToActor( message );
    }

    public void docmd( String cmdid, String cmdarg ) {
        CommUtils.outmagenta("OUTIN | docmd  " + cmdid );
        IApplMessage m1 = RobotUtils.moveAril("basicrobot", cmdarg);
        CommUtils.outmagenta("OUTIN | docmd m1=" + m1 );

         IApplMessage message = CommUtils.buildDispatch("gui23xyz9526", cmdid, cmdarg , "basicrobot");
        CommUtils.outmagenta("OUTIN | docmd message=" + message );
        sendToActor( message );
    }
    public void docmd( IApplMessage message ) {
        CommUtils.outmagenta("OUTIN | docmd message=" + message );
        sendToActor( message );
    }

    public void doreply(String senderId, String destActor, String replyId, String replyArg ) {
        CommUtils.outmagenta("OUTIN | doreply  " + replyId );
        IApplMessage message = CommUtils.buildReply(senderId, replyId, replyArg , destActor);
        sendToActor(message );
    }

    public void sendToOne(IApplMessage msg){
        wsHandler.sendToOne( msg  );
    }
    public void sendToOne(String msg){ wsHandler.sendToOne( msg  ); }

    public void sendToAll(String msg){ wsHandler.sendToAll( msg  ); }
}