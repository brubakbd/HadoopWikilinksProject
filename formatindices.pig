indices = LOAD '$input' AS (name:chararray);
ranked = RANK indices;
STORE ranked into '$output' using PigStorage('\t');
