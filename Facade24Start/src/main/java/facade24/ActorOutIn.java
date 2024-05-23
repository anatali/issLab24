package main.java.facade24;

 
import unibo.basicomm23.interfaces.IApplMessage;
import unibo.basicomm23.interfaces.Interaction;
import unibo.basicomm23.tcp.TcpClientSupport;
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
            String qakSysHost     = ApplSystemInfo.qakSysHost;
            String facadeportStr  = ApplSystemInfo.facadeportStr;
            int    ctxport        = ApplSystemInfo.ctxport;

             tcpConn = TcpClientSupport.connect(qakSysHost, ctxport, 20);
             CommUtils.outblue("OUTIN | Stabilita tcpConn: " + tcpConn + " con " + facadeportStr);
        } catch (Exception e) {
            tcpConn = null;
            CommUtils.outred("OUTIN | creation WARNING: " + e.getMessage());
        }
     }

     //Injection
     public void setGuiCore(ApplguiCore myguiCore){
          guiCore = myguiCore;
     }


    private void sendToActor(IApplMessage message ) {
        IApplMessage response = null;
        try {
            CommUtils.outmagenta("OUTIN | Sending " + message);
            if( message.isRequest() ) {
                response = tcpConn.request(message);
                CommUtils.outmagenta("OUTIN | Got response " + response);
                if (response != null) {
                    if(response.isRequest()){
                        guiCore.handleReplyMsg( response   );
                    }else {
                        guiCore.handleReplyMsg( response.toString()   );
                    }
                }
            }else tcpConn.forward(message);
        } catch (Exception e) {
            CommUtils.outred("OUTIN | ERROR " + e.getMessage() +" while sending the request");
        }
    }

    //
    public void dorequest(  String senderId, String destActor, String reqid, String reqarg ) {
        IApplMessage message = CommUtils.buildRequest(senderId, reqid, reqarg  , destActor);
        CommUtils.outmagenta("OUTIN | dorequest " + message );
        sendToActor( message );
    }

    public void docmd(String msg ) {
        CommUtils.outred("OUTIN | WARNING: docmd NOT IMPLEMENTED" + msg );
//        IApplMessage message = CommUtils.buildDispatch(senderId, cmdid, cmdarg.replace("X",msg) , destActor);
//        sendToActor(message, msgId);
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

    public void sendToAll(String msg){ 
    	CommUtils.outmagenta("OUTIN | sendToAll  " + msg + " " + wsHandler);
    	wsHandler.sendToAll( msg  ); 
    }
}