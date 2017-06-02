package net.witerat.cafenatedsql.spi.driver.tags;

import net.witerat.cafenatedsql.api.driver.template.TemplateEngineModel;
import net.witerat.cafenatedsql.spi.driver.Content;

// TODO: Auto-generated Javadoc
/**
 * The Class TextContent encapsulates static text content.
 */
public class TextContent implements Content{
	
	/** The text. */
	String text;
	
	/**
	 * Instantiates a new empty text content.
	 */
	public TextContent() {
		text="";
	}
	
	/**
	 * Instantiates a new text content.
	 *
	 * @param text the text
	 */
	public TextContent(String text){
		this.text=text;
	}
	/**
	 * Gets the text.
	 *
	 * @param model the model
	 * @return the text
	 */
	@Override
	public String getText(TemplateEngineModel model) {
		return text;
	}

}
