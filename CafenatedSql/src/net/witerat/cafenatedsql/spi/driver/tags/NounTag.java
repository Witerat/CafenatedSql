package net.witerat.cafenatedsql.spi.driver.tags;

import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlType;

/**
 * The Class NounTag.
 */
@XmlType(name = "NounType", namespace = "-//org.witerat/cafenated/sql")
public class NounTag extends SqlTemplate {

  /**
   * The name property is the noun id.
   */
  private String name;
  /**
   * The verbsByName property contains of associated verbs.
   */
  private Map<String, VerbTag> verbsByName;

  /**
   * Gets the noun' name.
   * @return the name of this noun or database entity.
   */
  @XmlAttribute(name = "name")
  public String getName() {
    return name;
  }

  /**
   * @return a collection of verbs applied to nouns.
   */
  @XmlTransient
  public Collection<VerbTag> getVerbs() {
    if (verbsByName == null) {
      verbsByName = new LinkedHashMap<>();
    }
    return verbsByName.values();
  }

  /**
   * The name property.
   *
   * @param name0 set the of this noun.
   */

  public void setName(final String name0) {
    this.name = name0;
  }

  /**
   * {@inheritDoc}
   * @see net.witerat.cafenatedsql.spi.driver.tags.SqlTemplate#setParameter(
   * net.witerat.cafenatedsql.spi.driver.tags.ParameterTag)
   */
  @XmlElement(name = "param", type = ParameterTag.class,
      namespace = "-//org.witerat/cafenated/sql")
  public void setParameter(final ParameterTag param) {
    super.setParameter(param);
  }

  /**
   * Adds a verb to a noun.
   * @param verb0 a verb to be added to this noun.
   */
  @XmlElement(type = VerbTag.class,
      namespace = "-//org.witerat/cafenated/sql")
  void setVerb(final VerbTag verb0) {
    if (verbsByName == null) {
      verbsByName = new LinkedHashMap<>();
    }
    verbsByName.put(verb0.getName(), verb0);
  }

  /**
   * Set the verbs applicable for this noun.
   * @param verbs
   *          a new collection of verbs
   */
  public void setVerbs(final Collection<VerbTag> verbs) {
    Map<String, VerbTag> vbn = new LinkedHashMap<>();
    for (VerbTag v : verbs) {
      vbn.put(v.getName(), v);
    }
    verbsByName = vbn;

  }
}
