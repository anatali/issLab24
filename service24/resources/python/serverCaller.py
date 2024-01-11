##############################################################
# serverCaller.py
##############################################################
import socket
import time

port = 8011
sock = socket.socket(socket.AF_INET, socket.SOCK_STREAM)

msg1  = "msg(dofibo,request,python,servicemath,dofibo(40),1)"
msg2  = "msg(dofibo,request,python,servicemath,dofibo(6),1)"
 

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

 

def workrequest() :
    request( msg1 ) 
    time.sleep(1)
    request( msg2 )
    handleAnswer()
    handleAnswer()
    
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
            ## print("answer len=", len(answer))
            if len(answer) <= 0 :
                break
            reply += answer.decode("utf-8")
            ## print("reply=", reply)
            if reply.endswith("\n") :
                break
        print("final reply=", reply)
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
workrequest()
##terminate()  
