CREATE DATABASE IF NOT EXISTS ${databaseName};
USE ${databaseName};
CREATE TABLE IF NOT EXISTS ${tableName}(name string, hit double, err double, year int, month int, day int, hour int)
ROW FORMAT DELIMITED FIELDS TERMINATED BY '\t'
STORED AS TEXTFILE;

LOAD DATA INPATH '${pigOutputDir}/${year}-${month}-${day}' OVERWRITE into table ${tableName};
