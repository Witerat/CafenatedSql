/**
 * @author John Hutcheson &lt;witerat.test@gmail.com&gt;
 */
package net.witerat.cafenatedsql.spi.driver;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * @author John Hutcheson &lt;witerat.test@gmail.com&gt;
 *
 */
public class TypeMapperTest extends TypeMapper {

  /**
   * @throws java.lang.Exception
   */
  @Before
  public void setUp() throws Exception {
  }

  /**
   * @throws java.lang.Exception
   */
  @After
  public void tearDown() throws Exception {
  }

  /**
   * Test method for {@link net.witerat.cafenatedsql.spi.driver.TypeMapper#getType(java.lang.String)}.
   */
  @Test
  public final void testGetType() {
    TypeMapper tm = TypeMapper.getDefault();
    assertEquals(Integer.class, tm.getType("int"));
  }

  /**
   * Test method for {@link net.witerat.cafenatedsql.spi.driver.TypeMapper#getDefault()}.
   */
  @Test
  public final void testGetDefault() {
    assertNotNull(TypeMapper.getDefault()); 
  }

  /**
   * Test method for {@link net.witerat.cafenatedsql.spi.driver.TypeMapper#registerType(
   *    java.lang.String, java.lang.Class)}.
   */
  @Test
  public final void testRegisterType() {
    {
    TypeMapper tm = new TypeMapper();
    try {
      tm.registerType("mapper", TypeMapper.class);
    } catch (IllegalAccessException e) {
      assertNull(e);
    }
    assertEquals(tm.getType("mapper"), TypeMapper.class);
    }

    {
    TypeMapper tm = new TypeMapper();
    assertEquals(tm.getType(TypeMapper.class.getName()), TypeMapper.class);
    }

    {
    TypeMapper tm = TypeMapper.getDefault();
    IllegalAccessException e1 = null;
    try {
      tm.registerType("mapper", TypeMapper.class);
    } catch (IllegalAccessException e) {
      e1=e;
    }
    assertNotNull(e1);
    }
  }

  /**
   * Test method for {@link net.witerat.cafenatedsql.spi.driver.TypeMapper#removeType(
   *    java.lang.String)}.
   */
  @Test
  public final void testRemoveType() {
    {
    TypeMapper tm = new TypeMapper();
    try {
      tm.registerType("mapper", TypeMapper.class);
      tm.removeType("mapper");
      assertNull(tm.getType("mapper"));
    } catch (IllegalAccessException e) {
      assertNull(e);
    }
    }

    {
    TypeMapper tm =  TypeMapper.getDefault();
    IllegalAccessException e1 = null;

    try {
      tm.removeType("mapper");
      assertNull(tm.getType("mapper"));
    } catch (IllegalAccessException e) {
      e1 = e;
    }
    assertNotNull(e1);
    }
  }

}
