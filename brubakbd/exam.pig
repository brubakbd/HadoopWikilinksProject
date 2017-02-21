REGISTER 'hdfs:///tmp/Exams/brubakbdUDF.jar'
DEFINE concatNames edu.rosehulman.brubakbd.concatNames();
DEFINE scoreToGrade edu.rosehulman.brubakbd.scoreToGrade();

grades = LOAD '$grades' USING PigStorage(',') AS (fName:chararray, lName:chararray, cNum:chararray, score:int);
filteredGrades = FILTER grades BY score <= 90;
courses = LOAD '$courses' USING PigStorage(',') AS (cNum:chararray, cName:chararray);
joined = FOREACH(JOIN filteredGrades BY cNum, courses BY cNum) GENERATE concatNames($0, $1), $4, $5, scoreToGrade($3);

STORE joined into '$pigOutput/$username'; 

