/* Generated by AN DISI Unibo */ 
package it.unibo.sonarusagemock

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

class Sonarusagemock ( name: String, scope: CoroutineScope, isconfined: Boolean=false  ) : ActorBasicFsm( name, scope, confined=isconfined ){

	override fun getInitialState() : String{
		return "s0"
	}
	override fun getBody() : (ActorBasicFsm.() -> Unit){
		//val interruptedStateTransitions = mutableListOf<Transition>()
		return { //this:ActionBasciFsm
				state("s0") { //this:State
					action { //it:State
						CommUtils.outblue("$name |    START")
						forward("sonarstart", "sonarstart(1)" ,"sonar24" ) 
						delay(10000) 
						CommUtils.outblue("$name |    STOP")
						forward("sonarstop", "sonarstop(1)" ,"sonar24" ) 
						delay(2000) 
						CommUtils.outblue("$name |   RESTART")
						forward("sonarstart", "sonarstart(1)" ,"sonar24" ) 
						delay(10000) 
						CommUtils.outblue("$name |    RESTOP")
						forward("sonarstop", "sonarstop(1)" ,"sonar24" ) 
						delay(2000) 
						CommUtils.outblue("$name | BYE")
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
