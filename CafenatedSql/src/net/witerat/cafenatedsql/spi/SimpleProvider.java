package net.witerat.cafenatedsql.spi;

import java.util.Properties;

import net.witerat.cafenatedsql.api.CafenatedFactory;
import net.witerat.cafenatedsql.api.ConnectionFactory;
import net.witerat.cafenatedsql.api.DatabaseFactory;
import net.witerat.cafenatedsql.api.DialectFactory;
import net.witerat.cafenatedsql.api.DriverFactory;
import net.witerat.cafenatedsql.api.GrammarFactory;
import net.witerat.cafenatedsql.api.NounFactory;
import net.witerat.cafenatedsql.api.ParameterFactory;
import net.witerat.cafenatedsql.api.Provider;
import net.witerat.cafenatedsql.api.SchemaFactory;
import net.witerat.cafenatedsql.api.TableFactory;
import net.witerat.cafenatedsql.api.TemplateModelFactory;
import net.witerat.cafenatedsql.api.URLFactory;
import net.witerat.cafenatedsql.api.VerbFactory;
import net.witerat.cafenatedsql.api.ViewFactory;
import net.witerat.cafenatedsql.api.driver.template.TemplateEngineModel;

/**
 * The Class SimpleProvider. This provider can be configured use a single object
 * to implement any or all of the factory services offers.
 */
public class SimpleProvider implements Provider {

  /** The connection factory. */
  private ConnectionFactory connectionFactory;

  /** The dialect factory. */
  private DialectFactory dialectFactory;

  /** The driver class. */
  private String driverClass;

  /** The driver factory. */
  private DriverFactory driverFactory;

  /** The grammar factory. */
  private GrammarFactory grammarFactory;

  /** The noun factory. */
  private NounFactory nounFactory;

  /** The parameter factory. */
  private ParameterFactory parameterFactory;

  /** The schema factory. */
  private SchemaFactory schemaFactory;

  /** The table factory. */
  private TableFactory tableFactory;

  /** The URL factory. */
  private URLFactory urlFactory;

  /** The verb factory. */
  private VerbFactory verbFactory;

  /** The view factory. */
  private ViewFactory viewFactory;

  /** The databaseFactory property. */
  private DatabaseFactory databaseFactory;

  /** The modelFactory property. */
  private TemplateModelFactory modelFactory;

  /**
   * Configures this provider to expose the factory services implemented by a
   * specified factory.
   *
   * @param factory0
   *          an implementor of factory some of the services published by the
   *          provider.
   */
  public void configure(final CafenatedFactory factory0) {
    if (factory0 instanceof ConnectionFactory) {
      connectionFactory = (ConnectionFactory) factory0;
    }
    if (factory0 instanceof SchemaFactory) {
      schemaFactory = (SchemaFactory) factory0;
    }
    if (factory0 instanceof TableFactory) {
      tableFactory = (TableFactory) factory0;
    }
    if (factory0 instanceof ViewFactory) {
      verbFactory = (VerbFactory) factory0;
    }
    if (factory0 instanceof DatabaseFactory) {
      databaseFactory = (DatabaseFactory) factory0;
    }
    if (factory0 instanceof DriverFactory) {
      driverFactory = (DriverFactory) factory0;
    }
    if (factory0 instanceof GrammarFactory) {
      grammarFactory = (GrammarFactory) factory0;
    }
    if (factory0 instanceof DialectFactory) {
      dialectFactory = (DialectFactory) factory0;
    }
    if (factory0 instanceof TemplateModelFactory) {
      modelFactory = new TemplateModelFactory() {
        private TemplateModelFactory target = (TemplateModelFactory) factory0;
        @Override
        public TemplateEngineModel newInstance(final Properties defaults) {
          Properties defs = defaults;
          if (null == defaults || !defaults.contains("provider")) {
            defs = new Properties(defaults);
            defs.put("provider", SimpleProvider.this);
          }
          if (null == defaults || !defaults.contains("connection_factory")) {
            if (defs == defaults) {
              defs = new Properties(defaults);
            }
            if (null != defaults && defaults.containsKey("connection_method")) {
              String method = (String) defaults.get("connection_method");
              defs.put("connection_factory", getConnectionFactory(method));
            }
          }
          return target.newInstance(defs);
        }
        @Override
        public TemplateEngineModel newInstance(
            final TemplateEngineModel model) {
          TemplateEngineModel defs = model;
          if (model == null || model.get("provider") == null) {
            defs = target.newInstance(model);
            defs.set("provider", SimpleProvider.this);
          }
          if (null == model || model.get("connection_factory") == null) {
            if (defs == model) {
              defs = target.newInstance(model);
            }
            if (null != model && model.get("connection_method") != null) {
              String method = (String) model.get("connection_method");
              defs.set("connection_factory", getConnectionFactory(method));
            }
          }
          return target.newInstance(defs);
        }
      };
    }
    if (factory0 instanceof NounFactory) {
      nounFactory = (NounFactory) factory0;
    }
    if (factory0 instanceof VerbFactory) {
      verbFactory = (VerbFactory) factory0;
    }
    if (factory0 instanceof ParameterFactory) {
      parameterFactory = (ParameterFactory) factory0;
    }
    if (factory0 instanceof URLFactory) {
      urlFactory = (URLFactory) factory0;
    }
  }

  /*
   * (non-Javadoc)
   *
   * @see net.witerat.cafenatedsql.api.Provider#getConnectionFactory()
   */
  @Override
  public ConnectionFactory getConnectionFactory() {
    return connectionFactory;
  }

  /**
   * {@inheritDoc}
   * @see net.witerat.cafenatedsql.api.Provider#getConnectionFactory(
   *    java.lang.String)
   */
  @Override
  public ConnectionFactory getConnectionFactory(final String method0) {
    // TODO Auto-generated method stub
    return connectionFactory;
  }

  /* (non-Javadoc)
   * @see net.witerat.cafenatedsql.api.Provider#getConnectionMethods()
   */
  @Override
  public String[] getConnectionMethods() {
    // TODO Auto-generated method stub
    return null;
  }

  /* (non-Javadoc)
   * @see net.witerat.cafenatedsql.api.Provider#getDatabaseFactory()
   */
  @Override
  public DatabaseFactory getDatabaseFactory() {
    if (null == databaseFactory) {
      return new SimpleDatabaseFactory();
    }
    return databaseFactory;
  }

  /*
   * (non-Javadoc)
   *
   * @see net.witerat.cafenatedsql.api.Provider#getDialectFactory()
   */
  @Override
  public DialectFactory getDialectFactory() {

    return dialectFactory;
  }

  /*
   * (non-Javadoc)
   *
   * @see net.witerat.cafenatedsql.api.Provider#getDriverClass()
   */
  @Override
  public String getDriverClass() {
    return driverClass;
  }

  /*
   * (non-Javadoc)
   *
   * @see net.witerat.cafenatedsql.api.Provider#getDriverFactory()
   */
  @Override
  public DriverFactory getDriverFactory() {
    return driverFactory;
  }

  /*
   * (non-Javadoc)
   *
   * @see net.witerat.cafenatedsql.api.Provider#getGrammarFactory()
   */
  @Override
  public GrammarFactory getGrammarFactory() {
    return grammarFactory;
  }

  /**
   * {@inheritDoc}
   * @see net.witerat.cafenatedsql.api.Provider#getModelFactory()
   */
  @Override
  public TemplateModelFactory getModelFactory() {
    if (null == modelFactory) {
      return SimpleModelFactory.get(this);
    }
    return modelFactory;
  }

  /*
   * (non-Javadoc)
   *
   * @see net.witerat.cafenatedsql.api.Provider#getNounFactory()
   */
  @Override
  public NounFactory getNounFactory() {
    return nounFactory;
  }

  /**
   * Gets the parameter factory.
   *
   * @return the parameter factory
   */
  public ParameterFactory getParamaterFactory() {
    return parameterFactory;
  }

  /*
   * (non-Javadoc)
   *
   * @see net.witerat.cafenatedsql.api.Provider#getParameterFactory()
   */
  @Override
  public ParameterFactory getParameterFactory() {
    return parameterFactory;
  }

  /*
   * (non-Javadoc)
   *
   * @see net.witerat.cafenatedsql.api.Provider#getSchemaFactory()
   */
  @Override
  public SchemaFactory getSchemaFactory() {
    return schemaFactory;
  }

  /*
   * (non-Javadoc)
   *
   * @see net.witerat.cafenatedsql.api.Provider#getTableFactory()
   */
  @Override
  public TableFactory getTableFactory() {
    return tableFactory;
  }

  /*
   * (non-Javadoc)
   *
   * @see net.witerat.cafenatedsql.api.Provider#getUrlFactory()
   */
  @Override
  public URLFactory getUrlFactory() {
    return urlFactory;
  }

  /*
   * (non-Javadoc)
   *
   * @see net.witerat.cafenatedsql.api.Provider#getVerbFactory()
   */
  @Override
  public VerbFactory getVerbFactory() {
    return verbFactory;
  }

  /*
   * (non-Javadoc)
   *
   * @see net.witerat.cafenatedsql.api.Provider#getViewFactory()
   */
  @Override
  public ViewFactory getViewFactory() {
    return viewFactory;
  }

  /**
   * Sets the connection factory.
   *
   * @param connectionFactory0
   *          the new connection factory
   */
  public void setConnectionFactory(final ConnectionFactory connectionFactory0) {
    this.connectionFactory = connectionFactory0;
  }

  /**
   * Sets the dialect factory.
   *
   * @param dialectFactory0
   *          the new dialect factory
   */
  public void setDialectFactory(final DialectFactory dialectFactory0) {
    this.dialectFactory = dialectFactory0;
  }

  /**
   * Sets the driver class.
   *
   * @param driverClass0
   *          the new driver class
   */
  public void setDriverClass(final String driverClass0) {
    this.driverClass = driverClass0;
  }

  /**
   * Sets the driver factory.
   *
   * @param driverFactory0
   *          the new driver factory
   */
  public void setDriverFactory(final DriverFactory driverFactory0) {
    this.driverFactory = driverFactory0;
  }

  /**
   * Sets the grammar factory.
   *
   * @param grammarFactory0
   *          the new grammar factory
   */
  public void setGrammarFactory(final GrammarFactory grammarFactory0) {
    this.grammarFactory = grammarFactory0;
  }

  /**
   * Sets the noun factory.
   *
   * @param nounFactory0
   *          the new noun factory
   */
  public void setNounFactory(final NounFactory nounFactory0) {
    this.nounFactory = nounFactory0;
  }

  /**
   * Sets the parameter factory.
   *
   * @param paramaterFactory0
   *          the new parameter factory
   */
  public void setParamaterFactory(final ParameterFactory paramaterFactory0) {
    this.parameterFactory = paramaterFactory0;
  }

  /**
   * Sets the schema factory.
   *
   * @param schemaFactory0
   *          the new schema factory
   */
  public void setSchemaFactory(final SchemaFactory schemaFactory0) {
    this.schemaFactory = schemaFactory0;
  }

  /**
   * Sets the table factory.
   *
   * @param tableFactory0
   *          the new table factory
   */
  public void setTableFactory(final TableFactory tableFactory0) {
    this.tableFactory = tableFactory0;
  }

  /**
   * Sets the url factory.
   *
   * @param urlFactory0
   *          the new url factory
   */
  public void setUrlFactory(final URLFactory urlFactory0) {
    this.urlFactory = urlFactory0;
  }

  /**
   * Sets the verb factory.
   *
   * @param verbFactory0
   *          the new verb factory
   */
  public void setVerbFactory(final VerbFactory verbFactory0) {
    this.verbFactory = verbFactory0;
  }

  /**
   * Sets the view factory.
   *
   * @param viewFactory0
   *          the new view factory
   */
  public void setViewFactory(final ViewFactory viewFactory0) {
    this.viewFactory = viewFactory0;
  }

}
