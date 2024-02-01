package unibo.servicefacade24;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

@Configuration
@EnableWebSocket
public class WebSocketConfiguration implements WebSocketConfigurer {
    public final WSHandler wsHandler = new WSHandler();
    public final String wsPath       = "accessgui";
    private FacadeBuilder builder = new FacadeBuilder(wsHandler) ;
    //public final ApplguiCore guiManager = new ApplguiCore(wsHandler);

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(wsHandler, wsPath).setAllowedOrigins("*");
    }
}