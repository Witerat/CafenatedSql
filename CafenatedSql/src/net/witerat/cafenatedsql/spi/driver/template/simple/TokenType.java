package net.witerat.cafenatedsql.spi.driver.template.simple;

/** Enumeration of tokens. */
enum TokenType {
  /** An <i>identifier</i> token. */
  ID,
  /** Logical not. */
  LNOT,
  /** Less Than. */
  LT,
  /** Less Than or equal. */
  LTE,
  /** left Shift. */
  LEFT_SHIFT,
  /** Right Shift. */
  RIGHT_SHIFT,
  /** Unsigned Right shift. */
  RIGHT_ARITH_SHIFT,
  /** Diamond. */
  DIAMOND,
  /** Modulus. */
  MODULUS,
  /** exclusive bit OR. */
  XOR,
  /** Bitwise AND. */
  BIT_AND,
  /** Logical AND. */
  LOG_AND,
  /** Bitwise OR. */
  BIT_OR,
  /** Logical OR. */
  LOG_OR,
  /** multiplication. */
  MULTIPLY,
  /** Open Parenthesis. */
  OPAREN,
  /** Close Parenthesis. */
  CPAREN,
  /** Unary negation/subtraction. */
  MINUS,
  /** Addition/concatenation. */
  PLUS,
  /** Division. */
  DIVIDE,
  /** Ternary question. */
  TERN1,
  /** Ternary colon. */
  COLON,
  /** Statement end. */
  SEMI,
  /** Decimal point/property. */
  DOT,
  /** variable formal param list. */
  ELIPSIS,
  /** Separator. */
  COMMA,
  /** Open braces. */
  OCURL,
  /** Close braces. */
  CCURL,
  /** Open Brackets. */
  OSQUARE,
  /** Close Brackets. */
  CSQUARE,
  /** Greater than. */
  GT,
  /** Greater than or equal. */
  GTE,
  /** Not equal. */
  NE,
  /** assignment. */
  ASSIGN,
  /** Equal. */
  EQU,
  /** Character quote. */
  APOS,
  /** Bitwise NOT. */
  BIT_NOT,
  /** String quote. */
  QUOTE,
  /** The <code>byte</code> type. */
  TBYTE,
  /** The <code>short</code> Type. */
  TSHORT,
  /** The <code>int</code> type. */
  TINT,
  /** The <code>boolean</code> type. */
  TBOOLEAN,
  /** The <code>long</code> type. */
  TLONG,
  /** The <code>double</code> type. */
  TDOUBLE,
  /** The <code>float</code> Type. */
  TFLOAT,
  /** The <code>char</code> Type. */
  TCHAR,
  /** The <code>import</code> keyword. */
  KIMPORT,
  /** The RTTI operator. */
  KINSTANCEOF,
  /** Type Declaration. */
  KINTERFACE,
  /** The <code>new</code> Operator. */
  NEW,
  /** The <code>switch</code> keyword. */
  SWITCH,
  /** The <code>case</code> keyword. */
  CASE,
  /** The <code>break</code> keyword. */
  BREAK,
  /** the <code>class</code> keyword. */
  CLASS,
  /** the <code>implements</code> keyword. */
  IMPLEMENTS,
  /** the <code>public</code> keyword. */
  PUBLIC,
  /** the <code>protected</code> keyword. */
  PROTECTED,
  /** the <code>private</code> keyword. */
  PRIVATE,
  /** the <code>abstract</code> keyword. */
  ABSTRACT,
  /** the <code>static</code> keyword. */
  STATIC,
  /** the <code>extends</code> keyword. */
  EXTENDS,
  /** the <code>for</code> keyword. */
  FOR,
  /** the <code>try</code> keyword. */
  TRY,
  /** the <code>catch</code> keyword. */
  CATCH,
  /** the <code>enum</code> keyword. */
  ENUM,
  /** the <code>finally</code> keyword. */
  FINALLY,
  /** the <code>final</code> keyword. */
  FINAL;

  /**
   * The SYMBOLS property.
   */
  static final String[] SYMBOLS = ("! < <= << >> >>> <> % ^ & && | || "
      + "* ( ) - + / ? : ; . ... , { } [ ] > >= != = == ' ~ \" "
      + "byte short int boolean long double float char "
      + "import instanceof interface new switch case break "
      + "class implements public protected private abstract "
      + "static extends for try catch enum finally final").split(" ");
}
