package main.java.robotfacade24;
import org.eclipse.californium.core.CoapHandler;
import org.eclipse.californium.core.CoapResponse;
import unibo.basicomm23.utils.CommUtils;

//Created by FacadeBuilder
public class CoapObserver implements CoapHandler {

    private final ApplguiCore guiCore;
    private final String observedActor;

    public CoapObserver(ApplguiCore guiCore, String actor) {
        this.guiCore = guiCore;
        this.observedActor = actor;
    }

    @Override
    public void onLoad(CoapResponse response) {
        CommUtils.outblue("Facade24  CoapObserver | Got update from " + observedActor + ": " + response.getResponseText());
        guiCore.handleMsgFromActor(response.getResponseText(), "");
    }

    @Override
    public void onError() {
        CommUtils.outred("Facade24 CoapObserver | Error while receiving update from " + observedActor);
        guiCore.updateMsg("CoapObserver ERROR");
    }
}
