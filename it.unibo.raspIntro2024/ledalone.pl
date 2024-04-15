%====================================================================================
% ledalone description   
%====================================================================================
dispatch( turnOn, turnOn(X) ).
dispatch( turnOff, turnOff(X) ).
event( ledchanged, ledchanged(V) ).
%====================================================================================
context(ctxledalone, "localhost",  "TCP", "8080").
 qactor( led, ctxledalone, "it.unibo.led.Led").
 static(led).
msglogging.
