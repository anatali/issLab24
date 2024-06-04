package mqtt

import it.unibo.kactor.ActorBasic
import kotlinx.coroutines.*
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken
import org.eclipse.paho.client.mqttv3.MqttCallback
import org.eclipse.paho.client.mqttv3.MqttClient
import org.eclipse.paho.client.mqttv3.MqttConnectOptions
import org.eclipse.paho.client.mqttv3.MqttMessage
import unibo.basicomm23.interfaces.IApplMessage
import unibo.basicomm23.utils.CommUtils


class CellEventsReceiver(name: String, scope: CoroutineScope, isconfined: Boolean=false) : ActorBasic(name,scope, confined=isconfined) {
    val brokerip = "tcp://mqtt.eclipseprojects.io"
    val celltopic = "ctxconwayactors"
    val clientId = "sonarReceiver"
    private lateinit var client: MqttClient
 
    init {
        runBlocking { autoMsg("recstart", "do") } // autostart
    }
 
    override suspend fun actorBody(msg: IApplMessage) {
        if (msg.msgId() == "recstart") {
            CommUtils.outgreen("$name | START  ")
            delay( 2000 )
            startMqttReceiver()
        }
    }

    private fun startMqttReceiver() {
        try {

        
            client = MqttClient(brokerip, clientId)
            val opt = MqttConnectOptions()
            opt.connectionTimeout = 5

            client.connect(opt)
            client.subscribe(celltopic)
            
            CommUtils.outgreen("$name | subscribed: $celltopic ")
            
            client.setCallback(object : MqttCallback {
                override fun connectionLost(cause: Throwable) {
                    //connectionStatus = false
                    // Give your callback on failure here
                     CommUtils.outred("$name | connectionLost $cause")
                }

                override fun messageArrived(topic: String, message: MqttMessage) {
                    try {
                        val data = String(message.payload, charset("UTF-8"))
                        val event = CommUtils.buildEvent(name, "sonardata", data)
                        //System.out.println("creo:"+data)
                        CommUtils.outgreen("$name | arrived: $topic, $message")
                         /*
                        GlobalScope.launch {
                            emitLocalStreamEvent(event)
                        }*/
                    } catch (e: Exception) {
                        // Give your callback on error here
                    }
                }

                override fun deliveryComplete(token: IMqttDeliveryToken) {
                    // Acknowledgement on delivery complete
                }
            })
        } catch (e: Exception) {
            CommUtils.outred("sonarMQTTReceiver | Failed to connect to MQTT client")
        }
    }
}


