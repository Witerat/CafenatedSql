package net.witerat.cafenatedsql.spi;

import java.util.Collection;

import net.witerat.cafenatedsql.api.driver.ConnectionType;
import net.witerat.cafenatedsql.api.driver.ParameterType;
import net.witerat.cafenatedsql.api.driver.UrlDef;
import net.witerat.cafenatedsql.api.driver.template.TemplateEngineModel;
import net.witerat.cafenatedsql.spi.driver.Content;
import net.witerat.cafenatedsql.spi.driver.DialectSelector;
import net.witerat.cafenatedsql.spi.driver.Driver;
import net.witerat.cafenatedsql.spi.driver.DriverLocator;

// TODO: Auto-generated Javadoc
/**
 * The Class JavaDBConnectionType.
 */
public class JavaDBConnectionType implements ConnectionType, DriverLocator {
	
	/**
	 * The Class DefaultUrl.
	 */
	private final class DefaultUrl implements UrlDef, Content {
		
		/* (non-Javadoc)
		 * @see net.witerat.cafenatedsql.api.driver.ParameterizedRequest#add(net.witerat.cafenatedsql.api.driver.ParameterType)
		 */
		@Override
		public void add(ParameterType p) {
			// TODO Auto-generated method stub

		}

		/* (non-Javadoc)
		 * @see net.witerat.cafenatedsql.api.driver.UrlDef#setParameter(net.witerat.cafenatedsql.api.driver.ParameterType)
		 */
		@Override
		public void setParameter(ParameterType type) {
			// TODO Auto-generated method stub

		}

		/* (non-Javadoc)
		 * @see net.witerat.cafenatedsql.api.driver.UrlDef#getParameters()
		 */
		@Override
		public Collection<ParameterType> getParameters() {
			// TODO Auto-generated method stub
			return null;
		}

		/* (non-Javadoc)
		 * @see net.witerat.cafenatedsql.api.driver.UrlDef#add(net.witerat.cafenatedsql.spi.driver.Content)
		 */
		@Override
		public void add(Content c) {
			// TODO Auto-generated method stub

		}

		/* (non-Javadoc)
		 * @see net.witerat.cafenatedsql.spi.driver.Content#getText(net.witerat.cafenatedsql.api.driver.template.TemplateEngineModel)
		 */
		@Override
		public String getText(TemplateEngineModel model) {
			StringBuilder sb = new StringBuilder();
			int ic = -1, bi = 0, si = -1;
			boolean expr=false, lit=false;
			String t = template;
			for (char c : t.toCharArray()) {
				ic++;
				if(lit){
					sb.append((String.valueOf(c)+"<>&tnrf").charAt("<>&\t\n\r\f".indexOf(c)+1));
					lit=false;
					bi=ic+1;
				}else if(c=='\\'){
					lit=true;
					sb.append(t.substring(bi, si));
				}else if (c == '<') {
					sb.append(t.substring(bi, ic));
					si = ic + 1;
				} else if (c == '>') {
					if (si >= 0) {
						String id = t.substring(si, ic);
						Object p = expr?model.getByExpression(id):model.get(id);
						sb.append(p==null?"":p.toString());
						si = -1;
						expr=false;
						bi=ic+1;
					}
				}else if(c=='=' && (!expr) && si>=0){
					expr=true;
				}
			}
			sb.append(t.toString().substring(bi));
			return sb.toString();
		}
	}

	/** The driver loc. */
	DriverLocator driverLoc = new DriverLocator() {
		@Override
		public Driver getDriver() {
			return JavaDBConnectionType.this.driver;
		}
	};
	
	/** The Constant DERBY_SERVER. */
	final static public JavaDBConnectionType DERBY_SERVER = new JavaDBConnectionType("Derby Server",
			"connect to server", "jdbc:derby://<servername>:<port>)", null);

	/** The Constant DERBY_JNDI. */
	final static public JavaDBConnectionType DERBY_JNDI = new JavaDBConnectionType("Derby Server", "connect to JNDI",
			"jdbc:derby://<JNDIname>)", null);

	/** The name. */
	final private String name;
	
	/** The description. */
	final private String description;
	
	/** The template. */
	final private String template;

	/** The driver. */
	final private Driver driver;
	
	/** The url. */
	private UrlDef url = new DefaultUrl();
	
	/** The dialect selector. */
	private DialectSelector dialectSelector;

	/**
	 * Instantiates a new java DB connection type.
	 *
	 * @param name the name
	 * @param description the description
	 * @param template the template
	 * @param driver the driver
	 */
	private JavaDBConnectionType(String name, String description, String template, Driver driver) {
		super();
		this.name = name;
		this.description = description;
		this.template = template;
		this.driver = driver;
	}

	/* (non-Javadoc)
	 * @see net.witerat.cafenatedsql.spi.driver.DriverLocator#getDriver()
	 */
	public Driver getDriver() {
		return driver;
	}

	/* (non-Javadoc)
	 * @see net.witerat.cafenatedsql.api.driver.ConnectionType#getName()
	 */
	@Override
	public String getName() {
		return name;
	}

	/* (non-Javadoc)
	 * @see net.witerat.cafenatedsql.api.driver.ConnectionType#getDescription()
	 */
	@Override
	public String getDescription() {
		return description;
	}

	/* (non-Javadoc)
	 * @see net.witerat.cafenatedsql.api.driver.ConnectionType#getUrlDef()
	 */
	@Override
	public UrlDef getUrlDef() {
		return url;
	}

	/* (non-Javadoc)
	 * @see net.witerat.cafenatedsql.api.driver.ConnectionType#getDialectSelector()
	 */
	@Override
	public DialectSelector getDialectSelector() {
		return dialectSelector;
	}

	/* (non-Javadoc)
	 * @see net.witerat.cafenatedsql.api.driver.ConnectionType#setDialectSelector(net.witerat.cafenatedsql.spi.driver.DialectSelector)
	 */
	@Override
	public void setDialectSelector(DialectSelector ds) {
		this.dialectSelector = ds;

	}

	/* (non-Javadoc)
	 * @see net.witerat.cafenatedsql.api.driver.ConnectionType#setUrlDef(net.witerat.cafenatedsql.api.driver.UrlDef)
	 */
	@Override
	public void setUrlDef(UrlDef url) {
		this.url = url;
	}
}
