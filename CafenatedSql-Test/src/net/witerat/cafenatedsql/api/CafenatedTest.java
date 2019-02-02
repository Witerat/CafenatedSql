package net.witerat.cafenatedsql.api;

import static org.junit.Assert.*;

import java.util.LinkedHashMap;
import java.util.Properties;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import net.witerat.cafenatedsql.api.driver.DriverCreationException;
import net.witerat.cafenatedsql.api.driver.template.TemplateEngineModel;
import net.witerat.cafenatedsql.api.mock.MockConnectionFactory;
import net.witerat.cafenatedsql.api.mock.MockDatabaseFactory;
import net.witerat.cafenatedsql.api.mock.MockDriverFactory;
import net.witerat.cafenatedsql.spi.SimpleProvider;

@SuppressWarnings("unused")
public class CafenatedTest {

  @BeforeClass
  public static void setUp() throws Exception {
    Cafenated.init();
    SimpleProvider sp = new SimpleProvider();
    sp.configure(new MockDriverFactory());
    sp.configure(new MockDatabaseFactory());
    sp.configure(new MockConnectionFactory(new LinkedHashMap<String, Object>(){
      /**
       * The serialVersionUID property.
       */
      private static final long serialVersionUID = 1L;

      {
        put(Cafenated.CONNECTION_METHOD, "simple");
      }
    }));
    Cafenated.getInstance().registerProvider(sp, "simple", "(C) 2018", 
        "StupidDB", "mock provider", "Buffoon Inc.");
  }

  @Test
  public void testGetInstance() {
    assertTrue("No registrar", Cafenated.getInstance() instanceof ProviderRegistrar); 
  }

  @Test
  public void testGetDatabaseStringTemplateEngineModel() {
    @SuppressWarnings("serial")
    Properties props=new Properties() {
      {
        put(Cafenated.CONNECTION_METHOD, "simple");
      }
    };
    ProviderRegistrar pr = Cafenated.getInstance();
    Provider sp = pr.getProvider("simple");
    TemplateModelFactory tmf = sp.getModelFactory();
    TemplateEngineModel tem = tmf.newInstance(props);
    Database db = null;
    try {
      db = Cafenated.getDatabase("simple", tem);
    } catch (DriverCreationException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
    assertNotNull(db);

  }

  @Test
  public void testGetDatabaseTemplateEngineModel() {
    @SuppressWarnings("serial")
    Properties props=new Properties() {
      {
        put(Cafenated.PROVIDER_NAME, "simple");
        put(Cafenated.CONNECTION_METHOD, "simple");
      }
    };
    ProviderRegistrar pr = Cafenated.getInstance();
    Provider sp = pr.getProvider("simple");
    TemplateModelFactory tmf = sp.getModelFactory();
    TemplateEngineModel tem = tmf.newInstance(props);
    Database db = null;
    try {
      db = Cafenated.getDatabase(tem);
    } catch (DriverCreationException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
    assertNotNull(db);


  }
  @Test
  public void testGetDatabaseProperties() {
    @SuppressWarnings("serial")
    Properties props=new Properties() {
      {
        put(Cafenated.PROVIDER_NAME, "simple");
        put(Cafenated.CONNECTION_METHOD, "simple");
      }
    };
    Database db = null;
    try {
      db = Cafenated.getDatabase(props);
    } catch (DriverCreationException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
    assertNotNull(db);

  }

}
