%====================================================================================
% ping24events description   
%====================================================================================
event( ball, ball(N) ). %info exchanged by the players
%====================================================================================
context(ctxpingev, "localhost",  "TCP", "8014").
context(ctxpongev, "127.0.0.1",  "TCP", "8015").
 qactor( pongev, ctxpongev, "external").
  qactor( pingev, ctxpingev, "it.unibo.pingev.Pingev").
 static(pingev).
