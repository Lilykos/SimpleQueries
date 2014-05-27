# Simple Queries

This is a really simple library that tries to make it easier to write SQL queries (specifically MySQL) in Java.
The syntax is partially inspired by [JOOQ](http://jooq.org/), although it does NOT do all the nifty things that JOOQ does. It simply helps the developer to create queries in a Java "method style", which makes it easier to keep track of what's going on and avoid the usual mistakes of String concat, keeping track of brackets and semicolons etc...


It is a work in progress and although many features are implemented, it is not yet quite ready for very complicated queries.


## How it works:

Basically the library provides a __QueryFactory__ instance which creates different query types, each one containing the right methods for the specific query. Let's see some examples:

### Some things to consider:

* The library does not make any kind of error checking, be it null, or putting the different parts of the query in the right order to be correct. It simply uses the chained methods, in the order that they are called, to create the query String. So the order has to be the right one.

* Currently it supports only MySQL. It can be extended to provide assistance for other dialects like PostgreSQL etc, but right now I want to focus on implementing more capabilities here.


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

    SELECT artist, age, album FROM artists
    WHERE artist IN ('John', 'George', 'Paul', 'Ringo')
    AND (SELECT album FROM british_albums WHERE sales > '1000000') LIKE '%Road'
    ORDER BY age;

which can be written as:

    String query = QueryFactory.newSelectQuery(SelectType.EXTERNAL)
            .select("artist", "age", "album")
            .from("artists")
            .where("artist").in("John", "George", "Paul", "Ringo")
            .and(QueryFactory.newSelectQuery(SelectType.NESTED)
                    .select("album")
                    .from("british_albums")
                    .where("sales").gt(1000000)
                    .buildSQLString()).like("%Road")
            .orderBy("age")
            .buildSQLString();
            
(The query may not make much sense, but it examplifies the possibilities here :) )

COUNT(), SUM() BETWEEN, OR and others are also implemented, and you can take a look at the JavaDoc for the specifics.


### CREATE

    CREATE TABLE people(
    id INT AUTO_INCREMENT NOT NULL,
    job VARCHAR(100) NOT NULL,
    PRIMARY KEY (id) );

can be written as:

    String query = QueryFactory.newCreateQuery()
            .createTable("people")
            .addColumn("id").ofDataType(DataType.INT).autoIncrement().notNull()
            .addColumn("job").ofDataType(DataType.VARCHAR(100)).notNull()
            .primaryKey("id")
            .buildSQLString();

Take into consideration the data type argument. For the data types that need no specified size (INT, DATE, etc) we use standard Objects.
For VARCHAR (and others to come, like BIGINT) we use a method "masked" as an object which helps us provide the size.

### INSERT

    INSERT INTO people( name, age, job )
    VALUES( 'Ilias', 23, 'Developer' ), ('John', 20, 'Student');

can be written as:

    String query = QueryFactory.newInsertQuery()
            .insertInto("people")
            .onColumns("name", "age", "job")
            .values("Ilias", 23, "Developer")
            .values("John", 20, "Student")
            .buildSQLString();


### DELETE

This works in a similar way to the SELECT clause, and a number of methods is the same.

    DELETE FROM people
    WHERE names IN ('Jack', 'Liz', 'Keneth')
    AND age <= '40';

can be written as:

    String query = QueryFactory.newDeleteQuery()
            .deleteFrom("people")
            .where("names").in("Jack", "Liz", "Keneth")
            .and("age").ltOrEq(40)
            .buildSQLString();

also:

    DELETE FROM cars
    WHERE production BETWEEN 2000 AND 2010
    OR origin = 'USA';

can be:

    String query = QueryFactory.newDeleteQuery()
            .deleteFrom("cars")
            .where("production").between(2000, 2010)
            .or("origin").eq("USA")
            .buildSQLString();


### UPDATE

Example:

    UPDATE people
    SET age = '20', name = 'John'
    WHERE name = 'Unknown';

can be:

    String query = QueryFactory.newUpdateQuery()
            .update("people")
            .set("age", 20)
            .set("name", "John")
            .where("name").eq("Unknown")
            .buildSQLString();