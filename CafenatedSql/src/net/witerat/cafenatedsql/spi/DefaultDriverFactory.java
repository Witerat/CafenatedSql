package net.witerat.cafenatedsql.spi;

import net.witerat.cafenatedsql.api.DialectFactory;
import net.witerat.cafenatedsql.api.DriverFactory;
import net.witerat.cafenatedsql.api.GrammarFactory;
import net.witerat.cafenatedsql.api.NounFactory;
import net.witerat.cafenatedsql.api.ParameterFactory;
import net.witerat.cafenatedsql.api.Provider;
import net.witerat.cafenatedsql.api.URLFactory;
import net.witerat.cafenatedsql.api.VerbFactory;
import net.witerat.cafenatedsql.api.driver.ConnectionType;
import net.witerat.cafenatedsql.api.driver.DialectDef;
import net.witerat.cafenatedsql.api.driver.DriverCreationException;
import net.witerat.cafenatedsql.api.driver.GrammarDef;
import net.witerat.cafenatedsql.api.driver.NounDef;
import net.witerat.cafenatedsql.api.driver.ParameterType;
import net.witerat.cafenatedsql.api.driver.UrlDef;
import net.witerat.cafenatedsql.api.driver.VerbDef;
import net.witerat.cafenatedsql.api.driver.template.TemplateEngineModel;
import net.witerat.cafenatedsql.spi.driver.DialectSelector;
import net.witerat.cafenatedsql.spi.driver.Driver;
import net.witerat.cafenatedsql.spi.driver.DynamicDialectSelector;

/**
 * A factory for building Driver objects from definitions available
 * through providers.
 */
public class DefaultDriverFactory implements DriverFactory {

  /** The provider. */
  private final Provider provider;

  /**
   * Instantiates a new default driver factory.
   *
   * @param provider0
   *          the provider
   */
  public DefaultDriverFactory(final Provider provider0) {
    this.provider = provider0;
  }

  /**
   * {@inheritDoc}
   *
   * @see net.witerat.cafenatedsql.api.DriverFactory#
   *    newDriver(
   *            net.witerat.cafenatedsql.api.driver.template
   *                    .TemplateEngineModel)
   */
  @Override
  public Driver newDriver(final TemplateEngineModel model)
      throws DriverCreationException {
    Class<Driver> driverType;
    try {
      @SuppressWarnings("unchecked")
      Class<Driver> dt =
      (Class<Driver>) Class.forName(provider.getDriverClass());
      driverType = dt;
      return driverType.newInstance();
    } catch (ClassNotFoundException e) {
      throw new DriverCreationException(
          "Driver class " + provider.getDriverClass() + "not in classpath", e);
    } catch (InstantiationException e) {
      throw new DriverCreationException(
          "Driver failed, may be misconfigured", e);
    } catch (IllegalAccessException e) {
      throw new DriverCreationException("attempt to improperly use driver", e);
    }
  }

  /**
   * {@inheritDoc}
   *
   * @see net.witerat.cafenatedsql.api.DriverFactory#
   *    newConnectionType(
   *            net.witerat.cafenatedsql.spi.driver.Driver,
   *            java.lang.String)
   */
  @Override
  public ConnectionType newConnectionType(final Driver driver,
      final String method) {
    return driver.getConnectionType(method);
  }

  /**
   * {@inheritDoc}
   *
   * @see net.witerat.cafenatedsql.api.DriverFactory#
   *    newDialectExpressionSelector(
   *            net.witerat.cafenatedsql.api.driver.ConnectionType,
   *            java.lang.String)
   */
  @Override
  public DialectSelector newDialectExpressionSelector(
      final ConnectionType ct, final String nameExpression) {
    DynamicDialectSelector dynamicDialectSelector =
        new DynamicDialectSelector(nameExpression, true);
    return dynamicDialectSelector;
  }

  /**
   * {@inheritDoc}
   *
   * @see net.witerat.cafenatedsql.api.DriverFactory#
   *    newDialectNameSelector(
   *            net.witerat.cafenatedsql.api.driver.ConnectionType,
   *            java.lang.String)
   */
  @Override
  public DialectSelector newDialectNameSelector(final ConnectionType ct,
      final String name) {
    DynamicDialectSelector dynamicDialectSelector =
        new DynamicDialectSelector(name, false);
    return dynamicDialectSelector;
  }

  /**
   * {@inheritDoc}
   *
   * @see net.witerat.cafenatedsql.api.DriverFactory#
   *    newGrammar(
   *            net.witerat.cafenatedsql.api.driver.DialectDef,
   *            java.lang.String)
   */
  @Override
  public GrammarDef newGrammar(final DialectDef dd, final String name) {
    GrammarFactory gf = provider.getGrammarFactory();
    GrammarDef gd = gf.newGrammar(dd, name);
    gd.setName(name);
    dd.addGrammar(gd);
    return gd;
  }

  /**
   * {@inheritDoc}
   *
   * @see  net.witerat.cafenatedsql.api.DriverFactory#
   *    newVerb(
   *            net.witerat.cafenatedsql.api.driver.NounDef,
   *            java.lang.String)
   */
  @Override
  public VerbDef newVerb(final NounDef nn, final String name) {
    VerbFactory vf = provider.getVerbFactory();
    VerbDef vd = vf.newVerb(nn, name);
    vd.setName(name);
    nn.addVerb(vd);
    return vd;
  }

  /**
   * {@inheritDoc}
   *
   * @see net.witerat.cafenatedsql.api.DriverFactory#
   *    newNoun(
   *            net.witerat.cafenatedsql.api.driver.GrammarDef,
   *            java.lang.String)
   */
  @Override
  public NounDef newNoun(final GrammarDef gg, final String name) {
    NounFactory nf = provider.getNounFactory();
    NounDef nd = nf.newNoun(gg, name);
    nd.setName(name);
    return nd;
  }

  /**
   * {@inheritDoc}
   *
   * @see net.witerat.cafenatedsql.api.DriverFactory#
   *    newDialectDefinition(
   *            net.witerat.cafenatedsql.spi.driver.Driver,
   *            java.lang.String)
   */
  @Override
  public DialectDef newDialectDefinition(
      final Driver driver, final String name) {
    DialectFactory df = provider.getDialectFactory();
    DialectDef dd = df.newDialect(driver, name);
    dd.setName(name);
    return dd;
  }

  /**
   * {@inheritDoc}
   *
   * @see net.witerat.cafenatedsql.api.DriverFactory#
   *    newUrlDef(net.witerat.cafenatedsql.api.driver.ConnectionType)
   */
  @Override
  public UrlDef newUrlDef(final ConnectionType ct) {
    URLFactory uf = provider.getUrlFactory();
    return uf.newUrl();
  }

  /**
   * {@inheritDoc}
   *
   * @see net.witerat.cafenatedsql.api.DriverFactory#newParameter()
   */
  @Override
  public ParameterType newParameter() {
    ParameterFactory parameterFactory = provider.getParameterFactory();
    return parameterFactory.newParameter();
  }
}
