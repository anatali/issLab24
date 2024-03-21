%====================================================================================
% qakdemo24 description   
%====================================================================================
event( data, value(V) ). %emesso da datasource 
%====================================================================================
context(ctxstreamsdemo, "localhost",  "TCP", "8045").
 qactor( qasink, ctxstreamsdemo, "it.unibo.qasink.Qasink").
 static(qasink).
  qactor( datasource, ctxstreamsdemo, "it.unibo.datasource.Datasource").
 static(datasource).
  qactor( filter, ctxstreamsdemo, "it.unibo.filter.Filter").
 static(filter).
