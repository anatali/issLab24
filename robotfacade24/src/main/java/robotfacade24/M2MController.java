package main.java.robotfacade24;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import unibo.basicomm23.interfaces.IApplMessage;
import unibo.basicomm23.msg.ApplMessage;
import unibo.basicomm23.utils.CommUtils;

@RestController
@RequestMapping(path = "/RestApi") //, produces = "application/json"
public class M2MController {
    public static M2MController m2mCtrl;
    private ApplguiCore guiCore;
    private String answer = null;
    protected String mainPage   = "basicrobot23EssentialGui"; //"basicrobot23Gui";

    public M2MController(){
        m2mCtrl = this;
        this.guiCore = FacadeBuilder.guiCore;
        CommUtils.outgreen (" --- M2MController | STARTS guiCore=" + guiCore);
    }

    //Injiection
    public void setGuiCore(ApplguiCore gui) {
        guiCore = gui;
        CommUtils.outgreen (" --- M2MController | injected guiCore=" + guiCore);
    }

    public synchronized void setAnswer(String answer){
        this.answer = answer;
    }

    @GetMapping("/")
    public String entry(Model viewmodel) {
        //Connection.trace = true;

        return mainPage;
    }

    @PostMapping("/testHTTP")  //per provare HTTP in ServiceCallerInteraction
    public String homePagePost(@RequestBody String request ) {
        if( guiCore == null ) guiCore = FacadeBuilder.guiCore;
        CommUtils.outgreen (" --- M2MController | entry request= "+request  );
        IApplMessage req = new ApplMessage(request);
        guiCore.dorequest( "gui", req.msgContent() ) ;
        //answer = M2MController.m2mCtrl.ge;
        while( answer == null ){
            try {
                Thread.sleep(300);
                CommUtils.outgreen (" --- M2MController | waits for the answer " );
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        //La risposta dell'actor viene gestita da handleReplyMsg in guiCore
        //return "testHTTP with POST done";
        String outS = answer;
        answer = null;
        return outS;
    }
}
