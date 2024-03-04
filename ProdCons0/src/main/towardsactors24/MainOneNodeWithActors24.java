package main.towardsactors24;

import unibo.basicomm23.utils.CommUtils;
import unibo.naiveactors24.ActorContext24;


/*
 * ===========================================================================
 * Crea due Producer come Actors24
 * Crea il Consumer come Actors24
 * I componenti-attori sono attivati quando creati
 * ===========================================================================
 */
public class MainOneNodeWithActors24 {

	public static final int port = 8223;
	public static final String entry = ""+port;
	
	public void configureTheSystem()  {
         
        CommUtils.outblue("MainOneNodeWithActors24 CREA I CONTESTI ");
        ActorContext24 ctx1 = new ActorContext24("ctxprodcons", "localhost", port);
        
        CommUtils.outblue("MainOneNodeWithActors24 CREA GLI ATTORI ");

        new ProducerAsActors24("prod1", ctx1 );
        //new ProducerAsActors24("prod2", ctx1 );
        new ConsumerAsActors24("consumer", ctx1 );
        
        
        //Utility per visualizzare i nomi degli attori locali al Contesto
        ctx1.showActorNames();  
        
        CommUtils.delay(1000);
        CommUtils.outblack("MainOneNodeWithActors24 TERMINATES thread=" + Thread.currentThread().getName());
        System.exit(0);
	}

	/*
	 * main
	 */
	public static void main(String[] args)  {
		 new MainOneNodeWithActors24().configureTheSystem();
	}

}
