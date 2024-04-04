

import alice.tuprolog.Prolog
import alice.tuprolog.Theory
import it.unibo.kactor.sysUtil
import unibo.basicomm23.utils.CommUtils
import java.io.FileInputStream
import java.io.FileOutputStream
import java.io.*
import java.util.HashMap

class ApplUtils {

    companion object {  //replaces static of Java
        protected var count = 1
        protected var thcounter = 0
        protected var numOfExecutors = 0
 
        fun create(): ApplUtils {
        	return ApplUtils()
        }
    }

    fun fibo(n: Int): Int {
        return if (n == 1 || n == 2) 1 else fibo(n - 1) + fibo(n - 2)
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
    
    fun waitUserCmd( prompt: String ) : Int {
		try {
			//val read  = java.util.Scanner(System.`in`)  
		    //CommUtils.outgreen("$read  ")
		    CommUtils.outgreen("$prompt")
		    val V = readLine()   
 			if( V !== null ) {
 				println("$V")
 				return V.toInt()
 			}
 			else return 0
		} catch (e: java.lang.Exception) {
			CommUtils.outred("ERROR: ${e} ")
			return 0
		}
	}

}//MathUtils
 