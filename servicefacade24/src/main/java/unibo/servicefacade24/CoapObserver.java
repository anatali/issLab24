package unibo.servicefacade24;

import org.eclipse.californium.core.CoapHandler;
import org.eclipse.californium.core.CoapResponse;
import unibo.basicomm23.utils.CommUtils;

public class CoapObserver implements CoapHandler {

    private final ApplguiCore guiManager;
    private final String observedActor;

    public CoapObserver(ApplguiCore manager, String actor) {
        this.guiManager    = manager;
        this.observedActor = actor;
    }

    @Override
    public void onLoad(CoapResponse response) {
        CommUtils.outblue("CoapObserver | Got update from " + observedActor + ": " + response.getResponseText());
        //guiManager.updateStatusGUI(observedActor, response.getResponseText());
        guiManager.hanldeMsgFromActor(response.getResponseText(), "");
    }

    @Override
    public void onError() {
        CommUtils.outred("CoapObserver | Error while receiving update from " + observedActor);
    }
}
