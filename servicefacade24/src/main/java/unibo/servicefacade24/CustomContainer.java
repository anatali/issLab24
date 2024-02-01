package unibo.servicefacade24;

import org.springframework.boot.web.server.WebServerFactoryCustomizer;
import org.springframework.boot.web.servlet.server.ConfigurableServletWebServerFactory;
import org.springframework.context.annotation.Configuration;
import unibo.basicomm23.utils.CommUtils;

import java.util.List;

@Configuration
public class CustomContainer implements WebServerFactoryCustomizer<ConfigurableServletWebServerFactory> {
    public static String appName = "todo";
    public void customize(ConfigurableServletWebServerFactory factory){
        List<String> config = QaksysConfigSupport.readConfig("facadeConfig.json");
         if( config != null ) {
             int port = Integer.parseInt(config.get(4));
             factory.setPort(port);
             appName = config.get(5);
             CommUtils.outblue("CustomContainer | facade port=" + port + " appName=" + appName);
         }
    }

}
