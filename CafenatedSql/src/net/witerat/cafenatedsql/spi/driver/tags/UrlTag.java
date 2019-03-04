package net.witerat.cafenatedsql.spi.driver.tags;

import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlElementRefs;
import javax.xml.bind.annotation.XmlMixed;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlType;

import net.witerat.cafenatedsql.api.driver.template.TemplateEngineModel;
import net.witerat.cafenatedsql.spi.driver.Content;

/**
 * The Class UrlTag.
 */
@XmlType(name = "UrlType", namespace = "-//org.witerat/cafenated/sql")
public class UrlTag extends MacroTag {

  /** The macros. */
  private AbstractMacroLocator macros;

  /** The content. */
  private Collection<AbstractContent> content;

  /**
   * The rawContent property.
   */
  private LinkedList<Object> rawContent;

  /**
   * The parametersByName property.
   */
  private Map<String, ParamTag> parametersByName;

  /**
   * Sets the macros.
   *
   * @param macros0
   *          the new macros
   */
  public void setMacros(final AbstractMacroLocator macros0) {
    this.macros = macros0;
  }

  /**
   * Gets the macros.
   *
   * @return the macros
   */
  @XmlTransient
  public AbstractMacroLocator getMacros() {
    return macros;
  }


  /**
   * Add raw elements.
   * @param raw A list of elements.
   */
  @XmlElementRefs({
      @XmlElementRef(name = "use", type = UseTag.class,
        namespace = "-//org.witerat/cafenated/sql"),
      @XmlElementRef(name = "param", type = ParamTag.class,
        namespace = "-//org.witerat/cafenated/sql") })
  @XmlMixed
  public void setRawContent(final List<Object> raw) {
    LinkedList<Object> rawC = new LinkedList<Object>();
    for (Object o : raw) {
      if (o instanceof ParamTag) {
        setParameter((ParamTag) o);
      } else {
        rawC.add(o);
      }
    }
    rawContent = rawC;
  }

  /**
   * @return a list of strings and tags strings and tags.
   */
  public List<Object> getRawContent() {
    if (rawContent == null) {
      rawContent = new LinkedList<>();
    }
    return rawContent;
  }

  /**
   * set a parameter definition.
   * @param pt a parameter description.
   */
  @XmlTransient
  private void setParameter(final ParamTag pt) {
    if (parametersByName == null) {
      parametersByName = new LinkedHashMap<>();
    }
    parametersByName.put(pt.getName(), pt);
  }

  /**
   * Sets the content.
   *
   * @param content0
   *          the new content
   */
  public void setContent(final Collection<AbstractContent> content0) {
    this.content = content0;
  }

  /**
   * Get content.
   * @return a translation of raw into part processed content objects
   *  that make up a template..
   */
  @XmlTransient
  public Collection<AbstractContent> getContent() {
    return content;
  }

  /*
   * (non-Javadoc)
   *
   * @see net.witerat.cafenatedsql.spi.driver.tags.MacroTag#getParent()
   */
  @XmlTransient
  @Override
  public AbstractContent getParent() {
    return null;
  }

  /*
   * (non-Javadoc)
   *
   * @see net.witerat.cafenatedsql.spi.driver.tags.MacroTag#getText(net.witerat.
   * cafenatedsql.api.driver.template.TemplateEngineModel)
   */
  @Override
  public String getText(final TemplateEngineModel model0) {
    StringBuilder sb = new StringBuilder();
    for (Content c : getContent()) {
      sb.append(c.getText(model0));
    }
    return sb.toString();
  }
}
