package net.witerat.cafenatedsql.spi.driver.template;

import java.io.StringWriter;
import java.text.MessageFormat;
import java.util.Map;

import net.witerat.cafenatedsql.api.driver.template.ExpressionFailedException;
import net.witerat.cafenatedsql.api.driver.template.TemplateEngineModel;

import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;
import org.apache.velocity.context.AbstractContext;

/**
 * The Class VelocityTemplateModel.
 * @author John Hutcheson &lt;witerat.test@gmail.com&gt;
 *
 */
public class VelocityTemplateModel implements TemplateEngineModel {

  /** The Constant RESULT. */
  private static final String RESULT = "__result";

  /** The context. */
  private VelocityContext context;

  /**
   * Instantiates a new velocity template model.
   */
  public VelocityTemplateModel() {
    context = new VelocityContext();
  }

  /**
   * Instantiates a new empty velocity template model in the given parent scope.
   *
   * @param scope
   *          A name/value pair model will be wrapped in a
   *          <code>VelocityContext</code> and used as parent scope.
   */
  public VelocityTemplateModel(final VelocityContext scope) {
    context = new VelocityContext(scope);
  }

  /**
   * Instantiates a new velocity template model as a global scope.
   *
   * @param model
   *          A name/value pair model will be wrapped in a
   *          <code>VelocityContext</code> and used as local scope.
   */
  public VelocityTemplateModel(final Map<String, Object> model) {
    context = new VelocityContext(model);
  }

  /**
   * Instantiates a new velocity template mode with a specified model within
   * a given scope.
   *
   * @param model
   *          A name/value pair model
   * @param scope
   *          the scope
   */
  public VelocityTemplateModel(final Map<String, Object> model,
      final VelocityContext scope) {
    context = new VelocityContext(model, scope);
  }

  /**
   * Gets the backing velocity context.
   *
   * @return the velocity context object
   */
  public VelocityContext getVelocity() {
    return context;
  }

  /**
   * {@inheritDoc}
   * @see net.witerat.cafenatedsql.api.driver.template
   *    .TemplateEngineModel#evaluateContextExpression(java.lang.String)
   */
  @Override
  public Object evaluateContextExpression(final String expression)
      throws ExpressionFailedException {
    VelocityContext vc = new VelocityContext(context);
    Velocity.evaluate(vc, new StringWriter(), this.getClass().getName(),
        MessageFormat.format("#set(${0}={1})", RESULT, expression));
    if (vc.containsKey(RESULT)) {
      return vc.get(RESULT);
    } else {
      throw new ExpressionFailedException();
    }

  }

  /**
   * {@inheritDoc}
   * @see net.witerat.cafenatedsql.api.driver.template
   *    .TemplateEngineModel#getByExpression(java.lang.String)
   */
  @Override
  public Object getByExpression(final String expression) {
    try {
      return evaluateContextExpression(expression);
    } catch (ExpressionFailedException efe) {
      return null;
    }
  }

  /**
   * {@inheritDoc}
   * @see net.witerat.cafenatedsql.api.driver.template
   *    .TemplateEngineModel#get(java.lang.String)
   */
  @Override
  public Object get(final String property) {
    return context.get(property);
  }

  /**
   * {@inheritDoc}
   * @see net.witerat.cafenatedsql.api.driver.template
   *      .TemplateEngineModel#set(
   *            java.lang.String, java.lang.Object)
   */
  @Override
  public void set(final String property, final Object value) {
    ((AbstractContext) context).put(property, value);

  }

  /**
   * {@inheritDoc}
   * @see net.witerat.cafenatedsql.api.driver.template
   *    .TemplateEngineModel#getPropertyType(java.lang.Object)
   */
  @Override
  public Class<?> getPropertyType(final Object property) {
    try {
      return context.get((String) property).getClass();
    } catch (NullPointerException npe) {
      return null;
    }
  }
}
