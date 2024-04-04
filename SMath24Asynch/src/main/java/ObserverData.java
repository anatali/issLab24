package main.java;

import java.util.Vector;

import unibo.basicomm23.utils.CommUtils;

public class ObserverData {
	private static ObserverData myself = null;
	
	private Vector<String> h = new Vector<String>();
	
	public static ObserverData create() {
		myself = new ObserverData();
		CommUtils.outgray("ObserverData create:" + myself);
		return myself;
	}
	
	public boolean ready() {
		CommUtils.outgray("ObserverData ready " + h.size());
		return h.size() == 2;
	}
	
	public void addToHistory( String history) {
		CommUtils.outgray("ObserverData addToHistory " + history);
		h.add(history);
	}
	
	public int historyLength() {
		return h.size();
	}
	
	//La risposta delle richiesta per il numero minore arriva prima
	public boolean checkOneCaller() {
		if( ready() ) {			
			CommUtils.outcyan("ObserverData 0 " + h.get(0));
			CommUtils.outcyan("ObserverData 1 " + h.get(1));
			//CALLER_N
			String[] first  = h.get(0).split("_");
			String[] second = h.get(1).split("_");
			CommUtils.outcyan("ObserverData  " + first[1] + " " + second[1] );
		    return first[0].equals("c1") &&  second[0].equals("c1") &&
		    	   Integer.parseInt( first[1] ) < Integer.parseInt( second[1] );
		}else return false;
	}	
	
	public boolean check() {
		if( ready() )
			return h.get(0).equals("callerdone(clr2)") &&  h.get(1).equals("callerdone(clr1)");
		else return false;
	}
}
