import it.unibo.kactor.ActorBasic
import unibo.basicomm23.interfaces.IApplMessage
import unibo.basicomm23.utils.CommUtils
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.CoroutineScope

class DisplayCodedQakOld(name:String, 
		scope: CoroutineScope = GlobalScope, 
		confined : Boolean =false): ActorBasic(name,scope, confined){
  val display = main.resources.utils.DisplayObj.create()
 
  override suspend fun actorBody(msg : IApplMessage){
	CommUtils.outcyan("$name  $msg | ${Thread.currentThread().getName()} n=${Thread.activeCount()}")
	if( msg.msgId() == "out") display.write(msg.msgContent() )
  }

}