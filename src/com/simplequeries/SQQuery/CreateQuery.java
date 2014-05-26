package com.simplequeries.SQQuery;

import com.simplequeries.SQUtils.DataType;
import com.simplequeries.SQUtils.Utils;

public class CreateQuery {

    private StringBuffer query = new StringBuffer("CREATE TABLE ");

    /**
     * Creates the table given the table name.
     *
     * @param tableName the name of the table we want to create
     * @return the updated query object
     */
    public CreateQuery createTable(String tableName) {
        query.append(tableName).append("( ");
        return this;
    }

    /**
     * Creates the table given the table name, checking if the table already exists.
     *
     * @param tableName the name of the table we want to create
     * @return the updated query object
     */
    public CreateQuery createTableIfNotExists(String tableName) {
        query.append("IF NOT EXISTS ").append(tableName).append("( ");
        return this;
    }

    /**
     * Adds a column with columnName in the table.
     *
     * @param columnName The name of the column
     * @return the updated query object
     */
    public CreateQuery addColumn(String columnName) {
        query.append(columnName);
        return this;
    }

    /**
     * Defines the data type for the previously created column.
     *
     * @param dataType the data type we want this column to be
     * @return the updated query object
     */
    public CreateQuery ofDataType(DataType dataType) {
        if (dataType.equals(DataType.INT)) {
            Utils.fixTrailingCommas(query).append(" INT, ");
        } else {
            Utils.fixTrailingCommas(query).append(" DATE, ");
        }
        return this;
    }

    /**
     * Defines the data type for the previously created column.
     * It is specifically used for the Varchar data type, as we need to also access the size
     * (defined using the size(int ) method).
     *
     * @param dataType the data type we want this column to be
     * @return the updated query object
     */
    public CreateQuery ofDataType(DataType.VarcharDataType dataType) {
        Utils.fixTrailingCommas(query).append(" VARCHAR(").append(dataType.getSize()).append("), ");
        return this;
    }

    /**
     * Adds the NOT NULL argument in the column creation.
     *
     * @return the updated query object
     */
    public CreateQuery notNull() {
        Utils.fixTrailingCommas(query).append(" NOT NULL, ");
        return this;
    }

    /**
     * Adds the AUTO_INCREMENT argument in the column creation.
     *
     * @return the updated query object
     */
    public CreateQuery autoIncrement() {
        Utils.fixTrailingCommas(query).append(" AUTO_INCREMENT, ");
        return this;
    }

    /**
     * Specifies and adds the primary key.
     *
     * @return the updated query object
     */
    public CreateQuery primaryKey(String arg) {
        query.append("PRIMARY KEY (").append(arg).append(") ");
        return this;
    }

    /**
     * Builds the SQL string.
     * @return the SQL string
     */
    public String buildSQLString() {
        query.append(");");
        return query.toString().replace(", );", " );");
    }
}
