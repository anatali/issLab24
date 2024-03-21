%====================================================================================
% qakdemo24 description   
%====================================================================================
request( cmd, cmd(X) ). %X =  w | s | a | d | h
reply( replytocmd, replytocmd(X) ).  %%for cmd
event( alarm, alarm(V) ).
%====================================================================================
context(ctxresourcecore, "localhost",  "TCP", "8045").
 qactor( resourcecore, ctxresourcecore, "it.unibo.resourcecore.Resourcecore").
 static(resourcecore).
