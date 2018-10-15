package net.witerat.cafenatedsql.spi.mock;

import java.util.LinkedHashMap;
import java.util.Map;

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

public class MockProvider implements Provider {

  private Map<String, Object> properties;
  private String[] connectionMethods = { "mock" };
  private String mockDriverClass = "MockDriver";
  private DatabaseFactory databaseFactory;

  @Override
  public ConnectionFactory getConnectionFactory() {
    return getConnectionFactory(connectionMethods[0]);
  }

  @Override
  public ConnectionFactory getConnectionFactory(String method) {
    if (connectionFactories.containsKey(method)) {
      return connectionFactories.get(method);
    }
    MockConnectionFactory mcf = new MockConnectionFactory(properties);
    mcf.properties = properties;
    return mcf;
  }

  @Override
  public String[] getConnectionMethods() {
    return connectionMethods;
  }

  @Override
  public DialectFactory getDialectFactory() {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public String getDriverClass() {
    // TODO Auto-generated method stub
    return mockDriverClass;
  }

  @Override
  public DatabaseFactory getDatabaseFactory() {
    // TODO Auto-generated method stub
    return databaseFactory;
  }

  @Override
  public GrammarFactory getGrammarFactory() {
    // TODO Auto-generated method stub
    return (GrammarFactory) getDriverFactory();
  }

  @Override
  public NounFactory getNounFactory() {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public ParameterFactory getParameterFactory() {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public SchemaFactory getSchemaFactory() {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public TableFactory getTableFactory() {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public URLFactory getUrlFactory() {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public VerbFactory getVerbFactory() {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public ViewFactory getViewFactory() {
    // TODO Auto-generated method stub
    return null;
  }

  Map<String, ConnectionFactory> connectionFactories = new LinkedHashMap<>();

  public void setConnectionFactory(ConnectionFactory mcf) {
    connectionFactories.put((String) mcf.getProperty("connection_method"), mcf);
  }

  public void setTemplateModelFactory(TemplateModelFactory templateModelFactory) {
  }

  public TemplateModelFactory getTemplateModelFactory() {
    return null;
  }

  public void setViewFactory(ViewFactory viewFactory) {
  }

  public void setVerbFactory(VerbFactory verbFactory) {
  }

  public void setUrlFactory(URLFactory urlFactory) {
  }

  public void setTableFactory(TableFactory tableFactory) {
  }

  public void setSchemaFactory(SchemaFactory schemaFactory) {
  }

  public void setParamaterFactory(ParameterFactory paramaterFactory) {
  }

  public void setNounFactory(NounFactory nounFactory) {
  }

  public void setGrammarFactory(GrammarFactory grammarFactory) {
  }

  public void setDriverFactory(DatabaseFactory driverFactory) {
  }

  public void setDriverClass(String driverClass) {
  }

  public void setDialectFactory(DialectFactory dialectFactory) {
  }

  @Override
  public DriverFactory getDriverFactory() {
    // TODO Auto-generated method stub
    return new MockDriverFactory();
  }

  @Override
  public TemplateModelFactory getModelFactory() {
    // TODO Auto-generated method stub
    return new MockModelFactory();
  }

}
