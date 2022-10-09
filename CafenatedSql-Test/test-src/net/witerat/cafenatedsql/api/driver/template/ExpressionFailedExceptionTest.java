package net.witerat.cafenatedsql.api.driver.template;


import org.junit.Test;

public class ExpressionFailedExceptionTest extends ExpressionFailedException {

  /**
   * The serialVersionUID property.
   */
  private static final long serialVersionUID = 1L;

  @Test
  public final void testExpressionFailedExceptionStringThrowableBooleanBoolean() {
    new ExpressionFailedException("blah!", new NullPointerException(),true,true);
  }

  @Test
  public final void testExpressionFailedExceptionStringThrowable() {
    new ExpressionFailedException("blah!", new NullPointerException());
  }

  @Test
  public final void testExpressionFailedExceptionString() {
    new ExpressionFailedException("blah!");
  }

  @Test
  public final void testExpressionFailedExceptionThrowable() {
    new ExpressionFailedException(new NullPointerException());
  }

  @Test
  public final void testExpressionFailedException() {
    new ExpressionFailedException();
  }

}
