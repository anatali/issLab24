/* Generated by AN DISI Unibo */ 
package it.unibo.mapbuilder

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
import unibo.planner23.*

class Mapbuilder ( name: String, scope: CoroutineScope, isconfined: Boolean=false  ) : ActorBasicFsm( name, scope, confined=isconfined ){

	override fun getInitialState() : String{
		return "s0"
	}
	override fun getBody() : (ActorBasicFsm.() -> Unit){
		//val interruptedStateTransitions = mutableListOf<Transition>()
		
		 
		val planner      = Planner23Util()
		var CurPlan   = ""
		var StepInPlan = false
		var CurMove    = ""
		var Athome     = false 
		var PlanForHome=false
		return { //this:ActionBasciFsm
				state("s0") { //this:State
					action { //it:State
						  planner.initAI()  
						connectToMqttBroker( "wss://test.mosquitto.org:8081" )
						subscribe(  "unibodisiplan" ) //mqtt.subscribe(this,topic)
						forward("halt", "halt(1)" ,"basicrobot" ) 
						delay(300) 
						//genTimer( actor, state )
					}
					//After Lenzi Aug2002
					sysaction { //it:State
					}	 	 
					 transition( edgeName="goto",targetState="explore", cond=doswitch() )
				}	 
				state("explore") { //this:State
					action { //it:State
						CommUtils.outblue("$name | explore (7,5) ")
						 planner.setGoal(7,5); PlanForHome = false  
						 CurPlan = planner.doPlanCompact()  
						//genTimer( actor, state )
					}
					//After Lenzi Aug2002
					sysaction { //it:State
					}	 	 
					 transition( edgeName="goto",targetState="execThePlan", cond=doswitchGuarded({ CurPlan.length>0  
					}) )
					transition( edgeName="goto",targetState="workdone", cond=doswitchGuarded({! ( CurPlan.length>0  
					) }) )
				}	 
				state("workdone") { //this:State
					action { //it:State
						CommUtils.outblue("$name | workdone ")
						 planner.showCurrentRobotState()  
						 planner.saveRoomMap("map2024ok")   
						//genTimer( actor, state )
					}
					//After Lenzi Aug2002
					sysaction { //it:State
					}	 	 
				}	 
				state("execThePlan") { //this:State
					action { //it:State
						CommUtils.outyellow("$name | execThePlan CurPlan=$CurPlan")
						if(  CurPlan.length > 0  
						 ){  CurMove = ""+CurPlan[0]; 
										CurPlan = CurPlan.drop(1) 
						}
						else
						 { CurMove=""  
						 }
						//genTimer( actor, state )
					}
					//After Lenzi Aug2002
					sysaction { //it:State
					}	 	 
					 transition( edgeName="goto",targetState="doMove", cond=doswitchGuarded({ (CurMove.length > 0)  
					}) )
					transition( edgeName="goto",targetState="endOfPlan", cond=doswitchGuarded({! ( (CurMove.length > 0)  
					) }) )
				}	 
				state("doMove") { //this:State
					action { //it:State
						CommUtils.outblue("$name | doMove CurMove=$CurMove")
						delay(100) 
						if(  CurMove == "w"  
						 ){ StepInPlan = true   
						}
						if(  CurMove == "l"  
						 ){forward("move", "move(l)" ,"basicrobot" ) 
						  planner.updateMap(  "l", "" )   
						 StepInPlan = false  
						}
						if(  CurMove == "r"  
						 ){forward("move", "move(r)" ,"basicrobot" ) 
						  planner.updateMap(  "r", "" )   
						 StepInPlan = false   
						}
						 val MAP = planner.getMapOneLine()  
						//genTimer( actor, state )
					}
					//After Lenzi Aug2002
					sysaction { //it:State
					}	 	 
					 transition( edgeName="goto",targetState="dostep", cond=doswitchGuarded({ StepInPlan  
					}) )
					transition( edgeName="goto",targetState="execThePlan", cond=doswitchGuarded({! ( StepInPlan  
					) }) )
				}	 
				state("dostep") { //this:State
					action { //it:State
						CommUtils.outblue("$name | dostep ")
						request("step", "step(340)" ,"basicrobot" )  
						//genTimer( actor, state )
					}
					//After Lenzi Aug2002
					sysaction { //it:State
					}	 	 
					 transition(edgeName="t00",targetState="stepcompleted",cond=whenReply("stepdone"))
					transition(edgeName="t01",targetState="planko",cond=whenReply("stepfailed"))
				}	 
				state("stepcompleted") { //this:State
					action { //it:State
						 planner.updateMap(  "w", "" )  
						//genTimer( actor, state )
					}
					//After Lenzi Aug2002
					sysaction { //it:State
					}	 	 
					 transition( edgeName="goto",targetState="execThePlan", cond=doswitch() )
				}	 
				state("endOfPlan") { //this:State
					action { //it:State
						 Athome = planner.atHome()  
						CommUtils.outmagenta("$name | endOfPlan with Athome=$Athome ")
						//genTimer( actor, state )
					}
					//After Lenzi Aug2002
					sysaction { //it:State
					}	 	 
					 transition( edgeName="goto",targetState="explore", cond=doswitchGuarded({ !Athome  
					}) )
					transition( edgeName="goto",targetState="tuneAtHome", cond=doswitchGuarded({! ( !Athome  
					) }) )
				}	 
				state("tuneAtHome") { //this:State
					action { //it:State
						delay(500) 
						 Athome  = false  
						 val Dir = planner.getDirection()  
						CommUtils.outmagenta("$name | tuneAtHome Dir=$Dir")
						if(  Dir == "upDir"  
						 ){forward("move", "move(l)" ,"basicrobot" ) 
						forward("move", "move(w)" ,"basicrobot" ) 
						forward("move", "move(r)" ,"basicrobot" ) 
						forward("move", "move(w)" ,"basicrobot" ) 
						}
						if(  Dir == "leftDir"  
						 ){forward("move", "move(w)" ,"basicrobot" ) 
						forward("move", "move(r)" ,"basicrobot" ) 
						forward("move", "move(w)" ,"basicrobot" ) 
						forward("move", "move(l)" ,"basicrobot" ) 
						}
						 planner.showCurrentRobotState()  
						delay(1000) 
						//genTimer( actor, state )
					}
					//After Lenzi Aug2002
					sysaction { //it:State
					}	 	 
					 transition( edgeName="goto",targetState="explore", cond=doswitch() )
				}	 
				state("planko") { //this:State
					action { //it:State
						CommUtils.outmagenta("$name | planko with CurPlan=$CurPlan PlanForHome=$PlanForHome")
						 planner.showCurrentRobotState()  
						if(  PlanForHome  
						 ){ planner.updateMap(  "w", "" )  
						CommUtils.outred("$name | Ignore collision while goingback to HOME")
						 planner.saveRoomMap("map2024fatal")   
						}
						else
						 { planner.updateMapObstacleOnCurrentDirection()  
						 }
						//genTimer( actor, state )
					}
					//After Lenzi Aug2002
					sysaction { //it:State
					}	 	 
					 transition( edgeName="goto",targetState="execThePlan", cond=doswitchGuarded({ PlanForHome  
					}) )
					transition( edgeName="goto",targetState="backToHome", cond=doswitchGuarded({! ( PlanForHome  
					) }) )
				}	 
				state("backToHome") { //this:State
					action { //it:State
						CommUtils.outmagenta("$name | ++++ going backToHome ++++ ")
						   
						   			planner.setGoal(0,0)
						   			PlanForHome = true
						   			CurPlan = planner.doPlanCompact()  			
						//genTimer( actor, state )
					}
					//After Lenzi Aug2002
					sysaction { //it:State
					}	 	 
					 transition( edgeName="goto",targetState="execThePlan", cond=doswitch() )
				}	 
			}
		}
} 