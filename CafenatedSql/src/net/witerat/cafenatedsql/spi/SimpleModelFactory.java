package net.witerat.cafenatedsql.spi;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Properties;

import net.witerat.cafenatedsql.api.Provider;
import net.witerat.cafenatedsql.api.TemplateModelFactory;
import net.witerat.cafenatedsql.api.driver.template.ExpressionFailedException;
import net.witerat.cafenatedsql.api.driver.template.TemplateEngineModel;

/**
 * @author John Hutcheson &lt;witerat.test@gmail.com&gt;
 *
 */
public class SimpleModelFactory implements TemplateModelFactory {
  /**
   * @author John Hutcheson &lt;witerat.test@gmail.com&gt;
   *
   */
  static class ModelP implements TemplateEngineModel {
    /**
     * The props property contains the name/value pairs of the model.
     */
    private Properties props;
    /**
     * The defaults property.
     */
    private Properties defaults;
    /**
     * the props property.
     * @return the props property.
     */
    protected Properties getProps() {
      return props;
    }

    /**
     * Instantiate a Model object.
     * @param properties Default properties for the model.
     */
    ModelP(final Properties properties) {
      props = new Properties(properties);
      defaults = properties;
    }

    @Override
    public Object getByExpression(final String expression) {
      if (getProps().containsKey(expression)) {
        return getProps().get(expression);
      }
      if (defaults == null) {
        return null;
      }
      return defaults.get(expression);
    }

    @Override
    public Object get(final String property) {
      if (getProps().containsKey(property)) {
        return getProps().get(property);
      }
      if (defaults == null) {
        return null;
      }
      return defaults.get(property);
    }

    @Override
    public Object evaluateContextExpression(final String expression)
        throws ExpressionFailedException {
      if (getProps().containsKey(expression)) {
        return getProps().get(expression);
      }
      if (defaults == null) {
        return null;
      }
      return defaults.get(expression);
    }

    @Override
    public void set(final String property, final Object value) {
      props.put(property, value);
    }

    /* (non-Javadoc)
     * @see net.witerat.cafenatedsql.api.driver.template
     *          .TemplateEngineModel#getPropertyType(java.lang.Object)
     */
    @Override
    public Class<?> getPropertyType(final Object property) {
      try {
      return get((String) property).getClass();
      } catch (NullPointerException npe) {
        return null;
      }
    }
  };
  /**
   * A model that derives it defaults from another model.
   * @author John Hutcheson &lt;witerat.test@gmail.com&gt;
   *
   */
  static class ModelT extends ModelP implements TemplateEngineModel {
    /**
     * The model property - a source of default values.
     */
    private TemplateEngineModel model;
    /**
     * Instantiate a ModelT object.
     * @param model0 the defaults model.
     */
    ModelT(final TemplateEngineModel model0) {
      super(null);
      model = model0;
    }
    @Override
    public Object getByExpression(final String expression) {
      Object v = super.getByExpression(expression);
      if (null == v) {
        v = model.getByExpression(expression);
      }
      return v;
    }
    @Override
    public Object get(final String property) {
      Object v = super.get(property);
      if (null == v) {
        v = model.get(property);
      }
      return v;
    }
    @Override
    public Object evaluateContextExpression(final String expression)
        throws ExpressionFailedException {
      Object v = super.evaluateContextExpression(expression);
      if (null == v) {
        v = model.evaluateContextExpression(expression);
      }
      return v;
    }
    @Override
    public void set(final String property, final Object value) {
      getProps().put(property, value);
    }
  }
  /**
   * The factories property.
   */
  private static Map<Provider, TemplateModelFactory>  factories =
      new LinkedHashMap<>();
  /* (non-Javadoc)
   * @see net.witerat.cafenatedsql.api.TemplateModelFactory#newInstance(
   * java.util.Properties)
   */
  @Override
  public TemplateEngineModel newInstance(final Properties defaults) {
    return new ModelP(defaults);
  }

  /* (non-Javadoc)
   * @see net.witerat.cafenatedsql.api.TemplateModelFactory#newInstance(
   * net.witerat.cafenatedsql.api.driver.template.TemplateEngineModel)
   */
  @Override
  public TemplateEngineModel newInstance(final TemplateEngineModel model0) {
    return new ModelT(model0);
  }
  /**
   * @param provider The provider requesting a factory.
   * @return a model factory the provider can use.
   */
  public static TemplateModelFactory get(final Provider provider) {
    TemplateModelFactory f = null;
    synchronized (factories) {
      f = factories.get(provider);
    }
    if (f == null) {
      f = new SimpleModelFactory();
      synchronized (factories) {
       factories.put(provider, f);
      }
    }
    return f;
 }
}
