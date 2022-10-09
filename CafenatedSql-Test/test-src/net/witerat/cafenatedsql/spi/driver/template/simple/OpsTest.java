/**
 * @author John Hutcheson &lt;witerat.test@gmail.com&gt;
 */
package net.witerat.cafenatedsql.spi.driver.template.simple;

import static org.junit.Assert.*;

import org.junit.Test;

/**
## type: class|interface|enum
 * The OpsTest type 
## OpsTest.getType()}.
 *
 * @author John Hutcheson &lt;witerat.test@gmail.com&gt;
 *
 */
public class OpsTest {

  /**
   * Test method for {@link net.witerat.cafenatedsql.spi.driver.template.simple.Ops#precedence()}.
   */
  @Test
  public final void testPrecedence() {
    for (Ops v:Ops.values()) {
        v.precedence();
    }
  }

  /**
   * Test method for {@link net.witerat.cafenatedsql.spi.driver.template.simple.Ops#encode(java.lang.Object[])}.
   */
  @Test
  public final void testEncode() {
    for (Ops v:Ops.values()) {
      Exception ex=null;
      try {
        v.encode(null);
      } catch (Exception e) {
        ex = e;
      }
      assertNotNull(ex);
    }
  }

}
