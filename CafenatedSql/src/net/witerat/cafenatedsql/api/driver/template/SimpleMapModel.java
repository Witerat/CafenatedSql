package net.witerat.cafenatedsql.api.driver.template;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * The Class SimpleMapModel implements a model using a map..
 */
public class SimpleMapModel implements TemplateEngineModel {

  /** The properties. */
  private final Map<String, Object> properties;

  /**
   * Instantiates a new simple map model.
   *
   * @param p0
   *          the p
   */
  SimpleMapModel(final Map<String, Object> p0) {
    Map<String, Object> p = p0;
    if (p == null) {
      p = new LinkedHashMap<>();
    }
    properties = p;
  }

  /**
   * Instantiates a new simple map model.
   */
  public SimpleMapModel() {
    properties = new LinkedHashMap<>();
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
    return properties.get(key);
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
    properties.put(property, (String) value);
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
    return get(expression);
  }

  /* (non-Javadoc)
   * @see net.witerat.cafenatedsql.api.driver.template
   *    .TemplateEngineModel#getPropertyType(java.lang.Object)
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
