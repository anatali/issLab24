package utils;

import unibo.basicomm23.utils.CommUtils;

public  class MathUtils {
protected static int count = 1;
protected static int thcounter = 0;
protected static int numOfExecutors = 0;
private static MathUtils singleton;

    public static MathUtils create() {
    	if( singleton == null ) singleton = new MathUtils();
    	return singleton;
    }
    
	public  int fibo(int n) {
		if( n==0 || n==1) return 1;
		return fibo(n-1) + fibo(n-2);	
	}
 }

 