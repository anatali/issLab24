%====================================================================================
% wolf description   
%====================================================================================
mqttBroker("broker.hivemq.com", "1883", "sonarwolf").
event( wolf, wolf(D) ). %emesso da sonarmock
%====================================================================================
context(ctxwolf, "localhost",  "TCP", "8120").
 qactor( sonarwolfmock, ctxwolf, "it.unibo.sonarwolfmock.Sonarwolfmock").
 static(sonarwolfmock).
