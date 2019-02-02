package net.witerat.cafenatedsql.spi.driver;

import static org.junit.Assert.*;

import java.util.Properties;

import org.junit.Test;

import net.witerat.cafenatedsql.api.Cafenated;
import net.witerat.cafenatedsql.api.driver.ConnectionType;
import net.witerat.cafenatedsql.api.driver.template.TemplateEngineModel;
import net.witerat.cafenatedsql.api.mock.MockModelFactory;
import net.witerat.cafenatedsql.spi.SimpleGrammarDef;

public class DriverTest extends Driver {

  @Test
  public void testSetModel() {
    Driver d = new Driver();
    TemplateEngineModel tem = new MockModelFactory().newInstance((Properties) null);
    d.setModel(tem);
    assertSame(tem, d.getModel());
  }

  @Test
  public void testAddDialect() {
    Driver d = new Driver();
    Dialect md = new Dialect();
    d.addDialect(md);
    assertTrue(d.getDialects().contains(md));
  }

  @Test
  public void testGetDialects() {
    Driver d = new Driver();
    Dialect md = new Dialect();
    assertTrue(d.getDialects().isEmpty());
    d.addDialect(md);
    assertFalse(d.getDialects().isEmpty());
    assertTrue(d.getDialects().contains(md));
  }

  @Test
  public void testGetConnectionType() {
    Driver d = new Driver();
    Dialect dd = new Dialect();
    SimpleGrammarDef sgd = new SimpleGrammarDef();
    dd.addGrammar(sgd);
    @SuppressWarnings("serial")
    TemplateEngineModel tem = new MockModelFactory().newInstance(
         new Properties() {
      {
        put(Cafenated.CONNECTION_METHOD, "mock");
      }
    });
    d.setModel(tem);
    d.addDialect(dd);
    SimpleConnectionType sct = new SimpleConnectionType();
    sct.setName("mock");
    d.addConnectionType(sct);
    ConnectionType ct = d.getConnectionType(Cafenated.CONNECTION_METHOD);
    assertNotNull(ct);
  }

}
