/* Generated by AN DISI Unibo */ 
package it.unibo.callerobserver

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

class Callerobserver ( name: String, scope: CoroutineScope, isconfined: Boolean=false  ) : ActorBasicFsm( name, scope, confined=isconfined ){

	override fun getInitialState() : String{
		return "s0"
	}
	override fun getBody() : (ActorBasicFsm.() -> Unit){
		//val interruptedStateTransitions = mutableListOf<Transition>()
		 val obsdata = main.java.ObserverData.create()
		return { //this:ActionBasciFsm
				state("s0") { //this:State
					action { //it:State
						CommUtils.outblack("$name STARTS  ")
						observeResource("localhost","8035","ctxmanycallers","clr1","callerinfo")
						observeResource("localhost","8035","ctxmanycallers","clr2","callerinfo")
						//genTimer( actor, state )
					}
					//After Lenzi Aug2002
					sysaction { //it:State
					}	 	 
					 transition(edgeName="t03",targetState="handleinfo",cond=whenDispatch("callerinfo"))
				}	 
				state("handleinfo") { //this:State
					action { //it:State
						CommUtils.outblack("$name in ${currentState.stateName} | $currentMsg | ${Thread.currentThread().getName()} n=${Thread.activeCount()}")
						 	   
						 obsdata.addToHistory( currentMsg.msgContent() )  
						CommUtils.outmagenta("callerobserver check=${obsdata.check()}")
						//genTimer( actor, state )
					}
					//After Lenzi Aug2002
					sysaction { //it:State
					}	 	 
					 transition(edgeName="t04",targetState="handleinfo",cond=whenDispatch("callerinfo"))
					transition(edgeName="t05",targetState="handlerequest",cond=whenRequestGuarded("info",{ obsdata.ready()  
					}))
				}	 
				state("handlerequest") { //this:State
					action { //it:State
						 val R = obsdata.check()  
						answer("info", "obsinfo", "obsinfo($R)"   )  
						CommUtils.outmagenta("callerobserver BYE")
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
