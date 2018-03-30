package net.witerat.cafenatedsql.api;

import java.util.Collection;

import net.witerat.cafenatedsql.spi.RootProviderRegistrar;

// TODO: Auto-generated Javadoc
/**
 * The Interface ProviderRegistrar.
 */
public interface ProviderRegistrar {

	ProviderRegistrar ROOT_REGISTRAR = new RootProviderRegistrar();

	/**
	 * Gets the provider.
	 *
	 * @param name the name
	 * @return the provider
	 */
	Provider getProvider(String name);

	/**
	 * Gets the provider names.
	 *
	 * @return the provider names
	 */
	Collection<String> getProviderNames();

	/**
	 * Register a vendor.
	 *
	 * @param vendor the vendor id. A URI, web address or other unique identifier.
	 * @param vendorName the vendor name, something simple like Oracle
	 * @param vendorLongName the vendor long name, like Oracle Systems Incorporated 
	 */
	void registerVendor(String vendor, String vendorName, String vendorLongName);

	/**
	 * Register provider.
	 *
	 * @param provider the provider
	 * @param name the name of the provider
	 * @param copyright the copyright applicable to the provider
	 * @param longName the long name the long human readable name of the provider
	 * @param description A description of the provider
	 * @param vendor the vendor id associate with the provider.
	 */
	void registerProvider(Provider provider, String name, String copyright, String longName, String description, String vendor);

	/**
	 * Gets the vendor long name.
	 *
	 * @param vendor the vendor id
	 * @return the vendor's long name
	 */
	String getVendorLongName(String vendor);

	/**
	 * Gets the vendor name.
	 *
	 * @param vendor the vendor
	 * @return the vendor's short name
	 */
	String getVendorName(String vendor);

	/**
	 * Gets the vendor.
	 *
	 * @param name the provider name
	 * @return the vendor associated with the provider
	 */
	String getVendor(String name);

	/**
	 * Gets the vendors.
	 *
	 * @return A collection of the registered vendor identifiers.
	 */
	Collection<String> getVendors();

	String getDescription(String string);

}