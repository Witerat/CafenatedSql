package net.witerat.cafenatedsql.spi.driver;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.Date;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Map;


/**
 * The TypeMapper class.
 */
public class TypeMapper {

  /** The name type mapping. */
  private Map<String, Class<?>> types = new HashMap<>();

  /** The default types. */
  private static Map<String, Class<?>> defaultTypes = new HashMap<>();

  /**
   * The defaultMapper singleton is used to map well known types.
   */
  private static TypeMapper defaultMapper;

  {
    // Default type to class map.
    String[][] type = {
        {"string", String.class.getName() },
        {"boolean", Boolean.class.getName() },
        {"byte", Byte.class.getName() },
        {"int", Integer.class.getName() },
        {"short", Short.class.getName() },
        {"long", Long.class.getName() },
        {"bigInt", BigInteger.class.getName() },
        {"decimal", BigDecimal.class.getName() },
        {"date", Date.class.getName() },
        {"time", Time.class.getName() },
        {"timestamp", Timestamp.class.getName() },
        {"double", Double.class.getName() },
        {"float", Float.class.getName() }
       };
    // Initialise default type mapping.
    for (String[] m : type) {
      try {
        defaultTypes.put(m[0], Class.forName(m[1]));
      } catch (ClassNotFoundException e) {
         throw new IllegalStateException(e);
      }
    }

  }

  /**
   * Instantiates a new type mapper.
   *
   */
  protected TypeMapper() {
  }

  /**
   * Gets the type mapped to the specified name. Uses local mapping, then
   *  parent scope, then class loader to determine which <code>Class</code>
   *  object to reurnt.
   *
   * @param typeName
   *          A type identifier.
   * @return the requested type.
   */
  public Class<?> getType(final String typeName) {
    if (types.containsKey(typeName)) {
      return types.get(typeName);
    }
    if (defaultTypes.containsKey(typeName)) {
      return defaultTypes.get(typeName);
    }
    try {
      return Class.forName(typeName);
    } catch (ClassNotFoundException e) {
      return null;
    }
  }

  /**
   * Get the default type mapper.
   * @return the default typeMapper singleton
   */
  public static TypeMapper getDefault() {
    if (defaultMapper == null) {
      defaultMapper = new TypeMapper();
    }
    return defaultMapper;
  }

  /**
   * Register type.
   *
   * @param t
   *          A type identifier
   * @param c
   *          A class object representing type..
   * @throws IllegalAccessException
   *            Attempt to modify default types.
   */
  public void registerType(final String t, final Class<?> c)
      throws IllegalAccessException {
    if (this == defaultMapper) {
      throw new IllegalAccessException("Default mapper cannot be moidifed");
    }
    types.put(t, c);
  }
  /**
   * Remove type.
   *
   * @param t
   *          A type identifier
   * @throws IllegalAccessException
   *            Attempt to modify default types.
   */
  public void removeType(final String t) throws IllegalAccessException {
    if (this == defaultMapper) {
      throw new IllegalAccessException("Default mapper cannot be moidifed");
    }
    types.remove(t);
  }
}
