import alice.tuprolog.Prolog
import alice.tuprolog.Theory
import it.unibo.kactor.sysUtil
import unibo.basicomm23.utils.CommUtils
import java.io.FileInputStream
import java.io.FileOutputStream
import java.util.HashMap

class MathUtils {

    companion object {
        protected var count = 1
        protected var thcounter = 0
        protected var numOfExecutors = 0
        private val map = HashMap<Int, Int>()

        fun create(): MathUtils {
         	return MathUtils()
        }
    }

    fun fibo(n: Int): Long {
        return if (n == 1 || n == 2) 1 else fibo(n - 1) + fibo(n - 2)
    }

 }//MathUtils
 