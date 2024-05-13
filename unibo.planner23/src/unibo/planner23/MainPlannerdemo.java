package unibo.planner23;

import java.util.List;
import aima.core.agent.Action;
import unibo.basicomm23.utils.CommUtils;


public class MainPlannerdemo {
private Planner23Util planner;

	protected void println(String m){
		System.out.println(m);
	}

 	public void doMentalMap() {
 		try {
 		   planner = new Planner23Util();
 		   planner.initAI();
 		   planner.showMap(); 
 		   //planner.doPathOnMap("[l, w, w, w, w, w, w]");
 		   for( int k=1;k<=2;k++) {
	 		   for( int i=0; i<4; i++) {
	 			  planner.doMove("w");
	 		   }
	 		   planner.doMove("l");
	 		   for( int i=0; i<6; i++) {
	 			  planner.doMove("w");
	 		   }	 	   
	 		   planner.doMove("l");	 		   
 		   }
 		   planner.showMap();
 		   
 		  moveToPos(2,3);
 		  planner.saveRoomMap("map0");
 		} catch ( Exception e) {
			//e.printStackTrace()
		}
	}

 	protected void moveToPos(int x, int y) throws Exception {
 		planner.setGoal(x,y);
 		List<Action> plan = planner.doPlan();
 		CommUtils.outblue( plan.toString() );
 		
 		String planCompact = planner.doPlanCompact();
 		CommUtils.outblue( planCompact.toString() );
 		
 		planner.doPathOnMap(planCompact);
 		planner.showMap();
 	}
 
 
	public static void main( String[] args)   {
		
 		MainPlannerdemo appl = new MainPlannerdemo( );
		appl.doMentalMap();

	}

}
