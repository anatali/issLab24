package main.towardsactors24;

import unibo.basicomm23.utils.CommUtils;
import unibo.towardsactors24.ActorContext24;


public class MainOneNodeWithActors24 {

	public static final int port = 8223;
	public static final String entry = ""+port;
	
	public void configureTheSystem()  {
         
        CommUtils.outblue("MainOneNodeWithActors24 CREA I CONTESTI ");
        ActorContext24 ctx1 = new ActorContext24("ctxprodcons", "localhost", port);
        
        CommUtils.outblue("MainOneNodeWithActors24 CREA GLI ATTORI ");

        new ProducerAsActors24("prod1", ctx1 );
        new ConsumerAsActors24("consumer", ctx1 );
        
        ctx1.showActorNames();
        
	}

	/*
	 * main
	 */
	public static void main(String[] args)  {
		 new MainOneNodeWithActors24().configureTheSystem();
	}

}
