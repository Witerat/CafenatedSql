package net.witerat.cafenatedsql.api.driver.template;

/**
 * The Class ExpressionFailedException is thrown to indicate that an
 * expression has failed to evaluate properly against a model.
 */
public class ExpressionFailedException extends Exception {

  /** The Constant serialVersionUID. */
  private static final long serialVersionUID = 1L;

  /**
   * Instantiates a new expression failed exception.
   *
   * @param msg
   *          the msg
   * @param cause
   *          the cause
   * @param enableSuppression
   *          the enable suppression
   * @param printableSackTrace
   *          the printable sack trace
   */
  public ExpressionFailedException(final String msg, final Throwable cause,
      final boolean enableSuppression, final boolean printableSackTrace) {
    super(msg, cause, enableSuppression, printableSackTrace);
  }

  /**
   * Instantiates a new expression failed exception.
   *
   * @param msg
   *          the msg
   * @param cause
   *          the cause
   */
  public ExpressionFailedException(final String msg, final Throwable cause) {
    super(msg, cause);
  }

  /**
   * Instantiates a new expression failed exception.
   *
   * @param msg
   *          the msg
   */
  public ExpressionFailedException(final String msg) {
    super(msg);
  }

  /**
   * Instantiates a new expression failed exception.
   *
   * @param cause
   *          the cause
   */
  public ExpressionFailedException(final Throwable cause) {
    super(cause);
  }

  /**
   * Instantiates a new expression failed exception.
   */
  public ExpressionFailedException() {
    super();
  }

}
