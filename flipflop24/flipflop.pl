%====================================================================================
% flipflop description   
%====================================================================================
dispatch( s, s(X) ).
dispatch( a, a(X) ).
%====================================================================================
context(ctxflipflop, "localhost",  "TCP", "8765").
 qactor( nor, ctxflipflop, "it.unibo.nor.Nor").
 static(nor).
  qactor( norusage, ctxflipflop, "it.unibo.norusage.Norusage").
 static(norusage).
