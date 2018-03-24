package net.witerat.cafenatedsql.spi;

import java.util.Collection;

import net.witerat.cafenatedsql.api.Provider;
import net.witerat.cafenatedsql.api.ProviderRegistrar;

/**
 * The Class DefaultProviderRegistrar.
 *
 * @author John Hutcheson &lt;witerat.test@gmail.com&gt;
 */
public class DefaultProviderRegistrar implements ProviderRegistrar {

  /** The provider registrar. */
  private static ProviderRegistrar providerRegistrar;

  /**
   * {@inheritDoc}
   *
   * @see
   * net.witerat.cafenatedsql.api.ProviderRegistrar#getFactory(java.lang.String)
   */
  public Provider getProvider(final String name) {
    return providerRegistrar.getProvider(name);
  }

  /**
   * {@inheritDoc}
   *
   * @see net.witerat.cafenatedsql.api.ProviderRegistrar#getFactoryNames()
   */
  public Collection<String> getProviderNames() {
    return providerRegistrar.getProviderNames();
  }

  /**
   * {@inheritDoc}
   *
   * @see
   * net.witerat.cafenatedsql.api.ProviderRegistrar#registerVendor(java.lang.
   * String, java.lang.String, java.lang.String)
   */
  public void registerVendor(final String vendor, final String vendorName,
      final String vendorLongName) {
    providerRegistrar.registerVendor(vendor, vendorName, vendorLongName);
  }

  /**
   * {@inheritDoc}
   *
   * @see
   * net.witerat.cafenatedsql.api.ProviderRegistrar#registerFactory(
   * net.witerat.cafenatedsql.api.driver.DriverFactory,
   * java.lang.String, java.lang.String,java.lang.String,
   * java.lang.String, java.lang.String)
   */
  public void registerProvider(final Provider provider, final String name,
      final String copyright,
      final String longName, final String description, final String vendor) {
    providerRegistrar.registerProvider(provider, name, copyright,
        longName, description, vendor);
  }

  /**
   * {@inheritDoc}
   *
   * @see
   * net.witerat.cafenatedsql.api.ProviderRegistrar#getVendorLongName(java.lang.
   * String)
   */
  public String getVendorLongName(final String vendor) {
    return providerRegistrar.getVendorLongName(vendor);
  }

  /**
   * {@inheritDoc}
   *
   * @see
   * net.witerat.cafenatedsql.api.ProviderRegistrar#getVendorName(java.lang.
   * String)
   */
  public String getVendorName(final String vendor) {
    return providerRegistrar.getVendorName(vendor);
  }

  /**
   * {@inheritDoc}
   *
   * @see
   * net.witerat.cafenatedsql.api.ProviderRegistrar#getVendor(java.lang.String)
   */
  public String getVendor(final String name) {
    return providerRegistrar.getVendor(name);
  }

  /**
   * {@inheritDoc}
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

  /**
   * {@inheritDoc}
   */
  @Override
  public String getDescription(final String string) {
    // TODO Auto-generated method stub
    return providerRegistrar.getDescription(string);
  }

}
