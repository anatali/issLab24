%====================================================================================
% ledusage description   
%====================================================================================
dispatch( turnOn, turnOn(X) ).
dispatch( turnOff, turnOff(X) ).
event( ledchanged, ledchanged(V) ).
%====================================================================================
context(ctxledusage, "localhost",  "TCP", "8080").
context(ctxledalone, "192.168.1.40",  "TCP", "8080").
 qactor( led, ctxledalone, "external").
  qactor( ledusage, ctxledusage, "it.unibo.ledusage.Ledusage").
 static(ledusage).
