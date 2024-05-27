package main.java.robotfacade24;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import unibo.basicomm23.interfaces.IApplMessage;
import unibo.basicomm23.msg.ApplMessage;
import unibo.basicomm23.utils.CommUtils;

 

@Controller
public class Robotfacade24Controller {
    protected String sysName = "unknown";
    public final static String robotName  = "basicrobot";
    //Settaggio degli attributi del modello VEDI application.properties
    //@Value("${robot24.protocol}")
    String protocol="tcp";
    //@Value("${robot24.webcamip}")
    String webcamip;
 
    @Value("${spring.application.name}")
    String appName;  //vedi application.properties

    @Value("${robot24.robotip}")
    String robotip;
    
    @Value("${robot24.plantodo}")
    String plantodo;
    
    @Value("${robot24.plandone}")
    String plandone;
    
    @Value("${robot24.steptime}")    
    String steptime;
    
    @Value("${robot24.map}")    
    String map;

    @Value("${robot24.robotpos}")    
    String robotpos;

    protected String mainPage     = "basicrobot24EssentialGui";
    protected ApplguiCore guiCore ;

    public Robotfacade24Controller(){
        CommUtils.outgreen (" --- Robotfacade24Controller | STARTS "  + steptime);
        new FacadeBuilder( ) ;
        guiCore = FacadeBuilder.guiCore;
    }

    protected String buildThePage(Model viewmodel) {
        setConfigParams(viewmodel);
        return mainPage;
    }
    protected void setConfigParams(Model viewmodel){
    	//CommUtils.outblack("Robotfacade24Controller | setConfigParams plandone="+ plandone);
        viewmodel.addAttribute("robotip",  robotip);
        viewmodel.addAttribute("steptime", steptime);
        viewmodel.addAttribute("plandone", plandone);
        viewmodel.addAttribute("map", map);
        viewmodel.addAttribute("robotpos", robotpos);

//      viewmodel.addAttribute("protocol", protocol);
//      viewmodel.addAttribute("webcamip", webcamip);
//      viewmodel.addAttribute("plantodo", plantodo);
    }
 

    @GetMapping("/")
    public String homePage(Model viewmodel) {
        viewmodel.addAttribute("appname", ApplSystemInfo.appName);
        String dir = System.getProperty("user.dir");
        CommUtils.outgreen (" --- Robotfacade24Controller | entry dir= "+dir  );
        return buildThePage(viewmodel);
    }

    @PostMapping("/robotmove")
    public String doMove(Model viewmodel  , @RequestParam String move ){
        CommUtils.outblue("Robotfacade24Controller | doMove:" + move  );
        guiCore.robotmove(move);
        return buildThePage(viewmodel);
    }

    @PostMapping("/basicrobotip")
    public String setrobotip(Model viewmodel, @RequestParam String ipaddr  ){
        this.robotip = ipaddr;
        CommUtils.outcyan("Robotfacade24Controller | setrobotip:" + ipaddr );
        guiCore.basicrobotconnect(robotip);
        return buildThePage(viewmodel);
    }

    @PostMapping("/setsteptime")
    public String setsteptime(Model viewmodel, @RequestParam String steptime  ){
    	this.steptime = steptime;
    	CommUtils.outcyan("Robotfacade24Controller | setsteptime:" + steptime);
        return buildThePage(viewmodel);
    }
    
    /* Anche se invocato da Ajax restituisce sempre la pagina */
    @PostMapping("/doplan")
    public String doplan(Model viewmodel , @RequestParam String plan ){
    	this.plantodo = plan;
    	CommUtils.outcyan("Robotfacade24Controller | doplan:" + plantodo + " robotName=" + robotName);
    	plandone = guiCore.doplan(plan, steptime);
        return buildThePage(viewmodel);
    }
    
    @PostMapping("/getenvmap")
    public String getenvmap(Model viewmodel   ){
    	this.map = guiCore.getEnvmap();
    	return buildThePage(viewmodel);
    }
    
    @PostMapping("/getrobotpos")
    public String getrobotpos(Model viewmodel   ){
    	this.robotpos =  guiCore.getRobotpos();
    	return buildThePage(viewmodel);
    }

    @PostMapping("/alarm")
    public String alarm(Model viewmodel   ){
        CommUtils.outmagenta("Robotfacade24Controller | alarm robotName=" + robotName);
        guiCore.setalarm(  )  ;
        return buildThePage(viewmodel);
    }


    @PostMapping("/dorobotpos")
    public String dorobotpos(Model viewmodel  , @RequestParam String x, @RequestParam String y ){
        //CommUtils.outblue("Robotfacade24Controller | dorobotpos x:" + x + " robotName=" + robotName);
        //CommUtils.outblue("Robotfacade24Controller | dorobotpos y:" + y + " robotName=" + robotName);
    	guiCore.doRobotPos(x, y);
        return buildThePage(viewmodel);
    }
    @PostMapping("/setrobotpos")
    public String setrobotpos(Model viewmodel,
                              @RequestParam String x, @RequestParam String y, @RequestParam String dir ){
        //CommUtils.outblue("Robotfacade24Controller | setrobotpos x:" + x + " robotName=" + robotName);
        //CommUtils.outblue("Robotfacade24Controller | setrobotpos y:" + y + " robotName=" + robotName);
        //CommUtils.outblue("Robotfacade24Controller | setrobotpos d:" + d + " robotName=" + robotName);
    	guiCore.setRobotPos(x, y, dir);
        return buildThePage(viewmodel);
    }

    @ExceptionHandler
    public ResponseEntity handle(Exception ex) {
        HttpHeaders responseHeaders = new HttpHeaders();
        return new ResponseEntity(
             "BaseController ERROR " + ex.getMessage(),
             responseHeaders, HttpStatus.CREATED);
    }

}
/*
 * curl --location --request POST 'http://localhost:8080/move' --header 'Content-Type: text/plain' --form 'move=l'
 * curl -d move=r localhost:8080/move
 */