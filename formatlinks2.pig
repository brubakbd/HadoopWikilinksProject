REGISTER 'hdfs:///tmp/input/FormatLinks.jar';
DEFINE Format edu.rosehulman.wikilinks.Format();
records = LOAD '$input' using PigStorage(':') AS (first:long, rest:chararray);
filtered = FILTER records BY (rest IS NOT NULL) AND NOT(TRIM(rest) MATCHES '');
generated = FOREACH filtered GENERATE FLATTEN(Format(*));
STORE generated into '$output' using PigStorage('\t');

