package net.witerat.cafenatedsql.api;

import java.beans.XMLDecoder;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;
import java.net.URL;
import java.util.Locale;
import java.util.MissingResourceException;
import java.util.Properties;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.xml.sax.InputSource;

import net.witerat.cafenatedsql.api.driver.DriverCreationException;
import net.witerat.cafenatedsql.api.driver.template.SimplePropertiesModel;
import net.witerat.cafenatedsql.api.driver.template.TemplateEngineModel;
import net.witerat.cafenatedsql.spi.driver.template.simple.SimpleExpressionLanguage;


/**
 * @author John Hutcheson &lt;witerat.test@gmail.com&gt;
 *
 */
public abstract class Cafenated {

  /**
   * The URL_PROTOCOL constant.
   */
  private static final String URL_PROTOCOL = "url:";

  /**
   * The CAFENATED_RESOURCE path constant.
   */
  private static final String CAFENATED_RESOURCE = "META-INF/cafenatedSql";

  /**
   * The SCHEMA_KEY constant.
   */
  static final String SCHEMA_KEY = "schema";

  /**
   * Model property name for registered provider name.
   * @see String
   */
  public static final String PROVIDER_NAME = "provider_name";

  /**
   * Model property name for provider.
   * @See {@link Provider}
   */
  public static final String PROVIDER = "provider";

  /**
   * Model property name for connection method name.
   * @see String
   */
  public static final String CONNECTION_METHOD = "connection_method";

  /**
   * Model property name for connection.
   * @see  java.sql.Connection
   */
  public static final String CONNECTION = "connection";


  /**
   * Model property name for registered provider name.
   * @see String
   */
  public static final String DATABASE_NAME = "database_name";

  /**
   * Model property name for database.
   * @see Database
   */
  public static final String DATABASE = "database";

  /**
   * The DRIVER_NAME property.
   * @see String
   */
  public static final String DRIVER_NAME = "driver_name";

  /** The DEFAULT_MODEL property.  */
  public static final TemplateEngineModel DEFAULT_MODEL
    = new SimplePropertiesModel(new Properties() {
      /** The serialVersionUID property. */
      private static final long serialVersionUID = 1L;
      {
        put(Cafenated.EXPRESSION_LANGUAGE, new SimpleExpressionLanguage());
      }
    });

  /**
   * The TEMPLATE_ENGINE property indexes the name of the
   * template engine associated with a provider or database.
   * @see net.witerat.cafenatedsql.api.driver.template.TemplateEngine
   */
  public static final String TEMPLATE_ENGINE = "template_engine";

  /**
   * The EXPRESSION_LANGUAGE property.
   * @see net.witerat.cafenatedsql.api.driver.template.ExpressionLanguage
   */
  public static final String EXPRESSION_LANGUAGE = "expression_language";

  /** The logger property. */
  private static Logger logger =
      Logger.getLogger(Cafenated.class.getName());

  /** The DEFAULT_RESOURCE property. */
  private static final String DEFAULT_RESOURCE =
      "/META-INF/cafenatedSql/schema";

  /** The root property refers to the {@link RootProviderRegistrar}. */
  private static ProviderRegistrar root;

  /** The jndiName property. */
  private static String jndiName;

  /** The resourcePath property. */
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
   * Get the singleton root registrar.
   * @param jndiName0 the name of the configuration.
   * @return the root registrar.
   */
  public static ProviderRegistrar getInstance(final String jndiName0) {
    if (root == null) {
      initJndi(jndiName0);
    }
    if (root == null) {
      throw new IllegalStateException("Initialisation failed");
    }
    return root;
  }

  /**
   * Bind the root registrar to a JNDI name.
   * @param jndiName0 The jndi to bind with which the root registrar.
   * @throws NamingException if JNDI rejects the request.
   */
  public void regsisterJNDI(final String jndiName0)
      throws NamingException {
      new InitialContext().bind(jndiName0, root);
  }

  /**
   * Initialise the registrar.
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
      try {
        initBySystemResource(DEFAULT_RESOURCE);
      } catch (MissingResourceException | NullPointerException
//        | FileNotFoundException | MalformedURLException
          | IOException e) {
        initByResource(DEFAULT_RESOURCE);
      }
    }
    if (root == null) {
      try {
        root = (ProviderRegistrar) Class.forName(
            "net.witerat.cafenatedsql.spi.RootProviderRegistrar")
          .newInstance();
      } catch (InstantiationException | IllegalAccessException
          | ClassNotFoundException e) {
        logger.log(Level.SEVERE, "Initialisation fail.", e);
      }
    }
  }

  /**
   * Initialise from JNDI binding.
   * @param jndiName0 the binding name.
   */
  private static void initJndi(final String jndiName0) {
    try {
      root = InitialContext.doLookup(jndiName0);
    } catch (NamingException e) {
      logger.log(Level.INFO, "No jndi config for " + jndiName0, e);
    }

  }

  /**
   * Load the {@link RootProviderRegistrar} configuration from a system
   * resource.
   * @param resourcePath0 a resource to find a root configuration.
   * @throws IOException
   * @throws MalformedURLException
   * @throws MissingResourceException
   */
  private static void initBySystemResource(final String resourcePath0)
      throws MissingResourceException,
//        MalformedURLException, FileNotFoundException,
        IOException {

    InputSource is = getSystemSource(resourcePath0);
    ResourceBundle rBundle = null;
    if (is == null) {
      rBundle = ResourceBundle.getBundle(
          CAFENATED_RESOURCE,
          Locale.ROOT);
      Object o = rBundle.getObject(SCHEMA_KEY);
      if (o instanceof String
          && ((String) o).startsWith(URL_PROTOCOL)) {
        o = new URL(((String) o).substring(URL_PROTOCOL.length()));
      }
      if (o instanceof URL) {
        is = new InputSource(((URL) o).openStream());
      } else if (o instanceof String) {
        is = new InputSource(new StringReader((String) o));
      }
    }
    if (is == null) {
      throw new MissingResourceException(
          "Missing system resource:",
          rBundle == null ? "<no bundle>" : rBundle.getClass().getName(),
          SCHEMA_KEY);
    }
    XMLDecoder xd = new java.beans.XMLDecoder(is);
    try {
      Object obj = xd.readObject();
      root = (ProviderRegistrar) obj;
    } catch (ArrayIndexOutOfBoundsException aioobe) {
      throw new MissingResourceException(
          "No RegistrarProvider in schema: ",
          resourcePath0, SCHEMA_KEY);
    } finally {
      xd.close();
    }
  }

  /**
   * @param resourcePath0 path neme to resource.
   * @return an {@link InputSource} for the stream at
   *   <code>resourcePath0</code>, or <tt>null</tt> if no stream there.
   */
  private static InputSource getSystemSource(
      final String resourcePath0) {
    InputStream ris =
        ClassLoader.getSystemResourceAsStream(resourcePath0);
    InputSource is = null;
    if (ris != null) {
      is = new InputSource(ris);
    }

    return is;
  }

  /**
   * Load the {@link RootProviderRegistrar} configuration from a resource.
   * @param resourcePath0 a resource to find a root configuration.
   */
  private static void initByResource(final String resourcePath0) {
    URL url = Cafenated.class.getResource(resourcePath0);
    try (InputStream is = url.openStream()) {
      try (XMLDecoder xd = new java.beans.XMLDecoder(is)) {
        Object obj = xd.readObject();
        root = (ProviderRegistrar) obj;
      }
    } catch (IOException ioe) {
      logger.log(Level.INFO, "failed to read resource", ioe);
    }
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
  public static Database getDatabase(final String dbpName,
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
  public static Database getDatabase(final TemplateEngineModel model)
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
