%====================================================================================
% vrobotusage description   
%====================================================================================
dispatch( cmd, cmd(MOVE) ). %MOVE = w|s|a|d   mosse del virtual robot
%====================================================================================
context(ctxvrobotusage, "localhost",  "TCP", "8120").
 qactor( vrclient, ctxvrobotusage, "it.unibo.vrclient.Vrclient").
 static(vrclient).
