package com.simplequeries.SQUtils;

/**
 * The DataType class is used to provide the right data type for the column
 * during the creation of a table.
 */
public final class DataType {

    private DataType() {}

    public static final DataType INT = new DataType();
    public static final DataType DATE = new DataType();
    private static final VarcharDataType varchar = new VarcharDataType();

    public static VarcharDataType VARCHAR(int size) {
        return varchar.size(size);
    }

    /**
     * Although VARCHAR is a standard data type, the problem is that we need to provide
     * its size. Trying to do it in an elegant way, I created a subclass especially for this
     * data type, with getter/setter for the size of the VARCHAR.
     */
    public static class VarcharDataType {
        private int size;

        private VarcharDataType() {}

        private VarcharDataType size(int arg) {
            size = arg;
            return this;
        }

        public int getSize() {
            return size;
        }
    }
}
