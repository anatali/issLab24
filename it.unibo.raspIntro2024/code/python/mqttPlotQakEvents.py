""" 
----------------------------------------------------
mqttPlotQakEvents
----------------------------------------------------
Raccoglie dati dal sonar via MQTT per duration secs
e poi li visualizza su un grafico

"""
import time
import paho.mqtt.client as paho
import matplotlib.pyplot as plt
#import matplotlib
#matplotlib.use('Agg')  #evita l'uso della grafica

brokerAddr="mqtt.eclipseprojects.io"  #"mqtt.eclipseprojects.io"  #"broker.hivemq.com" 
duration = 10
x        = []
d        = []
n        = 0
goon     = True
msgnum   = 0 
##############################################################
###  msg(sonarRobot,event,sonar,none,sonar(V),N)
##############################################################
def diagram() :
    global d
    ##plt.ylabel('sonar data')
    fig, ax = plt.subplots()
    ax.set_title('sonar distance alarm')
    ax.set_ylabel('sonar data')
    ax.axhline(5, color ='green', lw = 2) 
    ax.plot(list(d), 'ro') #, color='red'
    plt.show()

    
def on_message(client, userdata, message) :   #define callback
    global msgnum,  x,y,z, goon
    evMsg   = str( message.payload.decode("utf-8")  )
    print("RECEIVED ", evMsg)
    msgitems = evMsg.split(",")
    msgnum   = msgnum + 1
    vd       = float( msgitems[4].split('(')[1].split(')')[0] )
    print("evMsg=", evMsg, "vd=", vd   ) 
    d.append( vd )
    
client= paho.Client("receiver")      
client.on_message=on_message            # Bind function to callback

client.connect(brokerAddr)              #connect
print("connected to broker ", brokerAddr)
print("subscribing to unibo/sonar/events")
client.subscribe("unibo/sonar/events")      #subscribe

print("collecting values; please wait ..." )
client.loop_start()             #start loop to process received messages
time.sleep(duration)
client.disconnect()             #disconnect
print("bye")
client.loop_stop()              #stop loop    
diagram()
