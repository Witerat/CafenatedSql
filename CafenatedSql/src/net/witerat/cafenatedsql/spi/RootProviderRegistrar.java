package net.witerat.cafenatedsql.spi;

import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

import net.witerat.cafenatedsql.api.Provider;
import net.witerat.cafenatedsql.api.ProviderRegistrar;

/**
 * @author John Hutcheson &lt;witerat.test@gmail.com&gt;
 *
 */
public class RootProviderRegistrar extends SimpleProviderRegistrar {
  /**
   * The registrars property.
   */
  private Map<String, ProviderRegistrar> registrars =
      new LinkedHashMap<>();
  /**
   * The anons property.
   */
  private Set<ProviderRegistrar> anons = new LinkedHashSet<>();

  /**
   * Add an unnamed registrar.
   * @param pr a registrar.
   */
  public void addRegistrar(final ProviderRegistrar pr) {
    registrars.put(getDefaultName(pr), pr);
    anons.add(pr);
  }

  /**
   * Adds a named registrar.
   *
   * @param name A registration for the provider
   * @param pr the provider.
   */
  public void addRegistrar(final String name, final ProviderRegistrar pr) {
    int d = name.indexOf("/");
    if (d == -1) {
      registrars.put(name, pr);
    } else {
      ((RootProviderRegistrar) registrars.get(name.substring(0, d)))
      .addRegistrar(name.substring(d + 1), pr);
    }
  }

  /**
   * Creates a name for an anonymous registrar.
   *
   * @param pr the provider.
   * @return a name used by anonymous registrars.
   */
  public static String getDefaultName(final ProviderRegistrar pr) {
    return "{@" + System.identityHashCode(pr) + "}";
  }

  /**
   * Remove the specified registrar.
   *
   * @param pr The provider registrar.
   * @see RootProviderRegistrar#addRegistrar(ProviderRegistrar)
   */
  public void removeRegistrar(final ProviderRegistrar pr) {
    while (registrars.values().remove(pr)) {
      continue;
    }
    while (anons.remove(pr)) {
      continue;
    }
  }

  /**
   * Removes the named registrar.
   *
   * @param name
   *          the name of the registrar to be removed.
   */
  public void removeRegistrar(final String name) {
    registrars.remove(name);
  }

  /**
   * {@inheritDoc}
   * @see net.witerat.cafenatedsql.spi.SimpleProviderRegistrar
   *    #getDescription(java.lang.String)
   */
  @Override
  public String getDescription(final String pathName) {
    String[] path = pathName.split("/");
    RootProviderRegistrar rpr = this;
    ProviderRegistrar pr = this;
    for (int i = 0; i < path.length - 1; i++) {
      rpr = (RootProviderRegistrar) pr;
      pr = rpr.registrars.get(path[i]);
    }
    if (pr == this) {
      return super.getDescription(path[path.length - 1]);
    }
    return pr.getDescription(path[path.length - 1]);
  }

  /**
   * {@inheritDoc}
   * @see net.witerat.cafenatedsql.spi.SimpleProviderRegistrar
   *    #getProvider(java.lang.String)
   */
  @Override
  public Provider getProvider(final String name) {
    if (name.indexOf('/') == -1) {
      Provider p = super.getProvider(name);
      if (p == null) {
        for (ProviderRegistrar pr : anons) {
          p = pr.getProvider(name);
          if (p != null) {
            break;
          }
        }
      }
      return p;
    } else {
      String[] path = name.split("/");
      RootProviderRegistrar rpr = this;
      ProviderRegistrar pr = this;
      for (int i = 0; i < path.length - 1; i++) {
        rpr = (RootProviderRegistrar) pr;
        pr = rpr.registrars.get(path[i]);
      }
      if (pr == this) {
        return  super.getProviders().get(path[path.length - 1]);
      } else {
        return pr.getProvider(path[path.length - 1]);
      }

    }
  }
}
