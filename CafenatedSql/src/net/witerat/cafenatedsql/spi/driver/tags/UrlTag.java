package net.witerat.cafenatedsql.spi.driver.tags;

import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.xml.bind.JAXBElement;
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
@XmlType(name="UrlType",namespace="-//org.witerat/cafenated/sql")
public class UrlTag extends MacroTag {
	
	/** The macros. */
	AbstractMacroLocator macros;
	
	/** The content. */
	Collection<AbstractContent> content;

	@XmlTransient
	private LinkedList<Object> rawContent;

	private Map<String, ParameterTag> parametersByName;

	/**
	 * Sets the macros.
	 *
	 * @param macros the new macros
	 */
	public void setMacros(AbstractMacroLocator macros) {
		this.macros = macros;
	}


	/**
	 * Gets the macros.
	 *
	 * @return the macros
	 */
	@XmlTransient
	public AbstractMacroLocator getMacros(){
		return macros;
	}


	/**
	 * Adds the.
	 *
	 * @param content the content
	 */
	void add(AbstractContent content){
		this.content.add(content);
	}

	@XmlElementRefs({@XmlElementRef(name="use",type=UseTag.class),
		@XmlElementRef(name="param",type=ParameterTag.class)})
	@XmlMixed
	public void setRawContent(List<Object> raw){
		LinkedList<Object> rawC=new LinkedList<Object>();
		for(Object o:raw){
			if(o instanceof String){
				rawC.add(o);
			}else if(o instanceof JAXBElement){
				JAXBElement<?> jaxbE = (JAXBElement<?>) o;
				Object v = jaxbE.getValue();
				if(v instanceof ParameterTag){
					setParameter((ParameterTag)v);
				}else if(v instanceof UseTag){
					rawC.add((UseTag)v);
				}
			}
		}
		rawContent=rawC;
	}
	public LinkedList<Object> getRawContent() {
		if(rawContent==null)rawContent=new LinkedList<>();
		return rawContent;
	}

	@XmlTransient
	private void setParameter(ParameterTag v) {
		if(parametersByName==null) parametersByName=new LinkedHashMap<>();
		parametersByName.put(v.getName(), v);
	}


	/**
	 * Sets the content.
	 *
	 * @param content the new content
	 */
	public void setContent(Collection<AbstractContent> content) {
		this.content = content;
	}


	/* (non-Javadoc)
	 * @see net.witerat.cafenatedsql.spi.driver.tags.MacroTag#getContent()
	 */
	@XmlTransient
	public Collection<AbstractContent> getContent() {
		return content;
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
