package net.witerat.cafenatedsql.spi.driver.template.simple;
import net.witerat.cafenatedsql.spi.driver.template.simple.Processor.AbstractFetch;

/** Operators. */
enum Ops {
  /** open parenthesis. */
  PARENO(17),
  /** close parenthesis. */
  PARENC(18),

  /** Assignments. */
  SET(16), SET_ADD(16), SET_SUB(16), SET_MUL(16), SET_DIV(16), SET_MOD(16),
  /** Logical assignments. */
  SET_AND(16), SET_XOR(16), SET_OR(16),
  /** Shift assignments. */
  SET_LSH(16), SET_RSH(16), SET_URS(16),

  /** Type cast. */
  CAST(15),
  /** Ternary operator. */
  TERN2(14), TERN1(13),
  /** Logical operators. */
  ORL(12), ANDL(11), ORB(10), XORB(9), ANDB(8),

  /** Relational Operators. */
  EQ(7), NE(7), LT(6), LE(6), GT(6), GE(6), INSTANCEOF(6),
  /** Shift operators. */
  LSHIFT(5), RSHIFT(5), URSHIFT(5),
  /** Terms. */
  ADDITION(4), SUBTRACTION(4),
  /** Products. */
  MULTIPLICATION(3), DIVISION(3), MODULUS(3),
  /** Unary operators. */
  UINCR(2), UDECR(2), UPLUS(2), UMINUS(2), NOTB(2), NOTL(2),
  /** Counter operators.*/
  INCR(1), DECR(1),

  /** Values. */
  LITERAL(0) {
    @Override
    public AbstractFetch[] encode(final Object[] op) {
      Object literal = op[1];
      Class<?> literalType = (Class<?>) op[2];
      AbstractFetch[] afa = new AbstractFetch[]{
          new Processor.LiteralFetch(literal, literalType)};
      return afa;
    }
  },
  /** Value of property. */
  PROPERTY(0);

  /**
   * Instantiate operator enumeration value object.
   * @param priority
   *          the precedence value.
   */
  Ops(final int priority) {
    nPriority = priority;
  }

  /** the priority of the operation). */
  private final int nPriority;

  /**
   * Get the precedence of the operation.
   * @return this operation's precedence.
   */
  int precedence() {
    return nPriority;
  }

  /**
   * Encode as an AbsFetch processor sequence.
   * @param op an array containing parameters for the opcode.
   * @return A plan for executing the opcode.
   */
  public AbstractFetch[] encode(final Object[] op) {
    throw new IllegalStateException("operator has no runtime");
  }
}
