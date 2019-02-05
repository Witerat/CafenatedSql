package net.witerat.cafenatedsql.api.driver.template;

/**
 * The Interface TemplateEngineModel encapsulates a contract for
 * implementing a model usual by employing the model of the the
 * supporting templating component.
 */
public interface TemplateEngineModel {

  /**
   * gets the value of a model property by evaluating the expression language of
   * the underlying template system. Often a convenience method that calls
   * {@link #evaluateContextExpression(String)}.
   *
   * @param expression
   *          an expression in the template system's native expression language;
   * @return the evaluation product for expression, or null if the evaluation
   *         fails.
   */
  Object getByExpression(String expression);

  /**
   * gets the value of the specified property from the backing model.
   *
   * @param property
   *          the property name(<code>[a-z)[a-zA-Z0-9]*</code>) or property
   *          expression(<code>[a-z)[a-zA-Z0-9]*{.[a-z)[a-zA-Z0-9]*}*</code>) of
   *          the property to retrieve.
   * @return the value of the property.
   */
  Object get(String property);

  /**
   * Evaluate context expression.
   *
   * @param expression
   *          text representing an evaluable expression for the underlying
   *          template system.
   * @return the evaluated result of the expression.
   * @throws ExpressionFailedException
   *           The template system failed to evaluate the expression and arrive
   *           at a result.
   */
  Object evaluateContextExpression(String expression)
      throws ExpressionFailedException;

  /**
   * Set a model property.
   *
   * @param property
   *          the property name or expression
   * @param value
   *          the new value of the property
   */
  void set(String property, Object value);

  /** the class of a property.
   * @param property the name of the property.
   * @return the type for the property.
   * */
  Class<?> getPropertyType(Object property);

}
