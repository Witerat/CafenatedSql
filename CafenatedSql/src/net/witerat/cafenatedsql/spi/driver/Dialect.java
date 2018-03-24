package net.witerat.cafenatedsql.spi.driver;

import java.util.LinkedHashMap;

import net.witerat.cafenatedsql.api.driver.DialectDef;
import net.witerat.cafenatedsql.api.driver.GrammarDef;
import net.witerat.cafenatedsql.api.driver.ParameterType;
import net.witerat.cafenatedsql.api.driver.ParameterizedRequest;

/**
 * The Class Dialect describes a specific language set for data definition
 * and refactoring.
 */
public class Dialect implements ParameterizedRequest, DialectDef {

  /** The id. */
  private String id;

  /** The grammars. */
  private LinkedHashMap<String, GrammarDef> grammars;

  /** The params. */
  private LinkedHashMap<String, ParameterType> params;

  /*
   * (non-Javadoc)
   *
   * @see
   * net.witerat.cafenatedsql.spi.driver.DialectDef#setName(java.lang.String)
   */
  @Override
  public void setName(final String id0) {
    this.id = id0;

  }

  /*
   * (non-Javadoc)
   *
   * @see net.witerat.cafenatedsql.spi.driver.DialectDef#getId()
   */
  @Override
  public String getId() {
    return id;
  }

  /**
   * Adds a grammar to this dialect.
   *
   * @see DialectDef#addGrammar(GrammarDef)
   */
  @Override
  public void addGrammar(final GrammarDef g) {
    grammars.put(g.getName(), g);
  }

  /**
   * Adds a parameter to this <code>Dialect</code>.
   *
   * @see DialectDef#add(ParameterType)
   */
  @Override
  public void add(final ParameterType param) {
    params.put(param.getName(), param);
  }
}
