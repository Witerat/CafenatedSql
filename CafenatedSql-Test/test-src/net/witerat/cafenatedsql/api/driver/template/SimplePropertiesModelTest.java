package net.witerat.cafenatedsql.api.driver.template;

import static org.junit.Assert.*;

import java.util.Properties;

import org.junit.Before;
import org.junit.Test;

public class SimplePropertiesModelTest  {

  SimplePropertiesModel part ;
  
  @Before
  public void setup(){
    part = new SimplePropertiesModel();
    part.set("aString",  "hello");
  }
  
  @Test
  public final void testSimplePropertiesModelProperties() {
    Properties p = new Properties();
    SimplePropertiesModel spm = new SimplePropertiesModel(p);
    assertNotNull(spm);
  }

  @Test
  public final void testSimplePropertiesModel() {
    SimplePropertiesModel spm = new SimplePropertiesModel();
    assertNotNull(spm);
  }

  @Test
  public final void testGetByExpression() {
    String v = (String) part.getByExpression("aString");
    assertEquals("hello", v);
  }

  @Test
  public final void testGet() {
    String v = (String) part.get("aString");
    assertEquals("hello", v);
  }

  @Test
  public final void testSet() {
    part.set("aString", "bye");
    String v = (String) part.get("aString");
    assertEquals("bye", v);
    
  }

  @Test
  public final void testEvaluateContextExpression() {
    String v = null;
    
    ExpressionFailedException ex=null;
    try {
      v = (String) part.evaluateContextExpression("aString");
    } catch (ExpressionFailedException e) {
       ex = e;
    }
    assertNull(ex);
    assertEquals("hello", v);
  }

  @Test
  public final void testGetExpressionLanguage() {
    part.getExpressionLanguage();
  }

  @Test
  public final void testGetProperties() {
    Properties p = part.getProperties();
    assertNotNull(p);
  }

  @Test
  public final void testGetPropertyType() {
    Class<?> t1 = part.getPropertyType("aString");
    assertSame(String.class, t1);
  }

}
