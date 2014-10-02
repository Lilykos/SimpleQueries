package com.simplequeries;

import com.simplequeries.SQConnection.Connection;
import com.simplequeries.SQQuery.*;
import com.simplequeries.SQUtils.SelectType;

/**
 * The Query factory is a static class to be used for creating the right query.
 */
public final class QueryFactory {

    private QueryFactory() {}

    public static SelectQuery newSelectQuery(SelectType selectType) {
        return new SelectQuery(selectType);
    }

    public static CreateQuery newCreateQuery() {
        return new CreateQuery();
    }

    public static InsertQuery newInsertQuery() {
        return new InsertQuery();
    }

    public static DeleteQuery newDeleteQuery() {
        return new DeleteQuery();
    }

    public static UpdateQuery newUpdateQuery() {
        return new UpdateQuery();
    }

    // Connection info
    public static Connection newConnection(String url, String db,String username, String passwd) {
        return new Connection(url, db, username, passwd);
    }
}