package net.witerat.cafenatedsql.api.driver.template;

/**
 * An Expression Language encapsulates parsing of an expression and derivation
 * of a value from an input context model.
 *
 * @author John Hutcheson &lt;witerat.test@gmail.com&gt;
 *
 */
public interface ExpressionLanguage {

  /**
   * @param expression
   *          an expression nominating properties and operators signifying a
   *          derivable result.
   * @param model
   *          a context model against which the expression will be evaluated.
   * @return a derived result based on the expression and values of the model.
   * @throws ExpressionFailedException
   *           if the expression is malformed, or operations specified by the
   *           expression result in an exception.
   */
  Object evaluate(String expression, TemplateEngineModel model)
      throws ExpressionFailedException;

}
