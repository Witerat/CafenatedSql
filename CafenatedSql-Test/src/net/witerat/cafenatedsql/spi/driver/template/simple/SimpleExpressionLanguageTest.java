package net.witerat.cafenatedsql.spi.driver.template.simple;

import static org.junit.Assert.*;

import java.util.Properties;

import org.junit.Before;
import org.junit.Test;

import net.witerat.cafenatedsql.api.driver.template.ExpressionFailedException;
import net.witerat.cafenatedsql.api.driver.template.TemplateEngineModel;
import net.witerat.cafenatedsql.spi.SimpleModelFactory;
import net.witerat.cafenatedsql.spi.driver.template.simple.SimpleExpressionLanguage;

public class SimpleExpressionLanguageTest {

  SimpleExpressionLanguage fixture;
  @Before
  public void setup(){
    fixture= new SimpleExpressionLanguage();
  }
  @Test
  public void testEvaluate() {
    TemplateEngineModel model=new SimpleModelFactory().newInstance(new Properties(){
      /** The serialVersionUID property. */
      private static final long serialVersionUID = 1L;
      {
        put("x", 5);
      }
    });
    Object r=null;
    try {
      r = fixture.evaluate("3e4", model);
      assertNotNull(r);
    } catch (ExpressionFailedException e) {
      fail("Exception "+e.getClass().getName()+": "+e.getMessage());
    }
    assertEquals(Integer.valueOf(30000),r);

    r = null;
    try {
      r = fixture.evaluate("3", model);
      assertNotNull(r);
    } catch (ExpressionFailedException e) {
      fail("Exception "+e.getClass().getName()+": "+e.getMessage());
    }
    assertEquals(Integer.valueOf(3),r);
    
    r = null;
    try {
      r = fixture.evaluate("0o4", model);
      assertNotNull(r);
    } catch (ExpressionFailedException e) {
      fail("Exception "+e.getClass().getName()+": "+e.getMessage());
    }
    assertEquals(Integer.valueOf(32),r);

  }

}
