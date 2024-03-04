package unibo.naiveactors24.example;
import unibo.basicomm23.msg.ProtocolType;
import unibo.basicomm23.utils.CommUtils;
import unibo.naiveactors24.ActorContext24;
 

public class MainExampleNaiveActors24 {

	
	protected void alienCaller() {
		AlienJava caller = new AlienJava("javacaller", ProtocolType.tcp, "localhost", "8123");
		caller.activate();
	}
    public void configureTheSystem(){
         
        int port1 = 8123;
        CommUtils.outblue("MainExampleTowardsActors24 CREA I CONTESTI ");
        ActorContext24 ctx1 = new ActorContext24("ctx1", "localhost", port1);
        CommUtils.outblue("MainExampleTowardsActors24 CREA GLI ATTORI ");

        Actor24Sender a1   = new Actor24Sender("a1",ctx1);
        Actor24Receiver a2 = new Actor24Receiver("a2",ctx1);

        ctx1.showActorNames();
        
        a1.activateAndStart();
        a2.activateAndStart();
        
        alienCaller();
    }
    public static void main(String[] args ){
        new MainExampleNaiveActors24().configureTheSystem();
    }
}
