/* Generated by AN DISI Unibo */ 
package it.unibo.vrqakusage

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

//User imports JAN2024

class Vrqakusage ( name: String, scope: CoroutineScope, isconfined: Boolean=false  ) : ActorBasicFsm( name, scope, confined=isconfined ){

	override fun getInitialState() : String{
		return "s0"
	}
	override fun getBody() : (ActorBasicFsm.() -> Unit){
		//val interruptedStateTransitions = mutableListOf<Transition>()
		return { //this:ActionBasciFsm
				state("s0") { //this:State
					action { //it:State
						CommUtils.outcyan("$name STARTS")
						//genTimer( actor, state )
					}
					//After Lenzi Aug2002
					sysaction { //it:State
					}	 	 
					 transition( edgeName="goto",targetState="work", cond=doswitch() )
				}	 
				state("work") { //this:State
					action { //it:State
						request("step", "step(350)" ,"vrqak" )  
						//genTimer( actor, state )
					}
					//After Lenzi Aug2002
					sysaction { //it:State
					}	 	 
					 transition(edgeName="t011",targetState="handleStepdone",cond=whenReply("stepdone"))
					transition(edgeName="t012",targetState="handleStepfailed",cond=whenReply("stepfailed"))
				}	 
				state("handleStepdone") { //this:State
					action { //it:State
						CommUtils.outmagenta("$name | handleStepdone")
						delay(500) 
						//genTimer( actor, state )
					}
					//After Lenzi Aug2002
					sysaction { //it:State
					}	 	 
					 transition( edgeName="goto",targetState="work", cond=doswitch() )
				}	 
				state("handleStepfailed") { //this:State
					action { //it:State
						if( checkMsgContent( Term.createTerm("stepfailed(DURATION,CAUSE)"), Term.createTerm("stepfailed(T,C)"), 
						                        currentMsg.msgContent()) ) { //set msgArgList
								CommUtils.outmagenta("$name | handleStepfailed after ${payloadArg(0)} since ${payloadArg(1)}")
						}
						 System.exit(0)  
						//genTimer( actor, state )
					}
					//After Lenzi Aug2002
					sysaction { //it:State
					}	 	 
				}	 
			}
		}
} 