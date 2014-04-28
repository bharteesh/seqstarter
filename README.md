seqstarter
==========
This app demonstrates the use of following features in Seq:
1) Service Locator
2) Structured Logging (KAFKA)
3) Circuit Breaker
4) Persistent Variable

Access other apps/services: http://seqstarterapp.apps.test.cirrostratus.org/

Verified that logs are getting uploaded to S3 kafka logs bucket in JSON format

Circuit Breaker Test: http://seqstarterapp.apps.test.cirrostratus.org/v1/cb 
(invoke this URL 3 times which returns COULD_NOT_CONNECT and 4th time it returns CIRCUIT_BREAKER_TRIPPED

Persistent Variable issues: 
Received empty config value error when creating new persistent variable.

2014-04-28 04:21:00,700 [localhost-startStop-1] WARN  watchable.WatchableRegistry - {"dests":["SYSTEM"],"origin":"WatchableRegistry.addIfAbsent","host":"seqstarterapp02","level":"WARN","date":"2014-04-28 04:21:00","ver":"1.1","msg":{"itemName":"test.prop1","jsonFromConfig":"","watchableType":"persistentvariable","serviceName":"seqstarterapp"}}
2014-04-28 04:21:00,702 [localhost-startStop-1] INFO  watchable.WatchableRegistry - {"dests":["SYSTEM"],"origin":"WatchableRegistry.addIfAbsent","host":"seqstarterapp02","level":"INFO","date":"2014-04-28 04:21:00","ver":"1.1","msg":"Received empty config value"}
2014-04-28 04:21:00,738 [localhost-startStop-1] WARN  watchable.WatchableRegistry - {"dests":["SYSTEM"],"origin":"WatchableRegistry.addIfAbsent","host":"seqstarterapp02","level":"WARN","date":"2014-04-28 	04:21:00","ver":"1.1","msg":{"itemName":"test.prop2","jsonFromConfig":"","watchableType":"persistentvariable","serviceName":"seqstarterapp"}}
2014-04-28 04:21:00,738 [localhost-startStop-1] INFO  watchable.WatchableRegistry - {"dests":["SYSTEM"],"origin":"WatchableRegistry.addIfAbsent","host":"seqstarterapp02","level":"INFO","date":"2014-04-28 04:21:00","ver":"1.1","msg":"Received empty config value"}

