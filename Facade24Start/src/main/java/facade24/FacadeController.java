package main.java.facade24;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import unibo.basicomm23.utils.CommUtils;
 

 

@Controller
public class FacadeController {
    String protocol="tcp";
    //@Value("${robot23.webcamip}")
    String webcamip;
    @Value("${robot23.robotip}")
    String robotip="basicrobot";
    @Value("${robot23.plan}")
    String plantodo;
    @Value("${robot23.plandone}")
    String plandone;
    @Value("${robot23.stepTime}")
    public static String steptime = "330";

    @Value("${spring.application.name}")
    String appNameOld;  //vedi application.properties
     
    protected String mainPage = "Fcd24SGUI"; //TODO: "WebRobot24Gui";  

    public FacadeController(){
        CommUtils.outgreen (" --- FacadeController | STARTS " );
        new FacadeBuilder( ) ;
    }
 

    protected String buildThePage(Model viewmodel) {
        setConfigParams(viewmodel);
        return mainPage;
    }
    protected void setConfigParams(Model viewmodel){
 
    }
    @GetMapping("/")
    public String homePage(Model viewmodel) {
        //CommUtils.outcyan("FacadeController homePage appNameOld=" + appNameOld);
        viewmodel.addAttribute("appname", ApplSystemInfo.appName);
        String dir = System.getProperty("user.dir");
        CommUtils.outgreen (" --- FacadeController | entry dir= "+dir  );
        return buildThePage(viewmodel); //"qakFacadeGUI";
    }
    
    @PostMapping("/setrobotip")
    public String setrobotip(Model viewmodel, @RequestParam String ipaddr  ){
        robotip = ipaddr;
        System.out.println("ServiceFacadeController | setrobotip:" + ipaddr );
        viewmodel.addAttribute("robotip", robotip);
        RobotUtils.connectWithRobotUsingTcp(ipaddr);
        return buildThePage(viewmodel);
    }
    
    @PostMapping("/robotmove")
    public String doMove(Model viewmodel  , @RequestParam String move ){
        CommUtils.outblue("FacadeController | doMove:" + move  );
        try {
              String answer = RobotUtils.sendMsg("basicrobot",move);
              FacadeBuilder.wsHandler.sendToAll(answer);
        } catch (Exception e) {
            CommUtils.outred("FacadeController | doMove ERROR:"+e.getMessage());
        }
        return buildThePage(viewmodel);
    }    
}
