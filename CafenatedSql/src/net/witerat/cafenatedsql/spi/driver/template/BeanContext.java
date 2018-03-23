package net.witerat.cafenatedsql.spi.driver.template;

import java.lang.reflect.Field;
import java.text.MessageFormat;
import java.util.HashSet;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.apache.velocity.VelocityContext;
import org.apache.velocity.context.Context;

/**
 * This class allows a bean to be used as a context object. Properties not
 * supported by the bean are back by the internal map implementation;
 *
 */

public class BeanContext extends VelocityContext {
  /**
   * This type is used to indicate type will be inferred from a bean.
   *
   * @author John Hutcheson &lt;witerat.test@gmail.com&gt;
   *
   */
  interface OpenTyped {
  }

  /** The type. */
  private Class<?> type;

  /** The bean. */
  private Object bean;

  /**
   * Instantiates a new bean context.
   */
  public BeanContext() {
    this(null, null, null, null);
  }

  /**
   * Instantiates a new bean context.
   *
   * @param innerContext
   *          the inner context
   */
  public BeanContext(final Context innerContext) {
    this(null, null, null, innerContext);
  }

  /**
   * Instantiates a new bean context.
   *
   * @param bean0
   *          the bean
   * @param innerContext
   *          the inner context
   */
  public BeanContext(final Object bean0, final Context innerContext) {
    this(OpenTyped.class, bean0, null, innerContext);
  }

  /**
   * Instantiates a new untyped-bean context.
   *
   * @param bean0
   *          the bean
   */
  public BeanContext(final Object bean0) {
    this(null, bean0, null, null);
  }

  /**
   * Instantiates a new type-bean context.
   *
   * @param type0
   *          the type
   * @param bean0
   *          the bean
   */
  public BeanContext(final Class<?> type0, final Object bean0) {
    this(type0, bean0, null, null);
  }

  /**
   * Instantiates a new bean context.
   *
   * @param type0
   *          the type
   * @param bean0
   *          the bean
   * @param context0
   *          the context
   * @param innerContext0
   *          the inner context
   */
  public BeanContext(final Class<?> type0, final Object bean0,
      final Map<String, Object> context0, final Context innerContext0) {
    super(context0, innerContext0);
    if (type0 == OpenTyped.class) {
      if (bean == null) {
        this.type = null;
      } else {
        this.type = bean.getClass();
      }
    } else {
      this.type = type0;
    }
    this.bean = bean0;
  }

  /**
   * Change the bean backing this context.
   *
   * @param bean0
   *          the object whose properties will be the properties of this model.
   */
  public void setBean(final Object bean0) {
    if (bean0 == null
        || ((type != null) && !type.isAssignableFrom(bean0.getClass()))) {
      this.bean = bean0;
      if (bean0 != null && type == null) {
        type = bean0.getClass();
      }
    } else {
      throw new IllegalArgumentException("bean is not a/an " + type.getName());
    }
  }

  /**
   * Declare a type for this context.
   *
   * @param type0
   *          the new type constraint
   */
  public void setType(final Class<?> type0) {
    if (bean != null && !type0.isAssignableFrom(this.bean.getClass())) {
      throw new IllegalArgumentException(
          "bean is already assigned to incompatible classe.");
    }
    this.type = type0;
  }

  /**
   * get the context bean, create it if necessary.
   *
   * @return the object underlying this model.
   */
  public Object getBean() {
    if (bean != null && type != null
        && !type.isAssignableFrom(this.bean.getClass())) {
      throw new IllegalArgumentException(
          "bean type  is incompatible with assigned type " + type.getName());
    }
    if (bean == null) {
      bean = createBean();
    }
    return bean;
  }

  /**
   * Create a bean from the define type.
   *
   * @return a new bean of the constrained type;
   */
  public Object createBean() {
    try {
      return type.newInstance();
    } catch (InstantiationException e) {
      e.printStackTrace();
      return null;
    } catch (IllegalAccessException e) {
      e.printStackTrace();
      return null;
    }
  }

  /*
   * (non-Javadoc)
   *
   * @see
   * org.apache.velocity.VelocityContext#internalContainsKey(java.lang.Object)
   */
  @Override
  public boolean internalContainsKey(final Object name) {
    if (name == null) {
      return false;
    }
    try {
      type.getField((String) name);
      return false;
    } catch (NoSuchFieldException e) {
      return super.internalContainsKey(name);
    } catch (SecurityException e) {
      return false;
    }
  }

  /*
   * (non-Javadoc)
   *
   * @see org.apache.velocity.VelocityContext#internalGet(java.lang.String)
   */
  @Override
  public Object internalGet(final String name) {
    if (bean == null || name == null) {
      return null;
    }
    try {
      Field f = type.getField(name);
      Object v = f.get(bean);
      return v;
    } catch (NoSuchFieldException e) {
      return super.internalGet(name);
    } catch (SecurityException e) {
      return null;
    } catch (IllegalArgumentException e) {
      return null;
    } catch (IllegalAccessException e) {
      return null;
    }
  }

  /*
   * (non-Javadoc)
   *
   * @see org.apache.velocity.VelocityContext#internalGetKeys()
   */
  @Override
  public Object[] internalGetKeys() {
    Field[] fa = type.getFields();
    HashSet<String> hsNames = new HashSet<String>();
    for (Field f : fa) {
      hsNames.add(f.getName());
    }
    for (Object fn : super.internalGetKeys()) {
      hsNames.add((String) fn);
    }
    return hsNames.toArray();
  }

  /*
   * (non-Javadoc)
   *
   * @see org.apache.velocity.VelocityContext#internalPut(java.lang.String,
   * java.lang.Object)
   */
  @Override
  public Object internalPut(final String property, final Object value) {
    Field f;
    try {
      f = type.getField(property);
      f.set(bean, value);
    } catch (NoSuchFieldException e) {
      return super.internalPut(property, value);
    } catch (SecurityException e) {
      Logger log = Logger.getLogger(this.getClass().getName());
      log.log(Level.INFO,
          MessageFormat.format(
              "bean of class {0} has no accesible '{1}'  property",
              type.getClass().getName(), property),
          e);
      e.printStackTrace();
    } catch (IllegalArgumentException e) {
      e.printStackTrace();
    } catch (IllegalAccessException e) {
      e.printStackTrace();
    } catch (NullPointerException e) {
      Logger log = Logger.getLogger(this.getClass().getName());
      log.log(Level.INFO,
          "no bean of type " + type.getClass().getName() + " set", e);
    }
    return null;
  }

  /*
   * (non-Javadoc)
   *
   * @see org.apache.velocity.VelocityContext#internalRemove(java.lang.Object)
   */
  @Override
  public Object internalRemove(final Object key) {
    return super.internalRemove(key);
  }

}
