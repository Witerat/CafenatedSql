package net.witerat.cafenatedsql.spi.driver.tags;

import java.util.List;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlElementRefs;
import javax.xml.bind.annotation.XmlMixed;
import javax.xml.bind.annotation.XmlType;

/**
 * The Class DefTag describes a macro definition..
 */
@XmlType(name = "DefType", namespace = "-//org.witerat/cafenated/sql")
public class DefTag extends UrlTag {

  /** The name. */
  private String name;

  /**
   * Gets the name.
   *
   * @return the name
   */
  @XmlAttribute
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
   * Gets the tags and text.
   * @return a list of tags and text.
   */
  @XmlElementRefs({
      @XmlElementRef(name = "use", type = UseTag.class,
          namespace = "-//org.witerat/cafenated/sql"),
      @XmlElementRef(name = "param", type = ParameterTag.class,
          namespace = "-//org.witerat/cafenated/sql") })
  @XmlMixed
  public List<Object> getRawContent() {
    return super.getRawContent();
  }


  /**
   * Sets tags and text.
   * @param raw a list of tags and text.
   */
  public void setRawContent(final List<Object> raw) {
    super.setRawContent(raw);
  }
}
