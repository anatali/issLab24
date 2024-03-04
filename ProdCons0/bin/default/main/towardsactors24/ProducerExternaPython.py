##############################################################
# ProducerExternalPython.py
# Si connette in modo 'naive' cone il Consumer 
# usando una socket TCP
##############################################################
import socket
import time

port = 8223
sock = socket.socket(socket.AF_INET, socket.SOCK_STREAM)

req   = "msg(distance,request, prodpython,consumer,33,1)"
req1  = "msg(distance,request, prodpython,consumer,43,1)"
msg   = "msg(distance,dispatch,prodpython,consumer,22,2)"
 

def connect(port) :
    server_address = ('localhost', port)
    sock.connect(server_address)    
    print("CONNECTED " , server_address)

def forward( message ) :
    print("forward ", message)
    msg = message + "\n"
    byt=msg.encode()    #required in Python3
    sock.send(byt)

def emit( event ) :
    print("emit ", event)
    msg = event + "\n"
    byt=msg.encode()    #required in Python3
    sock.send(byt)

def doJob() :
    request( req ) 
    forward( msg )
    request( req1 ) 
    forward( msg )
    handleAnswer()
    #time.sleep(1)
    handleAnswer()
    terminate()
    
def terminate() :
    sock.close()
    print("BYE")

def request( message ) :
    print("request ", message)
    msg = message + "\n"
    byt=msg.encode()    #required in Python3
    sock.send(byt)
    #handleAnswer()
    
def handleAnswer() :
    print("handleAnswer " )
    while True:  ##client wants to maintain the connection
        reply = ''
        while True:
            answer = sock.recv(50)
            #print("handleAnswer answer len=", len(answer))
            if len(answer) <= 0 :
                break
            reply += answer.decode("utf-8")
            #print("handleAnswer reply=", reply)
            if reply.endswith("\n") :
                break
        print("handleAnswer final reply=", reply)
        break
        
def receiveALine() :    
    print("receiveALine " )
    reply = ''
    while True:
        answer = sock.recv(50)
        print("answer len=", len(answer))
        if len(answer) <= 0 :
            break
        reply += answer.decode("utf-8")
        ## print("reply=", reply)
        if reply.endswith("\n") :
            break
    print("final reply=", reply)
    
    
def console() :  
    v =  str( input() )
    print("INPUT" , v  )    
    while( v != "q"  ) :
        request(  depositrequest.replace("K", v) )
        v = str(input() ) 
            
###########################################    
connect(port)
doJob()
##terminate()  
