package net.witerat.cafenatedsql.spi;

import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Map;

import net.witerat.cafenatedsql.api.Provider;
import net.witerat.cafenatedsql.api.ProviderRegistrar;

/**
 * The Class AbstractProvider encapsulates the framework database providers
 * which offers one or more database product models .
 */
public class SimpleProviderRegistrar implements ProviderRegistrar {

  /** The providers. */
  private Map<String, Provider> providers = new LinkedHashMap<>();

  /** The description. */
  private Map<String, String> description = new LinkedHashMap<>();

  /** The copyright. */
  private Map<String, String> copyright = new LinkedHashMap<>();

  /** The long name. */
  private Map<String, String> longName = new LinkedHashMap<>();

  /** The vendor. */
  private Map<String, String> vendor = new LinkedHashMap<>();

  /** The vendor name. */
  private Map<String, String> vendorName = new LinkedHashMap<>();

  /** The vendor long name. */
  private Map<String, String> vendorLongName = new LinkedHashMap<>();

  /**
   * Gets the description for the specified provider.
   *
   * @param name
   *          the name
   * @return the description
   */
  public String getDescription(final String name) {
    return description.get(name);
  }

  /**
   * Gets the long name of the specified provider.
   *
   * @param name
   *          the name
   * @return the long name
   */
  public String getLongName(final String name) {
    return longName.get(name);
  }

  /**
   * Get a specified provider.
   *
   * @param name
   *          The registered name of the provider;
   * @return the factory
   * @see net.witerat.cafenatedsql.api.ProviderRegistrar#getProvider(
   *    java.lang.String)
   */
  @Override
  public Provider getProvider(final String name) {
    return providers.get(name);
  }

  /**
   * Get provider names. useful for publishing.
   *
   * @return the provider names
   * @see net.witerat.cafenatedsql.api.ProviderRegistrar#getProviderNames()
   */
  @Override
  public Collection<String> getProviderNames() {
    LinkedHashSet<String> names = new LinkedHashSet<String>();
    names.addAll(providers.keySet());
    return names;
  }

  /**
   * Get the map of providers by name.
   * @return the name-> provider map.
   */
  protected Map<String, Provider> getProviders() {
    return providers;
  }

  /**
   * Gets the description.
   *
   * @param name
   *          the name
   * @return the description
   */
  @Override
  public String getVendor(final String name) {
    return description.get(name);
  }

  /**
   * Gets the vendor long name.
   *
   * @param vendor0
   *          the vendor id.
   * @return the long name for the vendor.
   */
  @Override
  public String getVendorLongName(final String vendor0) {
    return vendorLongName.get(vendor0);
  }

  /**
   * Gets the vendor name.
   *
   * @param vendor0
   *          the vendor id.
   * @return vendor name
   */
  @Override
  public String getVendorName(final String vendor0) {
    return vendorName.get(vendor0);
  }

  /*
   * (non-Javadoc)
   *
   * @see net.witerat.cafenatedsql.api.ProviderRegistrar#getVendors()
   */
  @Override
  public Collection<String> getVendors() {
    return vendor.keySet();
  }

  /**
   * Register a provider.
   *
   * @param provider
   *          the provider
   * @param name
   *          the name
   * @param copyright0
   *          short copyright message
   * @param longName0
   *          the long name
   * @param description0
   *          the description
   * @param vendor0
   *          Vender id
   */
  @Override
  public void registerProvider(final Provider provider, final String name,
      final String copyright0, final String longName0,
      final String description0, final String vendor0) {
    this.providers.put(name, provider);
    this.copyright.put(name, copyright0);
    this.longName.put(name, longName0);
    this.description.put(name, description0);
    this.vendor.put(name, vendor0);
  }

  /**
   * Register vendor.
   *
   * @param vendor0
   *          the vendor id
   * @param vendorName0
   *          the vendor name
   * @param vendorLongName0
   *          the vendor long name
   */
  @Override
  public void registerVendor(final String vendor0, final String vendorName0,
      final String vendorLongName0) {
    this.vendorName.put(vendor0, vendorName0);
    this.vendorLongName.put(vendor0, vendorLongName0);
  }

}
