import alice.tuprolog.Prolog
import alice.tuprolog.Theory
import it.unibo.kactor.sysUtil
import unibo.basicomm23.utils.CommUtils
import java.io.FileInputStream
import java.io.FileOutputStream
import java.util.HashMap

class MathUtils {

    companion object {
 
        fun create(): MathUtils {
            //if (singleton.isInitialized ) singleton = test.MathUtils()
            //return singleton
        	return MathUtils()
        }
    }

    fun fibo(n: Int): Int {
        return if (n == 1 || n == 2) 1 else fibo(n - 1) + fibo(n - 2)
    }



    fun fiboWithMemo(
        n: Long, engine: Prolog
    ): Long { //vuso della cache Prolog
//    	CommUtils.outred("fiboWithMemo  n=$n    "   )
        val R =  solve("fibo($n,X)","X", engine)
//        CommUtils.outred("fiboWithMemo  R=$R   "   )
                 
        if( R != null ){
//            CommUtils.outyellow("fiboWithMemo has found: n=$n  R=$R "   )
            return R.toLong()
        }
        //CommUtils.outyellow("fiboWithMemo elab: n=$n "   )
        val v1 = fiboWithMemo(n - 1L, engine)
        val v2 = fiboWithMemo(n - 2L, engine)

        val fact = "fibo($n,${v1+v2})"
        //Inserisco senza ripetizioni
        //val R1 = 
        engine.solve("retract( $fact )." )
//        if( R1.isSuccess() ) CommUtils.outyellow("fiboWithMemo retracted $fact  $R1"   )
        //else CommUtils.outyellow("fiboWithMemo $fact absent "   )
        //val R2 = 
        engine.solve("assert($fact).")
//        CommUtils.outyellow("fiboWithMemo ASSERTED $fact ............. "   )
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
    }

    fun solve( goal: String, resVar: String, pengine: Prolog  ) : String? {
        //println("MathUtils  | solve  ${goal} resVar=$resVar" );
        val sol =  pengine.solve( goal+".");
        //println("MathUtils  | solve  sol=${sol}  " );
        if( sol.isSuccess ) {
            if( resVar.length == 0 ) return "success"
            val result = sol.getVarValue(resVar)  //Term
            var resStr = result.toString()
            println("MathUtils  | solve  $goal resStr=${resStr}  " );
            return sysUtil.strCleaned(resStr)
        }
        else return null
    }
    
    fun show(pengine: Prolog) {
    	println("MathUtils  | showwwwwwwwwwwwwwwwwwww" );
    	//val sol = solve(  "showFiboFacts(F)","F",pengine);    	
    	 solve(  "showFiboFacts","",pengine);
         //CommUtils.outblue( sol );
    }
}//MathUtils
 