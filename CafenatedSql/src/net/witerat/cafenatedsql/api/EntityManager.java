package net.witerat.cafenatedsql.api;

/**
 * @author John Hutcheson &lt;witerat.test@gmail.com&gt;
 *
 */
public interface EntityManager {

  /**
   * Describe operations applicable to database entities..
   * @author John Hutcheson &lt;witerat.test@gmail.com&gt;
   *
   */
  enum Calculus {
    /**
     * The CREATE property.
     */
    CREATE,
    /**
     * The DROP property.
     */
    DROP,
    /**
     * The ALTER property.
     */
    ALTER
  }
  /**
   * @param noun Dictionary context of operation
   * @param operation the operation
   * @param obj target of operation.
   */
  void refactor(String noun, Calculus operation, Object obj);

}
