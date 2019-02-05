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
   * Instantiates a new velocity template model.
   *
   * @param scope
   *          the scope
   */
  public VelocityTemplateModel(final VelocityContext scope) {
    context = new VelocityContext(scope);
  }

  /**
   * Instantiates a new velocity template model.
   *
   * @param model
   *          the model
   */
  public VelocityTemplateModel(final Map<String, Object> model) {
    context = new VelocityContext(model);
  }

  /**
   * Instantiates a new velocity template model.
   *
   * @param model
   *          the model
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
   * @return the velocity
   */
  public VelocityContext getVelocity() {
    return context;
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
    VelocityContext vc = new VelocityContext(context);
    Velocity.evaluate(vc, new StringWriter(), this.getClass().getName(),
        MessageFormat.format("#set(${0}={1})", RESULT, expression));
    if (vc.containsKey(RESULT)) {
      return vc.get(RESULT);
    } else {
      throw new ExpressionFailedException();
    }

  }

  /*
   * (non-Javadoc)
   *
   * @see net.witerat.cafenatedsql.api.driver.template.TemplateEngineModel#
   * getByExpression(java.lang.String)
   */
  @Override
  public Object getByExpression(final String expression) {
    try {
      return evaluateContextExpression(expression);
    } catch (ExpressionFailedException efe) {
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
  public Object get(final String property) {
    return context.get(property);
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
    ((AbstractContext) context).put(property, value);

  }

  /* (non-Javadoc)
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
