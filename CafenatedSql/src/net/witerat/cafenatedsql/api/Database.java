package net.witerat.cafenatedsql.api;

import java.sql.SQLException;

public interface Database {

	void connect();

	void close() throws SQLException;

	void add(Schema sch);

}