package com.simplequeries.SQUtils;

/**
 * This class is used to provide the (optional) order (ASC/DESC) in the ORDER BY clause.
 */
public class Order {

    private Order() {}

    public static final Order ASC = new Order();
    public static final Order DESC = new Order();
}
