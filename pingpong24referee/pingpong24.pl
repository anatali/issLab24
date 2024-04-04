%====================================================================================
% pingpong24 description   
%====================================================================================
dispatch( ball, ball(N) ). %info scambiata e osservata
%====================================================================================
context(ctxreferee, "localhost",  "TCP", "8013").
context(ctxping, "127.0.0.1",  "TCP", "8014").
context(ctxpong, "127.0.0.1",  "TCP", "8015").
 qactor( ping, ctxping, "external").
  qactor( pong, ctxpong, "external").
  qactor( referee, ctxreferee, "it.unibo.referee.Referee").
 static(referee).
