package net.witerat.cafenatedsql.api.driver;

/**
 * @author John Hutcheson &lt;witerat.test@gmail.com&gt;
 *
 */
public interface DDLEditor {

  /**
   * Construct and submit commands to the database.
   * @param obj a record of changes, or new state of the entity.
   */
  void marshal(Object obj);

}
