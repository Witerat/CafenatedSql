package net.witerat.cafenatedsql.spi;

import java.util.Collection;

import net.witerat.cafenatedsql.api.Provider;
import net.witerat.cafenatedsql.api.ProviderRegistrar;

// TODO: Auto-generated Javadoc
/**
 * The Class DefaultProviderRegistrar.
 *
 * @author John Hutcheson &lt;witerat.test@gmail.com&gt;
 */
public class DefaultProviderRegistrar implements ProviderRegistrar {
	
	/** The provider registrar. */
	static ProviderRegistrar providerRegistrar;

	/* (non-Javadoc)
	 * @see net.witerat.cafenatedsql.api.ProviderRegistrar#getFactory(java.lang.String)
	 */
	public Provider getProvider(String name) {
		return providerRegistrar.getProvider(name);
	}

	/* (non-Javadoc)
	 * @see net.witerat.cafenatedsql.api.ProviderRegistrar#getFactoryNames()
	 */
	public Collection<String> getProviderNames() {
		return providerRegistrar.getProviderNames();
	}

	/* (non-Javadoc)
	 * @see net.witerat.cafenatedsql.api.ProviderRegistrar#registerVendor(java.lang.String, java.lang.String, java.lang.String)
	 */
	public void registerVendor(String vendor, String vendorName, String vendorLongName) {
		providerRegistrar.registerVendor(vendor, vendorName, vendorLongName);
	}

	/* (non-Javadoc)
	 * @see net.witerat.cafenatedsql.api.ProviderRegistrar#registerFactory(net.witerat.cafenatedsql.api.driver.DriverFactory, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String)
	 */
	public void registerProvider(Provider provider, String name, String copyright, String longName,
			String description, String vendor) {
		providerRegistrar.registerProvider(provider, name, copyright, longName, description, vendor);
	}

	/* (non-Javadoc)
	 * @see net.witerat.cafenatedsql.api.ProviderRegistrar#getVendorLongName(java.lang.String)
	 */
	public String getVendorLongName(String vendor) {
		return providerRegistrar.getVendorLongName(vendor);
	}

	/* (non-Javadoc)
	 * @see net.witerat.cafenatedsql.api.ProviderRegistrar#getVendorName(java.lang.String)
	 */
	public String getVendorName(String vendor) {
		return providerRegistrar.getVendorName(vendor);
	}

	/* (non-Javadoc)
	 * @see net.witerat.cafenatedsql.api.ProviderRegistrar#getVendor(java.lang.String)
	 */
	public String getVendor(String name) {
		return providerRegistrar.getVendor(name);
	}

	/* (non-Javadoc)
	 * @see net.witerat.cafenatedsql.api.ProviderRegistrar#getVendors()
	 */
	public Collection<String> getVendors() {
		return providerRegistrar.getVendors();
	}

	/**
	 * Gets the single instance of DefaultProviderRegistrar.
	 *
	 * @return single instance of DefaultProviderRegistrar
	 */
	static ProviderRegistrar getInstance() {
		if (providerRegistrar == null) {
			providerRegistrar = new SimpleProviderRegistrar();
		}
		return providerRegistrar;
	}

	@Override
	public String getDescription(String string) {
		// TODO Auto-generated method stub
		return providerRegistrar.getDescription(string);
	}


}
