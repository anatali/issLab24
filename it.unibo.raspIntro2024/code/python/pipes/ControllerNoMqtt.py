# File: ControllerNoMqtt.py
import time
import sys

n         = 1
msg       = "msg(sonardata,event,sonar,none,distance(D),N)"
    

## print ('Mqtt client connected ')
##time.sleep(2)

for line in sys.stdin:
	## print("ControllerMqtt RECEIVES:", line)
	try:
		vf = float(line)
		v  = int( vf )
		if v <= 10 :
			n = n + 1
			#print ( 'on', flush=True )
			print ( 'on' )
			sys.stdout.flush()
		else:	
			#print ( 'off', flush=True )
			print ( 'off' )
			sys.stdout.flush()
	except:
		print("ControllerNoMqtt | An exception occurred")


### USAGE
### python sonar.py | python ControllerMqtt.py | python LedDevice.py


