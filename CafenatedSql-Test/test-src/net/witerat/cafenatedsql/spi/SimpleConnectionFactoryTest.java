/**
 * @author John Hutcheson &lt;witerat.test@gmail.com&gt;
 */
package net.witerat.cafenatedsql.spi;

import static org.junit.Assert.fail;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.SQLFeatureNotSupportedException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.logging.Logger;

import javax.sql.DataSource;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import net.witerat.cafenatedsql.api.driver.ConnectionType;
import net.witerat.cafenatedsql.spi.driver.Driver;

/**
 * The SimpleConnectionFactoryTest class.
 *
 * @author John Hutcheson &lt;witerat.test@gmail.com&gt;
 *
 */
public class SimpleConnectionFactoryTest extends SimpleConnectionFactory {
  
  static class Bundle extends ResourceBundle{
    private String[][] ini = {
        {"connection_url", "jdbc:javadb:mem:foo"}
    };
    private Map<String,String> cache = new HashMap<>(ini.length);
    {
      for (String[] pair : ini){
        cache.put(pair[0],pair[1]);
      }
    }

    @Override
    protected Object handleGetObject(String key) {
      return cache.get(key);
    }

    @Override
    public Enumeration<String> getKeys() {
      return new Enumeration<String>() {
        int i = 0;
        @Override
        public String nextElement() {
          return ini[i++][0];
        }
        
        @Override
        public boolean hasMoreElements() {
          return i<ini.length;
        }
      };
    }
  }

  /** The Constant FIVE_MINUTES_S for a fun timeout value. */
  private static final int FIVE_MINUTES_S = 500;

  /** The test part. */
  private SimpleConnectionFactory part;

  /**
   * @throws java.lang.Exception
   */
  @BeforeClass
  public static void setUpBeforeClass() throws Exception {
  }

  /**
   * @throws java.lang.Exception
   */
  @AfterClass
  public static void tearDownAfterClass() throws Exception {
  }

  /**
   * @throws java.lang.Exception
   */
  @Before
  public void setUp() throws Exception {
    part = new SimpleConnectionFactory();
  }

  /**
   * @throws java.lang.Exception
   */
  @After
  public void tearDown() throws Exception {
  }

  /**
   * Test method for {@link net.witerat.cafenatedsql.spi.SimpleConnectionFactory
   *    #loadFromResource(java.lang.String, java.lang.String)}.
   */
  @Test
  public final void testLoadFromResource() {
    String resourceName = Bundle.class.getName();
    try {
      part=SimpleConnectionFactory.loadFromResource(resourceName);
    } catch (IOException e) {
      fail(e.getClass().getName()+": "+e.getLocalizedMessage());
    }
    assertNotNull(part);
  }

  /**
   * Test method for {@link net.witerat.cafenatedsql.spi.SimpleConnectionFactory
   *    #loadFromResourceStream(net.witerat.cafenatedsql.spi.driver.Driver,
   *     java.lang.ClassLoader, java.lang.String)
   *     SimpleConnectionFactory.loadFromResourceStream(Driver, ClassLoader, String)}.
   */
  @Test
  public final void testLoadFromResourceStream() {
    String resourceName = Bundle.class.getName();
    try {
      part=SimpleConnectionFactory.loadFromResource(resourceName);
    } catch (IOException e) {
      fail(e.getClass().getName()+": "+e.getLocalizedMessage());
    }
    assertNotNull(part);
  }

  /**
   * Test method for {@link net.witerat.cafenatedsql.spi.SimpleConnectionFactory
   *    #loadFromXML(java.io.InputStream)}.
   */
  @Test
  public final void testLoadFromXML() {
    fail("Not yet implemented"); // TODO
  }

  /**
   * Test method for {@link net.witerat.cafenatedsql.spi.SimpleConnectionFactory
   *    #loadFromXMLResourceStream(net.witerat.cafenatedsql.spi.driver.Driver,
   *            java.lang.Class)
   *    SimpleConnectionFactory#loadFromXMLResourceStream(Driver,Class)}.
   */
  @Test
  public final void testLoadFromXMLResourceStreamDriverClass() {
    Driver driver = null;
    try {
      part = loadFromXMLResourceStream(driver, Bundle.class);
    } catch (IOException e) {
      fail(e.getClass().getName()+": "+e.getLocalizedMessage());
    } 
  }

  /**
   * Test method for {@link net.witerat.cafenatedsql.spi.SimpleConnectionFactory
   *    #loadFromXMLResourceStream(net.witerat.cafenatedsql.spi.driver.Driver,
   *     java.lang.ClassLoader, java.lang.String)}.
   */
  @Test
  public final void testLoadFromXMLResourceStreamDriverClassLoaderString() {
    fail("Not yet implemented"); // TODO
  }

  /**
   * Test method for {@link net.witerat.cafenatedsql.spi.SimpleConnectionFactory
   *    #SimpleConnectionFactory()}.
   */
  @Test
  public final void testSimpleConnectionFactory() {
    assertNotNull(part);
  }

  /**
   * Test method for {@link net.witerat.cafenatedsql.spi.SimpleConnectionFactory
   *    #SimpleConnectionFactory(java.io.InputStream)}.
   */
  @Test
  public final void testSimpleConnectionFactoryInputStream() {
    fail("Not yet implemented"); // TODO
  }

  /**
   * Test method for {@link net.witerat.cafenatedsql.spi.SimpleConnectionFactory
   *    #SimpleConnectionFactory(java.io.InputStream, boolean)}.
   */
  @Test
  public final void testSimpleConnectionFactoryInputStreamBoolean() {
    fail("Not yet implemented"); // TODO
  }

  /**
   * Test method for {@link net.witerat.cafenatedsql.spi.SimpleConnectionFactory
   *    #SimpleConnectionFactory(java.util.Properties)}.
   */
  @Test
  public final void testSimpleConnectionFactoryProperties() {
    fail("Not yet implemented"); // TODO
  }

  /**
   * Test method for {@link net.witerat.cafenatedsql.spi.SimpleConnectionFactory
   *    #connect()}.
   */
  @Test
  public final void testConnect() {
    Connection c = part.connect();
    assertNotNull("Connection failure, no connection", c);
  }

  /**
   * Test method for {@link net.witerat.cafenatedsql.spi.SimpleConnectionFactory
   *    #connect0()}.
   */
  @Test
  public final void testConnect0() {
    try {
      Connection c = part.connect0();
      assertNotNull(c);
    } catch (SQLException e) {
      StringWriter sw = new StringWriter();
      PrintWriter pw = new PrintWriter(sw);
      e.printStackTrace(pw);
      assertNull("Connection failure, " + e.getClass().getName()
        + ": " + e.getLocalizedMessage()
        + sw.toString(), e);
    }
  }

  /**
   * Test method for {@link net.witerat.cafenatedsql.spi.SimpleConnectionFactory
   *    #getConnection()}.
   */
  @Test
  public final void testGetConnection() {
    try {
      Connection c = part.getConnection();
      assertNotNull("Connection failure, no connection", c);
    } catch (SQLException e) {
      assertNull("Connection failure", e);
    }
  }

  /**
   * Test method for {@link net.witerat.cafenatedsql.spi.SimpleConnectionFactory
   *    #getConnection(java.lang.String, java.lang.String)}.
   */
  @Test
  public final void testGetConnectionStringString() {
    try {
      Connection c = part.getConnection("sa","");
      assertNotNull("Connection failure, no connection", c);
    } catch (SQLException e) {
      assertNull("Connection failure", e);
    }
  }

  /**
   * Test method for {@link net.witerat.cafenatedsql.spi.SimpleConnectionFactory
   *    #getConnectionType()}.
   */
  @Test
  public final void testGetConnectionType() {
    ConnectionType ct = part.getConnectionType();
    assertNull(ct);
  }

  /**
   * Test method for {@link net.witerat.cafenatedsql.spi.SimpleConnectionFactory
   *    #getLoginTimeout()}.
   */
  @Test
  public final void testGetLoginTimeout() {
    Exception ex = null;
    try {
      int x = part.getLoginTimeout();
      assertEquals(0, x);
    } catch (SQLException e) {
      ex = e;
    }
    assertNull(ex);
  }

  /**
   * Test method for {@link net.witerat.cafenatedsql.spi.SimpleConnectionFactory
   *    #getLogWriter()}.
   */
  @Test
  public final void testGetLogWriter() {
    SQLException ex = null;
    try {
      assertNull(part.getLogWriter());
    } catch (SQLException e) {
      ex = e;
    }
    assertNull(ex);
  }

  /**
   * Test method for {@link net.witerat.cafenatedsql.spi.SimpleConnectionFactory
   *    #getParentLogger()}.
   */
  @Test
  public final void testGetParentLogger() {
    SQLException ex = null;
    try {
      Logger l = part.getParentLogger();
      assertNull(l);
    } catch (SQLFeatureNotSupportedException e) {
      ex = e;
    }
    assertNull(ex);
  }

  /**
   * Test method for {@link net.witerat.cafenatedsql.spi.SimpleConnectionFactory
   *    #getProperty(java.lang.Object)}.
   */
  @Test
  public final void testGetPropertyObject() {
    Object foo = "foo";
    Object bar = "bar";
    part.setProperty(foo, bar);
    Object baz = part.getProperty(foo);
    assertEquals(bar, baz);
  }

  /**
   * Test method for {@link net.witerat.cafenatedsql.spi.SimpleConnectionFactory
   *    #getProperty(java.lang.String)}.
   */
  @Test
  public final void testGetPropertyString() {
    String foo = "foo";
    Object bar = "bar";
    part.setProperty(foo, bar);
    Object baz = part.getProperty(foo);
    assertEquals(bar, baz);
  }

  /**
   * Test method for {@link net.witerat.cafenatedsql.spi.SimpleConnectionFactory
   *    #getPropertyType(java.lang.Object)}.
   */
  @Test
  public final void testGetPropertyType() {
    String foo = "foo";
    Object bar = "baz";
    part.setProperty(foo, bar);
    Object baz = part.getPropertyType(foo);
    assertEquals(String.class, baz);
  }

  /**
   * Test method for {@link net.witerat.cafenatedsql.spi.SimpleConnectionFactory
   *    #isPropertyRequired(java.lang.Object)}.
   */
  @Test
  public final void testIsPropertyRequired() {
    assertFalse(part.isPropertyRequired("foo"));
  }

  /**
   * Test method for {@link net.witerat.cafenatedsql.spi.SimpleConnectionFactory
   *    #isWrapperFor(java.lang.Class)}.
   */
  @Test
  public final void testIsWrapperFor() {
    try {
      assertTrue(part.isWrapperFor(DataSource.class));
    } catch (SQLException e) {
      assertNull(e);
    }
  }

  /**
   * Test method for {@link net.witerat.cafenatedsql.spi.SimpleConnectionFactory
   *    #setConnectionType(net.witerat.cafenatedsql.api.driver.ConnectionType)}.
   */
  @Test
  public final void testSetConnectionType() {
    ConnectionType ct = new MockConnectionType();
    part.setConnectionType(ct);
  }

  /**
   * Test method for {@link net.witerat.cafenatedsql.spi.SimpleConnectionFactory
   *    #setLoginTimeout(int)}.
   */
  @Test
  public final void testSetLoginTimeout() {
    try {
      part.setLoginTimeout(FIVE_MINUTES_S);
    } catch (SQLException e) {
      assertNull(e);
    }
  }

  /**
   * Test method for {@link net.witerat.cafenatedsql.spi.SimpleConnectionFactory
   *    #setLogWriter(java.io.PrintWriter)}.
   */
  @Test
  public final void testSetLogWriter() {
    try {
      part.setLogWriter(new PrintWriter(System.out));
    } catch (SQLException e) {
      assertNull(e);
    }
  }


  /**
   * Test method for {@link net.witerat.cafenatedsql.spi.SimpleConnectionFactory
   *    #setProperty(java.lang.String, java.lang.Object)}.
   */
  @Test
  public final void testSetPropertyStringObject() {
    String foo="foo";
    Object bar = "bar";
    part.setProperty(foo, bar);
  }

  /**
   * Test method for {@link net.witerat.cafenatedsql.spi.SimpleConnectionFactory
   *    #unwrap(java.lang.Class)}.
   */
  @Test
  public final void testUnwrap() {
    try {
      DataSource ds = part.unwrap(DataSource.class);
      assertNotNull(ds);
    } catch (SQLException e) {
      assertNull(e);
    }
  }

  /**
   * Test method for {@link SimpleConnectionFactory
   *    #setPropertyRequired(String, boolean).
   */
  @Test
  public final void testSetPropertyRequiredStringBoolean() {
    part.setPropertyRequired("fiz", false);
    assertFalse(part.isPropertyRequired("baz"));
    part.setPropertyRequired("foo", true);
    assertTrue(part.isPropertyRequired("foo"));
    assertFalse(part.isPropertyRequired("bar"));
    part.setPropertyRequired("foo", false);
    assertFalse(part.isPropertyRequired("foo"));
  }
}
