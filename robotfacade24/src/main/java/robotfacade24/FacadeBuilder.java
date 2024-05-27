package main.java.robotfacade24;
import unibo.basicomm23.coap.CoapConnection;
import unibo.basicomm23.utils.CommUtils;

public class FacadeBuilder {
    public static  WSHandler wsHandler;
    public static  ApplguiCore guiCore  ;
    protected  ActorOutIn outinadapter;

    public FacadeBuilder( ){
        create();
    }

    public void create(){
        //CommUtils.outred("FacadeBuilder createeeeeeeeeeeeeeeeeeeeeeeeeeeee");
        wsHandler    = new WSHandler();
        outinadapter = new ActorOutIn( wsHandler );
        guiCore      = new ApplguiCore(outinadapter);
        outinadapter.setGuiCore(guiCore); //Injection
        wsHandler.setGuiCore(guiCore); //Injection


//        List<String> config = QaksysConfigSupport.readConfig("facadeConfig.json");
//        if( config != null ) {
            String qakSysHost    = ApplSystemInfo.qakSysHost;
            String ctxportStr    = ApplSystemInfo.ctxportStr;
            String qakSysCtx     = ApplSystemInfo.qakSysCtx;
            String applActorName = ApplSystemInfo.applActorName;

            CoapObserver obs = new CoapObserver(guiCore, applActorName);
            CoapConnection coapConn = new CoapConnection(qakSysHost + ":" + ctxportStr,
                    qakSysCtx + "/" + applActorName);
            CommUtils.outblue("FacadeBuilder | Stabilita coapConn : " + coapConn);

            coapConn.observeResource(obs);
        //}
    }
}
