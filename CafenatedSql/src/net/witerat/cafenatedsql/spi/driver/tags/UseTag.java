package net.witerat.cafenatedsql.spi.driver.tags;

import java.util.Collection;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import net.witerat.cafenatedsql.api.driver.template.TemplateEngineModel;
import net.witerat.cafenatedsql.spi.driver.Content;

/**
 * The Class Use describes the inclusion of def content that stands in place of
 * the Use tag in the produced template.
 */
@XmlRootElement(name = "use")
@XmlType
public class UseTag extends MacroTag {

  /**
   * The macro locator the chooses the def content to be substituted for this
   * Use tag.
   */
  private AbstractMacroLocator macros;

  /** The macro reference name. */
  private String macro;

  /**
   * Gets the macro.
   *
   * @return the macro
   */
  @XmlAttribute
  public String getMacro() {
    return macro;
  }

  /**
   * Sets the macro.
   *
   * @param macro0
   *          the new macro
   */
  public void setMacro(final String macro0) {
    this.macro = macro0;
  }

  /**
   * {@inheritDoc}
   *
   * @see net.witerat.cafenatedsql.spi.driver.tags.MacroTag#getContent()
   */
  @Override
  public Collection<AbstractContent> getContent() {
    return macros.getMacro(macro).getContent();
  }

  /**
   * {@inheritDoc}
   *
   * @see net.witerat.cafenatedsql.spi.driver.tags.MacroTag#getParent()
   */
  @Override
  public AbstractContent getParent() {
    return null;
  }

  /**
   * {@inheritDoc}
   * @see net.witerat.cafenatedsql.spi.driver.tags
   *    .MacroTag#getText(
   *            net.witerat.cafenatedsql.api.driver.template
   *                    .TemplateEngineModel)
   */
  @Override
  public String getText(final TemplateEngineModel model) {
    StringBuilder sb = new StringBuilder();
    for (Content c : getContent()) {
      sb.append(c.getText(model));
    }
    return sb.toString();
  }

}
