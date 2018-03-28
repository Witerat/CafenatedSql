package net.witerat.cafenatedsql.api;

import java.util.List;

import net.witerat.cafenatedsql.api.EntityManager.Calculus;

/**
 * @author John Hutcheson &lt;witerat.test@gmail.com&gt;
 *
 */
public interface Refactor {
  /**
   * @param refactors a list of refactorings.
   */
  void setJournal(List<Refactor> refactors);
  /**
   * @return Dictionary noun for the verb.
   */
  String getNoun();
  /**
   * @return an operation to be performed
   */
  Calculus getOperation();
  /**
   * @return a reference to the modified object
   */
  Object getObject();

}
