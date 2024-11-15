/* Generated by AN DISI Unibo */ 
package it.unibo.pongev

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

class Pongev ( name: String, scope: CoroutineScope, isconfined: Boolean=false  ) : ActorBasicFsm( name, scope, confined=isconfined ){

	override fun getInitialState() : String{
		return "s0"
	}
	override fun getBody() : (ActorBasicFsm.() -> Unit){
		//val interruptedStateTransitions = mutableListOf<Transition>()
		 val d = DisplayObj.create()
		  val TMAX = 3000L; 
				//val dd = DisplayData.create() 
		return { //this:ActionBasciFsm
				state("s0") { //this:State
					action { //it:State
						CommUtils.outgreen("$name STARTS")
						//genTimer( actor, state )
					}
					//After Lenzi Aug2002
					sysaction { //it:State
				 	 		stateTimer = TimerActor("timer_s0", 
				 	 					  scope, context!!, "local_tout_"+name+"_s0", TMAX )  //OCT2023
					}	 	 
					 transition(edgeName="t00",targetState="endofexchange",cond=whenTimeout("local_tout_"+name+"_s0"))   
					transition(edgeName="t01",targetState="reply",cond=whenEvent("ball"))
				}	 
				state("reply") { //this:State
					action { //it:State
						if( checkMsgContent( Term.createTerm("ball(N)"), Term.createTerm("ball(X)"), 
						                        currentMsg.msgContent()) ) { //set msgArgList
								 val N = payloadArg(0).toInt()  
								CommUtils.outgreen("$name perceives $N")
								 d.print("$name perceives $N")  
								if(  N < 10  
								 ){delay(500) 
								 val V = "reply$N"  
								CommUtils.outgreen("$name emits $V ..........v")
								emit("ball", "ball($V)" ) 
								}
						}
						//genTimer( actor, state )
					}
					//After Lenzi Aug2002
					sysaction { //it:State
				 	 		stateTimer = TimerActor("timer_reply", 
				 	 					  scope, context!!, "local_tout_"+name+"_reply", TMAX )  //OCT2023
					}	 	 
					 transition(edgeName="t02",targetState="endofexchange",cond=whenTimeout("local_tout_"+name+"_reply"))   
					transition(edgeName="t03",targetState="reply",cond=whenEvent("ball"))
				}	 
				state("endofexchange") { //this:State
					action { //it:State
						CommUtils.outgreen("$name ENDS ")
						//genTimer( actor, state )
					}
					//After Lenzi Aug2002
					sysaction { //it:State
					}	 	 
				}	 
			}
		}
} 
