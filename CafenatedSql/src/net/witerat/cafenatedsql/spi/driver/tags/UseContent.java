package net.witerat.cafenatedsql.spi.driver.tags;

import java.util.Collection;

import net.witerat.cafenatedsql.api.driver.template.TemplateEngineModel;
import net.witerat.cafenatedsql.spi.driver.Content;
import net.witerat.cafenatedsql.spi.driver.MacroLocator;

/**
 * The Class UseContent represents a tag that indicates defined content
 * should be included in the expansion of a template.
 */
public class UseContent extends MacroTag {

  /** The macros. */
  private AbstractMacroLocator macros;

  /** The macro. */
  private String macro;

  /**
   * Gets the name of the macro.
   *
   * @return the macro
   */
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
   * Sets the macros.
   *
   * @param macros0
   *          the new macros
   */
  public void setMacros(final AbstractMacroLocator macros0) {
    this.macros = (AbstractMacroLocator) macros0;
  }

  /**
   * {@inheritDoc}
   *
   * @see net.witerat.cafenatedsql.spi.driver.tags.MacroTag#getContent()
   */
  public Collection<AbstractContent> getContent() {
    return getMacros().getMacro(macro).getContent();
  }

  /**
   * Gets the macros.
   *
   * @return the macros
   */

  public AbstractMacroLocator getMacros() {
    if (macro != null) {
      return macros;
    }
    Content parent = getParent();
    if (parent instanceof MacroLocator) {
      return (AbstractMacroLocator) parent;
    }
    return null;
  }

  /*
   * (non-Javadoc)
   *
   * @see net.witerat.cafenatedsql.spi.driver.tags.MacroTag#getParent()
   */
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
  public String getText(final TemplateEngineModel model) {
    StringBuilder sb = new StringBuilder();
    for (Content c : getContent()) {
      sb.append(c.getText(model));
    }
    return sb.toString();
  }
}
