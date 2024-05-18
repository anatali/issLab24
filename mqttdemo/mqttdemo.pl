%====================================================================================
% mqttdemo description   
%====================================================================================
event( sonardata, sonardata(D) ).
%====================================================================================
context(ctxmmqtt, "localhost",  "TCP", "8920").
 qactor( publisher, ctxmmqtt, "it.unibo.publisher.Publisher").
 static(publisher).
  qactor( receiver, ctxmmqtt, "it.unibo.receiver.Receiver").
 static(receiver).
