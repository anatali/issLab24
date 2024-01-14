package utils


import it.unibo.kactor.sysUtil
import unibo.basicomm23.utils.CommUtils
import java.util.HashMap

class MathUtils {

    companion object {
        protected var count = 1
        protected var thcounter = 0
        protected var numOfExecutors = 0
        private var singleton: MathUtils? = null
        private val map = HashMap<Int, Int>()
        fun create(): MathUtils? {
            if (singleton == null) singleton = MathUtils()
            return singleton
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
        n: Int
    ): Int { //vuso della cache Prolog
        if (n == 1 || n == 2) return 1
        val R = sysUtil.solve("fibo($n,X)","X")

        if( R != null ){
            CommUtils.outyellow("fibo4 has found: n=$n "   )
            return R.toInt()
        }
        CommUtils.outyellow("fibo4 elab: n=$n "   )
        val v1 = fibo4(n - 1)
        val v2 = fibo4(n - 2)

        val fact = "fibo($n,${v1+v2})"
        //Inserisco senza ripetizioni
        val R1 = sysUtil.solve("retract( $fact )","")
        if( R1 == null ) CommUtils.outyellow("fibo4 $fact absent "   )
        val R2 = sysUtil.solve("assert($fact)","")
        //if( R2 != null ) CommUtils.outyellow("fibo4 $n R2:  $R2 "   )
        return v1+v2
    }

}//MathUtils
