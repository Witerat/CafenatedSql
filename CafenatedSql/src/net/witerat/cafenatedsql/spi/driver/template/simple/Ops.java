package net.witerat.cafenatedsql.spi.driver.template.simple;
import net.witerat.cafenatedsql.spi.driver.template.simple.Processor.AbstractFetch;

/** Operators. */
enum Ops {
  /** open parenthesis. */
  PARENO(17),
  /** close parenthesis. */
  PARENC(18),

  /** Assignments. */
  SET(16),
  /** sddition assignment. */
  SET_ADD(16),
  /** Subtraction assignment. */
  SET_SUB(16),
  /** Multiplication assignment. */
  SET_MUL(16),
  /** Division assignment. */
  SET_DIV(16),
  /** Modulus assignment. */
  SET_MOD(16),


  /** And assignment. */
  SET_AND(16),
  /** Xor assignment. */
  SET_XOR(16),
  /** Or assignment. */
  SET_OR(16),
  /** Shift assignments. */
  SET_LSH(16),
  /** Right-shift assignment. */
  SET_RSH(16),
  /** Unsigned right-shift assignment. */
  SET_URS(16),


  /** Type cast. */
  CAST(15),
  /** Ternary operator path splitter. */
  TERN_PATHS(14),
  /** Test operator. */
  TERN_TEST(13),

  /** Logical Or. */
  ORL(12),
  /** logical And. */
  ANDL(11),
  /** bitwise Or. */
  ORB(10),
  /** Bitwise exclusive-or. */
  XORB(9),
  /** Bitwise And.*/
  ANDB(8),


  /** Test equality. */
  EQ(7),
  /** Test inequality. */
  NE(7),
  /** Test less-then. */
  LT(6),
  /** Test Not greater. */
  LE(6),
  /** Test greater-than. */
  GT(6),
  /** Test not less than. */
  GE(6),
  /** Test type membership. */
  INSTANCEOF(6),

  /** Left Shift. */
  LSHIFT(5),
  /** Right shift. */
  RSHIFT(5),
  /** Unsigned shift. */
  URSHIFT(5),

  /** Addition. */
  ADDITION(4),
  /** Subtraction. */
  SUBTRACTION(4),

  /** Products. */
  MULTIPLICATION(3),
  /** Division. */
  DIVISION(3),
  /** Modulus. */
  MODULUS(3),

  /** Increment.*/
  UINCR(2),
  /** Decrement.*/
  UDECR(2),
  /** unary plus. */
  UPLUS(2),
  /** unary minus. */
  UMINUS(2),
  /** Bitwise not. */
  NOTB(2),
  /** logical not.*/
  NOTL(2),
  /** Counter operators.*/
  INCR(1),
  /** Decrement. */
  DECR(1),

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
   * Get the precedence value of this operation.
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
