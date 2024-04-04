

import alice.tuprolog.Prolog
import alice.tuprolog.Theory
import it.unibo.kactor.sysUtil
import unibo.basicomm23.utils.CommUtils
import java.io.FileInputStream
import java.io.FileOutputStream
import java.io.*
import java.util.HashMap

class MathUtils {

    companion object {  //replaces static of Java
        protected var count = 1
        protected var thcounter = 0
        protected var numOfExecutors = 0
        private val map = HashMap<Int, Int>()

        fun create(): MathUtils {
        	return MathUtils()
        }
    }

    fun fibo(n: Int): Int {
        return if (n == 1 || n == 2) 1 else fibo(n - 1) + fibo(n - 2)
    }

    fun fibo(n: Int, last: Int, fibolast: Int): Int {
        //HashMap<int, int> map = new HashMap<int, int>();
        CommUtils.outgreen("fibo:$n")
        if (n == 1 || n == 2) return 1
        if (n == last) return fibolast
        var v1 = map[n - 1]
        if (v1 == null) {
            v1 = fibo(n - 1, last, fibolast)
            if (v1 != 0 && v1 != 1) {
                CommUtils.outgreen("stored in map:" + (n - 1) + "=" + v1) //sostituisce
                map[n - 1] = v1
            }
        }
        var v2 = map[n - 2]
        if (v2 == null) {
            v2 = fibo(n - 2, last, fibolast)
            if (v1 != 0 && v1 != 1) {
                CommUtils.outgreen("stored in map:" + (n - 2) + "=" + v2)
                map[n - 2] = v2
            }
        }
        CommUtils.outgreen("stored in map:" + n + "=" + (v1 + v2))
        map[n] = v1 + v2
        return v1 + v2
    }

    fun fibo2(
        n: Int,
        last: Pair<Int, Int>, prelast: Pair<Int, Int>
    ): Pair<Int, Int> {
        if (n == last.first) return last
        if (n == prelast.first) return prelast
        CommUtils.outgreen("fibo2:" + n  )
        val v1 = fibo2(n - 1, last, prelast)
        val v2 = fibo2(n - 2, last, prelast)
        CommUtils.outyellow("fibo2: n=$n, v1=$v1 v2=$v2"   )
        return Pair(n, v1.second + v2.second)
    }

    fun fibo3(
        n: Int
    ): Pair<Int, Int> { //valore di f(n) e f(n-1)
            val v1 = fibo(n - 1)
            val v2 = fibo(n - 2)
            return Pair(v1 + v2, v1)
        }

    fun fibo4(
        n: Int, engine: Prolog
    ): Int { //vuso della cache Prolog
        if (n == 1 || n == 2) return 1
        val R =  solve("fibo($n,X)","X", engine)

        if( R != null ){
            CommUtils.outyellow("fibo4 has found: n=$n "   )
            return R.toInt()
        }
        CommUtils.outyellow("fibo4 elab: n=$n "   )
        val v1 = fibo4(n - 1, engine)
        val v2 = fibo4(n - 2, engine)

        val fact = "fibo($n,${v1+v2})"
        //Inserisco senza ripetizioni
        val R1 = engine.solve("retract( $fact )." )
        if( R1 == null ) CommUtils.outyellow("fibo4 $fact absent "   )
        //val R2 =  
        engine.solve("assert($fact).") 
        //if( R2 != null ) CommUtils.outyellow("fibo4 $n R2:  $R2 "   )
        return v1+v2
    }

    fun fiboWithMemo(
        n: Long, engine: Prolog
    ): Long { //vuso della cache Prolog
        if (n == 1L || n == 2L ) return 1L
        val R =  solve("fibo($n,X)","X", engine)

        if( R != null ){
            //CommUtils.outyellow("fiboWithMemo has found: n=$n "   )
            return R.toLong()
        }
        //CommUtils.outyellow("fiboWithMemo elab: n=$n "   )
        val v1 = fiboWithMemo(n - 1L, engine)
        val v2 = fiboWithMemo(n - 2L, engine)

        val fact = "fibo($n,${v1+v2})"
        //Inserisco senza ripetizioni
        val R1 = engine.solve("retract( $fact )." )
        if( R1.isSuccess() ) CommUtils.outyellow("fiboWithMemo retracted $fact  $R1"   )
        //else CommUtils.outyellow("fiboWithMemo $fact absent "   )
        //val R2 = 
        engine.solve("assert($fact).")
        //if( R2 != null ) CommUtils.outyellow("fiboWithMemo $n R2:  $R2 "   )
        return v1+v2
    }

    fun loadTheory( path: String, pengine: Prolog ) {
        try {
            CommUtils.outyellow("%%% test.MathUtils | loadTheory: th $path"   )
            //user.dir is typically the directory in which the Java virtual machine was invoked.
            //val executionPath = System.getProperty("user.dir")
             val worldTh = Theory( FileInputStream(path) )
             pengine.addTheory(worldTh)
        } catch (e: Exception) {
            CommUtils.outred("%%% test.MathUtils | loadheory WARNING: ${e}" )
            throw e
        }
    }
    fun saveTheory( FileName: String, pengine: Prolog) {
        val curTh = pengine.getTheory();
        //CommUtils.outyellow("%%% test.MathUtils | saveTheory: th $curTh"   )
        FileOutputStream(FileName).write(curTh.toString().toByteArray())
            //.write( curTh.toString() );
    }

    fun solve( goal: String, resVar: String, pengine: Prolog  ) : String? {
        //println("sysUtil  | solve  ${goal} resVar=$resVar" );
        val sol =  pengine.solve( goal+".");
        if( sol.isSuccess ) {
            if( resVar.length == 0 ) return "success"
            val result = sol.getVarValue(resVar)  //Term
            var resStr = result.toString()
            return sysUtil.strCleaned(resStr)
        }
        else return null
    }
    
    fun waitUserCmd( prompt: String    ) : Int {
		try {
			//CommUtils.outgreen("$prompt>>>  ")
			//val input = BufferedReader(InputStreamReader(System.`in`))
			//val startTime = System.currentTimeMillis()
			//while (System.currentTimeMillis() - startTime < tout && !input.ready() ) { }
			//while ( !input.ready() ) { }
//			var V = readLine();
//			while( V == null ) {
//				CommUtils.outgreen("???  $V  ")
//				V = readLine();
//			}
//			CommUtils.outgreen(">>>  $V  ")
			
			val read  = java.util.Scanner(System.`in`)  
					CommUtils.outgreen("$read  ")
		    CommUtils.outgreen("$prompt>>>  ")
		    val V = read.nextInt()  
			//return V;
//			while ( !input.ready() ) { }
 			println("$V")
 			return V
		} catch (e: java.lang.Exception) {
			CommUtils.outred("ERROR: ${e} ")
				return 10
				//e.printStackTrace()
		}
}
}//test.MathUtils
 