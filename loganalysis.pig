REGISTER 'hdfs:///tmp/shared/lib/LogAnalysis-0.0.1-SNAPSHOT.jar';
DEFINE BlogFilter edu.rosehulman.suvarna.BlogFilter();
DEFINE BlogGroup edu.rosehulman.suvarna.BlogGroup();
DEFINE Ratio edu.rosehulman.suvarna.Ratio();
records = LOAD '$input' using PigStorage('\t') AS (date:chararray, time:chararray, xedgelocation:chararray, scbytes:chararray, cip:chararray, csmethod:chararray, csHost:chararray, csuristem:chararray, scstatus:chararray, csReferer:chararray, csUserAgent:chararray, csuriquery:chararray, csCookie:chararray, xedgeresulttype:chararray, xedgerequestid:chararray);
filtered = FILTER records BY BlogFilter(csuristem);
groupedbyblog = GROUP filtered BY BlogGroup(csuristem);
blogcount = FOREACH groupedbyblog GENERATE group AS blog, COUNT(filtered) AS total;
hitfiltered = FILTER filtered BY (xedgeresulttype MATCHES '.*Hit.*');
groupedhitblog = GROUP hitfiltered BY BlogGroup(csuristem);
hitcount = FOREACH groupedhitblog GENERATE group AS blog, COUNT(hitfiltered) AS hits;
losefiltered = FILTER filtered BY (xedgeresulttype MATCHES '.*Error.*');
groupedloseblog = GROUP losefiltered BY BlogGroup(csuristem);
losecount = FOREACH groupedloseblog GENERATE group AS blog, COUNT(losefiltered) AS loses;
joined1 = JOIN hitcount BY blog LEFT OUTER, blogcount BY blog;
joined = JOIN joined1 BY hitcount::blog LEFT OUTER, losecount BY blog; 
out = FOREACH joined GENERATE blogcount::blog, Ratio(blogcount::total, hitcount::hits), (losecount::loses IS NOT NULL ? Ratio(blogcount::total, losecount::loses) : NULL), GetYear(CurrentTime()), GetMonth(CurrentTime()), GetDay(CurrentTime()), GetHour(CurrentTime());
STORE out into '$output/$year-$month-$day' USING PigStorage('\t');





