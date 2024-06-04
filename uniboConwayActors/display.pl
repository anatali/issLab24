%====================================================================================
% display description   
%====================================================================================
dispatch( guicmd, guicmd(X) ).
dispatch( fromdisplay, fromdisplay(CELL) ).
dispatch( todisplay, todisplay(CELL,STATE) ).
event( startthegame, startthegame(X) ).
event( stopthegame, stopthegame(X) ).
%====================================================================================
context(ctxconwayactors, "localhost",  "TCP", "8360").
 qactor( gridisplay, ctxconwayactors, "it.unibo.gridisplay.Gridisplay").
 static(gridisplay).
  qactor( displaytester, ctxconwayactors, "it.unibo.displaytester.Displaytester").
 static(displaytester).
