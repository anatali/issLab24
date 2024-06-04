%====================================================================================
% gamelifeactors description   
%====================================================================================
dispatch( guicmd, guicmd(X) ).
dispatch( fromdisplay, fromdisplay(CELL) ).
dispatch( todisplay, todisplay(CELL,STATE) ).
event( startthegame, startthegame(X) ).
event( stopthegame, stopthegame(X) ).
%====================================================================================
context(ctxconwayactors, "localhost",  "TCP", "8360").
 qactor( griddisplay, ctxconwayactors, "it.unibo.griddisplay.Griddisplay").
 static(griddisplay).
  qactor( gamemock, ctxconwayactors, "it.unibo.gamemock.Gamemock").
 static(gamemock).
