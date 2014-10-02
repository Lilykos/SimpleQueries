package com.simplequeries.SQResults;

import java.util.HashMap;
import java.util.List;

public class SQResultSet {

    private HashMap<String, List<Object>> resultsMap;

    public SQResultSet(HashMap resultsMap) {
        this.resultsMap = resultsMap;
    }

    /**
     * Gets a List containing the information of a column, based on the column name.
     * @param columnName the column name
     * @return a list containing the column information.
     */
    public List getColumn(String columnName) {
        return resultsMap.get(columnName);
    }

    /**
     * Gets a Map representing one Result object.
     * @return a HashMap containing the information of a row.
     */
    public HashMap fetchOne() {
        HashMap<String, Object> fetched = new HashMap<>();

        for (String key : resultsMap.keySet()) {
            fetched.put(key, resultsMap.get(key).get(0));
        }
        return fetched;
    }

    /**
     * Gets a Map representing all the results.
     * @return a HashMap containing all the results.
     */
    public HashMap getAll() {
        return resultsMap;
    }
}
