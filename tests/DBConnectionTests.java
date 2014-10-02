import com.simplequeries.QueryFactory;
import com.simplequeries.SQResults.SQResultSet;
import com.simplequeries.SQUtils.SelectType;
import junit.framework.TestCase;
import org.junit.Test;

import java.sql.SQLException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

/* These tests were implemented on a local db with this schema:
 * db name: albums
 * table name: the_classics
 * columns: name (varchar), artist (varchar), year (int)
 */

public class DBConnectionTests extends TestCase {

    @Test
    public void testResultSetGetColumn() throws SQLException, ClassNotFoundException {
        SQResultSet results = QueryFactory.newSelectQuery(SelectType.EXTERNAL)
                .select("artist")
                .from("the_classics")
                .executeOnConnection(QueryFactory
                        .newConnection("localhost", "albums", "root", "root"));

        List<String> expected = Arrays.asList("Metallica", "The Beatles", "Iron Maiden");

        assertEquals(expected, results.getColumn("artist"));
    }

    @Test
    public void testResultSetFetchOne() throws SQLException, ClassNotFoundException {
        SQResultSet results = QueryFactory.newSelectQuery(SelectType.EXTERNAL)
                .select("*")
                .from("the_classics")
                .executeOnConnection(QueryFactory
                        .newConnection("localhost", "albums", "root", "root"));

        HashMap<String, Object> expected = new HashMap<>();
        expected.put("name", "The Black Album");
        expected.put("artist", "Metallica");
        expected.put("year", 1991);

        assertEquals(expected, results.fetchOne());
    }
}
