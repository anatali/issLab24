/* Generated by AN DISI Unibo */ 
package it.unibo.publisher

import it.unibo.kactor.*
import alice.tuprolog.*
import unibo.basicomm23.*
import unibo.basicomm23.interfaces.*
import unibo.basicomm23.utils.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import it.unibo.kactor.sysUtil.createActor   //Sept2023
//Sept2024
import org.slf4j.Logger
import org.slf4j.LoggerFactory 
import org.json.simple.parser.JSONParser
import org.json.simple.JSONObject


//User imports JAN2024

class Publisher ( name: String, scope: CoroutineScope, isconfined: Boolean=false  ) : ActorBasicFsm( name, scope, confined=isconfined ){

	override fun getInitialState() : String{
		return "s0"
	}
	override fun getBody() : (ActorBasicFsm.() -> Unit){
		//val interruptedStateTransitions = mutableListOf<Transition>()
		return { //this:ActionBasciFsm
				state("s0") { //this:State
					action { //it:State
						connectToMqttBroker( "wss://test.mosquitto.org:8081" )
						subscribe(  "sonardatatopic" ) //mqtt.subscribe(this,topic)
						delay(1000) 
						//val m = MsgUtil.buildEvent(name, "sonardata", "sonardata(10)" ) 
						publish(MsgUtil.buildEvent(name,"sonardata","sonardata(10)").toString(), "sonardatatopic" )   
						//genTimer( actor, state )
					}
					//After Lenzi Aug2002
					sysaction { //it:State
					}	 	 
					 transition(edgeName="t00",targetState="handlesonardata",cond=whenEvent("sonardata"))
				}	 
				state("handlesonardata") { //this:State
					action { //it:State
						if( checkMsgContent( Term.createTerm("sonardata(D)"), Term.createTerm("sonardata(D)"), 
						                        currentMsg.msgContent()) ) { //set msgArgList
								CommUtils.outblue("$name| sonar distance ${payloadArg(0)}")
						}
						//genTimer( actor, state )
					}
					//After Lenzi Aug2002
					sysaction { //it:State
					}	 	 
				}	 
			}
		}
} 
