package com.simplequeries.SQUtils;

public final class Utils {

    private Utils() {}

    /**
     * This method is used to remove trailing commas from the query StringBuffer.
     * Due to the way commas are added sometimes it is necessary to check and remove them
     * to assure the consistency of the query.
     *
     * @param query The query StringBuffer
     * @return The updated query StringBuffer
     */
    public static StringBuffer fixTrailingCommas(StringBuffer query) {
        if (query.toString().endsWith(", ")) {
            return query.delete(query.length() - 2, query.length());
        }
        return query;
    }
}
