package com.simplequeries.SQQuery;

import com.simplequeries.SQUtils.Utils;

public class UpdateQuery {

    private StringBuffer query = new StringBuffer("UPDATE ");

    /**
     * Selects the table to update.
     *
     * @param tableName table name
     * @return the updated query object
     */
    public UpdateQuery update(String tableName) {
        query.append(tableName).append(" SET ");
        return this;
    }

    /**
     * Sets the column for change with the new value.
     *
     * @param columnName the column to change
     * @param updateArg the new value
     * @return the updated query object
     */
    public UpdateQuery set(String columnName, Object updateArg) {
        query.append(columnName).append(" = ")
                .append("'").append(updateArg).append("'").append(", ");
        return this;
    }

    /**
     * The WHERE SQL clause. Accepts the column name.
     *
     * @param columnName the columnName that you want to check
     * @return the updated query object
     */
    public UpdateQuery where(String columnName) {
        Utils.fixTrailingCommas(query).append(" WHERE ").append(columnName);
        return this;
    }


    /**
     * The methods bellow are used to check specific conditions in the WHERE clause.
     * Currently implemented methods:
     *      eq:     equals ( = )
     *      notEq:  not equals ( != )
     *      lt:     less than ( < )
     *      gt:     greater than ( > )
     *      ltOrEq: less than or equals ( <= )
     *      gtOrEq: greater than or equals ( >= )
     */
    public UpdateQuery eq(Object arg) { return insertComparison(" = ", arg); }
    public UpdateQuery notEq(Object arg) { return insertComparison(" != ", arg); }
    public UpdateQuery gt(Object arg) { return insertComparison(" > ", arg); }
    public UpdateQuery lt(Object arg) { return insertComparison(" < ", arg); }
    public UpdateQuery gtOrEq(Object arg) { return insertComparison(" >= ", arg); }
    public UpdateQuery ltOrEq(Object arg) { return insertComparison(" <= ", arg); }

    private UpdateQuery insertComparison(String comparison, Object arg) {
        query.append(comparison).append("\'").append(arg).append("\'");
        return this;
    }

    /**
     * This adds AND in the query and can be used in different places,
     * usually with the WHERE clause.
     *
     * @param andArg the and argument
     * @return the updated query object
     */
    public UpdateQuery and(String andArg) {
        query.append(" AND ").append(andArg);
        return this;
    }

    /**
     * The OR SQL clause. Accepts the column name.
     *
     * @param columnName the columnName that you want to check
     * @return the updated query object
     */
    public UpdateQuery or(String columnName) {
        query.append(" OR ").append(columnName);
        return this;
    }

    /**
     * Accepts multiple arguments which will create a list of strings that can be checked.
     * Used with the WHERE clause, e.g WHERE name IN ('John', 'George', 'Paul', 'Ringo')
     *
     * @param args the arguments to create the IN list
     * @return the updated query object
     */
    public UpdateQuery in(String... args) {
        if (args.length == 1) {
            for (String arg : args) {
                if (arg.startsWith("(")) { // nested select
                    query.append(" IN ").append(arg);
                } else { // just single value, same as below
                    query.append(" IN (").append("\'").append(arg).append("\'").append(")");
                }
            }
            return this;
        } else {
            query.append(" IN (");
            for (String arg : args) {
                query.append("\'").append(arg).append("\'").append(", ");
            }
            query.delete(query.lastIndexOf(", "), query.length()).append(")");
            return this;
        }
    }

    /**
     * Adds the LIKE clause in the query.
     *
     * @param likeArg the string to compare
     * @return the updated query object
     */
    public UpdateQuery like(String likeArg) {
        query.append(" LIKE \'").append(likeArg).append("\'");
        return this;
    }

    /**
     * Adds the BETWEEN, usually used with WHERE. The method accepts 2 arguments (of type
     * Object, so we can use ints, floats strings and whatever else) which are the top-bottom boundaries
     * for the comparison.
     *
     * @param least the bottom boundary
     * @param most the top boundary
     * @return the updated query object
     */
    public UpdateQuery between(Object least, Object most) {
        query.append(" BETWEEN ").append(least);
        and(most.toString());
        return this;
    }

    /**
     * NOT clause.
     *
     * @return the updated query object
     */
    public UpdateQuery not() {
        query.append(" NOT");
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
