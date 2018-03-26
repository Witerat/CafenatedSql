package net.witerat.cafenatedsql.spi.driver;

import java.util.Map;

import net.witerat.cafenatedsql.api.driver.DialectDef;
import net.witerat.cafenatedsql.api.driver.template.ExpressionFailedException;
import net.witerat.cafenatedsql.api.driver.template.TemplateEngineModel;

/**
 * This class provides a dialect object using model parameters. This function is
 * usually used in the connection process to identify the SQL dialect to be used
 * for the duration of the connection.
 *
 * @author John Hutcheson &lt;witerat.test@gmail.com&gt;
 *
 */

public class DynamicDialectSelector implements DialectSelector {

  /** The expression. */
  private boolean expression;

  /** The dialects by name. */
  private Map<String, Dialect> dialectsByName;

  /** The name. */
  private String name;

  /**
   * Instantiates a new dynamic dialect selector.
   *
   * @param name0
   *          the name
   * @param isExpression
   *          the is expression
   */
  public DynamicDialectSelector(final String name0,
      final boolean isExpression) {
    this.name = name0;
    expression = isExpression;
  }

  /**
   * Get the name of the dialect.
   *
   * @param model
   *          the currently executing model
   * @return the name of the nominated dialect.
   * @throws ExpressionFailedException
   *           if name fails to evaluate.
   */
  @Override
  public String getDialectName(final TemplateEngineModel model)
      throws ExpressionFailedException {
    if (expression) {
      return (String) model.evaluateContextExpression(name);
    } else {
      return name;
    }

  }

  /**
   * Gets the dialect.
   *
   * @param model
   *          The request model active in the connection process.
   * @return The dialect that provides command templates for operations to be
   *         performed while the connection is active.
   */
  @Override
  public DialectDef getDialect(final TemplateEngineModel model) {
    try {
      return dialectsByName.get(getDialectName(model));
    } catch (ExpressionFailedException e) {
      return null;
    }
  }

}
