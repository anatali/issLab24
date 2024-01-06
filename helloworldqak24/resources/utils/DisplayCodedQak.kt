import it.unibo.kactor.*
import alice.tuprolog.*
import unibo.basicomm23.*
import unibo.basicomm23.interfaces.*
import unibo.basicomm23.utils.*
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.CoroutineScope

class DisplayCodedQak(name:String, scope: CoroutineScope = GlobalScope, confined : Boolean =false):
          ActorBasic(name,scope, confined){
	val display = utils.ActorIO()
//	init{
//		  runBlocking{  autoMsg("start","do") }
//		}
 
  override suspend fun actorBody(msg : IApplMessage){
    if( msg.msgId() == "start"){
      kotlin.concurrent.thread(start = true) {
	     display.initialize( ) 
	  } 
      workStep(  msg  )
    }else if( msg.msgId() == "out"){
    	display.write(msg.msgContent())
    }
    else CommUtils.outgreen("$tt $name | received  $msg ")
  }

	suspend fun workStep(  msg : IApplMessage  ){
		CommUtils.outgreen("$tt $name | working on  $msg ")
	}
}