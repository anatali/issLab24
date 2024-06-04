package mqtt

import it.unibo.kactor.ActorBasic
import org.eclipse.paho.client.mqttv3.MqttClient
import org.eclipse.paho.client.mqttv3.MqttConnectOptions
import org.eclipse.paho.client.mqttv3.MqttMessage
import unibo.basicomm23.interfaces.IApplMessage
import unibo.basicomm23.utils.CommUtils


class ledMQTTSender(name: String) : ActorBasic(name) {
    val brokerip = "tcp://mqtt.eclipseprojects.io"
    val ledtopic = "unibo/led/events"
    val clientId = "ledSender"
    private lateinit var client: MqttClient

    init {
        try {
            client = MqttClient(brokerip, clientId)
            val opt = MqttConnectOptions()
            opt.connectionTimeout = 5

            client.connect(opt)
        } catch (e: Exception) {
            CommUtils.outred("ledMQTTSender | Failed to connect to MQTT client")
        }
    }

    override suspend fun actorBody(msg: IApplMessage) {
        //System.out.println(msg)
        if (msg.msgSender() != "warningdevice") return
        if (msg.msgSender() == name) return //AVOID to handle the event emitted by itself
        elabData(msg)
    }

    private suspend fun elabData(msg: IApplMessage) { //OPTIMISTIC

        if (client.isConnected) {
            System.out.println("$name | mando: " + msg.msgContent())
            var status = -1
            if (msg.msgContent().contains("ledoff")) status = 0
            else if (msg.msgContent().contains("ledon")) status = 1
            else if (msg.msgContent().contains("ledblink")) status = 2

            val mqttMessage = MqttMessage(status.toString().toByteArray())
            client.publish(ledtopic, mqttMessage)
        }
    }
}