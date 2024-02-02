package unibo.servicefacade24;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import unibo.basicomm23.utils.CommUtils;

import java.util.List;

@Controller
public class ServiceFacadeController {
    @Value("${spring.application.name}")
    String appNameOld;  //vedi application.properties
    protected String sysName = "unknown";

    public ServiceFacadeController(){
         new FacadeBuilder( ) ;
    }
    private void updateViewmodel(Model model, String info ){
        model.addAttribute("info", info );
        model.addAttribute("sysNames", sysName);
    }

    @GetMapping("/")
    public String homePage(Model viewmodel) {
        //CommUtils.outcyan("ServiceFacadeController homePage appNameOld=" + appNameOld);
        viewmodel.addAttribute("appname", ApplSystemInfo.appName);
        String dir = System.getProperty("user.dir");
        CommUtils.outgreen (" --- ServiceFacadeController | entry dir= "+dir  );

        return "qakFacadeGUI";
    }
}
