%====================================================================================
% ppgreferee description   
%====================================================================================
dispatch( ballview, ball(N) ). %info  osservata | payload INDICATIVO
request( info, info(X) ).
reply( obsinfo, obsinfo(X) ).  %%for info
request( obsconnect, obsconnect(X) ).
reply( obsconnected, obsconnected(X) ).  %%for obsconnect
event( startgame, startgame(X) ).
event( stopgame, stopgame(X) ).
dispatch( endgame, endgame(X) ).
%====================================================================================
context(ctxreferee, "localhost",  "TCP", "8013").
context(ctxping, "127.0.0.1",  "TCP", "8014").
context(ctxpong, "127.0.0.1",  "TCP", "8015").
 qactor( ping, ctxping, "external").
  qactor( pong, ctxpong, "external").
  qactor( referee, ctxreferee, "it.unibo.referee.Referee").
 static(referee).
