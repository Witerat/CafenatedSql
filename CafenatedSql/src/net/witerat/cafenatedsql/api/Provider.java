package net.witerat.cafenatedsql.api;


/**
 * The Interface Provider.
 */
public interface Provider {

  /**
   * Gets the connection factory.
   *
   * @return the connection factory
   */
  ConnectionFactory getConnectionFactory();

  /**
   * Get a connection factory suitable the specified method.
   * @param method the method of connection
   * @return a factory for connecting by the specified method
   */
  ConnectionFactory getConnectionFactory(String method);

  /**
   * Gets the available connection methods.
   * @return an array of connection method names
   */
  String[] getConnectionMethods();

  /**
   * Get a database factory the configures database access for this provider.
   * @return a database factory
   */
  DatabaseFactory getDatabaseFactory();

  /**
   * Gets the dialect factory.
   *
   * @return the dialect factory
   */
  DialectFactory getDialectFactory();

  /**
   * Gets the driver class.
   *
   * @return the driver class
   */
  String getDriverClass();

  /**
   * Gets the driver factory.
   *
   * @return the driver factory
   */
  DriverFactory getDriverFactory();

  /**
   * Gets the grammar factory.
   *
   * @return the grammar factory
   */
  GrammarFactory getGrammarFactory();

  /**
   * This property refers to a model factory creates models for use with
   * the underlying templat engine.
   * @return A model for passing parameter information to DBMS.
   */
  TemplateModelFactory getModelFactory();

  /**
   * Gets the noun factory.
   *
   * @return the noun factory
   */
  NounFactory getNounFactory();

  /**
   * Gets the parameter factory.
   *
   * @return the parameter factory
   */
  ParameterFactory getParameterFactory();

  /**
   * Gets the schema factory.
   *
   * @return the schema factory
   */
  SchemaFactory getSchemaFactory();

  /**
   * Gets the table factory.
   *
   * @return the table factory
   */
  TableFactory getTableFactory();

  /**
   * Gets the url factory.
   *
   * @return the url factory
   */
  URLFactory getUrlFactory();

  /**
   * Gets the verb factory.
   *
   * @return the verb factory
   */
  VerbFactory getVerbFactory();

  /**
   * Gets the view factory.
   *
   * @return the view factory
   */
  ViewFactory getViewFactory();
}
