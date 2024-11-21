%====================================================================================
% mqttdemo description   
%====================================================================================
mqttBroker("localhost", "1883", "applsevents").
event( sonardata, sonardata(D) ).
dispatch( cmd, cmd(MOVE) ).
request( engage, engage(OWNER,STEPTIME) ).
reply( engagedone, engagedone(ARG) ).  %%for engage
reply( engagermockefused, engagermockefused(ARG) ).  %%for engage
%====================================================================================
context(ctxmmqtt, "localhost",  "TCP", "8920").
context(ctxbasicrobot, "127.0.0.1",  "TCP", "8020").
 qactor( basicrobot, ctxbasicrobot, "external").
  qactor( engagermock, ctxmmqtt, "it.unibo.engagermock.Engagermock").
 static(engagermock).
