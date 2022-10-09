/**
 * @author John Hutcheson &lt;witerat.test@gmail.com&gt;
 */
package net.witerat.cafenatedsql.spi.driver.tags;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;

import java.util.HashMap;
import java.util.Iterator;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
## type: class|interface|enum
 * The DriverTagTest type 
## DriverTagTest.getType()}.
 *
 * @author John Hutcheson &lt;witerat.test@gmail.com&gt;
 *
 */
public class DriverTagTest {
  DriverTag part;

  /**
   * @throws java.lang.Exception
   */
  @Before
  public void setUp() throws Exception {
    part = new DriverTag();
  }

  /**
   * @throws java.lang.Exception
   */
  @After
  public void tearDown() throws Exception {
    part = null;;
  }

  /**
   * Test method for {@link net.witerat.cafenatedsql.spi.driver.tags.DriverTag
   *    #getMacro(java.lang.String)}.
   */
  @Test
  public final void testGetDef() {
    assertNull(part.getDef("nodef"));
  }

  /**
   * Test method for {@link net.witerat.cafenatedsql.spi.driver.tags.DriverTag
   *    #iterator()}.
   */
  @Test
  public final void testIterator() {
    final DefTag d = new MockDefTag();
    part.setDef(d);
    int i=0;
    for (MacroTag mt : part) {
      i++;
      assertSame(d, mt);
    }
    assertEquals(1, i);
    
  }

  /**
   * Test method for {@link net.witerat.cafenatedsql.spi.driver.tags.DriverTag
   *    #add(net.witerat.cafenatedsql.spi.driver.tags.DefTag)}.
   */
  @Test
  public final void testAdd() {
    testSetDef();
  }

  /**
   * Test method for {@link net.witerat.cafenatedsql.spi.driver.tags.DriverTag
   *    #getConnections()}.
   */
  @Test
  public final void testGetConnections() {
    assertNotNull(part.getConnections());
  }

  /**
   * Test method for {@link net.witerat.cafenatedsql.spi.driver.tags.DriverTag
   *    #getDdl()}.
   */
  @Test
  public final void testGetDdl() {
    assertNull(part.getDdl());
  }

  /**
   * Test method for {@link net.witerat.cafenatedsql.spi.driver.tags.DriverTag
   *    #getDefs()}.
   */
  @Test
  public final void testGetDefs() {
    assertNotNull(part.getDefs());
  }

  /**
   * Test method for {@link net.witerat.cafenatedsql.spi.driver.tags.DriverTag
   *    #getDml()}.
   */
  @Test
  public final void testGetDml() {
    assertNull(part.getDml());
  }

  /**
   * Test method for {@link net.witerat.cafenatedsql.spi.driver.tags.DriverTag
   *    #setConnection(net.witerat.cafenatedsql.spi.driver.tags.ConnectionTag)}.
   */
  @Test
  public final void testSetConnection() {
    final ConnectionTag c = new ConnectionTag();
    c.setMethod("foo");
    part.setConnection(c);
    final Iterator<ConnectionTag> c1 = part.getConnections().iterator();
    ConnectionTag c2 = c1.next();
    assertSame(c,c2);
    assertEquals("foo",c2.getMethod());
  }

  /**
   * Test method for {@link net.witerat.cafenatedsql.spi.driver.tags.DriverTag
   *    #setDialect(net.witerat.cafenatedsql.spi.driver.tags.DialectTag)}.
   */
  @Test
  public final void testSetDialect() {
    final DialectTag d = new DialectTag();
    final String foo = "foo";
    d.setId(foo);
    part.setDialect(d);
    final DialectTag d2 = part.getDialectByName(foo);
    assertSame(d,d2);
    assertEquals(foo,d2.getId());
    
  }

  /**
   * Test method for {@link net.witerat.cafenatedsql.spi.driver.tags.DriverTag
   *    #setDdl(net.witerat.cafenatedsql.spi.driver.tags.DdlTag)}.
   */
  @Test
  public final void testSetDdl() {
    part.setDdl(new DdlTag());
  }

  /**
   * Test method for {@link net.witerat.cafenatedsql.spi.driver.tags.DriverTag
   *    #setDefs(java.util.Map)}.
   */
  @Test
  public final void testSetDefs() {
    part.setDefs(new HashMap<>());
  }

  /**
   * Test method for {@link net.witerat.cafenatedsql.spi.driver.tags.DriverTag
   *    #setDef(net.witerat.cafenatedsql.spi.driver.tags.DefTag)}.
   */
  @Test
  public final void testSetDef() {
    final DefTag d = new DefTag();
    d.setName("foo");
    part.setDef(d);
    final DefTag d2 = part.getDef("foo");
    assertSame(d,d2);
    assertEquals("foo",d2.getName());
  }

  /**
   * Test method for {@link net.witerat.cafenatedsql.spi.driver.tags.DriverTag
   *    #setDML(net.witerat.cafenatedsql.spi.driver.tags.DmlTag)}.
   */
  @Test
  public final void testSetDml() {
    part.setDML(new DmlTag());
  }

  /**
   * Test method for {@link net.witerat.cafenatedsql.spi.driver.tags.DriverTag
   *    #getDialectByName(java.lang.String)}.
   */
  @Test
  public final void testGetDialectByName() {
    assertNull(part.getDialectByName("noDialect"));
  }

  /**
   * Test method for {@link net.witerat.cafenatedsql.spi.driver.tags.DriverTag
   *    #getDialects()}.
   */
  @Test
  public final void testGetDialects() {
    assertNotNull(part.getDialects());
  }

  /**
   * Test method for {@link net.witerat.cafenatedsql.spi.driver.tags.DriverTag
   *    #getMeta()}.
   */
  @Test
  public final void testGetMeta() {
    assertNull(part.getMeta());
  }

  /**
   * Test method for {@link net.witerat.cafenatedsql.spi.driver.tags.DriverTag
   *    #setMeta(net.witerat.cafenatedsql.spi.driver.tags.MetaTag)}.
   */
  @Test
  public final void testSetMeta() {
    part.setMeta(new MetaTag());
    assertNotNull(part.getMeta());
  }

  /**
   * Test method for {@link net.witerat.cafenatedsql.spi.driver.tags.DriverTag
   *    #getTemplateEngine()}.
   */
  @Test
  public final void testGetTemplateEngine() {
    assertNull(part.getTemplateEngine());
  }

  /**
   * Test method for {@link net.witerat.cafenatedsql.spi.driver.tags.DriverTag
   *    #setTemplateEngine(java.lang.String)}.
   */
  @Test
  public final void testSetTemplateEngine() {
    part.setTemplateEngine("foo");
    assertEquals("foo", part.getTemplateEngine());
  }

}
