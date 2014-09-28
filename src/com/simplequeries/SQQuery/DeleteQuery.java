package com.simplequeries.SQQuery;

public class DeleteQuery {

    private StringBuffer query = new StringBuffer("DELETE FROM ");

    public DeleteQuery deleteFrom(String tableName) {
        query.append(tableName);
        return this;
    }

    /**
     * The WHERE SQL clause. Accepts the column name.
     *
     * @param columnName the columnName that you want to check
     * @return the updated query object
     */
    public DeleteQuery where(String columnName) {
        query.append(" WHERE ").append(columnName);
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
    public DeleteQuery eq(Object arg) { return insertComparison(" = ", arg); }
    public DeleteQuery notEq(Object arg) { return insertComparison(" != ", arg); }
    public DeleteQuery gt(Object arg) { return insertComparison(" > ", arg); }
    public DeleteQuery lt(Object arg) { return insertComparison(" < ", arg); }
    public DeleteQuery gtOrEq(Object arg) { return insertComparison(" >= ", arg); }
    public DeleteQuery ltOrEq(Object arg) { return insertComparison(" <= ", arg); }

    private DeleteQuery insertComparison(String comparison, Object arg) {
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
    public DeleteQuery and(String andArg) {
        query.append(" AND ").append(andArg);
        return this;
    }

    /**
     * The OR SQL clause. Accepts the column name.
     *
     * @param columnName the columnName that you want to check
     * @return the updated query object
     */
    public DeleteQuery or(String columnName) {
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
    public DeleteQuery in(String... args) {
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
    public DeleteQuery like(String likeArg) {
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
    public DeleteQuery between(Object least, Object most) {
        query.append(" BETWEEN ").append(least);
        and(most.toString());
        return this;
    }

    /**
     * NOT clause.
     *
     * @return the updated query object
     */
    public DeleteQuery not() {
        query.append(" NOT");
        return this;
    }

    /**
     * Builds the SQL string.
     * @return the SQL string
     */
    public String buildSQLString() {
        return query.append(";").toString();
    }
}