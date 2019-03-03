package net.witerat.cafenatedsql.spi.driver.tags;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

/**
 * The Class ParameterTag describes a model parameter.
 */
@XmlRootElement(name = "param")
@XmlType(name = "ParamType", namespace = "-//org.witerat/cafenated/sql")
public class ParameterTag {

  /** The type. */
  private String type;

  /** The name. */
  private String name;

  /** The optional. */
  private boolean optional;

  /** The usual(default) value for this parameter. */
  private String usual;

  /**
   * Checks if is optional.
   *
   * @return true, if is optional
   */
  public boolean isOptional() {
    return optional;
  }

  /**
   * Sets the optional.
   *
   * @param optional0
   *          the new optional
   */
  @XmlAttribute
  public void setOptional(final boolean optional0) {
    this.optional = optional0;
  }

  /**
   * Gets the class name for the type of a model parameter for template
   * processing. if left unset, any object may be provided to the model,
   * otherwise arguments for this parameter must pass the instanceof test.
   *
   * @return the class-name of the parameter.
   */

  public String getType() {
    return type;
  }

  /**
   * Defines the type for a model parameter for template processing. If left
   * unset, any object may be provides to the model, otherwise arguments for
   * this parameter must pass the instanceof test.
   *
   * @param type0
   *          the name or identifier a type.
   */
  @XmlAttribute
  public void setType(final String type0) {
    this.type = type0;
  }

  /**
   * gets the name of the parameter;.
   *
   * @return the parameter name
   */
  public String getName() {
    return name;
  }

  /**
   * Sets the name of the parameter.
   *
   * @param name0
   *          the new name of the parameter.
   */
  @XmlAttribute
  public void setName(final String name0) {
    this.name = name0;
  }

  /**
   * Gets the default.
   *
   * @return the default
   */
  @XmlAttribute(name = "usual")
  public String getUsual() {
    return usual;
  }

  /**
   * Sets the default.
   *
   * @param usual0
   *          the new default
   */
  public void setUsual(final String usual0) {
    this.usual = usual0;
  }
}
