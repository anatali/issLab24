package unibo.servicefacade24;

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
    private  String senderId;
    private  String destActor;
    private ApplguiCore applGuiCore;
    private  Interaction tcpConn;
    private String qakSysHost;
    private String qakSysPort;
    private String qakSysCtx;
    private String applActorName;
    private String reqid;
    private String reqarg;
    public static String facadePort = "";
    public static String sysname = "";

    public ActorOutIn(ApplguiCore gui ) {
        this.applGuiCore = gui;
        try {
            List<String> config = QaksysConfigSupport.readConfig("facadeConfig.json");
            if( config != null ) {
                qakSysHost = config.get(0);
                qakSysPort = config.get(1);
                qakSysCtx = config.get(2);
                applActorName = config.get(3);
                facadePort = config.get(4);
                sysname = config.get(5);
                reqid = "dofibo";           //config.get(6);
                reqarg = "dofibo(X)";       //config.get(7);
                senderId = "gui";
                destActor = applActorName;

             tcpConn = TcpClientSupport.connect(qakSysHost, Integer.parseInt(qakSysPort), 10);
             CommUtils.outblue("OUTIN | Stabilita tcpConn: " + tcpConn + " con " + qakSysPort);

            CoapObserver obs = new CoapObserver(gui , applActorName);

            CoapConnection coapConn = new CoapConnection(qakSysHost+":"+qakSysPort,
                    qakSysCtx+"/"+applActorName);
            CommUtils.outblue("OUTIN | Stabilita coapConn : " + coapConn );

                coapConn.observeResource( obs );
            }
        } catch (Exception e) {
            tcpConn = null;
            CommUtils.outred("OUTIN | creation WARNING: " + e.getMessage());
        }
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
                        //applGuiCore.hanldeMsgFromActor("Sorry, I'm not able to answet to request " + response.msgId() + ":"+ response.msgContent(), response.msgId());
                        applGuiCore.updateMsg("Sorry, I'm not able to answet to request " + response.msgId() + ":"+ response.msgContent() );
                        //dovrei delegare la ask a un oggetto locale alla gui che
                        //dovrebbe essere plugged-in nella dichiarazione della faccade
                    }else {
                        //applGuiCore.hanldeMsgFromActor(response.msgContent(), requestId);
                        applGuiCore.updateMsg(response.msgContent() );
                    }
                }
            }else tcpConn.forward(message);
        } catch (Exception e) {
            CommUtils.outred("OUTIN | ERROR " + e.getMessage() +" while sending the request");
        }

    }

    //
    public void dorequest(String msg ) {
        CommUtils.outmagenta("OUTIN | dorequest " + msg );
        IApplMessage message = CommUtils.buildRequest(senderId, reqid, reqarg.replace("X",msg) , destActor);
        sendToActor( message );
    }

    public void docmd(String msg ) {
        CommUtils.outmagenta("OUTIN | docmd NOT IMPLEMENTED" + msg );
//        IApplMessage message = CommUtils.buildDispatch(senderId, cmdid, cmdarg.replace("X",msg) , destActor);
//        sendToActor(message, msgId);
    }


}