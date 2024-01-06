import it.unibo.kactor.*
import it.unibo.kactor.ActorBasic
import alice.tuprolog.*
import unibo.basicomm23.*
import unibo.basicomm23.interfaces.*
import unibo.basicomm23.utils.*
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.CoroutineScope

class DisplayCodedQak(name:String, scope: CoroutineScope = GlobalScope, confined : Boolean =false):
          ActorBasic(name,scope, confined){
  val display = utils.DisplayObj.create()
 
  override suspend fun actorBody(msg : IApplMessage){
	CommUtils.outcyan("$name  $msg | ${Thread.currentThread().getName()} n=${Thread.activeCount()}")
	if( msg.msgId() == "out") display.write(msg.msgContent() )
  }

}