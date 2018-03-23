package net.witerat.cafenatedsql.api;

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
  static ProviderRegistrar getInstance() {
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
   * @param dbpName provider Name
   * @param model model of connection and other properties
   * @return A new database object.
   * @throws DriverCreationException Drive creation fail.
   */
  public Database getDatabase(final String dbpName,
      final TemplateEngineModel model) throws DriverCreationException {
    Provider p = root.getProvider(dbpName);
    Database db = p.getDatabaseFactory().newDatabase(model);
    return db;
  }
}
