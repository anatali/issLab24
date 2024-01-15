##############################################################
# mqtt_emitter.py
# sendDispatch       : sends a command in output
# read               : acquires data from input

# conda install conda-forge::paho-mqtt (lungo)
# conda update -n base -c defaults conda
# conda install conda=23.11.0 mimize the packages

# see https://www.emqx.com/en/blog/how-to-use-mqtt-in-python
##############################################################
import time
import paho.mqtt.client as paho
 
brokerAddr     = "broker.hivemq.com"
topic          = "servicemathsynch/events"
 
alarmFire     = "msg(alarm,event,pythonan,none,alarm(firemqtt),1)"
alarmTsunami  = "msg(alarm,event,pythonan,none,alarm(tsunamimqtt),2)"

req1          = "msg(dofibo,request,pythonan,servicemath,dofibo(6),3)"

def emit( message ) :
    print("emit ", message)
    msg = message + "\n"
    #byt=msg.encode()    #required in Python3
    client.publish(topic, msg)

def sendMsg( message ) :
    print("sendMsg ", message)
    msg = message + "\n"
    #byt=msg.encode()    #required in Python3
    client.publish(topic, msg)
 
def work() :
    emit( alarmFire )
    time.sleep(1)
    sendMsg( req1 )
#    time.sleep(1)
#    emit( alarmTsunami )

## RICEZIONE RISPOSTA SU TOPIC unibo/qak/pythonan

### def on_message(client, userdata, message):
###    print("received message =",str(message.payload.decode("utf-8")))

def terminate() :
    client.disconnect()
    print("BYE")

def on_message(client, userdata, msg):
    print(f"Received  {msg.payload.decode()} from  {msg.topic}  topic")

#################################################
client= paho.Client("sender")      
client.connect(brokerAddr)              #connect
print("connected to broker ", brokerAddr)
work()
## subscribe(   )
print("SUBSCRIBEEEEEEEEE")
client.on_message = on_message
client.subscribe("unibo/qak/pythonan")


client.loop_forever()
#terminate()