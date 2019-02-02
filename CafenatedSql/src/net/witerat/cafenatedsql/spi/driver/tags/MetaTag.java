package net.witerat.cafenatedsql.spi.driver.tags;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;


/**
 * The MetaTag class is used to populate the provider meta data.
 * @see net.witerat.cafenatedsql.api.ProviderRegistrar
 */
@XmlType(name = "MetaTag")
public class MetaTag {
  /** The class name. */
  private String className;

  /**
   * The description property.
   */
  private String description;

  /**
   * The name property.
   */
  private String name;

  /**
   * The protocol property.
   */
  private String protocol;

  /**
   * Gets the class name.
   *
   * @return the class name
   */
  @XmlAttribute
  public String getClassName() {
    return className;
  }

  /**
   * @return human readable description.
   */
  @XmlAttribute
  public String getDescription() {
    return description;
  }

  /**
   * The name of the driver.
   * @return a human name for the driver
   */
  @XmlAttribute
  public String getName() {
    return name;
  }

  /**
   * The main protocol for connection.
   * @return default protocol
   */
  @XmlAttribute
  public String getProtocol() {
    return protocol;
  }

  /**
   * Sets the class name.
   *
   * @param className0
   *          the new class name
   */
  public void setClassName(final String className0) {
    this.className = className0;
  }

  /**
   * Sets the human readable description of the driver.
   *
   * @param description0 the new driver description
   */
  public void setDescription(final String description0) {
    this.description = description0;
  }

  /**
   * Sets the unique name of the driver.
   *
   * @param name0 the name of the driver.
   */
  public void setName(final String name0) {
    this.name = name0;
  }

  /**
   * Sets the driver's base protocol.
   *
   * @param protocol0 the primary protocol.
   */
  public void setProtocol(final String protocol0) {
    this.protocol = protocol0;
  }

}
