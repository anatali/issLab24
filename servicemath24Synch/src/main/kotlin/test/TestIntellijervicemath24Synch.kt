package test;

import alice.tuprolog.Struct
import alice.tuprolog.Term
import it.unibo.ctxservice.main
import it.unibo.kactor.MsgUtil.buildRequest
import org.junit.AfterClass
import org.junit.Assert
import org.junit.BeforeClass
import org.junit.Test
import unibo.basicomm23.interfaces.IApplMessage
import unibo.basicomm23.interfaces.Interaction
import unibo.basicomm23.msg.ApplMessage
import unibo.basicomm23.tcp.TcpConnection
import unibo.basicomm23.utils.CommUtils

//package test;

//Per impostazione predefinita, i nuovi file di Kotlin vengono salvati in src/main/java/

class TestIntellijervicemath24Synch {
    private var conn: Interaction? = null

    var req35 = buildRequest("tester", "dofibo", "dofibo(35)", "servicemath")
    var req6 = buildRequest("tester", "dofibo", "dofibo(6)", "servicemath")

    @BeforeClass
    fun initSystemTotest() {
        CommUtils.outmagenta("initSystemTotest")
        startTheService()
    }

    private fun startTheService() {
        object : Thread() {
            override fun run() {
                try {
                    CommUtils.outblue("memory:" + Runtime.getRuntime().totalMemory())
                    CommUtils.outblue("processors:" + Runtime.getRuntime().availableProcessors())
                    main()
                } catch (e: Exception) {
                    Assert.fail("startTheService " + e.message)
                }
            }
        }.start()
        try {
            conn = TcpConnection.create("localhost", 8031)
        } catch (e: Exception) {
            Assert.fail("connection " + e.message)
        }
    }
    //Utility
    private fun fromAnswerToValue(msganswer: String): Int {
        //msganswer:fibodone( CALLER,N,V,TIME )
        val answreq: IApplMessage = ApplMessage(msganswer)
        val tanswer = Term.createTerm(answreq.msgContent()) as Struct
        val n: Int = Integer.parseInt( tanswer.getArg(1).toString() )
        val res: Int = Integer.parseInt(  tanswer.getArg(2).toString()  )
        CommUtils.outgreen("fibo for $n=$res")
        return res
    }

    @Test
    fun test35() {
        CommUtils.outgreen("test35 ---------------------- ")
        try {
            val answer35 = conn!!.request(req35.toString())
            val res35 = fromAnswerToValue(answer35)
            Assert.assertEquals(res35.toLong(), 9227465)
        } catch (e: Exception) {
            Assert.fail("test35 " + e.message)
        }
    }

    @Test
    fun test6() {
        CommUtils.outgreen("test6 ---------------------- ")
        try {
            val answer6 = conn!!.request(req6.toString())
            val res6 = fromAnswerToValue(answer6)
            Assert.assertEquals(res6.toLong(), 8)
        } catch (e: Exception) {
            Assert.fail("test6 " + e.message)
        }
    }

    companion object {
        private var conn: Interaction? = null

        //See https://www.baeldung.com/junit-before-beforeclass-beforeeach-beforeall
        @BeforeClass
        fun initSystemTotest() {
            CommUtils.outmagenta("initSystemTotest")
            startTheService()
        }

        private fun startTheService() {
            object : Thread() {
                override fun run() {
                    try {
                        CommUtils.outblue("memory:" + Runtime.getRuntime().totalMemory())
                        CommUtils.outblue("processors:" + Runtime.getRuntime().availableProcessors())
                        main()
                    } catch (e: Exception) {
                        Assert.fail("startTheService " + e.message)
                    }
                }
            }.start()
            try {
                conn = TcpConnection.create("localhost", 8031)
            } catch (e: Exception) {
                Assert.fail("connection " + e.message)
            }
        }

        @AfterClass
        fun endOfTest() {
            CommUtils.outmagenta("endOfTest")
        } /*
	public static void main(String[] args) {

		new TesttttttServicemath24Synch().initSystemTotest();
	} */
    }
}
