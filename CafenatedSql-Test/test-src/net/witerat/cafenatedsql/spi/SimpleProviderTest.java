package net.witerat.cafenatedsql.spi;

import static org.junit.Assert.*;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Properties;

import org.junit.Before;
import org.junit.Test;

import net.witerat.cafenatedsql.api.ConnectionFactory;
import net.witerat.cafenatedsql.api.Database;
import net.witerat.cafenatedsql.api.DatabaseFactory;
import net.witerat.cafenatedsql.api.Provider;
import net.witerat.cafenatedsql.api.ProviderRegistrar;
import net.witerat.cafenatedsql.api.TemplateModelFactory;
import net.witerat.cafenatedsql.api.driver.DriverCreationException;
import net.witerat.cafenatedsql.api.driver.template.TemplateEngineModel;
import net.witerat.cafenatedsql.api.mock.MockConnectionFactory;
import net.witerat.cafenatedsql.api.mock.MockDatabaseFactory;


public class SimpleProviderTest  {
  @SuppressWarnings({ "unchecked", "serial", "rawtypes" })
  Map<String, Object> mockConProp = new LinkedHashMap() {
    {
      put("connection_method", "mock");
    }
  };

  @Before
  public void setUp() throws Exception {
    ProviderRegistrar reg = ProviderRegistrar.ROOT_REGISTRAR;
    SimpleProvider simpleProv = new SimpleProvider();
    ConnectionFactory mcf = new MockConnectionFactory(mockConProp);
    simpleProv.setConnectionFactory(mcf);
    simpleProv.configure(new MockDatabaseFactory());

    reg.registerProvider((Provider) simpleProv, "simple", "(c) 1967", "Buffoon DB", "test model db",
        "Stupidity Inc.");
  }

  @Test
  public void test() {
    @SuppressWarnings("serial")
    Properties defaults = new Properties(){{
     put("connection_method", "mock");
     put("provider_name", "simple");
    }};
    ProviderRegistrar reg = ProviderRegistrar.ROOT_REGISTRAR;
    Provider prov = reg.getProvider("simple");
    TemplateModelFactory tmf = prov.getModelFactory();
    TemplateEngineModel tem = tmf.newInstance(defaults);
    Database db=null;
    DatabaseFactory df = prov.getDatabaseFactory();
    assertNotNull(df);

    try {
      db= df.newDatabase(tem);
    } catch (DriverCreationException e) {
      e.printStackTrace();
    }
    assertNotNull(db); 
  }

}
