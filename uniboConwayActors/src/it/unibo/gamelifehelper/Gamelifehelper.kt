/* Generated by AN DISI Unibo */ 
package it.unibo.gamelifehelper

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

class Gamelifehelper ( name: String, scope: CoroutineScope, isconfined: Boolean=false  ) : ActorBasicFsm( name, scope, confined=isconfined ){

	override fun getInitialState() : String{
		return "s0"
	}
	override fun getBody() : (ActorBasicFsm.() -> Unit){
		//val interruptedStateTransitions = mutableListOf<Transition>()
		 
			   var NAllCells  = conway.GridSupport.getNumOfCells()
		       var stopped    = false
		       var NCellended = 0 //set in terminatethegame       
		return { //this:ActionBasciFsm
				state("s0") { //this:State
					action { //it:State
						CommUtils.outblue("$name | STARTS  NAllCells=$NAllCells")
						//genTimer( actor, state )
					}
					//After Lenzi Aug2002
					sysaction { //it:State
					}	 	 
					 transition(edgeName="t015",targetState="handleguicmd",cond=whenDispatch("fromdisplay"))
					transition(edgeName="t016",targetState="handlerequest",cond=whenRequest("gridparams"))
				}	 
				state("handlerequest") { //this:State
					action { //it:State
						if( checkMsgContent( Term.createTerm("gridparams(X)"), Term.createTerm("gridparams(X)"), 
						                        currentMsg.msgContent()) ) { //set msgArgList
								
										        val res    = conway.GridSupport.readGridConfig("gridConfig.json")
												val RowsN  = res.get(0)
												val ColsN  = res.get(1)
												val CellSize = res.get(2)				
								answer("gridparams", "gridparamsvalues", "gridparamsvalues($RowsN,$ColsN,$CellSize)"   )  
						}
						//genTimer( actor, state )
					}
					//After Lenzi Aug2002
					sysaction { //it:State
					}	 	 
					 transition(edgeName="t017",targetState="handleguicmd",cond=whenDispatch("fromdisplay"))
					transition(edgeName="t018",targetState="handlerequest",cond=whenRequest("gridparams"))
				}	 
				state("handleguicmd") { //this:State
					action { //it:State
						if( checkMsgContent( Term.createTerm("fromdisplay(CMD)"), Term.createTerm("fromdisplay(start)"), 
						                        currentMsg.msgContent()) ) { //set msgArgList
								CommUtils.outgreen("$name | startthegame ")
								 stopped    = false  
								emit("startthegame", "startthegame(ok)" ) 
						}
						if( checkMsgContent( Term.createTerm("fromdisplay(CMD)"), Term.createTerm("fromdisplay(stop)"), 
						                        currentMsg.msgContent()) ) { //set msgArgList
								if(  ! stopped  
								 ){forward("gamestopped", "gamestopped($NAllCells)" ,"gamelife" ) 
								 conway.GridSupport.sendStopToAllCells(myself)  
								}
								 stopped = true  
						}
						//genTimer( actor, state )
					}
					//After Lenzi Aug2002
					sysaction { //it:State
					}	 	 
					 transition(edgeName="t019",targetState="handleguicmd",cond=whenDispatch("fromdisplay"))
					transition(edgeName="t020",targetState="terminatethegame",cond=whenDispatch("cellends"))
					transition(edgeName="t021",targetState="handlerequest",cond=whenRequest("gridparams"))
				}	 
				state("terminatethegame") { //this:State
					action { //it:State
						 NCellended = NCellended + 1  
						if(  NCellended == NAllCells  
						 ){CommUtils.outblack("$name | terminatethegame $NCellended/$NAllCells  ")
						forward("gameended", "gameended($NAllCells)" ,"gamelife" ) 
						 NCellended = 0  
						}
						//genTimer( actor, state )
					}
					//After Lenzi Aug2002
					sysaction { //it:State
					}	 	 
					 transition(edgeName="t022",targetState="terminatethegame",cond=whenDispatch("cellends"))
					transition(edgeName="t023",targetState="handleguicmd",cond=whenDispatch("fromdisplay"))
					transition(edgeName="t024",targetState="handlerequest",cond=whenRequest("gridparams"))
				}	 
			}
		}
} 