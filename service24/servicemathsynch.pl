%====================================================================================
% servicemathsynch description   
%====================================================================================
mqttBroker("broker.hivemq.com", "1883", "servicemathsynch/events").
request( dofibo, dofibo(N) ).
reply( fibodone, fibodone(CALLER,N,R) ).  %%for dofibo
dispatch( show, show(S) ).
dispatch( out, out(S) ).
event( alarm, alarm(X) ).
%====================================================================================
context(ctxservice, "localhost",  "TCP", "8011").
 qactor( display, ctxservice, "it.unibo.display.Display").
 static(display).
  qactor( caller1, ctxservice, "it.unibo.caller1.Caller1").
 static(caller1).
  qactor( servicemath, ctxservice, "it.unibo.servicemath.Servicemath").
 static(servicemath).