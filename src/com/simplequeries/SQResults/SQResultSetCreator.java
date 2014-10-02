package com.simplequeries.SQResults;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public final class SQResultSetCreator {

    private static HashMap<String, List<Object>> resultsMap = new HashMap<>();

    public static SQResultSet createResultSet(ResultSet results) throws SQLException {
        ResultSetMetaData rsmd = results.getMetaData();
        int columnCount = rsmd.getColumnCount();

        // Get the column names and add them to the map
        for (int i = 1; i < columnCount + 1; i++ ) {
            resultsMap.put(rsmd.getColumnName(i), new ArrayList<Object>());
        }

        while (results.next()) {
            for (int i = 0; i < resultsMap.size(); i++) {
                resultsMap.get(rsmd.getColumnName(i + 1))
                        .add(getResult(rsmd, results, i + 1));
            }
        }

        return new SQResultSet(resultsMap);
    }

    private static Object getResult(ResultSetMetaData rsmd, ResultSet results, int i)
            throws SQLException {

        int type = rsmd.getColumnType(i);
        switch (type) {
            case Types.VARCHAR:
                return results.getString(i);
            case Types.INTEGER:
                return results.getInt(i);
            case Types.DATE:
                return results.getDate(i);
            default:
                return null;
        }
    }
}
