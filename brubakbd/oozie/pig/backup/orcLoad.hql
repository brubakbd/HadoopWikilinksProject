USE ${hiveconf:databaseName};

CREATE TABLE ${hiveconf:orcName}(name string, hit double, err double)
PARTITIONED BY (year int, month int, day int, hour int)
ROW FORMAT DELIMITED FIELDS TERMINATED BY '\t'
STORED AS orc;

Set hive.exec.dynamic.partition.mode=nonstrict;
insert into table ${hiveconf:orcName} partition(year, month, day, hour) select * from ${hiveconf:tableName};
