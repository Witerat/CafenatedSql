package net.witerat.cafenatedsql.spi;

import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.Map;

import net.witerat.cafenatedsql.api.Provider;
import net.witerat.cafenatedsql.api.ProviderRegistrar;

// TODO: Auto-generated Javadoc
/**
 * The Class AbstractProvider encapsulates the framework database providers
 * which offers one or more database product models .
 */
public class SimpleProviderRegistrar implements ProviderRegistrar {

	/** The factory. */
	Map<String, Provider> providers;

	/** The description. */
	Map<String, String> description;

	/** The copyright. */
	Map<String, String> copyright;

	/** The long name. */
	Map<String, String> longName;

	/** The vendor. */
	Map<String, String> vendor;

	/** The vendor name. */
	Map<String, String> vendorName;

	/** The vendor long name. */
	Map<String, String> vendorLongName;

	/**
	 * 
	 * Get a specified factory.
	 *
	 * @param name
	 *            The registered name o the factory;
	 * @return the factory
	 * @see net.witerat.cafenatedsql.api.ProviderRegistrar#getProvider(java.lang.String)
	 */
	@Override
	public Provider getProvider(String name) {
		return providers.get(name);
	}

	/**
	 * 
	 * Get Factory names. useful for publishing.
	 *
	 * @return the factory names
	 * @see net.witerat.cafenatedsql.api.ProviderRegistrar#getProviderNames()
	 */
	@Override
	public Collection<String> getProviderNames() {
		LinkedHashSet<String> names = new LinkedHashSet<String>();
		names.addAll(providers.keySet());
		return names;
	}

	/**
	 * Gets the long name of the specified provider.
	 *
	 * @param name
	 *            the name
	 * @return the long name
	 */
	public String getLongName(String name) {
		return longName.get(name);
	}

	/**
	 * Gets the description for the specified provider.
	 *
	 * @param name
	 *            the name
	 * @return the description
	 */
	public String getDescription(String name) {
		return description.get(name);
	}
	
	/* (non-Javadoc)
	 * @see net.witerat.cafenatedsql.api.ProviderRegistrar#getVendors()
	 */
	@Override
	public Collection<String> getVendors(){
		return vendor.keySet();
	}

	/**
	 * Gets the description.
	 *
	 * @param name
	 *            the name
	 * @return the description
	 */
	@Override
	public String getVendor(String name) {
		return description.get(name);
	}

	/**
	 * Gets the vendor name.
	 *
	 * @param vendor the vendor id.
	 * @return vendor name
	 */
	@Override
	public String getVendorName(String vendor){
		return vendorName.get(vendor);
	}
	
	/**
	 * Gets the vendor long name.
	 *
	 * @param vendor the vendor id.
	 * @return the long name for the vendor.
	 */
	@Override
	public String getVendorLongName(String vendor){
		return vendorLongName.get(vendor);
	}
	
	/**
	 * Register factory.
	 *
	 * @param provider
	 *            the factory
	 * @param name
	 *            the name
	 * @param copyright
	 *            short copyright message
	 * @param longName
	 *            the long name
	 * @param description
	 *            the description
	 * @param vendor
	 *            Vender id
	 */
	@Override
	public void registerProvider(Provider provider, String name, String copyright, String longName,
			String description, String vendor) {
		this.providers.put(name, provider);
		this.copyright.put(name, copyright);
		this.longName.put(name, longName);
		this.description.put(name, description);
		this.vendor.put(name, vendor);
	}

	/**
	 * Register vendor.
	 *
	 * @param vendor
	 *            the vendor id
	 * @param vendorName
	 *            the vendor name
	 * @param vendorLongName
	 *            the vendor long name
	 */
	@Override
	public void registerVendor(String vendor, String vendorName, String vendorLongName) {
		this.vendorName.put(vendor, vendorName);
		this.vendorLongName.put(vendor, vendorLongName);
	}

}
