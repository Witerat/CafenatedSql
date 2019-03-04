package net.witerat.cafenatedsql.spi.driver.tags;

import java.util.logging.Level;
import java.util.logging.Logger;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;

/**
 * The knitting.
 * @author John Hutcheson &lt;witerat.test@gmail.com&gt;
 *
 */
@XmlRegistry
public class ObjectFactory {

  /**
   * The _ParamTag_QNAME property.
   */
  private static final QName QNAME_PARAM =
      new QName("-//org.witerat/cafenated/sql", "param");

  /**
   * The _UseTag_QNAME property.
   */
  private static final QName QNAME_USE =
      new QName("-//org.witerat/cafenated/sql", "use");

  /**
   * Make a verb.
   * @return a new verb.
   */
  public VerbTag createVerbTag() {
    return new VerbTag();
  }

  /**
   * Make A param.
   * @return a new param.
   */
  public ParamTag createParamTag() {
    return new ParamTag();
  }

  /**
   * Make A new use.
   * @return A new use;
   */
  public UseTag createUseTag() {
    return new UseTag();
  }

  /**
   * Build a jaxb object element.
   * @param value the param value.
   * @return a new jaxb element.
   */
  @XmlElementDecl(namespace = "-//org.witerat/cafenated/sql", name = "param")
  public JAXBElement<ParamTag> createVerbTagParamTag(final ParamTag value) {
    Logger.getAnonymousLogger().log(Level.INFO, "creating param", value);
    return new JAXBElement<ParamTag>(QNAME_PARAM,
        ParamTag.class, VerbTag.class, value);
  }
  /**
   * Build a jaxb object element.
   * @param value the use value.
   * @return a new jaxb element.
   */
  @XmlElementDecl(namespace = "-//org.witerat/cafenated/sql", name = "use")
  public JAXBElement<UseTag> createVerbTagUseTag(final UseTag value) {
    Logger.getAnonymousLogger().log(Level.INFO, "creating param", value);
    return new JAXBElement<UseTag>(QNAME_USE,
        UseTag.class, VerbTag.class, value);
  }
}
