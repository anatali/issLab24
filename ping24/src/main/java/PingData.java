package main.java;

import java.util.Vector;
import unibo.basicomm23.utils.CommUtils;

/*
 * Provides an object used by the manycallerobserver
 * of the system smath24synchmanycallers 
 * defined in the model smath24synch_0_manycalllers.qak
 */
public class PingData {
	private static PingData myself = null;
	
	public Vector<String> h = new Vector<String>();
	
	public static PingData create() {
		myself = new PingData();
		CommUtils.outcyan("PingData create:" + myself);
		return myself;
	}
 	
	public boolean ready() {
		CommUtils.outcyan("PingData ready " + h.size());
		return h.size() == 2;
	}
	
	public void addToHistory( String history) {
		CommUtils.outcyan("PingData addToHistory " + history);
		h.add(history);
	}
	
	public int historyLength() {
		return h.size();
	}
    
	public void showHistory() {
		CommUtils.outcyan("PingData showHistory " + h);
//		h.forEach( v -> {
//			CommUtils.outmagenta(v);
//		} );
	}
 
	
 
}
