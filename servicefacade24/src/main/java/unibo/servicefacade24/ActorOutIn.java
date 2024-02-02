package unibo.servicefacade24;

import it.unibo.kactor.MsgUtil;
import unibo.basicomm23.coap.CoapConnection;
import unibo.basicomm23.interfaces.IApplMessage;
import unibo.basicomm23.interfaces.Interaction;
import unibo.basicomm23.msg.ApplMessage;
import unibo.basicomm23.tcp.TcpClientSupport;
import unibo.basicomm23.utils.CommUtils;

import java.util.List;

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
            String qakSysPort     = ApplSystemInfo.qakSysPort;
            int    ctxport      = ApplSystemInfo.ctxport;

             tcpConn = TcpClientSupport.connect(qakSysHost, ctxport, 20);
             CommUtils.outblue("OUTIN | Stabilita tcpConn: " + tcpConn + " con " + qakSysPort);
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
        IApplMessage response = null;
        try {
            CommUtils.outmagenta("OUTIN | Sending " + message);
            if( message.isRequest() ) {
                response = tcpConn.request(message);
                CommUtils.outmagenta("OUTIN | Got response " + response);
                if (response != null) {
                    if(response.isRequest()){
                        String s =  "Sorry, I'm not able to answer to request " + response.msgId() + ":"+ response.msgContent();
                        CommUtils.outmagenta("OUTIN |  " + s);
                        //IApplMessage sorry = MsgUtil.buildReply("gui", response.msgId(), s, response.msgSender());
                        //wsHandler.sendToAll( s );
                        guiCore.handleReplyMsg( s  );
                    }else {
                        //wsHandler.sendToAll( response );
                        guiCore.handleReplyMsg( response.msgContent()  );
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
        CommUtils.outmagenta("OUTIN | docmd NOT IMPLEMENTED" + msg );
//        IApplMessage message = CommUtils.buildDispatch(senderId, cmdid, cmdarg.replace("X",msg) , destActor);
//        sendToActor(message, msgId);
    }

    public void sendToAll(String msg){ wsHandler.sendToAll( msg  ); }
}