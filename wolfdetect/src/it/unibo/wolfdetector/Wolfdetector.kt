/* Generated by AN DISI Unibo */ 
package it.unibo.wolfdetector

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

class Wolfdetector ( name: String, scope: CoroutineScope, isconfined: Boolean=false  ) : ActorBasicFsm( name, scope, confined=isconfined ){

	override fun getInitialState() : String{
		return "s0"
	}
	override fun getBody() : (ActorBasicFsm.() -> Unit){
		//val interruptedStateTransitions = mutableListOf<Transition>()
		return { //this:ActionBasciFsm
				state("s0") { //this:State
					action { //it:State
						CommUtils.outblack("$name STARTS")
						delay(1000) 
						subscribeToLocalActor("sonarmock") 
						//genTimer( actor, state )
					}
					//After Lenzi Aug2002
					sysaction { //it:State
					}	 	 
					 transition(edgeName="t05",targetState="dosonarjob",cond=whenDispatch("startsonar"))
				}	 
				state("dosonarjob") { //this:State
					action { //it:State
						CommUtils.outblack("$name working ...")
						//genTimer( actor, state )
					}
					//After Lenzi Aug2002
					sysaction { //it:State
				 	 		stateTimer = TimerActor("timer_dosonarjob", 
				 	 					  scope, context!!, "local_tout_"+name+"_dosonarjob", 2000.toLong() )  //OCT2023
					}	 	 
					 transition(edgeName="t06",targetState="noobstacle",cond=whenTimeout("local_tout_"+name+"_dosonarjob"))   
					transition(edgeName="t07",targetState="handleObstacle",cond=whenEvent("obstacle"))
					transition(edgeName="t08",targetState="endsonarjob",cond=whenDispatch("stopsonar"))
				}	 
				state("noobstacle") { //this:State
					action { //it:State
						forward("ledCmd", "ledCmd(off)" ,"ledblu" ) 
						forward("ledCmd", "ledCmd(off)" ,"ledred" ) 
						//genTimer( actor, state )
					}
					//After Lenzi Aug2002
					sysaction { //it:State
					}	 	 
					 transition( edgeName="goto",targetState="dosonarjob", cond=doswitch() )
				}	 
				state("handleObstacle") { //this:State
					action { //it:State
						CommUtils.outblue("$name found an obstacke  ")
						forward("ledCmd", "ledCmd(on)" ,"ledblu" ) 
						request("takePhoto", "takePhoto(ok)" ,"camera" )  
						//genTimer( actor, state )
					}
					//After Lenzi Aug2002
					sysaction { //it:State
					}	 	 
					 transition(edgeName="t09",targetState="handlePhoto",cond=whenReply("photo"))
				}	 
				state("handlePhoto") { //this:State
					action { //it:State
						CommUtils.outcyan("$name handle the photo  ")
						if( checkMsgContent( Term.createTerm("photo(PHOTO)"), Term.createTerm("photo(V)"), 
						                        currentMsg.msgContent()) ) { //set msgArgList
								request("detectWolf", "detectWolf(payloadArg(0))" ,"imagerecusage" )  
						}
						//genTimer( actor, state )
					}
					//After Lenzi Aug2002
					sysaction { //it:State
					}	 	 
					 transition(edgeName="t010",targetState="checkWolfAnswer",cond=whenReply("iswolf"))
				}	 
				state("checkWolfAnswer") { //this:State
					action { //it:State
						if( checkMsgContent( Term.createTerm("iswolf(V)"), Term.createTerm("iswolf(true)"), 
						                        currentMsg.msgContent()) ) { //set msgArgList
								forward("ledCmd", "ledCmd(on)" ,"ledred" ) 
						}
						if( checkMsgContent( Term.createTerm("iswolf(V)"), Term.createTerm("iswolf(false)"), 
						                        currentMsg.msgContent()) ) { //set msgArgList
								forward("ledCmd", "ledCmd(off)" ,"ledred" ) 
						}
						//genTimer( actor, state )
					}
					//After Lenzi Aug2002
					sysaction { //it:State
					}	 	 
					 transition( edgeName="goto",targetState="dosonarjob", cond=doswitch() )
				}	 
				state("endsonarjob") { //this:State
					action { //it:State
						CommUtils.outblack("$name ENDS")
						//genTimer( actor, state )
					}
					//After Lenzi Aug2002
					sysaction { //it:State
					}	 	 
				}	 
			}
		}
} 