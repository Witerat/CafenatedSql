/**
 * @author John Hutcheson &lt;witerat.test@gmail.com&gt;
 */
package net.witerat.cafenatedsql.api.driver.template;

import static org.junit.Assert.*;

import java.util.LinkedHashMap;
import java.util.Map;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * The SimpleMapModelTest class.
 *
 * @author John Hutcheson &lt;witerat.test@gmail.com&gt;
 *
 */
public class SimpleMapModelTest {
  SimpleMapModel part = new SimpleMapModel();

  /**
   * Set up before class.
   *
   * @throws Exception
   *           the exception
   */
  @BeforeClass
  public static void setUpBeforeClass() throws Exception {
  }

  /**
   * Tear down after class.
   *
   * @throws Exception
   *           the exception
   */
  @AfterClass
  public static void tearDownAfterClass() throws Exception {
  }

  /**
   * Set up.
   *
   * @throws Exception
   *           the exception
   */
  @Before
  public void setUp() throws Exception {
    part.set("aString", "hello");
  }

  /**
   * Tear down.
   *
   * @throws Exception
   *           the exception
   */
  @After
  public void tearDown() throws Exception {
  }

  /**
   * Test method for {@link net.witerat.cafenatedsql.api.driver.template.SimpleMapModel
   *    #SimpleMapModel(java.util.Map)}.
   */
  @Test
  public final void testSimpleMapModelMapOfStringObject() {
    SimpleMapModel smm0 = new SimpleMapModel(null);
    assertNotNull(smm0);

    Map<String, Object> model = new LinkedHashMap<String, Object>();
    model.put("aObject", new Object());
    model.put("bObject", new Object());

    SimpleMapModel smm = new SimpleMapModel(model);
    assertSame("aObject wrong", model.get("aObject"), smm.get("aObject"));
    assertSame("bObject wrong", model.get("bObject"), smm.get("bObject"));

  }
  

  /**
   * Test method for {@link net.witerat.cafenatedsql.api.driver.template.SimpleMapModel
   *    #getByExpression(java.lang.String)}.
   */
  @Test
  public final void testGetByExpression() {
    Object v = part.getByExpression("absentKey");
    assertNull("\"absentKey\" should evaluate to null", v); 

    Object v1 = part.getByExpression("aString");
    assertTrue("\"aString\" should evaluate to a String",v1 instanceof String);
      
  }

  /**
   * Test method for {@link net.witerat.cafenatedsql.api.driver.template.SimpleMapModel
   *    #get(java.lang.String)}.
   */
  @Test
  public final void testGet() {
    Object v = part.get("absentKey");
    assertNull("\"absentKey\" should evaluate to null", v); 

    Object v1 = part.get("aString");
    assertTrue("\"aString\" should evaluate to a String",v1 instanceof String);
      
  }

  /**
   * Test method for {@link net.witerat.cafenatedsql.api.driver.template.SimpleMapModel
   *    #set(java.lang.String, java.lang.Object)}.
   */
  @Test
  public final void testSet() {
    Object v = new Object();
    part.set("aObject", v);
    assertSame(v, part.get("aObject"));
  }

  /**
   * Test method for {@link net.witerat.cafenatedsql.api.driver.template.SimpleMapModel
   *    #evaluateContextExpression(java.lang.String)}.
   */
  @Test
  public final void testEvaluateContextExpression() {
    Exception ex = null;
    try {
      Object v = part.evaluateContextExpression("absentKey");
      assertNull("\"absentKey\" should evaluate to null", v); 
    } catch (Exception e) {
      ex=e;
    }
    assertNotNull("epression evaluated with missing property",ex);
    
    Object v1=null;
    try {
      v1 = part.evaluateContextExpression("aString");
    } catch (ExpressionFailedException e) {
      fail("Evaluation failed simple property nomination.");
    }
    assertTrue("\"aString\" should evaluate to a String",v1 instanceof String);
  }

  /**
   * Test method for {@link net.witerat.cafenatedsql.api.driver.template.SimpleMapModel
   *    #getPropertyType(java.lang.Object)}.
   */
  @Test
  public final void testGetPropertyType() {
    Class<?> t1 = part.getPropertyType("aString");
    assertTrue("\"aString\" should be a String", t1 == String.class);
    
    part.set("null", null);
    Class<?> t2 = part.getPropertyType("null");
    assertTrue("\"null\" should be null type", t2 == null);
    
    Class<?> t3 = part.getPropertyType("void");
    assertTrue("missing property should be null type", t3 == null);

  }

}
