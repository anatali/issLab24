/* Generated by AN DISI Unibo */ 

import it.unibo.kactor.QakContext
import kotlinx.coroutines.runBlocking
import unibo.basicomm23.utils.CommUtils

fun main() = runBlocking {
	CommUtils.outblue("MainCtxservice.kt STARTS")
	QakContext.createContexts(
		"localhost", this,
		"servicemathsynchbasealone.pl", "sysRules.pl",
		"ctxservice"
	)
}
