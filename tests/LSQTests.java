import com.simplequeries.SQUtils.DataType;
import com.simplequeries.SQUtils.SelectType;
import com.simplequeries.QueryFactory;
import org.junit.Test;
import junit.framework.TestCase;

public class LSQTests extends TestCase{

    private String query;

    @Test
    public void testSelect() {
        {
            query = "SELECT * " +
                    "FROM countries " +
                    "GROUP BY population;";
            assertEquals(query, QueryFactory.newSelectQuery(SelectType.EXTERNAL)
                    .select("*")
                    .from("countries")
                    .groupBy("population")
                    .buildSQLString()
            );
        }

        {
            query = "SELECT people AS ppl, age " +
                    "FROM members " +
                    "WHERE ppl IN ('John', 'George', 'Paul', 'Ringo') " +
                    "OR age >= '30' " +
                    "ORDER BY ppl;";
            assertEquals(query, QueryFactory.newSelectQuery(SelectType.EXTERNAL)
                    .select("people AS ppl", "age")
                    .from("members")
                    .where("ppl").in("John", "George", "Paul", "Ringo")
                    .or("age").gtOrEq(30)
                    .orderBy("ppl")
                    .buildSQLString()
            );
        }

        {
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
                            .buildSQLString()
                    )
                    .buildSQLString()
            );
        }
    }

    @Test
    public void testCreate() {
        {
            query = "CREATE TABLE people( " +
                    "id INT AUTO_INCREMENT NOT NULL, " +
                    "job VARCHAR(100) NOT NULL, " +
                    "PRIMARY KEY (id) );";
            assertEquals(query, QueryFactory.newCreateQuery()
                    .createTable("people")
                    .addColumn("id").ofDataType(DataType.INT).autoIncrement().notNull()
                    .addColumn("job").ofDataType(DataType.VARCHAR(100)).notNull()
                    .primaryKey("id")
                    .buildSQLString()
            );
        }

        {
            query = "CREATE TABLE IF NOT EXISTS countries( " +
                    "country VARCHAR(30), " +
                    "population INT NOT NULL );";
            assertEquals(query, QueryFactory.newCreateQuery()
                    .createTableIfNotExists("countries")
                    .addColumn("country").ofDataType(DataType.VARCHAR(30))
                    .addColumn("population").ofDataType(DataType.INT).notNull()
                    .buildSQLString()
            );
        }
    }

    @Test
    public void testInsert() {
        {
            query = "INSERT INTO people( name, age, job) " +
                    "VALUES( 'Ilias', 23, 'Developer');";
            assertEquals(query, QueryFactory.newInsertQuery()
                    .insertInto("people")
                    .onColumns("name", "age", "job")
                    .values("Ilias", 23, "Developer")
                    .buildSQLString()
            );
        }

        {
            query = "INSERT INTO animals( name, continent) " +
                    "VALUES( 'lion', 'africa'), ( 'bear', 'america');";
            assertEquals(query, QueryFactory.newInsertQuery()
                    .insertInto("animals")
                    .onColumns("name", "continent")
                    .values("lion", "africa")
                    .values("bear", "america")
                    .buildSQLString()
            );
        }
    }

    @Test
    public void testDelete() {
        {
            query = "DELETE FROM people " +
                    "WHERE names IN ('Jack', 'Liz', 'Keneth') " +
                    "AND age <= '40';";
            assertEquals(query, QueryFactory.newDeleteQuery()
                    .deleteFrom("people")
                    .where("names").in("Jack", "Liz", "Keneth")
                    .and("age").ltOrEq(40)
                    .buildSQLString()
            );
        }
    }
}