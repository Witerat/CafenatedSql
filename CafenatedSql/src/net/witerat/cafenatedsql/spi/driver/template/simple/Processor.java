package net.witerat.cafenatedsql.spi.driver.template.simple;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import net.witerat.cafenatedsql.api.driver.template.ExpressionFailedException;
import net.witerat.cafenatedsql.api.driver.template.TemplateEngineModel;

/** The class Processor. */
class Processor {
  /**
   * A halt instruction.
   * @author John Hutcheson &lt;witerat.test@gmail.com&gt;
   */
  public static class Halt extends AbstractFetch {

    /* (non-Javadoc)
     * @see net.witerat.cafenatedsql.spi.driver.template.simple.
     * Processor.AbstractFetch#fetch(
     *          net.witerat.cafenatedsql.spi.driver.template.simple.Processor)
     */
    @Override
    Object fetch(final Processor processor) throws ExpressionFailedException {
      processor.halt();
      return null;
    }

  }
  /**
   * Abstract instruction.
   *
   */
  abstract static class AbstractFetch {
    /**
     * @param processor
     *          the active processor.
     * @return the extracted value.
     * @throws ExpressionFailedException
     *           if fails.
     */
    abstract Object fetch(Processor processor)
        throws ExpressionFailedException;
  }
/**
   * Unconditional branch instruction.
   */
  static class Branch extends AbstractFetch {
    /**
     * The branch property - the index of the first fetch of the sub routine.
     */
    private final int branch;

    /**
     * Instantiate a Call instruction object.
     *
     * @param branch0
     *          index of next instruction
     */
    Branch(final int branch0) {
      branch = branch0;
    }

    @Override
    Object fetch(final Processor processor) throws ExpressionFailedException {
      processor.branch(branch);
      return processor;
    }

    /**
     * @return index of next instruction
     */
    protected int getBranch() {
      return branch;
    }
  }

  /**
   * Conditional branch instruction.
   */
  static class BranchIfFalse extends Branch {
    /**
     * Instantiate a BranchIfFalse object.
     *
     * @param branch0
     *          index of instruction
     */
    BranchIfFalse(final int branch0) {
      super(branch0);
    }

    @Override
    Object fetch(final Processor processor) throws ExpressionFailedException {
      Object v = processor.pop();
      if (v == null || (v instanceof Boolean && Boolean.FALSE.equals(v))
          || (v instanceof String && "".equals(v))
          || (v instanceof Number
              && ((v instanceof Float || v instanceof Double)
                  && ((Number) v).doubleValue() == 0.0d)
              || (!(v instanceof Float || v instanceof Double)
                  && ((Number) v).longValue() == 0L))
          || (v instanceof BigInteger && BigInteger.ZERO.equals(v))
          || (v instanceof BigDecimal && BigDecimal.ZERO.equals(v))) {
        return super.fetch(processor);
      }
      return processor;
    }
  }

  /**
   * Macro call instruction.
   *
   * @author John Hutcheson &lt;witerat.test@gmail.com&gt;
   */
  static class Call extends Branch {
    /**
     * Instantiate a Call object.
     *
     * @param branch0
     *          index of first instruction
     */
    Call(final int branch0) {
      super(branch0);
    }

    @Override
    Object fetch(final Processor processor) throws ExpressionFailedException {
      processor.call(getBranch());
      return processor;
    }
  }

  /**
   * An instruction to cast the top value to a given type.
   */
  static class CastFetch extends AbstractFetch {
    /**
     * The type property.
     */
    private Class<?> type;

    /**
     * Instantiate a(n) CastFetch object.
     *
     * @param type0
     *          the cast target type.
     */
    CastFetch(final Class<?> type0) {
      type = type0;
    }

    @Override
    Object fetch(final Processor processor) throws ExpressionFailedException {
      Object v = processor.operand(0);
      try {
        type.cast(v);
      } catch (ClassCastException e) {
        throw new ExpressionFailedException(e);
      }
      processor.type = type;
      return null;
    }
  }

  /**
   * Branch to top of control stack instruction.
   */
  static class EndCall extends AbstractFetch {
    @Override
    Object fetch(final Processor processor) throws ExpressionFailedException {
      processor.endCall();
      return processor;
    }
  }

  /**
   * Object field property fetch instruction.
   *
   */
  static class FeildFetch extends AbstractFetch {
    /**
     * The name property - name of bean field.
     */
    private String name;

    /**
     * Instantiate a(n) FieldFetch object.
     *
     * @param name0
     *          the field name
     */
    FeildFetch(final String name0) {
      name = name0;
    }

    @Override
    Object fetch(final Processor processor) throws ExpressionFailedException {
      Object bean = processor.pop();
      Class<?> c = bean.getClass();
      Field f;
      try {
        f = c.getField(name);
      } catch (NoSuchFieldException | SecurityException e) {
        throw new ExpressionFailedException("Feild read", e);
      }
      try {
        return processor.push(f.get(bean));
      } catch (IllegalArgumentException | IllegalAccessException e) {
        throw new ExpressionFailedException("Field read", e);
      }
    }
  }

  /** Produce a literal value. */
  static class LiteralFetch extends AbstractFetch {
    /** The literal constant. */
    private final Object literal;
    /** the type of the literal constant. */
    private final Class<?> type;

    /** create a literal push operation.
     *
     * Instantiate a(n) LiteralFetch object.
     * @param literal0 The literal constant to push;
     * @param type0 the type of the literal constant;
     */
    LiteralFetch(final Object literal0, final Class<?> type0) {
      this.literal = literal0;
      this.type = type0;
    }

    /**
     * Fetch the literal and set its type.
     * @param processor The processing context.
     */
    @Override
    Object fetch(final Processor processor) throws ExpressionFailedException {
      processor.setType(type);
      processor.push(literal);
      return literal;
    }

    /**
     * The literal constant.
     * @return The literal constant.
     */
    protected Object getLiteral() {
      return literal;
    }

    /**
     * The type of the literal constant.
     * @return The type of the literal constant.
     */
    protected Class<?> getType() {
      return type;
    }

  }

  /**
   * Model property fetch instruction.
   */
  static class MapFetch extends AbstractFetch {

    @Override
    Object fetch(final Processor processor) throws ExpressionFailedException {
      Object key = processor.pop();
      Map<?, ?> model;
      try {
        Map<?, ?> m = (Map<?, ?>) processor.pop();
        model = m;
      } catch (ClassCastException cce) {
        throw new ExpressionFailedException("not a map", cce);
      }
      if (model == null) {
        throw new ExpressionFailedException("no map",
            new NullPointerException());
      }
      return processor.push(model.get(key));
    }
  }

  /**
   * Method invocation instruction.
   */
  static class MethodFetch extends AbstractFetch {
    /** Declared type of target object. */
    private Class<?> type;

    /** The name property - key to model property. */
    private String name;
    /**
     * The args property - method signature/formal arguments.
     */
    private Class<?>[] args;
    /** the number of informal parameters. */
    private int count;
    /**
     * The method property - the target method to invoke.
     */
    private Method method;
    /**
     * The fail property - deferred failure.
     */
    private Exception fail;

    /**
     * Instantiate a MethodFetch object.
     *
     * @param type0
     *          The declared type of object;
     * @param name0
     *          method name
     * @param args0
     *          formal parameters
     * @param count0
     *          number of informal parameters;
     */
    MethodFetch(final Class<?> type0, final String name0,
        final Class<?>[] args0, final int count0) {
      count = count0;
      type = type0;
      name = name0;
      args = args0;
      try {
        method = type0.getMethod(name, args);
      } catch (NoSuchMethodException | SecurityException e) {
        fail = e;
      }
    }

    @Override
    Object fetch(final Processor processor) throws ExpressionFailedException {
      if (null != fail) {
        throw new ExpressionFailedException("Method acceess", fail);
      }
      Object[] parVal = new Object[count];
      processor.pop(parVal);
      Object bean = processor.pop();
      type.cast(bean);
      try {
        final Object rv = method.invoke(bean, parVal);
        final Class<?> rType = method.getReturnType();
        processor.setType(rType);
        if (rType != Void.TYPE) {
          processor.push(rv);
        }
        return rv;
      } catch (IllegalAccessException | IllegalArgumentException
          | InvocationTargetException e) {
        throw new ExpressionFailedException("Method invocation", e);
      }
    }
  }

  /**
   * Extract value from model instruction.
   *
   * @author John Hutcheson &lt;witerat.test@gmail.com&gt;
   */
  static class ModelFetch extends AbstractFetch {
    /**
     * The name property - the name of the model object.
     */
    private String name;
    /**
     * The model property - the model from which the model object is extracted.
     */
    private TemplateEngineModel model;

    /**
     * Instantiate a(n) ModelFetch object.
     *
     * @param model0
     *          source model
     * @param name0
     *          property key
     */
    ModelFetch(final TemplateEngineModel model0, final String name0) {
      model = model0;
      name = name0;
    }

    @Override
    Object fetch(final Processor processor) throws ExpressionFailedException {
      if (model == null) {
        throw new ExpressionFailedException("no model",
            new NullPointerException());
      }
      Object value = model.get(name);
      if (value == null) {
        processor.type = null;
      } else {
        processor.type = value.getClass();
      }
      return value;
    }
  }

  /** The code property - the instructions. */
  private final AbstractFetch[] code;
  /** A cast type for the top operand. */
  private Class<?> type;
  /** The next instruction of flow it changing. */
  private int branch = -1;
  /** The next instruction. */
  private int ip = 0;
  /** Return indexes for macro calls. */
  private ArrayList<Integer> ipstack = new ArrayList<>();
  /** The data stack. */
  private ArrayList<Object> data = new ArrayList<>();
  /** Cache control of top of data stack. */
  private boolean data0set;
  /** Cache control of second from top of data stack. */
  private boolean data1set;
  /** Top of data stack. */
  private Object data0;
  /** Cache second from top of data stack. */
  private Object data1;
  /** First level cache control for control stack. */
  private boolean ip0set;
  /** First level cache value for control stack. */
  private int ip0;
  /** Second level cache control for control stack. */
  private boolean ip1set;
  /** Second level cache value for control stack. */
  private int ip1;


  /**
   * Construct a process to execute a plan.
   *
   * @param plan
   *          The plan
   */
  Processor(final AbstractFetch[] plan) {
    code = plan;
  }


  /**
   * Branch to another fetch which will finish at an endCall.
   *
   * @param branch0
   *          the index of the first instruction.
   */
  void branch(final int branch0) {
    this.branch = branch0;
  }

  /** First level ip cache.
   * @return cache for return ip.
   */
  int getIp0() {
    return ip0;
  }
  /** Next likely instruction.
   * @return next likely instruction.
   */
  int getIp() {
    return ip;
  }
  /** second level ip cache.
   * @return cache for return ip.
   */
  int getIp1() {
    return ip1;
  }

  /** next instruction pointer when control changed.
   * @return next instruction to execute.
   */
  int getBranch() {
    return branch;
  }

  /**
   * The stack of ip return values.
   * @return the stack of ip return values.
   */
  ArrayList<Integer> ipStack() {
    return ipstack;
  }
  /**
   * Branch to subroutine which will finish at an endCall.
   *
   * @param branch0
   *          the index of the first instruction.
   */
  void call(final int branch0) {
    ++ip;
    if (!ip0set) {
      ip0set = true;
      ip0 = ip;
    } else if (!ip1set) {
      ip1set = ip0set;
      ip1 = ip0;
      ip0 = ip;
    } else {
      ipstack.add(ip1);
      ip1 = ip0;
      ip0 = ip;
    }
    this.branch = branch0;
  }

  /**
   * Set execution to continue at a previously stacked location.
   * @throws ExpressionFailedException if the stack is empty
   *    - underflow.
   */
  public void endCall() throws ExpressionFailedException {
    if (ip0set) {
      branch = ip0;
      ip0set = false;
    } else if (ip1set) {
      branch = ip1;
      ip1set = false;
    } else {
      int size = ipstack.size();
      switch (size) {
      case 0:
        throw new ExpressionFailedException("Control stack underflow.");
      case 1:
        branch = ipstack.remove(size - 1);
        break;
      case 2:
        branch = ipstack.remove(--size);
        ip0 =  ipstack.remove(size - 1);
        ip0set = true;
        break;
      default:
        branch = ipstack.remove(--size);
        ip0 =  ipstack.remove(--size);
        ip0set = true;
        ip1 =  ipstack.remove(--size);
        ip1set = true;
      }
    }
  }

  /**
   * Execute a set of fetches/instructions.
   *
   * @throws ExpressionFailedException
   *           when evaluation fails
   */
  void execute() throws ExpressionFailedException {
    while (branch != Integer.MIN_VALUE) {
      int i = execute0();
      if (i < 0) {
        break;
      }
      AbstractFetch op = code[i];
      op.fetch(this);
    }
  }


  /**
   * Instruction scheduling.
   * @return next instruction index or <code>-1</code> if execution should
   *            terminate.
   */
   int execute0() {
    int i = ip;
    if (branch == -1) {
      ip++;
    } else {
      ip = branch;
      i = ip;
      branch = -1;
    }
    if (i >= code.length) {
      return -1;
    }
    return i;
  }

  /**
   * Getter of <code>type</code> property.
   *
   * @return produced or cast type.
   */
  Class<?> getType() {
    return type;
  }

  /**
   * forces the execution to halt at the next instruction cycle.
   */
  void halt() {
    branch = Integer.MIN_VALUE;
  }

  /**
   * Fetch the top <code>i</code><sup>th</sup> value from the stack.
   *
   * @param i
   *          index of the operand to extract.
   * @return the top <code>i</code><sup>th</sup> value.
   * @throws ExpressionFailedException
   *           if failed
   */
  public Object operand(final int i) throws ExpressionFailedException {
    int ii = i;
    int o = 1;
    if (!data0set) {
      ii++;
    } else {
      o++;
    }
    if (!data1set) {
      if (ii > 0) {
        ii++;
      }
    } else {
      o++;
    }
    if (ii == 0) {
      data0set = false;
      return data0;
    } else if (ii == 1) {
      data1set = false;
      return data1;
    } else {
      int x = data.size() - i - o;
      if (x >= 0) {
        return data.get(x);
      }
    }
    throw new ExpressionFailedException("Value stack underflow");
  }

  /**
   * Get top data stack value.
   *
   * @return top value of from the data stack.
   * @throws ExpressionFailedException
   *           if stack is empty
   */
  public Object pop() throws ExpressionFailedException {
    if (data0set) {
      data0set = false;
      return data0;
    } else if (data1set) {
      data1set = false;
      return data1;
    } else {
      if (data.isEmpty()) {
        throw new ExpressionFailedException("Stack under flow");
      }
      return data.remove(data.size() - 1);
    }
  }

  /**
   * Populate an array with value from the top of the stack.
   * @param args An array to be populated from the stack.
   * @throws ExpressionFailedException
   *    if stack underflow.
   */
  void pop(final Object[] args) throws ExpressionFailedException {
    if (args.length != 0) {
      if (data1set) {
        data.add(data1);
      }
      if (data0set) {
        data.add(data0);
      }
      data1set = false;
      data0set = false;
      if (data.size() < args.length) {
        data.clear();
        throw new ExpressionFailedException("Stack under flow");
      }
      final List<Object> subList = data.subList(
          data.size() - args.length,
          data.size());
      subList.toArray(args);
      subList.clear();
    }
  }
  /**
   * Put a data value on the data stack.
   *
   * @param value
   *          the value to push.
   * @return the new top of stack value.
   */
  public Object push(final Object value) {
    if (!data0set) {
      data0 = value;
      data0set = true;
    } else {
      if (data1set) {
        data.add(data1);
      }
      data1 = data0;
      data1set = data0set;
      data0 = value;
      data0set = true;
    }
    return value;
  }

  /**
   * Set a new type.
   * @param type0 the new type.
   */
  void setType(final Class<?> type0) {
    this.type = type0;
  }
}
