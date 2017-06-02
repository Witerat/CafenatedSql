package net.witerat.cafenatedsql.spi.driver.tags;

import java.util.Collection;

import javax.xml.bind.annotation.XmlTransient;

import net.witerat.cafenatedsql.api.driver.template.TemplateEngineModel;

// TODO: Auto-generated Javadoc
/**
 * The Class MacroTag.
 */
@XmlTransient
public abstract class MacroTag extends AbstractContent{

	/**
	 * Gets the content.
	 *
	 * @return the content
	 */
	abstract public Collection<AbstractContent> getContent();
	
	/**
	 * Gets the parent.
	 *
	 * @return the parent
	 */
	abstract public AbstractContent getParent();

	/**
	 * Gets the text.
	 *
	 * @param model the model
	 * @return the text
	 */
	@Override
	public String getText(TemplateEngineModel model) {
		StringBuilder sb=new StringBuilder();
		for(AbstractContent content:getContent()){
			  sb.append(content.getText(model));
		}
		return sb.toString();
	}

}

