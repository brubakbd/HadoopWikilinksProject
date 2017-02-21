CREATE DATABASE IF NOT EXISTS ${hiveconf:databaseName};
USE ${hiveconf:databaseName};
CREATE TABLE ${hiveconf:tableName}(name string, hit double, err double, year int, month int, day int, hour int)
ROW FORMAT DELIMITED FIELDS TERMINATED BY '\t'
STORED AS TEXTFILE;

LOAD DATA INPATH '${hiveconf:pigOutputDir}/${hiveconf:year}-${hiveconf:month}-${hiveconf:day}' OVERWRITE into table archiveLogData;
