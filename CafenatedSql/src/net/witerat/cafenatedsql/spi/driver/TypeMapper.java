package net.witerat.cafenatedsql.spi.driver;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.Date;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Map;

/**
 * The Class TypeMapper.
 */
public class TypeMapper {

  /** The types. */
  private Map<String, Class<?>> types = new HashMap<>();

  /** The default types. */
  private static Map<String, Class<?>> defaultTypes = new HashMap<>();

  /**
   * The defaultMapper singleton is used to map well known types.
   */
  private static TypeMapper defaultMapper;

  /** Initialise default type map. */
  {
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
    for (String[] m : type) {
      try {
        defaultTypes.put(m[0], Class.forName(m[1]));
      } catch (ClassNotFoundException e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
      }
    }

  }

  /**
   * Instantiates a new type mapper.
   *
   */
  protected TypeMapper() {
    // TODO Auto-generated constructor stub
  }

  /**
   * Gets the type.
   *
   * @param typeName
   *          the type name
   * @return the type
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
   *          the t
   * @param c
   *          the c
   */
  public void registerType(final String t, final Class<?> c) {
    types.put(t, c);
  }
}
