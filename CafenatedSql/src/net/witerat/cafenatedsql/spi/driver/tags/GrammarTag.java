package net.witerat.cafenatedsql.spi.driver.tags;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlType;


/**
 * The Class GrammarTag.
 */
@XmlType(name = "GrammarType", namespace = "-//org.witerat/cafenated/sql")
public class GrammarTag {

  /** The name. */
  private String name;

  /** The nouns. */
  private Collection<NounTag> nouns;

  /** The params. */
  private Map<String, ParamTag> params;

  /**
   * Sets the noun.
   *
   * @param noun
   *          the new noun
   */
  @XmlElement(name = "noun", type = NounTag.class,
      namespace = "-//org.witerat/cafenated/sql")
  void setNoun(final NounTag noun) {
    if (nouns == null) {
      nouns = new ArrayList<>();
    }
    nouns.add(noun);
  }

  /**
   * Gets the nouns.
   *
   * @return the nouns
   */
  @XmlTransient
  public Collection<NounTag> getNouns() {
    if (nouns == null) {
      nouns = new ArrayList<>();
    }
    return nouns;
  }

  /**
   * Gets the id.
   *
   * @return the id
   */
  @XmlAttribute(name = "name")
  public String getId() {
    return name;
  }

  /**
   * Sets the id.
   *
   * @param name0
   *          the new id
   */
  public void setId(final String name0) {
    this.name = name0;
  }

  /**
   * Gets the params.
   *
   * @return the params
   */
  @XmlTransient
  public Collection<ParamTag> getParams() {
    if (params == null) {
      params = new LinkedHashMap<>();
    }
    return params.values();
  }

  /**
   * Sets the parameter.
   *
   * @param p
   *          the new parameter
   */
  @XmlElement(name = "param", type = ParamTag.class,
      namespace = "-//org.witerat/cafenated/sql")
  void setParameter(final ParamTag p) {
    if (params == null) {
      params = new LinkedHashMap<>();
    }
    params.put(p.getName(), p);
  }

  /**
   * Gets the name of this Grammar.
   * @return the name of this grammar.
   */
  public String getName() {
    return name;
  }

  /**
   * Defines the name of this Grammar.
   * @param name0 the new name of this grammar.
   */
  @XmlAttribute(name = "name")
  public void setName(final String name0) {
    this.name = name0;
  }
}
