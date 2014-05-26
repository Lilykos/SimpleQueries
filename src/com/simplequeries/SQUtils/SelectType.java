package com.simplequeries.SQUtils;

/**
 * This class is used to create 2 different kinds of objects that are used as a way
 * to differentiate between external and internal select queries.
 *      EXTERNAL: The query is finalized and we add a ";" in the end.
 *      NESTED: The query is inside another query so we don't add the ";".
 */
public final class SelectType {

    private SelectType() {}

    public static final SelectType EXTERNAL = new SelectType();
    public static final SelectType NESTED = new SelectType();
}
