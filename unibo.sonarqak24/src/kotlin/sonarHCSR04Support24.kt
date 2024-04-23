/*
 sonarHCSR04Support24
 A CodedQactor that auto-starts.
 Launches a process p that activates sonar.py.
 Reads data from the InputStream of p and, for each value,
 emits the event   sonar : distance( V ).
 */
import java.io.BufferedReader
import java.io.InputStreamReader
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import it.unibo.kactor.ActorBasic		 
import kotlinx.coroutines.delay
import it.unibo.kactor.MsgUtil
import kotlinx.coroutines.runBlocking
import unibo.basicomm23.interfaces.IApplMessage
import unibo.basicomm23.utils.CommUtils


class sonarHCSR04Support24 ( name : String ) : ActorBasic( name ) {
	lateinit var reader : BufferedReader
	var working = false
    lateinit var p : Process
	//var coapSupport = javacode.CoapSupport("coap://localhost:8028","ctxsonarresource/sonarresource")
	
	//init{
		//autostart
		//runBlocking{  autoMsg("sonarstart","do") }
	//}

	
	
	override suspend fun actorBody(msg : IApplMessage){
		CommUtils.outgreen("$tt $name | received  $msg "  )
 		//println("$tt $name | received  $msg "  )   
  		if( msg.msgId() == "sonarstart") startTheSonar(   )
  		if( msg.msgId() == "sonarstop")  stopTheSonar(   )
//		if( msg.msgId() == "sonarwork" && working) work(   )

//		if( msg.msgId() == "sonarstart"){
			//println("sonarHCSR04Support24 STARTING") //AVOID SINCE pipe ...
//			try{
//				//val p  = Runtime.getRuntime().exec("sudo ./SonarAlone")
//				val p  = Runtime.getRuntime().exec("python sonar.py")
//				reader = BufferedReader(  InputStreamReader(p.getInputStream() ))
//				doRead(   )
//			}catch( e : Exception){
//				println("WARNING: $name does not find low-level code")
//			}
// 		}
     }
		
	suspend fun startTheSonar() {
    	CommUtils.outblue("$tt $name | startTheSonar "  )    	
    	working = true		
		p       = Runtime.getRuntime().exec("python sonar.py")
		reader = BufferedReader(  InputStreamReader(p.getInputStream() ))
		doRead(   )
	}
	
	suspend fun stopTheSonar() {
     	working = false	
    	delay(500)
       	CommUtils.outred("$tt $name | stopTheSonar destroy the process"  )
   	    p.destroy()
    	if (p.isAlive()) {
    	    p.destroyForcibly();
    	}
       	CommUtils.outred("$tt $name | stopTheSonar ENDS"  )
	}
	
//	fun work() {
//		val p  = Runtime.getRuntime().exec("python sonarwork.py")
//		reader = BufferedReader(  InputStreamReader(p.getInputStream() ))
//		doRead(   )
//		//CommUtils.outmagenta("$name with python: data = $data"   )
//	}
	
	suspend fun doRead(   ){
 		var counter = 0
 		CommUtils.outblue("$tt $name | doRead "  )
		//GlobalScope.launch{	//to allow message handling
 		myscope.launch{
		while( working ){
				var data = reader.readLine()
				//CommUtils.outyellow("$name with python: data = $data"   )
				if( data != null ){
					try{
						val vd = data.toFloat()
						val v  = vd.toInt()
						if( v <= 100 ){	//A first filter ...
							val m1 = "distance( ${v} )"
							val event = MsgUtil.buildEvent( "sonarHCSR04Support","sonardata",m1)
							//emit( event )  //should be propagated also to the remote resource
							emitLocalStreamEvent( event )		//not propagated to remote actors
							CommUtils.outyellow("sonarHCSR04Support24 doRead emits ${counter++}: $event "   )
						}
					}catch(e: Exception){
						CommUtils.outred("sonarHCSR04Support24 doRead ERROR: $e "   )
					}
				}
				delay( 250 ) 	//Avoid too fast generation
 		}
		CommUtils.outred("sonarHCSR04Support24 doRead ENDS"   )
		}
	}
}