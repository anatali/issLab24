package main.resources.utils;

import unibo.basicomm23.utils.CommUtils;

public  class Common {
protected static int count = 1;
protected static int thcounter = 0;
protected static int numOfExecutors = 0;

	public static void hello() {
		CommUtils.outblue("static hello " + count++ +" written by Common ");
	}
	
	public void helloMsg(String name ) {
		CommUtils.outblue(name + " | helloMsg " + count++ +" written by Common ");
	}
	
	public static  int incthcounter(int k) {
		for( int i=0; i<k; i++) thcounter = thcounter + 1;
		return thcounter;
	}
	
	public static  int counterInc( ) {
		count = count + 1;
		return count;
	}
	
	public static /*synchronized*/ int longAction( ) {
		CommUtils.outcyan("Common longAction STARTS: " + count);
		count = count + 1;
		int f = fibo(35);
		count = count + 1;
		CommUtils.outcyan("Common longAction ENDS with fibo=" + f + " count=" + count);		
		return count;
	}
	
	public static /*synchronized*/ int counterDec( ) {
		count = count - 1;
		return count;
	}
	
	public static  int counterVal( ) {
		return count;
	}
	public static void resetCounter() {
		count = 0;
	}	
	public static int getThcounter() {
		return thcounter;
	}
	public static void resetThcounter() {
		 thcounter = 0;
	}
	
	public static int incNumExecutors( ) {
		numOfExecutors++;
		return numOfExecutors;
	}
	
	public static int getNumExecutors() {
		return numOfExecutors;
	}
	public static void resetNumExecutors() {
		numOfExecutors = 0;
	}	
	
	
	public static int fibo(int n) {
		if( n==0 || n==1) return 1;
		return fibo(n-1) + fibo(n-2);	
	}
}

//C:\Didattica2023\issLab2023\qakdemo23