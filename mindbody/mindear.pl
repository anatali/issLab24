%====================================================================================
% mindear description   
%====================================================================================
dispatch( sensed, sensed(S,X) ).
dispatch( sound, sound(X) ).
dispatch( work, work(X) ).
event( mindcmd, mindcmd(X) ).
event( earmemo, earmemo(X) ).
dispatch( out, out(S) ).
dispatch( show, show(S) ).
%====================================================================================
context(ctxmb, "localhost",  "TCP", "8045").
 qactor( mind, ctxmb, "it.unibo.mind.Mind").
 static(mind).
  qactor( ear, ctxmb, "it.unibo.ear.Ear").
 static(ear).
  qactor( worldmock, ctxmb, "it.unibo.worldmock.Worldmock").
 static(worldmock).
  qactor( display, ctxmb, "it.unibo.display.Display").
 static(display).
