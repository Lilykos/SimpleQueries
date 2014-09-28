import com.simplequeries.QueryFactory;
import com.simplequeries.SQUtils.DataType;
import com.simplequeries.SQUtils.Order;
import com.simplequeries.SQUtils.SelectType;
import junit.framework.TestCase;
import org.junit.Test;

public class SimpleQueriesTests extends TestCase{

    private String query;

    @Test
    public void testSelect() {
        query = "SELECT * " +
                "FROM countries " +
                "GROUP BY population;";
        assertEquals(query, QueryFactory.newSelectQuery(SelectType.EXTERNAL)
                .select("*")
                .from("countries")
                .groupBy("population")
                .buildSQLString());


        query = "SELECT COUNT(*) FROM database;";
        assertEquals(query, QueryFactory.newSelectQuery(SelectType.EXTERNAL)
                .select().count("*")
                .from("database")
                .buildSQLString());


        query = "SELECT people AS ppl, age " +
                "FROM members " +
                "WHERE ppl NOT IN ('John', 'George', 'Paul', 'Ringo') " +
                "OR age >= '30' " +
                "ORDER BY ppl DESC;";
        assertEquals(query, QueryFactory.newSelectQuery(SelectType.EXTERNAL)
                .select("people AS ppl", "age")
                .from("members")
                .where("ppl").not().in("John", "George", "Paul", "Ringo")
                .or("age").gtOrEq(30)
                .orderBy("ppl", Order.DESC)
                .buildSQLString());


        query = "SELECT * " +
                "FROM countries " +
                "WHERE (SELECT name FROM countryNames WHERE name LIKE 'G%');";
        assertEquals(query, QueryFactory.newSelectQuery(SelectType.EXTERNAL)
                .select("*")
                .from("countries")
                .where(QueryFactory.newSelectQuery(SelectType.NESTED)
                        .select("name")
                        .from("countryNames")
                        .where("name").like("G%")
                        .buildSQLString())
                .buildSQLString());


        query = "SELECT artist, age FROM artists " +
                "WHERE artist IN ('John', 'George', 'Paul', 'Ringo') " +
                "AND (SELECT album FROM british_albums WHERE sales > '1000000') LIKE '%Road' " +
                "ORDER BY age ASC;";
        assertEquals(query, QueryFactory.newSelectQuery(SelectType.EXTERNAL)
                .select("artist", "age")
                .from("artists")
                .where("artist").in("John", "George", "Paul", "Ringo")
                .and(QueryFactory.newSelectQuery(SelectType.NESTED)
                        .select("album")
                        .from("british_albums")
                        .where("sales").gt(1000000)
                        .buildSQLString()).like("%Road")
                .orderBy("age", Order.ASC)
                .buildSQLString());


        query = "SELECT id, name " +
                "FROM catalogue " +
                "WHERE name IN (SELECT name " +
                    "FROM catalogue " +
                    "GROUP BY name " +
                    "HAVING COUNT(name) != '1');";
        assertEquals(query, QueryFactory.newSelectQuery(SelectType.EXTERNAL)
                .select("id", "name")
                .from("catalogue")
                .where("name").in(QueryFactory.newSelectQuery(SelectType.NESTED)
                        .select("name")
                        .from("catalogue")
                        .groupBy("name")
                        .having().count("name").notEq(1)
                        .buildSQLString())
                .buildSQLString());


        query = "SELECT name FROM names WHERE name IN ('John');";
        assertEquals(query, QueryFactory.newSelectQuery(SelectType.EXTERNAL)
                .select("name")
                .from("names")
                .where("name").in("John")
                .buildSQLString());
    }

    @Test
    public void testCreate() {
        query = "CREATE TABLE people( " +
                "id INT AUTO_INCREMENT NOT NULL, " +
                "job VARCHAR(100) NOT NULL, " +
                "PRIMARY KEY (id) );";
        assertEquals(query, QueryFactory.newCreateQuery()
                .createTable("people")
                .addColumn("id").ofDataType(DataType.INT).autoIncrement().notNull()
                .addColumn("job").ofDataType(DataType.VARCHAR(100)).notNull()
                .primaryKey("id")
                .buildSQLString());

        query = "CREATE TABLE IF NOT EXISTS countries( " +
                "country VARCHAR(30), " +
                "population INT NOT NULL );";
        assertEquals(query, QueryFactory.newCreateQuery()
                .createTableIfNotExists("countries")
                .addColumn("country").ofDataType(DataType.VARCHAR(30))
                .addColumn("population").ofDataType(DataType.INT).notNull()
                .buildSQLString());
    }

    @Test
    public void testInsert() {
        query = "INSERT INTO people( name, age, job ) " +
                "VALUES( 'Ilias', 23, 'Developer' );";
        assertEquals(query, QueryFactory.newInsertQuery()
                .insertInto("people")
                .onColumns("name", "age", "job")
                .values("Ilias", 23, "Developer")
                .buildSQLString());

        query = "INSERT INTO animals( name, continent ) " +
                "VALUES( 'lion', 'africa' ), ( 'bear', 'america' );";
        assertEquals(query, QueryFactory.newInsertQuery()
                .insertInto("animals")
                .onColumns("name", "continent")
                .values("lion", "africa")
                .values("bear", "america")
                .buildSQLString());
    }

    @Test
    public void testDelete() {
        query = "DELETE FROM people " +
                "WHERE names IN ('Jack', 'Liz', 'Keneth') " +
                "AND age <= '40';";
        assertEquals(query, QueryFactory.newDeleteQuery()
                .deleteFrom("people")
                .where("names").in("Jack", "Liz", "Keneth")
                .and("age").ltOrEq(40)
                .buildSQLString());

        query = "DELETE FROM cars " +
                "WHERE production BETWEEN 2000 AND 2010 " +
                "OR origin = 'USA';";
        assertEquals(query, QueryFactory.newDeleteQuery()
                .deleteFrom("cars")
                .where("production").between(2000, 2010)
                .or("origin").eq("USA")
                .buildSQLString());
    }

    @Test
    public void testUpdate() {
        query = "UPDATE people " +
                "SET age = '20', name = 'John' " +
                "WHERE name = 'Unknown';";
        assertEquals(query, QueryFactory.newUpdateQuery()
                .update("people")
                .set("age", 20)
                .set("name", "John")
                .where("name").eq("Unknown")
                .buildSQLString());

        query = "UPDATE cars " +
                "SET model = 'Audi', year = '2010' " +
                "WHERE countries_sold IN ('USA', 'Germany', 'Japan');";
        assertEquals(query, QueryFactory.newUpdateQuery()
                .update("cars")
                .set("model", "Audi")
                .set("year", 2010)
                .where("countries_sold").in("USA", "Germany", "Japan")
                .buildSQLString());
    }
}