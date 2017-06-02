package net.witerat.cafenatedsql.spi.driver.tags;

import javax.xml.bind.annotation.XmlTransient;

import net.witerat.cafenatedsql.api.driver.template.TemplateEngineModel;
import net.witerat.cafenatedsql.spi.driver.Content;

/**
 * @author John Hutcheson &lt;witerat.test@gmail.com&gt;
 *
 */
@XmlTransient
public abstract class AbstractContent implements Content{
	/* (non-Javadoc)
	 * @see net.witerat.cafenatedsql.spi.driver.Content#getText(net.witerat.cafenatedsql.api.driver.template.TemplateEngineModel)
	 */
	abstract public String getText(TemplateEngineModel model) ;

}
