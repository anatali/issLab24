%====================================================================================
% servicecallers description   
%====================================================================================
request( dofibo, dofibo(N) ).
reply( fibodone, fibodone(CALLER,N,RESULT,TIME) ).  %%for dofibo
dispatch( out, out(S) ).
dispatch( show, show(S) ).
%====================================================================================
context(ctxcallers, "localhost",  "TCP", "8015").
context(ctxservice, "127.0.0.1",  "TCP", "8011").
 qactor( servicemath, ctxservice, "external").
  qactor( display, ctxcallers, "it.unibo.display.Display").
 static(display).
  qactor( caller_1, ctxcallers, "it.unibo.caller_1.Caller_1").
 static(caller_1).
