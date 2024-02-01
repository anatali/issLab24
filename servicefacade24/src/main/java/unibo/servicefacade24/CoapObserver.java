package unibo.servicefacade24;
import org.eclipse.californium.core.CoapHandler;
import org.eclipse.californium.core.CoapResponse;
import unibo.basicomm23.utils.CommUtils;

public class CoapObserver implements CoapHandler {

    private final ApplguiCore guiCore;
    private final String observedActor;

    public CoapObserver(ApplguiCore guiCore, String actor) {
        this.guiCore = guiCore;
        this.observedActor = actor;
    }

    @Override
    public void onLoad(CoapResponse response) {
        CommUtils.outblue("CoapObserver | Got update from " + observedActor + ": " + response.getResponseText());
        //guiManager.updateStatusGUI(observedActor, response.getResponseText());
        guiCore.hanldeMsgFromActor(response.getResponseText(), "");
    }

    @Override
    public void onError() {
        CommUtils.outred("CoapObserver | Error while receiving update from " + observedActor);
    }
}
