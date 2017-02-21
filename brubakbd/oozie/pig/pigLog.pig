REGISTER 'hdfs:///tmp/shared/brubakbd/UDFs.jar'
DEFINE ratio edu.rosehulman.brubakbd.ratio();
DEFINE getNames edu.rosehulman.brubakbd.getName();
DEFINE getHit edu.rosehulman.brubakbd.getHit();
DEFINE getError edu.rosehulman.brubakbd.getError();
records = LOAD '$input' using PigStorage('\t') AS (date:chararray, time:chararray, xedgelocation:chararray, scbytes:chararray, cip:chararray, csmethod:chararray, csHost:chararray, csuristem:chararray, scstatus:chararray,csReferer:chararray, csUserAgent:chararray, csuriquery:chararray,csCookie:chararray, xedgeresulttype:chararray, xedgerequestid:chararray);
named = FOREACH records GENERATE getNames($7) AS name, getHit($13) AS hit, getError($13) AS error;
grouped = GROUP named BY $0;
results = FOREACH grouped GENERATE group, ratio(SUM($1.hit), COUNT($1.hit)), ratio(SUM($1.error), COUNT($1.error)), '$year', '$month', '$day', GetHour(CurrentTime());
STORE results into '$output/$year-$month-$day';

