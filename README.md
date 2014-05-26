# Simple Queries

This is a really simple library that tries to make it easier to write SQL queries (specifically MySQL) in Java.
The syntax is partially inspired by [JOOQ](http://jooq.org/), although it does NOT do all the nifty things that JOOQ does. It simply helps the developer to create queries in a Java "method style", which makes it easier to keep track of what's going on and avoid the usual mistakes of String concat, keeping track of brackets and semicolons etc...


It is a work in progress and although many features are implemented, it is not yet quite ready for very complicated queries.

## How it works:

Basically the library provides a __QueryFactory__ instance which creates different query types, each one containing the right methods for the specific query. Let's see some examples:

### SELECT

This simple query here:

    SELECT * FROM countries WHERE population >= 1000000;


can be written as:

    String query = QueryFactory.newSelectQuery(SelectType.EXTERNAL)
            .select("*")
            .from("countries")
            .where("population").gtOrEq(1000000)
            .buildSQLString();

The __SelectType.EXTERNAL__ is given as a parameter to identify external or nested query. That makes it very easy to translate queries like:

    SELECT artist, age FROM artists
    WHERE artist IN ('John', 'George', 'Paul', 'Ringo')
    AND (SELECT album FROM british_albums WHERE sales > '1000000') LIKE '%Road'
    ORDER BY age;

can be written as:

    String query = QueryFactory.newSelectQuery(SelectType.EXTERNAL)
            .select("artist", "age")
            .from("artists")
            .where("artist").in("John", "George", "Paul", "Ringo")
            .and(QueryFactory.newSelectQuery(SelectType.NESTED)
                    .select("album")
                    .from("british_albums")
                     .where("sales").gt(1000000)
                     .buildSQLString()).like("%Road")
            .orderBy("age")
            .buildSQLString();
