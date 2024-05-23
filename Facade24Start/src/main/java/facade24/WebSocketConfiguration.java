package main.java.facade24;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;
import unibo.basicomm23.utils.CommUtils;

@Configuration
@EnableWebSocket
public class WebSocketConfiguration implements WebSocketConfigurer {

    public final String wsPath  = "accessgui";

    public WebSocketConfiguration(){
        //Inovocato alla CONNESSIONE
        //CommUtils.outred("WebSocketConfiguration onnnnnnnnnnnnnnnnnn");
    }

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(FacadeBuilder.wsHandler, wsPath).setAllowedOrigins("*");
    }
}