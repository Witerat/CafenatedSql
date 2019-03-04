package net.witerat.cafenatedsql.spi.driver.tags;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlElementRefs;
import javax.xml.bind.annotation.XmlMixed;
import javax.xml.bind.annotation.XmlType;

import org.apache.commons.lang.builder.ToStringBuilder;

/**
 * The Class <code>VerbTag</code> describes how instructions related to database
 * entities - nouns are rendered and submitted to a backend database.
 */
@XmlType
public class VerbTag {
  /** The rawContent property. */
  private Collection<Object> rawContent;
  /** The parametersByName property. */
  private Map<String, ParamTag> parametersByName;
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
  @XmlMixed
  @XmlElementRefs(
      {@XmlElementRef(namespace = "-//org.witerat/cafenated/sql",
          type = ParamTag.class, name = "param"),
       @XmlElementRef(namespace = "-//org.witerat/cafenated/sql",
           type = UseTag.class, name = "use") })
  public void setRawContent(final Collection<Object> rawContent0) {
    Collection<Object> rc = new ArrayList<>();
//    Logger.getAnonymousLogger().log(Level.INFO,
//        ToStringBuilder.reflectionToString(rawContent0));
    for (Object o : rawContent0) {
      if (o instanceof ParamTag) {
        setParameter((ParamTag) o);
      } else if (!rc.isEmpty() || (!(o instanceof String))
          || !(((String) o).trim().isEmpty())) {
        rc.add(o);
      }
    }
//    Logger.getAnonymousLogger().log(Level.INFO,
//        MessageFormat.format("verb {0}: {1}\n",
//         this.name, ToStringBuilder.reflectionToString(rc)));
    this.rawContent = rc;
  }
  /**
   * The raw content.
   * @return a collection of raw content.
   */
  public Collection<Object> getRawContent() {
//    if (rawContent == null) {
//      return new ArrayList<Object>();
//    }
    return rawContent;
  }
  /**
   * Sets a parameter.
   *
   * @param param
   *          the new parameter
   */
  public void setParameter(final ParamTag param) {
    if (parametersByName == null) {
      parametersByName = new LinkedHashMap<>();
    }
    parametersByName.put(param.getName(), param);
  }

  /**
   * get parameter tags.
   * @return the parameters
   */
  public Collection<ParamTag> getParameters() {
    if (parametersByName == null) {
      parametersByName = new LinkedHashMap<>();
    }
    return parametersByName.values();
  }
}
