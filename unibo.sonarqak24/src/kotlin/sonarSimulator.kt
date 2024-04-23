import it.unibo.kactor.*
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import unibo.basicomm23.interfaces.IApplMessage
import unibo.basicomm23.utils.CommUtils

/*
-------------------------------------------------------------------------------------------------
sonarSimulator.kt
-------------------------------------------------------------------------------------------------
 */

class sonarSimulator ( name : String ) : ActorBasic( name ) {
	var working = false
	var v0      = 80

	init{
		//autostart
		//runBlocking{  autoMsg("simulatorstart","do") }
	}
//@kotlinx.coroutines.ObsoleteCoroutinesApi

    override suspend fun actorBody(msg : IApplMessage){
  		CommUtils.outblue("$tt $name | received  $msg "  )
  		if( msg.msgId() == "sonarstart") startSimulation(   )
  		if( msg.msgId() == "sonarstop")  stopimulation(   )
		if( msg.msgId() == "simulatorstart" && working) startDataReadSimulation(   )
     }
  	 
//@kotlinx.coroutines.ObsoleteCoroutinesApi

    suspend fun stopimulation(    ){
    	CommUtils.outblue("$tt $name | stopimulation "  )
    	working = false
    }
	suspend fun startSimulation(    ){
		CommUtils.outblue("$tt $name | startSimulation "  )
		working = true
		startDataReadSimulation(    )
	}
   
	suspend fun startDataReadSimulation(    ){
		//working = true
//		var i = 0
//		val data = sequence<Int>{
//			var v0 = 80
//			yield(v0)
//			while(true){
//				v0 = v0 - 5
//				yield( v0 )
//			}
//		}
		//while( i < 20 ){
		//while( working ){
// 	 			val m1 = "distance( ${data.elementAt(i)} )"
 	 			val m1 = "distance( ${v0} )"
//				i++
 	 			v0 = v0 - 5
 				val event = CommUtils.buildEvent( name,"sonardistance",m1)
  				emitLocalStreamEvent( event )
 				println("$tt $name | generates $event working=$working")
 				//emit(event)  //APPROPRIATE ONLY IF NOT INCLUDED IN A PIPE
 				delay( 500 )
// 				if( working) 
 				runBlocking{  autoMsg("simulatorstart","do") }
  		//}			
		//terminate()
	}

} 

//@kotlinx.coroutines.ObsoleteCoroutinesApi
//
//fun main() = runBlocking{
// //	val startMsg = MsgUtil.buildDispatch("main","start","start","datasimulator")
//	val consumer  = dataConsumer("dataconsumer")
//	val simulator = sonarSimulator( "datasimulator" )
//	val filter    = dataFilter("datafilter", consumer)
//	val logger    = dataLogger("logger")
//	simulator.subscribe( logger ).subscribe( filter ).subscribe( consumer ) 
//	MsgUtil.sendMsg("start","start",simulator)
//	simulator.waitTermination()
// } 