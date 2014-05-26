package com.simplequeries;

import com.simplequeries.SQQuery.CreateQuery;
import com.simplequeries.SQQuery.DeleteQuery;
import com.simplequeries.SQQuery.InsertQuery;
import com.simplequeries.SQQuery.SelectQuery;
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
}