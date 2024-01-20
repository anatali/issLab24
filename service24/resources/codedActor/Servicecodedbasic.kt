package codedActor

import it.unibo.kactor.*
import kotlinx.coroutines.delay
import it.unibo.kactor.MsgUtil
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.runBlocking
import unibo.basicomm23.interfaces.IApplMessage
import unibo.basicomm23.utils.CommUtils
import utils.MathUtils
import alice.tuprolog.Term
import alice.tuprolog.Struct

/*
--------------------------------------------------
Servicecodedbasic
--------------------------------------------------
 */

class Servicecodedbasic (name:String,  scope: CoroutineScope = GlobalScope,
						 confined : Boolean =false): ActorBasic(name,scope, confined){
 	
	private var msgArgList = mutableListOf<String>()
	
			
    override suspend fun actorBody(msg : IApplMessage){
		CommUtils.outgreen("$tt $name | received  $msg "  )
		if( msg.msgId() == "dofibo"){
			dofibo( msg.msgContent()  )
		}//else CommUtils.outgreen("$tt $name | received  $msg "  )
     }
  	
	suspend fun dofibo(  content : String  ){
		CommUtils.outmagenta("$tt $name | content $content}")
		val tt = Term.createTerm(content) as Struct
	    val ttar = tt.arity
	    for( i in 0..ttar-1 ) msgArgList.add( tt.getArg(i).toString().replace("'","") )
	    val v    =  msgArgList.elementAt(0)
		val math = utils.MathUtils.create()
		var F    = math.fibo( v.toInt() )
		CommUtils.outmagenta("$tt $name | F= $F")
	}
	
	
 
    fun  payloadArg( n : Int  ) : String{
         return msgArgList.elementAt(n)
    }

} 
 