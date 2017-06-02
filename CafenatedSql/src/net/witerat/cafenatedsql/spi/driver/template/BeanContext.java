package net.witerat.cafenatedsql.spi.driver.template;

import java.lang.reflect.Field;
import java.util.HashSet;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.apache.velocity.VelocityContext;
import org.apache.velocity.context.Context;

// TODO: Auto-generated Javadoc
/**
 * This class allows a bean to be used as a context object. Properties not
 * supported by the bean are back by the internal map implementation;
 *
 */

public class BeanContext extends VelocityContext {

	/** The type. */
	Class<?> type;

	/** The bean. */
	Object bean;

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
	 *            the inner context
	 */
	public BeanContext(Context innerContext) {
		this(null, null, null, innerContext);
	}

	/**
	 * Instantiates a new bean context.
	 *
	 * @param bean
	 *            the bean
	 * @param innerContext
	 *            the inner context
	 */
	public BeanContext(Object bean, Context innerContext) {
		this(bean == null ? null : bean.getClass(), bean, null, innerContext);
	}

	/**
	 * Instantiates a new untyped-bean context.
	 *
	 * @param bean
	 *            the bean
	 */
	public BeanContext(Object bean) {
		this(null, bean, null, null);
	}

	/**
	 * Instantiates a new type-bean context.
	 *
	 * @param type
	 *            the type
	 * @param bean
	 *            the bean
	 */
	public BeanContext(Class<?> type, Object bean) {
		this(type, bean, null, null);
	}

	/**
	 * Instantiates a new bean context.
	 *
	 * @param type
	 *            the type
	 * @param bean
	 *            the bean
	 * @param context
	 *            the context
	 * @param innerContext
	 *            the inner context
	 */
	public BeanContext(Class<?> type, Object bean, Map<String, Object> context, Context innerContext) {
		super(context, innerContext);
		this.type = type;
		this.bean = bean;
	}

	/**
	 * Change the bean backing this context.
	 *
	 * @param bean
	 *            the object whose properties will be the properties of this
	 *            model.
	 */
	public void setBean(Object bean) {
		if (bean == null || ((type != null) && !type.isAssignableFrom(bean.getClass()))) {
			this.bean = bean;
			if (bean != null && type == null)
				type = bean.getClass();
		} else
			throw new IllegalArgumentException("bean is not a/an " + type.getName());
	}

	/**
	 * Declare a type for this context.
	 * 
	 * @param type
	 *            the new type constraint
	 */
	public void setType(Class<?> type) {
		if (bean != null && !type.isAssignableFrom(this.bean.getClass())) {
			throw new IllegalArgumentException("bean is already assigned to incompatible classe.");
		}
		this.type = type;
	}

	/**
	 * get the context bean, create it if necessary.
	 * 
	 * @return the object underlying this model.
	 */
	public Object getBean() {
		if (bean != null && type != null && !type.isAssignableFrom(this.bean.getClass())) {
			throw new IllegalArgumentException("bean type  is incompatible with assigned type " + type.getName());
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
	public boolean internalContainsKey(Object name) {
		if (name == null)
			return false;
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
	public Object internalGet(String name) {
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
		HashSet<String> fNames = new HashSet<String>();
		for (Field f : fa) {
			fNames.add(f.getName());
		}
		for (Object fn : super.internalGetKeys()) {
			fNames.add((String) fn);
		}
		return fNames.toArray();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.apache.velocity.VelocityContext#internalPut(java.lang.String,
	 * java.lang.Object)
	 */
	@Override
	public Object internalPut(String property, Object value) {
		Field f;
		try {
			f = type.getField(property);
			f.set(bean, value);
		} catch (NoSuchFieldException e) {
			return super.internalPut(property, value);
		} catch (SecurityException e) {
			Logger log = Logger.getLogger(this.getClass().getName());
			log.log(Level.INFO,
					"bean of class " + type.getClass().getName() + "has no accesible '" + property + "' property", e);
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (NullPointerException e) {
			Logger log = Logger.getLogger(this.getClass().getName());
			log.log(Level.INFO, "no bean of type " + type.getClass().getName() + " set", e);
		}
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.apache.velocity.VelocityContext#internalRemove(java.lang.Object)
	 */
	@Override
	public Object internalRemove(Object key) {
		return super.internalRemove(key);
	}

}
