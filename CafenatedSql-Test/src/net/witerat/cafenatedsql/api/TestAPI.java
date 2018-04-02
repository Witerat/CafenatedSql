package net.witerat.cafenatedsql.api;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import net.witerat.cafenatedsql.api.driver.DriverCreationException;
import net.witerat.cafenatedsql.api.driver.template.TemplateEngineModel;
import net.witerat.cafenatedsql.spi.mock.MockConnectionFactory;
import net.witerat.cafenatedsql.spi.mock.MockProvider;

import java.sql.Connection;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Properties;

public class TestAPI {
  @SuppressWarnings({ "unchecked", "serial", "rawtypes" })
  Map<String, Object> mockConProp = new LinkedHashMap() {
    {
      put("connection_method", "mock");
    }
  };

  @Test
  public void testConnect() {
    ProviderRegistrar reg = ProviderRegistrar.ROOT_REGISTRAR;
    Provider prov = reg.getProvider("mock");
    ConnectionFactory cf = prov.getConnectionFactory("mock");
    Connection c = cf.connect();
    assertNotNull(c);
  }

  public void testDatabase() {
    ProviderRegistrar reg = ProviderRegistrar.ROOT_REGISTRAR;
    Provider provider = reg.getProvider("mock");
    ConnectionFactory cf = provider.getConnectionFactory("mock");
    Properties properties = new Properties();
    properties.putAll(mockConProp);
    TemplateEngineModel model = provider.getModelFactory().newInstance(properties);

    Database db = null;
    try {
      db = provider.getDatabaseFactory().newDatabase(model);
    } catch (DriverCreationException e) {
      // TODO Auto-generated catch block
      fail("Exception " + e.getLocalizedMessage());
      throw new RuntimeException(e);
    }
    Schema sch = provider.getSchemaFactory().createSchema();
    sch.setName("mock");
    db.add(sch);

  }

  /**
   * 
   */
  @Before
  public void beforeTest() {
    ProviderRegistrar reg = ProviderRegistrar.ROOT_REGISTRAR;
    MockProvider mockProv = new MockProvider();
    ConnectionFactory mcf = new MockConnectionFactory(mockConProp);
    mockProv.setConnectionFactory(mcf);

    reg.registerProvider((Provider) mockProv, "mock", "(c) 1967", "Buffoon DB", "test model db",
        "Stupidity Inc.");

  }
}
