# File: sonarLed.py
'''
==============================
sonarAndLed.py
==============================
'''

'''
Sistema organizzato in funzioni  Python
Ciascuna funzione ha una precisa responsbilità
La funzione doJob() funge da coordinatore
La funzione applLogic definisce la logica applicativa
'''



import RPi.GPIO as GPIO
import time
import sys
import paho.mqtt.client as paho

'''
----------------------------------
CONFIGURATION FOR LED
----------------------------------
'''
GPIO.setmode(GPIO.BCM)
GPIO.setup(25,GPIO.OUT)

'''
----------------------------------
CONFIGURATION FOR SONAR
----------------------------------
'''
GPIO.setmode(GPIO.BCM)
GPIO.setwarnings(False)
TRIG = 17
ECHO = 27

GPIO.setup(TRIG,GPIO.OUT)
GPIO.setup(ECHO,GPIO.IN)

'''
----------------------------------
MQTT
----------------------------------
'''
brokerAddr="mqtt.eclipseprojects.io"  #"broker.hivemq.com"   ##"mqtt.eclipseprojects.io"
msg       = "msg(sonardata,event,sonar,none,distance(D),N)"
n         = 1
client    = paho.Client(paho.CallbackAPIVersion.VERSION1,"sonarAndLed")    
 

def init():
    GPIO.output(TRIG, False)   #TRIG parte LOW
    ## See https://stackoverflow.com/questions/77984857/paho-mqtt-unsupported-callback-api-version-error
    client.connect(brokerAddr, 1883, 60) 
    print ('Waiting a few seconds for the sensor to settle')
    time.sleep(2)

def ledOn():
   GPIO.output(25,GPIO.HIGH)
   ##forward()  #QUI???

def ledOff():
   GPIO.output(25,GPIO.LOW)

def sonarWork():
   GPIO.output(TRIG, True)    #invia impulsoTRIG
   time.sleep(0.00001)
   GPIO.output(TRIG, False)

   #attendi che ECHO parta e memorizza tempo
   while GPIO.input(ECHO)==0:
      pulse_start = time.time()

   # register the last timestamp at which the receiver detects the signal.
   while GPIO.input(ECHO)==1:
      pulse_end = time.time()

   pulse_duration = pulse_end - pulse_start
   dist = pulse_duration * 17165   #distance = vt/2
   return dist

def forward(distance):
    global n
    n = n + 1
    client.publish("unibo/sonar/events", msg.replace("D",str(distance)).replace("N", str(n)))  

def applLogic(distance):
        if( distance  > 0.0 and distance < 5.0 ):  #ANALIZZO
            ledOn()
            forward(distance)
        else:
            ledOff()
    

def doJob():   
    init()
    while True:
        d = sonarWork()               #acquisico la distanza correntr
        if( d  > 0.0 and d < 150.0 ): # FILTRO
            #distance = d
            print ( d  )
        applLogic(d)           # LOGICA APPLICATIVA
        sys.stdout.flush()
        time.sleep(0.25)


if __name__ == '__main__':
    print ('sonarAndLed is starting ... ')
    try:
        doJob()
    except KeyboardInterrupt:  # When 'Ctrl+C' is pressed, the child program destroy() will be  executed.
        print ('sonarAndLed BYE ... ')
