package main.java.facade24;

import org.springframework.boot.web.server.WebServerFactoryCustomizer;
import org.springframework.boot.web.servlet.server.ConfigurableServletWebServerFactory;
import org.springframework.context.annotation.Configuration;
import unibo.basicomm23.utils.CommUtils;



@Configuration
public class CustomContainer implements WebServerFactoryCustomizer<ConfigurableServletWebServerFactory> {

    public void customize(ConfigurableServletWebServerFactory factory){
        ApplSystemInfo.readConfig();
        int port = ApplSystemInfo.facadeport;
        factory.setPort(port);  //customization
        String appName = ApplSystemInfo.appName;
        CommUtils.outblue("CustomContainer | facade port=" + port + " appName=" + appName);
    }

}
