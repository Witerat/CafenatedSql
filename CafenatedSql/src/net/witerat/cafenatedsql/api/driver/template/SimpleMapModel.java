package net.witerat.cafenatedsql.api.driver.template;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * The Class SimpleMapModel implements a model using a map..
 */
public class SimpleMapModel implements TemplateEngineModel {

  /** Class of properties field. */
  @SuppressWarnings("unchecked")
  private static final Class<? extends Map<String, Object>>
      PROPERTIES_CLASS =
          (Class<? extends Map<String, Object>>) LinkedHashMap.class;
  /** The properties. */
  private final Map<String, Object> properties;

  /**
   * Instantiates a new simple map model.
   *
   * @param p0
   *          A map of name value pairs.
   */
  SimpleMapModel(final Map<String, Object> p0) {
    Map<String, Object> p = p0;
    if (p == null) {
      p = allocate();
    }
    properties = p;
  }

  /**
   * Allocate a map to hold properties.
   * @return the map for properties.
   */
  private Map<String, Object> allocate() {
    try {
      return (Map<String, Object>) PROPERTIES_CLASS.newInstance();
    } catch (InstantiationException | IllegalAccessException e) {
      throw new RuntimeException("Failed to allocate property map", e);
    }
  }

  /**
   * Instantiates a new simple map model.
   */
  public SimpleMapModel() {
    properties = allocate();
  }

  /**
   * Fetch the value by evalution of and expression. This method uses
   * {@link #evaluateContextExpression(String)} but returns <tt>null</tt>
   * if an {@link ExpressionFailedException} is thrown.
   *
   * @see net.witerat.cafenatedsql.api.driver.template.TemplateEngineModel#
   *    getByExpression(java.lang.String)
   */
  @Override
  public Object getByExpression(final String name) {
    try {
      return evaluateContextExpression(name);
    } catch (ExpressionFailedException e) {
      return null;
    }
  }

  /**
   * {@inheritDoc}
   *
   * @see net.witerat.cafenatedsql.api.driver.template.TemplateEngineModel#
   *    get(java.lang.String)
   */
  @Override
  public Object get(final String key) {
    return properties.get(key);
  }

  /**
   * {@inheritDoc}
   *
   * @see net.witerat.cafenatedsql.api.driver.template.TemplateEngineModel#
   *    set(java.lang.String, java.lang.Object)
   * @Todo Why was there a string cast of value?
   */
  @Override
  public void set(final String property, final Object value) {
    properties.put(property, value);
  }

  /**
   * {@inheritDoc}
   *
   * @see net.witerat.cafenatedsql.api.driver.template.TemplateEngineModel#
   *    evaluateContextExpression(java.lang.String)
   */
  @Override
  public Object evaluateContextExpression(final String expression)
      throws ExpressionFailedException {
    if (properties.containsKey(expression)) {
      return properties.get(expression);
    }
    throw new ExpressionFailedException("No property named " + expression);
  }

  /**
   * {@inheritDoc}
   *
   * @see net.witerat.cafenatedsql.api.driver.template.TemplateEngineModel#
   *    getPropertyType(java.lang.Object)
   */
  @Override
  public Class<?> getPropertyType(final Object property) {
    if (properties.containsKey(property)) {
      try {
        return properties.get((String) property).getClass();
      } catch (NullPointerException npe) {
        return null;
      }
    } else {
      return null;
    }
  }

}
