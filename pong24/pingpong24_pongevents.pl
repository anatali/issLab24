%====================================================================================
% pingpong24_pongevents description   
%====================================================================================
event( ball, ball(N) ). %info exchanged by the players
%====================================================================================
context(ctxping, "127.0.0.1",  "TCP", "8014").
context(ctxpong, "localhost",  "TCP", "8015").
 qactor( ping, ctxping, "external").
  qactor( pong, ctxpong, "it.unibo.pong.Pong").
 static(pong).
