package net.witerat.cafenatedsql.spi.driver.tags;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlElementRefs;
import javax.xml.bind.annotation.XmlMixed;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlType;

/**
 * The Class <code>VerbTag</code> describes how instructions related to database
 * entities - nouns are rendered and submitted to a backend database.
 */
@XmlType(name = "VerbType", namespace = "-//org.witerat/cafenated/sql")
public class VerbTag {
  /** The rawContent property. */
  private Collection<Object> rawContent;
  /** The parametersByName property. */
  private Map<String, ParameterTag> parametersByName;
  /** The name. */
  private String name;

  /**
   * Gets the name.
   *
   * @return the name
   */
  @XmlAttribute(name = "name")
  public String getName() {
    return name;
  }

  /**
   * Get additional tags and text.
   * @return strings and tags
   */
  public Collection<Object> getRawContent() {
    return rawContent;
  }

  /**
   * Sets the name.
   *
   * @param name0
   *          the new name
   */
  public void setName(final String name0) {
    this.name = name0;
  }

  /**
   * Set Tags and text fragment content.
   * @param rawContent0
   *          strings and tags
   */
  @XmlElementRefs({ @XmlElementRef(name = "param", type = ParameterTag.class),
      @XmlElementRef(name = "use", type = UseTag.class) })
  @XmlMixed
  public void setRawContent(final Collection<Object> rawContent0) {
    Collection<Object> rc = new ArrayList<>();
    for (Object o : rawContent0) {
      if (o instanceof ParameterTag) {
        setParameter((ParameterTag) o);
      } else if (!rc.isEmpty() || (!(o instanceof String))
          || !(((String) o).trim().isEmpty())) {
        rc.add(o);
      }
    }
    this.rawContent = rc;
  }

  /**
   * Sets a parameter.
   *
   * @param param
   *          the new parameter
   */
  private void setParameter(final ParameterTag param) {
    if (parametersByName == null) {
      parametersByName = new LinkedHashMap<>();
    }
    parametersByName.put(param.getName(), param);
  }

  /**
   * get parameter tags.
   * @return the parameters
   */
  @XmlTransient
  public Collection<ParameterTag> getParameters() {
    if (parametersByName == null) {
      parametersByName = new LinkedHashMap<>();
    }
    return parametersByName.values();
  }
}
