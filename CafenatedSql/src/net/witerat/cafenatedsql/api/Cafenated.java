package net.witerat.cafenatedsql.api;

import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.naming.InitialContext;
import javax.naming.NamingException;

import net.witerat.cafenatedsql.api.driver.DriverCreationException;
import net.witerat.cafenatedsql.api.driver.template.TemplateEngineModel;


/**
 * @author John Hutcheson &lt;witerat.test@gmail.com&gt;
 *
 */
public abstract class Cafenated {
  /**
   * Model property name for registered provider name.
   */
  public static final String PROVIDER_NAME = "provider_name";
  /**
   * Model property name for provider.
   */
  public static final String PROVIDER = "provider";
  /**
   * Model property name for connection method name.
   */
  public static final String CONNECTION_METHOD = "connection_method";
  /**
   * Model property name for connection.
   */
  public static final String CONNECTION = "connection";
  /**
   * Model property name for registered provider name.
   */
  public static final String DATABASE_NAME = "database_name";
  /**
   * Model property name for database.
   */
  public static final String DATABASE = "database";
  /**
   * The DRIVER_NAME property.
   */
  public static final String DRIVER_NAME = "driver_name";

  /**
   * The logger property.
   */
  private static Logger logger =
      Logger.getLogger(Cafenated.class.getName());
  /**
   * The DEFAULT_RESOURCE property.
   */
  private static final String DEFAULT_RESOURCE =
      "/META-INF/cafenatedSql/schema";
  /**
   * The root property.
   */
  private static ProviderRegistrar root;
  /**
   * The jndiName property.
   */
  private static String jndiName;
  /**
   * The resourcePath property.
   */
  private static String resourcePath;

  /**
   * Get the singleton root registrar.
   * @return the root registrar.
   */
  public static ProviderRegistrar getInstance() {
    if (root == null) {
      init();
    }
    if (root == null) {
      throw new IllegalStateException("Initialisation failed");
    }
    return root;
  }
  /**
   * bind the root registrar to a JNDI name.
   * @param jndiName0 The jndi to bind with which the root registrar.
   */
  public void regsisterJNDI(final String jndiName0) {

  }

  /**
   *
   */
  static void init() {
    if (root != null) {
      throw new IllegalStateException("Already configured");
    }
    if (jndiName != null) {
      initJndi(jndiName);
    } else if (resourcePath != null) {
      initByResource(resourcePath);
    } else {
      initByResource(DEFAULT_RESOURCE);
    }
    try {
      root = (ProviderRegistrar) Class.forName(
          "net.witerat.cafenatedsql.spi.RootProviderRegistrar")
        .newInstance();
    } catch (InstantiationException | IllegalAccessException
        | ClassNotFoundException e) {
      logger.log(Level.SEVERE, "Initialisation fail.", e);
    }
  }

  /**
   * Initialise from JINDI binding.
   * @param jndiName0 the binding name.
   */
  private static void initJndi(final String jndiName0) {
    try {
      root = InitialContext.doLookup(jndiName0);
    } catch (NamingException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }

  }

  /**
   * @param resourcePath0 a resource to find a root configuration.
   */
  private static void initByResource(final String resourcePath0) {
    // TODO Auto-generated method stub

  }

  /**
   * Configure from JNDI binding name.
   * @param jndiName0 the binding name from which to get configuration
   * information.
   */
  static void configureJNDI(final String jndiName0) {
    jndiName = jndiName0;
  }

  /**
   * Gets a new unconnected database using values of the specified model and
   * provider.
   * @param dbpName provider name this overrides the model's provider name.
   * @param model model of connection and other properties
   * @return A new database object.
   * @throws DriverCreationException Driver creation fail.
   */
  public Database getDatabase(final String dbpName,
      final TemplateEngineModel model) throws DriverCreationException {
    Provider p = root.getProvider(dbpName);
    Database db = p.getDatabaseFactory().newDatabase(model);
    return db;
  }

  /**
   * Gets new unconnected database using properties in the specified model.
   * @param model model of connection and other properties.
   * @return A new database object.
   * @throws DriverCreationException Drive creation fail.
   */
  public Database getDatabase(final TemplateEngineModel model)
      throws DriverCreationException {
    Provider p = root.getProvider((String) model.get(PROVIDER_NAME));
    Database db = p.getDatabaseFactory().newDatabase(model);
    return db;
  }
  /**
   * Gets new unconnected database using properties in the specified model.
   * @param props model of connection and other properties.
   * @return A new database object.
   * @throws DriverCreationException Drive creation fail.
   */
  public static Database getDatabase(final Properties props)
      throws DriverCreationException {
    String pn = props.getProperty(PROVIDER_NAME);
    Provider p = root.getProvider(pn);
    TemplateModelFactory tmf = p.getModelFactory();
    TemplateEngineModel model = tmf.newInstance(props);
    DatabaseFactory dbf = p.getDatabaseFactory();
    Database db = dbf.newDatabase(model);
    return db;
  }
}
