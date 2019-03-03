package net.witerat.cafenatedsql.spi.driver.tags;


import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;

/**
 * This class encapsulates the XML description of a dialect reference for a
 * collection.
 *
 * @author John Hutcheson &lt;witerat.test@gmail.com&gt;
 *
 */
@XmlType(name = "UseDialectType", namespace = "-//org.witerat/cafenated/sql")
public class UseDialectTag {

  /** The name. */
  private String name;

  /** The default name. */
  private String defaultName;

  /** The expression. */
  private boolean expression;

  /**
   * Gets the default name.
   *
   * @return the default name
   */
  public String getDefaultName() {
    return defaultName;
  }

  /**
   * Gets the name.
   *
   * @return the name
   */
  public String getNameExpression() {
    if (expression) {
      return  name;
    } else {
      return null;
    }
  }

  /**
   * Gets the name expression.
   *
   * @return the name expression
   */
  @XmlAttribute(name = "name")
  public String getName() {
    if (expression) {
      return null;
    } else {
      return name;
    }
  }

  /**
   * Checks if is expression.
   *
   * @return true, if is expression
   */
  public boolean isExpression() {
    return expression;
  }

  /**
   * Sets the default name.
   *
   * @param defaultName0
   *          the new default name
   */
  @XmlAttribute(name = "default")
  public void setDefaultName(final String defaultName0) {
    this.defaultName = defaultName0;
  }

  /**
   * Set a literal name for the dialect.
   *
   * @param name0
   *          a literal name
   */
  public void setName(final String name0) {
    this.name = name0;
    expression = false;
  }

  /**
   * The name property.
   *
   * @param name0
   *          a template language expression that is evaluable to the name of a
   *          dialect.
   */
  @XmlAttribute(name = "nameEx")
  public void setNameExpression(final String name0) {
    this.name = name0;
    expression = true;
  }

}
