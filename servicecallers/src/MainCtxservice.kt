
import it.unibo.kactor.QakContext
import kotlinx.coroutines.runBlocking
import unibo.basicomm23.utils.CommUtils

fun main() = runBlocking {
    CommUtils.outblue("MainCtxservice.kt CodedQak STARTS")
    QakContext.createContexts(
        "localhost", this,
        "servicemathsynchbasealone.pl", "sysRules.pl",
        "ctxservice"
    )
}
