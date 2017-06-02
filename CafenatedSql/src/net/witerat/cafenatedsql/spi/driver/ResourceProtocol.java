package net.witerat.cafenatedsql.spi.driver;

import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLStreamHandler;
import java.net.URLStreamHandlerFactory;
import java.util.HashMap;
import java.util.Map;

/**
 * The Class ResourceProtocol provide late registration for URL protocol handlers..
 *
 * @author John Hutcheson &lt;witerat.test@gmail.com&gt;
 */
public class ResourceProtocol implements URLStreamHandlerFactory {
	
	/** The Constant protocolHandlers. */
	private static final Map<String, URLStreamHandler> protocolHandlers = new HashMap<String, URLStreamHandler>();
	static {
		protocolHandlers.put("classpath", new URLStreamHandler() {

			@Override
			protected URLConnection openConnection(URL u) throws IOException {
				return Thread.currentThread().getContextClassLoader().getResource(u.getPath()).openConnection();
			}
		});
	}
	static {
		System.out.println("registering classpath");
		URL.setURLStreamHandlerFactory(new ResourceProtocol());
		System.out.println("registered classpath successfully");
	}

	/**
	 * Register protocol.
	 *
	 * @param protocol the protocol
	 * @param handler the handler
	 */
	public void registerProtocol(String protocol, URLStreamHandler handler) {
		protocolHandlers.put(protocol, handler);
	}

	/* (non-Javadoc)
	 * @see java.net.URLStreamHandlerFactory#createURLStreamHandler(java.lang.String)
	 */
	@Override
	public URLStreamHandler createURLStreamHandler(String protocol) {

		return protocolHandlers.get(protocol);
	}

	/**
	 * Initialises this class. Invocation of this method forces the class to be loaded and initialise.
	 *
	 * @return return the number of default handlers pre-registered
	 */
	public static int init() {
		return protocolHandlers.size();

	}

}
