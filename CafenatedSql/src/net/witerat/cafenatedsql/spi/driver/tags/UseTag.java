package net.witerat.cafenatedsql.spi.driver.tags;

import java.util.Collection;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import net.witerat.cafenatedsql.api.driver.template.TemplateEngineModel;
import net.witerat.cafenatedsql.spi.driver.Content;

/**
 * The Class Use describes the inclusion of def content that stands in place of the Use tag in the produced template.
 */
@XmlRootElement(name="use", namespace="-//org.witerat/cafenated/sql")
@XmlType(namespace="-//org.witerat/cafenated/sql")
public class UseTag extends MacroTag{
	
	/** The macro locator the chooses the def content to be substituted for this Use tag. */
	private AbstractMacroLocator macros;
	
	/** The macro. */
	private String macro;

	/**
	 * Gets the macro.
	 *
	 * @return the macro
	 */
	public String getMacro() {
		return macro;
	}

	/**
	 * Sets the macro.
	 *
	 * @param macro the new macro
	 */
	public void setMacro(String macro) {
		this.macro = macro;
	}
	
	/* (non-Javadoc)
	 * @see net.witerat.cafenatedsql.spi.driver.tags.MacroTag#getContent()
	 */
	@Override
	public Collection<AbstractContent> getContent() {
		return macros.getMacro(macro).getContent();
	}

	/* (non-Javadoc)
	 * @see net.witerat.cafenatedsql.spi.driver.tags.MacroTag#getParent()
	 */
	@Override
	public AbstractContent getParent() {
		return null;
	}

	/* (non-Javadoc)
	 * @see net.witerat.cafenatedsql.spi.driver.tags.MacroTag#getText(net.witerat.cafenatedsql.api.driver.template.TemplateEngineModel)
	 */
	@Override
	public String getText(TemplateEngineModel model) {
		StringBuilder sb=new StringBuilder();
		for(Content c:getContent()){
			sb.append(c.getText(model));
		}
		return sb.toString();
	}
	
}
