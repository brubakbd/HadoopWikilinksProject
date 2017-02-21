CREATE DATABASE IF NOT EXISTS ${hiveconf:databaseName};
USE ${hiveconf:databaseName};

CREATE TABLE IF NOT EXISTS ${hiveconf:tempTableName}(name string, cno string, cname string, grade string)
PARTITIONED BY (username string)
ROW FORMAT DELIMITED FIELDS TERMINATED BY '\t'
STORED AS TEXTFILE;

CREATE TABLE IF NOT EXISTS ${hiveconf:tableName}(name string, cno string, cname string, grade string)
PARTITIONED BY (username string)
ROW FORMAT DELIMITED FIELDS TERMINATED BY '\t'
STORED AS orc;
