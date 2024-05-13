package main.java;

import java.util.List;
import aima.core.agent.Action;
import unibo.basicomm23.utils.CommUtils;
import unibo.planner23.Planner23Util;


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
 		   CommUtils.outblue(" ------ FIRST STEP");
 		   moveHalfBoundary();
 		   planner.showMap();
 		  CommUtils.outblue(" ------ SECOND STEP");
 		   moveHalfBoundary();
 		   planner.showMap();
 		   
 		  CommUtils.outblue(" ------ moveToPos(2,3)");
		   moveToPos(2,3);
 		  planner.saveRoomMap("map0");
 		} catch ( Exception e) {
			e.printStackTrace();
		}
	}
 	
 	protected void moveHalfBoundary() {
		   for( int i=0; i<4; i++) {
			  planner.doMove("w");
		   }
		   planner.doMove("l");
		   for( int i=0; i<6; i++) {
			  planner.doMove("w");
		   }	 	   
		   planner.doMove("l");	 		   
 	}

 	protected void moveToPos(int x, int y) throws Exception {
 		planner.setGoal(x,y);
 		List<Action> plan = planner.doPlan();
 		CommUtils.outgreen( "PLAN:"+plan.toString() ); 		
 		String planCompact = planner.doPlanCompact();
 		CommUtils.outgreen( "PLAN COMPACT:"+planCompact.toString() ); 		
 		planner.doPathOnMap(planCompact);
 		planner.showMap();
 	}
 
 
	public static void main( String[] args)   {		
 		MainPlannerdemo appl = new MainPlannerdemo( );
		appl.doMentalMap();
	}

}
