%====================================================================================
% wolf description   
%====================================================================================
mqttBroker("broker.hivemq.com", "1883", "sonarbw24data").
event( wolf, wolf(D) ). %emesso da sonarmock
%====================================================================================
context(ctxwolf, "localhost",  "TCP", "8120").
 qactor( sonarwolfmock, ctxwolf, "it.unibo.sonarwolfmock.Sonarwolfmock").
 static(sonarwolfmock).
