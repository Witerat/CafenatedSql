package net.witerat.cafenatedsql.spi.driver.tags;

import net.witerat.cafenatedsql.api.driver.template.TemplateEngineModel;
import net.witerat.cafenatedsql.spi.driver.Content;

/**
 * The Class TextContent encapsulates static text content.
 */
public class TextContent implements Content {

  /** The text. */
  private String text;

  /**
   * Instantiates a new empty text content.
   */
  public TextContent() {
    text = "";
  }

  /**
   * Instantiates a new text content.
   *
   * @param text0
   *          the text
   */
  public TextContent(final String text0) {
    this.text = text0;
  }

  /**
   * Gets the text.
   *
   * @param model
   *          the model
   * @return the text
   */
  @Override
  public String getText(final TemplateEngineModel model) {
    return text;
  }

}
