package net.witerat.cafenatedsql.spi;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import net.witerat.cafenatedsql.api.Cafenated;
import net.witerat.cafenatedsql.api.ConnectionFactory;
import net.witerat.cafenatedsql.api.Database;
import net.witerat.cafenatedsql.api.DriverFactory;
import net.witerat.cafenatedsql.api.EntityManager;
import net.witerat.cafenatedsql.api.Provider;
import net.witerat.cafenatedsql.api.Refactor;
import net.witerat.cafenatedsql.api.Schema;
import net.witerat.cafenatedsql.api.driver.DDLEditor;
import net.witerat.cafenatedsql.api.driver.DriverCreationException;
import net.witerat.cafenatedsql.api.driver.template.TemplateEngineModel;
import net.witerat.cafenatedsql.spi.driver.Driver;

/**
 * Base implementation of database system to be refactored.
 *
 * @author John Hutcheson &lt;witerat.test@gmail.com&gt;
 *
 */
public class SimpleDatabase implements Database, SchemaManager {

  /**
   * The connectionFactory property.
   */
  private ConnectionFactory connectionFactory;
  /**
   * The connection property.
   */
  private Connection connection;
  /**
   * The driver property.
   */
  private Driver driver;
  /**
   * The provider property.
   */
  private Provider provider;
  /**
   * The refactors property.
   */
  private List<Refactor> refactors;
  /**
   * The schemas property.
   */
  private SchemaManager schemas = new SchemaManager() {

    @Override
    public Schema getSchema(final String name) {
      Schema schema = provider.getSchemaFactory().getSchema(name);
      schema.setJournal(refactors);
      return schema;
    }

    @Override
    public Schema getSchema() {
      Schema newSchema = provider.getSchemaFactory().createSchema();
      newSchema.setJournal(refactors);
      return newSchema;
    }

    @Override
    public void add(final Schema schema) {
      refactor(NOUN, Calculus.CREATE, schema);

    }

    @Override
    public void drop(final Schema schema) {
      refactor(NOUN, Calculus.DROP, schema);

    }

    /**
     * @param noun
     * @param operation
     * @param obj
     */
    @Override
    public void refactor(final String noun,
        final EntityManager.Calculus operation,
        final Object obj) {
      DDLEditor editor  = driver.getEditor(noun, operation);
      editor.marshal(obj);
    }
  };

  /**
   * Instantiate a(n) SimpleDatabase object.
   */
  private SimpleDatabase() {
  }

  /**
   * Instantiate a(n) SimpleDatabase object.
   *
   * @param providerName
   *          name of a registered provider
   * @param method
   *          name of the connection method.
   * @param properties
   *          model and connection properties
   * @throws DriverCreationException
   *           if provider fails to provide a driver.
   */
  public SimpleDatabase(final String providerName, final String method,
      final TemplateEngineModel properties)
      throws DriverCreationException {
    this();
    String pn = providerName;
    Provider p = null;
    if (null != pn) {
      pn = (String) properties.get(Cafenated.PROVIDER_NAME);
      p = Cafenated.getInstance().getProvider(pn);
    }
    if (null == p) {
      String dn = (String) properties.get(Cafenated.DRIVER_NAME);
      p = Cafenated.getInstance().getProvider(dn);
    }
    this.provider = p;
    init0(provider, provider.getConnectionFactory(method), properties);
  }

  /**
   * Instantiate a(n) SimpleDatabase object.
   *
   * @param provider0
   *          name of a register provider
   * @param method
   *          name of the connection method
   * @param properties
   *          model and connection properties.
   * @throws DriverCreationException
   *           if provider fails to provide a driver.
   */
  public SimpleDatabase(final Provider provider0, final String method,
      final TemplateEngineModel properties)
      throws DriverCreationException {

    this.provider = provider0;
    init0(provider0, provider0.getConnectionFactory(method), properties);
  }

  /**
   * Instantiate a(n) SimpleDatabase object.
   *
   * @param p
   *          A DBMS provider
   * @param cf
   *          a connection factory
   * @param model
   *          model and connection properties
   * @throws DriverCreationException
   *           if provider fails to provide a driver.
   */

  public SimpleDatabase(final Provider p, final ConnectionFactory cf,
      final TemplateEngineModel model)
      throws DriverCreationException {
    init0(p, cf, model);
  }

  /**
   * Common initialisation.
   *
   * @param provider0
   *          a provider
   * @param properties
   *          the model and connection properties
   * @param connectionFactory0
   *          the connection factory.
   * @throws DriverCreationException
   *           if provider fails to provide a driver.
   */

  private void init0(final Provider provider0,
      final ConnectionFactory connectionFactory0,
      final TemplateEngineModel properties) throws DriverCreationException {
    TemplateEngineModel tem =
        provider0.getModelFactory().newInstance(properties);
    DriverFactory df = this.provider.getDriverFactory();
    driver = df.newDriver(tem);
    this.connectionFactory = connectionFactory0;
  }

  /**
   * {@inheritDoc}
   *
   * @see net.witerat.cafenatedsql.spi.SchemaManager#
   *    add(net.witerat.cafenatedsql.api.Schema)
   */
  @Override
  public void add(final Schema schema) {
    schemas.add(schema);
  }

  /**
   * {@inheritDoc}
   *
   * @see net.witerat.cafenatedsql.spi.SchemaManager#getSchema()
   */
  @Override
  public Schema getSchema() {
    return schemas.getSchema();

  }

  /**
   * {@inheritDoc}
   *
   * @see net.witerat.cafenatedsql.spi.SchemaManager#getSchema(java.lang.String)
   */
  @Override
  public Schema getSchema(final String name) {
    return schemas.getSchema(name);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void drop(final Schema schema) throws SQLException {
    schemas.drop(schema);
  }

  /**
   * {@inheritDoc}
   *
   * @see net.witerat.cafenatedsql.api.Database#connect()
   */
  @Override
  public void connect() {
    connection = connectionFactory.connect();
  }

  /**
   * {@inheritDoc}
   *
   * @see net.witerat.cafenatedsql.api.Database#close()
   */
  @Override
  public void close() throws SQLException {
    connection.close();
    connection = null;

  }

  /**
   * {@inheritDoc}
   * @see net.witerat.cafenatedsql.api.Database#update()
   */
  @Override
  public void update() throws SQLException {
    for (Refactor refactor : refactors) {
      submitRefactor(refactor);
      connection.commit();
    }

  }

  /**
   * Ask the underlying dbms to implement the change.
   * @param refactor0 then change to apply.
   */
  private void submitRefactor(final Refactor refactor0) {
    refactor(refactor0.getNoun(), refactor0.getOperation(),
        refactor0.getObject());

  }

  /**
   * {@inheritDoc}
   *
   * @see net.witerat.cafenatedsql.api.EntityManager#
   *    refactor(
   *            java.lang.String,
   *            EntityManager.Calculus,
   *            java.lang.Object)
   */
  @Override
  public void refactor(final String noun,
      final EntityManager.Calculus operation,
      final Object obj) {
    DDLEditor editor = driver.getEditor(noun, operation);
    editor.marshal(obj);
  }
}
