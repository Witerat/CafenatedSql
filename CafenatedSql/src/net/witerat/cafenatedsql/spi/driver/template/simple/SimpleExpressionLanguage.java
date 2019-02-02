package net.witerat.cafenatedsql.spi.driver.template.simple;

import net.witerat.cafenatedsql.api.driver.template.ExpressionFailedException;
import net.witerat.cafenatedsql.api.driver.template.ExpressionLanguage;
import net.witerat.cafenatedsql.api.driver.template.TemplateEngineModel;
import net.witerat.cafenatedsql.spi.driver.template.simple.Processor.AbstractFetch;

/**
 * @author John Hutcheson &lt;witerat.test@gmail.com&gt;
 *
 */
public class SimpleExpressionLanguage implements ExpressionLanguage {
  /** The No-char char. */
  static final char CHAR_NIL = 0xffff;

  /**
   * Compile expression into an execution plan.
   * @param expression
   *          the expression to compile
   * @param model
   *          the model providing runtime values
   * @return an array of compiled operations.
   * @throws ExpressionFailedException
   *           if compilation fails.
   */
  private AbstractFetch[] compile(final String expression,
      final TemplateEngineModel model) throws ExpressionFailedException {
    Compiler compiler = new Compiler();
    AbstractFetch[] plan = compiler.compile(expression, model);
    if (plan == null || plan.length == 0) {
      throw new ExpressionFailedException("Expression did not produce a plan");
    }
    return plan;
  }

  /*
   * (non-Javadoc)
   *
   * @see net.witerat.cafenatedsql.api.driver.template.ExpressionLanguage
   * #evaluate(java.lang.String,
   * net.witerat.cafenatedsql.api.driver.template.TemplateEngineModel)
   */
  @Override
  public final Object evaluate(final String expression,
      final TemplateEngineModel model) throws ExpressionFailedException {
    AbstractFetch[] exPlan = compile(expression, model);

    return execute(exPlan);
  }

  /**
   * @param exPlan
   *          The execution plan
   * @return the expression result;
   * @throws ExpressionFailedException
   *           if fail
   */
  private Object execute(final AbstractFetch[] exPlan)
      throws ExpressionFailedException {
    Processor proc = new Processor(exPlan);
    proc.execute();
    return proc.pop();
  }

}
