package net.witerat.cafenatedsql.spi.driver.tags;

import java.util.Collection;

import net.witerat.cafenatedsql.api.driver.template.TemplateEngineModel;
import net.witerat.cafenatedsql.spi.driver.Content;
import net.witerat.cafenatedsql.spi.driver.MacroLocator;

// TODO: Auto-generated Javadoc
/**
 * The Class UseContent.
 */
public class UseContent extends MacroTag {
	
	/** The macros. */
	AbstractMacroLocator macros;
	
	/** The macro. */
	String macro;

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
	 * @param macro the new macro
	 */
	public void setMacro(String macro) {
		this.macro = macro;
	}

	/**
	 * Sets the macros.
	 *
	 * @param macros the new macros
	 */
	public void setMacros(AbstractMacroLocator macros) {
		this.macros = (AbstractMacroLocator) macros;
	}

	/* (non-Javadoc)
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
	
	public AbstractMacroLocator getMacros(){
		if(macro!=null) return macros;
		Content parent = getParent();
		if(parent instanceof MacroLocator)
			return (AbstractMacroLocator) parent;
		return null;
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
