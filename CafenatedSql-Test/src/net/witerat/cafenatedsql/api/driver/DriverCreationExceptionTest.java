package net.witerat.cafenatedsql.api.driver;

import static org.junit.Assert.*;

import org.junit.Test;

public class DriverCreationExceptionTest extends DriverCreationException {

  @Test
  public void testDriverCreationException() {
    new DriverCreationException();
  }

  @Test
  public void testDriverCreationExceptionString() {
    new DriverCreationException("Bugger");
  }

  @Test
  public void testDriverCreationExceptionThrowable() {
    new DriverCreationException(new RuntimeException("Bugger"));
  }

  @Test
  public void testDriverCreationExceptionStringThrowable() {
    new DriverCreationException("Bugger", new RuntimeException("Bugger Damn"));
  }

  @Test
  public void testDriverCreationExceptionStringThrowableBooleanBoolean() {
    new DriverCreationException("Bugger", new RuntimeException("Bugger Damn"), true, true);
  }

}
