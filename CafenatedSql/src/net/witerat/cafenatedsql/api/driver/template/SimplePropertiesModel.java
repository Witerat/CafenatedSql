package net.witerat.cafenatedsql.api.driver.template;

import java.util.Properties;

import net.witerat.cafenatedsql.api.Cafenated;

/**
 * A simple implementation that backs a template model with a
 * <code>java.util.Properties</code> it has the limitation that values can only
 * be strings.
 *
 * @author John Hutcheson &lt;witerat.test@gmail.com&gt;
 *
 */
public class SimplePropertiesModel implements TemplateEngineModel {

  /** The properties. */
  private final Properties properties;

  /**
   * Instantiates a new simple properties model.
   *
   * @param p
   *          the p
   */
  public SimplePropertiesModel(final Properties p) {
    properties = p;
  }

  /**
   * Instantiates a new simple properties model.
   */
  public SimplePropertiesModel() {
    properties = new Properties();
  }

  /*
   * (non-Javadoc)
   *
   * @see net.witerat.cafenatedsql.api.driver.template.TemplateEngineModel#
   * getByExpression(java.lang.String)
   */
  @Override
  public Object getByExpression(final String name) {
    try {
      return evaluateContextExpression(name);
    } catch (ExpressionFailedException e) {
      return null;
    }
  }

  /*
   * (non-Javadoc)
   *
   * @see
   * net.witerat.cafenatedsql.api.driver.template.TemplateEngineModel#get(java.
   * lang.String)
   */
  @Override
  public Object get(final String key) {
    return getProperties().getProperty(key);
  }

  /*
   * (non-Javadoc)
   *
   * @see
   * net.witerat.cafenatedsql.api.driver.template.TemplateEngineModel#set(java.
   * lang.String, java.lang.Object)
   */
  @Override
  public void set(final String property, final Object value) {
    getProperties().setProperty(property, (String) value);
  }

  /*
   * (non-Javadoc)
   *
   * @see net.witerat.cafenatedsql.api.driver.template.TemplateEngineModel#
   * evaluateContextExpression(java.lang.String)
   */
  @Override
  public Object evaluateContextExpression(final String expression)
      throws ExpressionFailedException {
    ExpressionLanguage el = getExpressionLanguage();
    if (el != null) {
      return el.evaluate(expression, this);
    }
    return get(expression);
  }

  /**
   * The ExpressionLanguage property returns the expression language parser to
   * be used to extract and compute values from a model. If no expression
   * language is a available a default of treating the expression as a key is
   * recommended and is the default.
   * @return the expression language used to analyse model values.
   */
  public ExpressionLanguage getExpressionLanguage() {
    ExpressionLanguage el
      = (ExpressionLanguage) get(Cafenated.EXPRESSION_LANGUAGE);
    return el;
  }

  /**
   * Gets the properties.
   *
   * @return the properties
   */
  public Properties getProperties() {
    return properties;
  }

}
