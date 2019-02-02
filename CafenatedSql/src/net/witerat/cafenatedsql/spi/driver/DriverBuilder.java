package net.witerat.cafenatedsql.spi.driver;

import java.util.Collection;

import net.witerat.cafenatedsql.api.DriverFactory;
import net.witerat.cafenatedsql.api.driver.ConnectionType;
import net.witerat.cafenatedsql.api.driver.DialectDef;
import net.witerat.cafenatedsql.api.driver.DriverCreationException;
import net.witerat.cafenatedsql.api.driver.GrammarDef;
import net.witerat.cafenatedsql.api.driver.NounDef;
import net.witerat.cafenatedsql.api.driver.ParameterType;
import net.witerat.cafenatedsql.api.driver.ParameterizedRequest;
import net.witerat.cafenatedsql.api.driver.UrlDef;
import net.witerat.cafenatedsql.api.driver.VerbDef;
import net.witerat.cafenatedsql.api.driver.template.TemplateEngineModel;
import net.witerat.cafenatedsql.spi.driver.tags.ConnectionTag;
import net.witerat.cafenatedsql.spi.driver.tags.DialectTag;
import net.witerat.cafenatedsql.spi.driver.tags.DriverTag;
import net.witerat.cafenatedsql.spi.driver.tags.GrammarTag;
import net.witerat.cafenatedsql.spi.driver.tags.NounTag;
import net.witerat.cafenatedsql.spi.driver.tags.ParameterTag;
import net.witerat.cafenatedsql.spi.driver.tags.UrlTag;
import net.witerat.cafenatedsql.spi.driver.tags.VerbTag;

/**
 * The Class DriverBuilder uses a driver factory to construct and configure
 * a new driver object aggregated of definition objects derived from tag
 * objects.
 * @see DriverFactory
 * @see TypeMapper
 */
public class DriverBuilder {

  /** The factory. */
  private DriverFactory factory;

  /** The type mapper. */
  private TypeMapper typeMapper;

  /**
   * Builds the driver.
   *
   * @param model
   *          the model
   * @param spec
   *          the spec
   * @return the driver
   * @throws DriverCreationException
   *           the driver creation exception
   */
  protected Driver buildDriver(final TemplateEngineModel model,
      final DriverTag spec) throws DriverCreationException {
    Driver driver = factory.newDriver(model);
    driver.setModel(model);
    buildDialects(driver, model, spec);
    for (ConnectionTag conTag : spec.getConnections()) {
      ConnectionType ct = factory.newConnectionType(driver, conTag.getMethod());
      UrlDef url = buildUrlDef(ct, conTag);
      DialectTag dt = conTag.getDialect();
      if (dt.getNameExpression() != null) {
        DialectSelector ds = factory.newDialectExpressionSelector(
            ct, dt.getNameExpression());
        ct.setDialectSelector(ds);
      } else if (dt.getName() != null) {
        DialectSelector ds = factory.newDialectNameSelector(ct, dt.getName());
        ct.setDialectSelector(ds);
      }
      ct.setUrlDef(url);
    }
    return driver;
  }

  /**
   * Builds a URL def.
   *
   * @param ct
   *          connection type under build
   * @param conTag
   *          the connection tage object.
   * @return connection url construction
   */
  public UrlDef buildUrlDef(final ConnectionType ct,
      final ConnectionTag conTag) {
    UrlDef newUrlDef = factory.newUrlDef(ct);
    buildParams(newUrlDef, conTag.getParams());
    UrlTag urlTag = conTag.getUrl();
    for (Content c : urlTag.getContent()) {
      newUrlDef.add(c);
    }

    return newUrlDef;
  }

  /**
   * Builds the dialects.
   *
   * @param driver
   *          the driver
   * @param model
   *          the model
   * @param spec
   *          the spec
   */
  private void buildDialects(final Driver driver,
      final TemplateEngineModel model, final DriverTag spec) {
    for (DialectTag d : spec.getDialects()) {
      DialectDef dd = factory.newDialectDefinition(driver, d.getName());
      driver.addDialect(dd);
      for (GrammarTag g : d.getGrammars()) {
        GrammarDef gg = factory.newGrammar(dd, g.getId());
        dd.addGrammar(gg);
        buildParams(dd, g.getParams());
        for (NounTag n : g.getNouns()) {
          NounDef nn = factory.newNoun(gg, n.getName());
          gg.addNoun(nn);
          buildParams(gg, n.getParameters());
          for (VerbTag v : n.getVerbs()) {
            VerbDef vv = factory.newVerb(nn, v.getName());
            nn.addVerb(vv);
          }
        }
      }
    }

  }

  /**
   * Builds the params.
   *
   * @param dd
   *          the dd
   * @param params
   *          the params
   */
  private void buildParams(final ParameterizedRequest dd,
      final Collection<ParameterTag> params) {
    for (ParameterTag pt : params) {
      ParameterType p = factory.newParameter();
      p.setName(pt.getName());
      p.setType(typeMapper.getType(pt.getType()));
      p.setDefault(pt.getUsual());
      p.setOptional(pt.isOptional());

      dd.add(p);
    }

  }
}
