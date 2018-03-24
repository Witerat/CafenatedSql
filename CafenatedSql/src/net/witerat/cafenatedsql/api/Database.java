package net.witerat.cafenatedsql.api;

import java.sql.SQLException;

/**
 * @author John Hutcheson &lt;witerat.test@gmail.com&gt;
 *
 */
public interface Database {

  /**
   * Open a new connection or get a shared connection using connection model and
   * properties.
   */
  void connect();

  /**
   * Commit pending changes and close the current connection or disassociate
   * with a shared connection.
   *
   * @throws SQLException if underlying provider throws an exception.
   */
  void close() throws SQLException;

  /**
   * Add a new Schema object.
   *
   * @param sch a schema to be added to the database.
   */
  void add(Schema sch);

  /**
   * Gets a named schema.
   *
   * @param name the name of the to retrieve.
   * @return the database schema with the specified name.
   */
  Schema getSchema(String name);

  /**
   * Push changes to the supporting database from all associated entity objects.
   *
   * @throws SQLException if the underlying DBMS provider experience an
   *    exception.
   */
  void update() throws SQLException;
}
