package com.simplequeries.SQQuery;

import com.simplequeries.SQUtils.Order;
import com.simplequeries.SQUtils.SelectType;

public class SelectQuery {

    private StringBuffer query = new StringBuffer();
    private final SelectType selectType;

    /**
     * The constructor accepts the selectType argument to check
     * whether this is an external or an internal query.
     *
     * @param selectType the select type (EXTERNAL or NESTED)
     */
    public SelectQuery(SelectType selectType){
        this.selectType = selectType;

        if (selectType.equals(SelectType.EXTERNAL)) {
            query.append("SELECT");
        } else {
            query.append("(SELECT");
        }
    }

    /**
     * Accepts any amount of selectArgs which are the columns that we
     * want to be returned.
     *
     * @param selectArgs the select arguments we want to have
     * @return the updated query object
     */
    public SelectQuery select(String... selectArgs) {
        if (selectArgs.length == 0) {
            return this;
        }
        for (String select : selectArgs) {
            query.append(" ").append(select).append(",");
        }
        query.delete(query.lastIndexOf(","), query.length());
        return this;
    }

    /**
     * Adds the COUNT(arg) in the query string.
     * Can be used with or without the select() method.
     *
     * @param columnName the column name you want to count
     * @return the updated query object
     */
    public SelectQuery count(String columnName) {
        query.append(" COUNT(").append(columnName).append(")");
        return this;
    }

    /**
     * Adds the SUM(arg) in the query string.
     * Can be used with or without the select() method.
     *
     * @param columnName the column name you want to count
     * @return the updated query object
     */
    public SelectQuery sum(String columnName) {
        query.append(" SUM(").append(columnName).append(")");
        return this;
    }

    /**
     * The FROM SQL clause. Accepts the table name.
     *
     * @param tableName the table name from which you want to access data
     * @return the updated query object
     */
    public SelectQuery from(String tableName) {
        query.append(" FROM ").append(tableName);
        return this;
    }

    /**
     * The WHERE SQL clause. Accepts the column name.
     *
     * @param columnName the columnName that you want to check
     * @return the updated query object
     */
    public SelectQuery where(String columnName) {
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
    public SelectQuery eq(Object arg) { return insertComparison(" = ", arg); }
    public SelectQuery notEq(Object arg) { return insertComparison(" != ", arg); }
    public SelectQuery gt(Object arg) { return insertComparison(" > ", arg); }
    public SelectQuery lt(Object arg) { return insertComparison(" < ", arg); }
    public SelectQuery gtOrEq(Object arg) { return insertComparison(" >= ", arg); }
    public SelectQuery ltOrEq(Object arg) { return insertComparison(" <= ", arg); }

    private SelectQuery insertComparison(String comparison, Object arg) {
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
    public SelectQuery and(String andArg) {
        query.append(" AND ").append(andArg);
        return this;
    }

    /**
     * The OR SQL clause. Accepts the column name.
     *
     * @param columnName the columnName that you want to check
     * @return the updated query object
     */
    public SelectQuery or(String columnName) {
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
    public SelectQuery in(String... args) {
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
    public SelectQuery like(String likeArg) {
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
    public SelectQuery between(Object least, Object most) {
        query.append(" BETWEEN ").append(least);
        and(most.toString());
        return this;
    }

    /**
     * GROUP BY clause.
     *
     * @param columnName the column to be used for the grouping
     * @return the updated query object
     */
    public SelectQuery groupBy(String columnName) {
        query.append(" GROUP BY ").append(columnName);
        return this;
    }

    /**
     * ORDER BY clause.
     *
     * @param columnName the column to be used for the ordering
     * @param order the order by (ASC/DESC)
     * @return the updated query object
     */
    public SelectQuery orderBy(String columnName, Order order) {
        query.append(" ORDER BY ").append(columnName);
        if (order.equals(Order.DESC)) {
            query.append(" DESC");
        } else {
            query.append(" ASC");
        }
        return this;
    }

    /**
     * ORDER BY clause without specifying ASC/DESC.
     *
     * @param columnName the column to be used for the ordering
     * @return the updated query object
     */
    public SelectQuery orderBy(String columnName) {
        query.append(" ORDER BY ").append(columnName);
        return this;
    }

    /**
     * LIMIT clause.
     *
     * @param limit amount
     * @return the updated query object
     */
    public SelectQuery limit(Object limit) {
        query.append(" LIMIT ").append(limit);
        return this;
    }

    /**
     * HAVING clause.
     *
     * @return the updated query object
     */
    public SelectQuery having() {
        query.append(" HAVING");
        return this;
    }

    /**
     * NOT clause.
     *
     * @return the updated query object
     */
    public SelectQuery not() {
        query.append(" NOT");
        return this;
    }

    /**
     * Builds the SQL string.
     * @return the SQL string
     */
    public String buildSQLString() {
        if (selectType.equals(SelectType.EXTERNAL)) {
            return query.append(";").toString();
        }
        return query.append(")").toString();
    }
}