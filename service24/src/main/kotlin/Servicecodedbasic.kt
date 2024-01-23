import it.unibo.kactor.*
import unibo.basicomm23.interfaces.IApplMessage
import unibo.basicomm23.utils.CommUtils
import alice.tuprolog.Term
import alice.tuprolog.Struct


/*
--------------------------------------------------
Servicecodedbasic
--------------------------------------------------
 */
/*
 class Servicecodedbasic (name:String, scope: CoroutineScope = GlobalScope,
 						 confined : Boolean =false): ActorBasic(name,scope,  confined){
*/
class `Servicecodedbasic` (name:String ): ActorBasic( name ){
//se no dice: manca codedActor.Servicecodedbasic.<init>
init{
	CommUtils.outblue("$tt $name | init: WAITING for messages ...  "  )
}
/*
MESSAGE DRIVEN structure
 */
    override suspend fun actorBody(msg : IApplMessage){
		CommUtils.outblue("$name | received  $msg "  )
		if( msg.msgId() == "dofibo"){
			addValueToRequestMap(msg.msgId()+msg.msgSender(), msg)
			dofibo( msg.msgSender(), msg.msgContent()  )
		}
     }
  	
	suspend fun dofibo(  sender: String, content : String  ){ //suspend since answer
		CommUtils.outblue("$name | content $content}")
		val math = utils.MathUtils.create()
		val T0 = getCurrentTime()
		val cterm = Term.createTerm(content) as Struct
		val v     = cterm.getArg(0).toString().replace("'","")
		//CommUtils.outmagenta("$tt $name | v= $v")
		var R   = math.fibo( v.toInt() ) //
		val TF  = getDuration(T0)
		val answerMsg = "fibodone( $sender,$v,$R,$TF )"
		updateResourceRep(answerMsg)
		if(  mqttConnected ){
			CommUtils.outblue("$name | publish ....")
			mqtt.publish("servertopic", "serverinfo") 
		}
        answer("dofibo", "fibodone ", answerMsg)
		CommUtils.outblue("$name | send the answer fibsodone:$answerMsg}")
	}
}
 