package main.java.facade24;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import unibo.basicomm23.interfaces.IApplMessage;
import unibo.basicomm23.msg.ApplMessage;
import unibo.basicomm23.utils.CommUtils;

@RestController
@RequestMapping(path = "/RestApi") //, produces = "application/json"
public class M2MController {
    public static M2MController m2mCtrl;
    private ApplguiCore guiCore;
    private String answer = null;

    public M2MController(){
        m2mCtrl = this;
        this.guiCore = FacadeBuilder.guiCore;
        CommUtils.outgreen (" --- M2MController | STARTS guiCore=" + guiCore);
    }

    //Injiection
    public void setGuiCore(ApplguiCore gui) {
        guiCore = gui;
    }

    public synchronized void setAnswer(String answer){
        this.answer = answer;
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
