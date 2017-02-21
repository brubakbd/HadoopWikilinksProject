USE ${hiveconf:databaseName};

LOAD DATA INPATH '${hiveconf:pigOutputDir}/part-r-00000' OVERWRITE into table ${hiveconf:tempTableName} Partition(username='brubakbd');

set hive.exec.dynamic.partition.mode=nonstrict;
INSERT INTO TABLE ${hiveconf:tableName} partition(username) select name, cno, cname, grade,username FROM ${hiveconf:tempTableName};
