package unibo.servicefacade24;

import org.springframework.boot.web.server.WebServerFactoryCustomizer;
import org.springframework.boot.web.servlet.server.ConfigurableServletWebServerFactory;
import org.springframework.context.annotation.Configuration;
import unibo.basicomm23.utils.CommUtils;

import java.util.List;

@Configuration
public class CustomContainer implements WebServerFactoryCustomizer<ConfigurableServletWebServerFactory> {
    public static String appName ;
    public void customize(ConfigurableServletWebServerFactory factory){
        ApplSystemInfo.readConfig();
        int port = ApplSystemInfo.facadeport;
        factory.setPort(port);  //customization
        appName = ApplSystemInfo.appName;
        CommUtils.outblue("CustomContainer | facade port=" + port + " appName=" + appName);
    }

}
