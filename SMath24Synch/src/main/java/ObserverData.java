package main.java;

import java.util.Vector;

import unibo.basicomm23.utils.CommUtils;

/*
 * Provides an object used by the manycallerobserver
 * of the system smath24synchmanycallers 
 * defined in the model smath24synch_0_manycalllers.qak
 */
public class ObserverData {
	private static ObserverData myself = null;
	
	private Vector<String> h = new Vector<String>();
	
	public static ObserverData create() {
		myself = new ObserverData();
		CommUtils.outcyan("ObserverData create:" + myself);
		return myself;
	}
 	
	public boolean ready() {
		CommUtils.outcyan("ObserverData ready " + h.size());
		return h.size() == 2;
	}
	
	public void addToHistory( String history) {
		CommUtils.outcyan("ObserverData addToHistory " + history);
		h.add(history);
	}
	
	public int historyLength() {
		return h.size();
	}

	public boolean checkOneCaller() {
		if( ready() ) {			
			CommUtils.outcyan("ObserverData 0 " + h.get(0));
			CommUtils.outcyan("ObserverData 1 " + h.get(1));
			//CALLER_REQUESTTIME_ELABTIME,RECEIVETIME
			String[] first  = h.get(0).split("_");
			String[] second = h.get(1).split("_");
			CommUtils.outcyan("ObserverData  " + first[1] + " " + second[1] );
		    return first[0].equals("c1") &&  second[0].equals("c1") &&
		    	   Integer.parseInt( first[1] ) < Integer.parseInt( second[1] );
		}else return false;
	}
	
	public boolean checkManyCallers() {
		if( ready() ) {
			CommUtils.outcyan("ObserverData 0 " + h.get(0));
			CommUtils.outcyan("ObserverData 1 " + h.get(1));
			//CALLER_REQUESTTIME_ELABTIME,RECEIVETIME
			String[] first  = h.get(0).split("_");
			String[] second = h.get(1).split("_");
			//CommUtils.outcyan("ObserverData  " + first[1] + " " + second[1] );
			int treqOf_clr1 = 0;
			int treqOf_clr2 = 0;
			if( first[0].equals("clr1")  ) { //first perceived  
				treqOf_clr1=Integer.parseInt(first[1]); 
				treqOf_clr2=Integer.parseInt(second[1]);
			}else { //first perceived by observer is clr2
				CommUtils.outmagenta("manycallerobserver was updated by clr2 first ");
				treqOf_clr1 = Integer.parseInt(second[1]);
				treqOf_clr2 = Integer.parseInt(first[1]); 
			}		  
		    return  treqOf_clr1 < treqOf_clr2;
		    //HINT: Integer.parseInt( first[2] ) could be > Integer.parseInt( second[2] )
		}else return false;
	}
}
