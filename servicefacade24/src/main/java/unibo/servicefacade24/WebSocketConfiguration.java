package unibo.servicefacade24;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;
import unibo.basicomm23.utils.CommUtils;

@Configuration
@EnableWebSocket
public class WebSocketConfiguration implements WebSocketConfigurer {

    //public  WSHandler wsHandler = new WSHandler();
    public final String wsPath       = "accessgui";
    //private FacadeBuilder builder = new FacadeBuilder(wsHandler) ;
    //public final ApplguiCore guiManager = new ApplguiCore(wsHandler);

    public WebSocketConfiguration(){
        //Inovocato alla connessione
        CommUtils.outred("WebSocketConfiguration onnnnnnnnnnnnnnnnnn");
    }

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(FacadeBuilder.wsHandler, wsPath).setAllowedOrigins("*");
    }
}