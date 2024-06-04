package conway;

import alice.tuprolog.Term
import it.unibo.kactor.ActorBasic
import unibo.basicomm23.utils.CommUtils
import java.util.*

object GLMSupport {

    @JvmStatic suspend fun sendToNeighbors(
        a: ActorBasic, MyInfo: String, mode:String="evstream",
        NbNameslist: Vector<Term>? = null ){
        when( mode ){
            "evstream" -> emitLocalStreamToNeighbors(a,  MyInfo )
            "mqtt"     -> emitEvstreamWithmqtt(a,  MyInfo )
            "dispatch" -> forwardToNeighbors(a,  MyInfo, NbNameslist!! )
            else -> CommUtils.outred("sendToNeighbors mode $mode not found")
        }

    }

    @JvmStatic suspend fun emitLocalStreamToNeighbors(a: ActorBasic, MyInfo: String ){
        a.emitLocalStreamEvent("curstate", "curstate($MyInfo)" )
    }

    @JvmStatic suspend fun emitEvstreamWithmqtt(a: ActorBasic, MyInfo: String){
        CommUtils.outcyan("${a.name} emitstreammqtt $MyInfo")
        a.emitstreammqtt(a.name, "curstate","curstate($MyInfo)")
    }


    @JvmStatic suspend fun forwardToNeighbors(a: ActorBasic, MyInfo: String, NbNameslist: Vector<Term> ) {
        //Ricaloclare i vicini tutte le volte per ogni cella ...
//        val Coords      = conway.GridSupport.getCellCoords(a.name)
//        val NbNameslist = conway.GridSupport.getCellNeighbors(Coords[0], Coords[1])
if(NbNameslist.size==0) CommUtils.outred("GLMSupport | LIST NbNameslist EMPTY ")
        val nblist      = NbNameslist.iterator()
        while (nblist.hasNext()) {
            val next = "cell" + nblist.next().toString()
            //CommUtils.outcyan("GLMSupport | forwardToNeighbors curstate($MyInfo) to $next")
            a.forward("curstate", "curstate($MyInfo)", next)
        }
    }

    fun subscribeToNeighborsMqtt( a: ActorBasic, cellName : String ) : Int{
        val Coords  = conway.GridSupport.getCellCoords(cellName)
        var Countnb = 0
        val x = Coords[0]
        val y = Coords[1]
  /*1*/ val nblist=conway.GridSupport.getCellNeighbors(x,y)
        val nblistiter  = nblist.iterator()
        while( nblistiter.hasNext() ){
            Countnb++
            val next = nblistiter.next().toString()
            //CommUtils.outcyan("${a.name} subscribes to topic cell$next")
  /*2*/     //a.subscribe( "cell$next" )  //TOPIC cell_x_y
        }
        return Countnb
    }



}