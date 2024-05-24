package main.java.facade24;
import org.eclipse.californium.core.CoapHandler;
import org.eclipse.californium.core.CoapResponse;
import unibo.basicomm23.utils.CommUtils;

//Created by FacadeBuilder
public class CoapObserver implements CoapHandler {

    private final ApplguiCore guiCore;
    private final String observedActor;
    private String lastString = "";

    public CoapObserver(ApplguiCore guiCore, String actor) {
        this.guiCore = guiCore;
        this.observedActor = actor;
    }

    @Override
    public void onLoad(CoapResponse response) {
        CommUtils.outblue("Facade24  CoapObserver | Got update from " + observedActor + ": " + response.getResponseText());
        String info = response.getResponseText();
        if( ! lastString.equals(info)) {   //Evito ripetizioni dovute e CoAP get
        	guiCore.handleMsgFromActor( "Cobs " + info, "");
        	lastString = info;
        }
    }

    @Override
    public void onError() {
        CommUtils.outred("Facade24 CoapObserver | Error while receiving update from " + observedActor);
        guiCore.updateMsg("CoapObserver ERROR");
    }
}
