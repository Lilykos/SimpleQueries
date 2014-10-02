package com.simplequeries.SQConnection;

public class Connection {

    private final String url;
    private final String db;
    private final String username;
    private final String passwd;

    public Connection(String url, String db, String username, String passwd) {
        this.url = url;
        this.db = db;
        this.username = username;
        this.passwd = passwd;
    }

    public String getUrl() {
        return url;
    }

    public String getDb() {
        return db;
    }

    public String getUsername() {
        return username;
    }

    public String getPasswd() {
        return passwd;
    }
}
