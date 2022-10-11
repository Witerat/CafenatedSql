package net.witerat.cafenatedsql.spi.driver.tags;

import java.util.Collection;

import net.witerat.cafenatedsql.api.Cafenated;
import net.witerat.cafenatedsql.api.driver.template.TemplateEngine;
import net.witerat.cafenatedsql.api.driver.template.TemplateEngineModel;
import net.witerat.cafenatedsql.spi.driver.Content;
import net.witerat.cafenatedsql.spi.driver.DefLocator;

/**
 * The Class UseContent represents a tag that indicates defined content
 * should be included in the expansion of a template.
 */
public class UseContent extends MacroTag {

  /** The macros. */
  private AbstractDefLocator macros;

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
  public void setMacros(final AbstractDefLocator macros0) {
    this.macros = (AbstractDefLocator) macros0;
  }

  /**
   * {@inheritDoc}
   *
   * @see net.witerat.cafenatedsql.spi.driver.tags.MacroTag#getContent()
   */
  public Collection<AbstractContent> getContent() {
    return getMacros().getDef(macro).getContent();
  }

  /**
   * Gets the macros.
   *
   * @return the macros
   */

  public AbstractDefLocator getMacros() {
    if (macros != null) {
      return macros;
    }
    Content parent = getParent();
    if (parent instanceof DefLocator) {
      return (AbstractDefLocator) parent;
    }
    return null;
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
   *
   * @see net.witerat.cafenatedsql.spi.driver.tags
   *    .MacroTag#getText(
   *            net.witerat.cafenatedsql.api.driver.template
   *                    .TemplateEngineModel)
   */
  @Override
  public String getText(final TemplateEngineModel model) {
    TemplateEngine te = (TemplateEngine) model.get(Cafenated.TEMPLATE_ENGINE);
    StringBuilder sb = new StringBuilder();
    for (Content c : getContent()) {
      sb.append(c.getText(model));
    }
    if (te != null) {
      te.appendText(sb.toString());
      te.setModel(model);
      return te.produce(null, model);
    } else {
    return sb.toString();
    }
  }
}

