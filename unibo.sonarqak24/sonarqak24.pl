%====================================================================================
% sonarqak24 description   
%====================================================================================
event( sonardata, distance(D) ).
dispatch( sonarwork, sonarwork(X) ).
dispatch( doread, doread(X) ).
%====================================================================================
context(ctxsonarqak24, "localhost",  "TCP", "8128").
 qactor( sonar24, ctxsonarqak24, "it.unibo.sonar24.Sonar24").
 static(sonar24).
  qactor( sonardevice, ctxsonarqak24, "it.unibo.sonardevice.Sonardevice").
 static(sonardevice).
