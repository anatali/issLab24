package utils;

import javafx.util.Pair;
import unibo.basicomm23.utils.CommUtils;

import java.util.HashMap;

public  class MathUtilsJava {
protected static int count = 1;
protected static int thcounter = 0;
protected static int numOfExecutors = 0;
private static MathUtilsJava singleton = new MathUtilsJava();

private static HashMap<Integer, Integer> map = new HashMap<Integer, Integer>();

    public static MathUtilsJava create() {
    	//if( singleton == null ) singleton = new MathUtilsJava();
    	return singleton;
    }
    
	public  int fibo(int n) {
		if( n==1 || n==2 ) return 1;
		return fibo(n-1) + fibo(n-2);	
	}
	
	public  int fibo(int n, int last, int fibolast) {
		//HashMap<int, int> map = new HashMap<int, int>();
		CommUtils.outgreen("fibo:" + n  );
		if( n==0 || n==1) return 1;
		if( n == last ) return fibolast;
		Integer v1 = map.get(n-1);
		if( v1 == null ) {
			v1 = fibo(n-1,last,fibolast);
			if( v1 != 0 && v1 != 1 ) {
				CommUtils.outgreen("stored in map:" + (n - 1) + "=" + v1); //sostituisce
				map.put(n - 1, v1);
			}
		}
		Integer v2 = map.get(n-2);
		if( v2 == null ) {
			v2 = fibo(n-2,last,fibolast);
			if( v1 != 0 && v1 != 1 ) {
				CommUtils.outgreen("stored in map:" + (n - 2) + "=" + v2);
				map.put(n - 2, v2);
			}
		}
		CommUtils.outgreen("stored in map:" + (n) + "=" + (v1 + v2));
		map.put(n,v1 + v2);
		return v1 + v2;	
	}	
	
	public Pair<Integer,Integer> fibo2(int n,
				 Pair<Integer,Integer> last, Pair<Integer,Integer> preclast) {
		if( n == last.getKey() )     return last;
		if( n == preclast.getKey() ) return preclast;
		Pair<Integer,Integer> v1 = fibo2(n-1, last,preclast);
		Pair<Integer,Integer> v2 = fibo2(n-2, last,preclast);
		return new Pair<Integer,Integer>(v1.getValue()+v2.getValue(),v1.getValue());
	}
	
 
	/*
	 * Theory curTh = engine.getTheory(); // save current theory to file
new FileOutputStream(args[1]).write(curTh.toString().getBytes());
	 */
 }

 