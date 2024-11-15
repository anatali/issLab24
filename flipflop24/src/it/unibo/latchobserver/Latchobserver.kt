/* Generated by AN DISI Unibo */ 
package it.unibo.latchobserver

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

class Latchobserver ( name: String, scope: CoroutineScope, isconfined: Boolean=false  ) : ActorBasicFsm( name, scope, confined=isconfined ){

	override fun getInitialState() : String{
		return "s0"
	}
	override fun getBody() : (ActorBasicFsm.() -> Unit){
		//val interruptedStateTransitions = mutableListOf<Transition>()
		return { //this:ActionBasciFsm
				state("s0") { //this:State
					action { //it:State
						CommUtils.outblue("$name | START")
						observeResource("localhost","8765","ctxflipflop","latch","info")
						//genTimer( actor, state )
					}
					//After Lenzi Aug2002
					sysaction { //it:State
					}	 	 
					 transition(edgeName="t012",targetState="handleinfo",cond=whenDispatch("info"))
				}	 
				state("handleinfo") { //this:State
					action { //it:State
						if( checkMsgContent( Term.createTerm("latchval(SOURCE,TERM)"), Term.createTerm("latchval(SOURCE,TERM)"), 
						                        currentMsg.msgContent()) ) { //set msgArgList
								  val Source = payloadArg(0)
										        val infoMsg = payloadArg(1)
										        val M      = "$infoMsg from $Source"
								CommUtils.outblue("		$name | $M")
						}
						//genTimer( actor, state )
					}
					//After Lenzi Aug2002
					sysaction { //it:State
					}	 	 
					 transition(edgeName="t013",targetState="handleinfo",cond=whenDispatch("info"))
				}	 
			}
		}
} 
