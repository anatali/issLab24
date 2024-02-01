package unibo.servicefacade24;
import unibo.basicomm23.coap.CoapConnection;
import unibo.basicomm23.utils.CommUtils;
import java.util.List;

public class FacadeBuilder {

    protected   WSHandler wsHandler     ;
    protected   ApplguiCore guiCore  ;
    protected   ActorOutIn outinadapter;

    public FacadeBuilder(WSHandler wsHandler){
        wsHandler    = this.wsHandler = wsHandler;
        create();
    }

    public void create(){

        outinadapter = new ActorOutIn(wsHandler );
        guiCore      = new ApplguiCore(outinadapter);
        wsHandler.setGuiCore(guiCore); //Injections


        List<String> config = QaksysConfigSupport.readConfig("facadeConfig.json");
        if( config != null ) {
            String qakSysHost = config.get(0);
            String qakSysPort = config.get(1);
            String qakSysCtx = config.get(2);
            String applActorName = config.get(3);

            CoapObserver obs = new CoapObserver(guiCore, applActorName);
            CoapConnection coapConn = new CoapConnection(qakSysHost + ":" + qakSysPort,
                    qakSysCtx + "/" + applActorName);
            CommUtils.outblue("FacadeBuilder | Stabilita coapConn : " + coapConn);

            coapConn.observeResource(obs);
        }
    }
}
