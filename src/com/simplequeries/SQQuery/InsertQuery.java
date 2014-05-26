package com.simplequeries.SQQuery;

import com.simplequeries.SQUtils.Utils;

public class InsertQuery {

    private StringBuffer query = new StringBuffer("INSERT INTO ");

    /**
     * Select the table name to insert values into.
     *
     * @param tableName the table name to insert the new values
     * @return the updated query object
     */
    public InsertQuery insertInto(String tableName) {
        query.append(tableName).append("( ");
        return this;
    }

    /**
     * This method accepts more than 1 arguments, and adds the column names
     * that we provide to the INSERT query.
     *
     * @param columns the column names that you want to add to the table
     * @return the updated query object
     */
    public InsertQuery onColumns(String... columns) {
        for (String column : columns) {
            query.append(column).append(", ");
        }
        query.delete(query.lastIndexOf(", "), query.length()).append(")").append(" VALUES");
        return this;
    }

    /**
     * This method accepts more than 1 arguments, and adds the values
     * that we provide to the INSERT query (to be used after we specified the column names).
     * The number of value args should match the number of columns!
     *
     * @param values the values that we want to insert to the columns
     * @return the updated query object
     */
    public InsertQuery values(Object... values) {
        query.append("( ");
        for (Object value : values) {
            if (value.getClass().getName().equals("java.lang.String")) {
                query.append("\'").append(value).append("\'").append(", ");
            } else {
                query.append(value).append(", ");
            }
        }
        query.delete(query.lastIndexOf(", "), query.length()).append("), ");
        return this;
    }

    /**
     * Builds the SQL string.
     * @return the SQL string
     */
    public String buildSQLString() {
        return Utils.fixTrailingCommas(query).append(";").toString();
    }
}
