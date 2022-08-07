package net.witerat.cafenatedsql.spi.driver.template.simple;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Stack;

import net.witerat.cafenatedsql.api.driver.template.ExpressionFailedException;
import net.witerat.cafenatedsql.api.driver.template.TemplateEngineModel;
import net.witerat.cafenatedsql.spi.driver.template.simple.Processor.AbstractFetch;

/**
 * @author John Hutcheson &lt;witerat.test@gmail.com&gt;
 *
 */
class Compiler {
  /** Binary radix. */
  static final int RADIX_BIN = 2;
  /** Octal radix. */
  static final int RADIX_OCT = 8;
  /** Decimal radix. */
  static final int RADIX_DEC = 10;
  /** Hexadecimal radix. */
   static final int RADIX_HEX = 16;
  /** List of language symbols. */
  static final Object[][] SYMBOL_LIST = {
      {"!", TokenType.LNOT},
      {"<", TokenType.LT},
      {"<=", TokenType.LTE},
      {"<<", TokenType.LEFT_SHIFT},
      {">>", TokenType.RIGHT_SHIFT},
      {">>>", TokenType.RIGHT_ARITH_SHIFT},
      {"<>", TokenType.DIAMOND},
      {"%", TokenType.MODULUS},
      {"^", TokenType.XOR},
      {"&", TokenType.BIT_AND},
      {"&&", TokenType.LOG_AND},
      {"|", TokenType.BIT_OR},
      {"||", TokenType.LOG_OR},
      {"*", TokenType.MULTIPLY},
      {"(", TokenType.OPAREN},
      {")", TokenType.CPAREN},
      {"-", TokenType.MINUS},
      {"+", TokenType.PLUS},
      {"/", TokenType.DIVIDE},
      {"?", TokenType.TERN1},
      {":", TokenType.COLON},
      {";", TokenType.SEMI},
      {".", TokenType.DOT},
      {",", TokenType.COMMA},
      {"{", TokenType.OCURL},
      {"}", TokenType.CCURL},
      {"[", TokenType.OSQUARE},
      {"]", TokenType.CSQUARE},
      {">", TokenType.GT},
      {">=", TokenType.GTE},
      {"!=", TokenType.NE},
      {"=", TokenType.ASSIGN},
      {"==", TokenType.EQU},
      {"'", TokenType.APOS},
      {"~", TokenType.BIT_NOT},
      {"\"", TokenType.QUOTE}

  };
  /**
   * A symbol value for identifiers.
   */
  public static final SymbolTrei SYMBOL_ID = new SymbolTrei(TokenType.ID);
  /** A trei mapping characters to token types. */
  @SuppressWarnings("serial")
  private Map<Character, SymbolTrei> symbols =
      new HashMap<Character, SymbolTrei>() {
        {
          for (Object[] s : SYMBOL_LIST) {
            String ss = (String) s[0];
            TokenType st = (TokenType) s[1];
            SymbolTrei p = null;
            for (int c = 0; c < ss.length(); c++) {
              SymbolTrei t = null;
              final char cc = ss.charAt(c);
              Map<Character, SymbolTrei> m = c == 0 ? this : p;
              t = m.get(cc);
              if (t == null) {
                t = new SymbolTrei();
                m.put(cc, t);
              }
              if (c + 1 == ss.length()) {
                t.setToken(st);
              }
              p = t;
            }
          }
        }
      };

  {
    SyntaxFactory sf = new SyntaxFactory();
    sf.define("white",
        sf.zeroOrMore(
            sf.anyOf(
                sf.character(' '),
                sf.character('\n'),
                sf.character('\r'),
                sf.character('\t'))));
    sf.define("imports",
        sf.oneOrMore(
            sf.token(TokenType.KIMPORT),
            sf.oneOrLess(
                sf.ident(),
                sf.zeroOrMore(
                    sf.token(TokenType.DOT),
                    sf.ident()))));
    sf.define("typeDef",
        sf.use("classDef"),
        sf.use("enumDef"),
        sf.use("interfaceDef"));
    sf.define("enumDef",
        sf.token(TokenType.ENUM),
        sf.ident(),
        sf.token(TokenType.OCURL),
        sf.use("enumValue"),
        sf.zeroOrMore(sf.sequence(
            sf.token(TokenType.COMMA),
            sf.use("enumValue")
            )),
        sf.token(TokenType.SEMI),
        sf.zeroOrMore(sf.use("classMember")),
        sf.token(TokenType.CCURL)
    );
    sf.define("enumValue",
        sf.ident(),
        sf.oneOrLess(sf.use("formalParams")),
        sf.oneOrLess(sf.sequence(
            sf.token(TokenType.OCURL),
            sf.zeroOrMore(sf.use("classMember")),
            sf.token(TokenType.CCURL)
            )
        )
    );
    sf.define("expression",
        sf.use("comparison"),
        sf.oneOrLess(
            sf.allOf(sf.token(TokenType.LOG_AND), sf.use("comparison")),
            sf.allOf(sf.token(TokenType.LOG_OR), sf.use("comparison"))));
    sf.define("comparison",
        sf.oneOf(
            sf.allOf(sf.token(TokenType.LNOT), sf.use("relation")),
            sf.allOf(
                sf.use("relation"),
                sf.oneOf(
                    sf.token(TokenType.LT),
                    sf.token(TokenType.LTE),
                    sf.token(TokenType.NE),
                    sf.token(TokenType.EQU),
                    sf.token(TokenType.GT),
                    sf.token(TokenType.GTE),
                    sf.sequence(
                        sf.token(TokenType.TERN1),
                        sf.use("relation"),
                        sf.token(TokenType.COLON))),
                sf.use("relation"))));
    sf.define("relation",
        sf.use("product"),
        sf.oneOrLess(
            sf.sequence(
                sf.token(TokenType.BIT_OR), sf.use("product")),
            sf.sequence(
                sf.token(TokenType.BIT_AND), sf.use("product"))));
    sf.define("product",
        sf.use("sum"),
        sf.oneOrLess(
            sf.sequence(sf.token(TokenType.MULTIPLY), sf.use("sum")),
            sf.sequence(sf.token(TokenType.MODULUS), sf.use("sum")),
            sf.sequence(sf.token(TokenType.DIVIDE), sf.use("sum"))));
    sf.define("sum", sf.use("term"),
        sf.oneOrLess(sf.allOf(sf.token(TokenType.PLUS), sf.use("term")),
            sf.sequence(sf.token(TokenType.MINUS), sf.use("term"))));
    sf.define("term",
        sf.oneOrLess(
            sf.token(TokenType.MINUS), sf.token(TokenType.BIT_NOT)),
        sf.oneOrLess(
            sf.sequence(sf.token(TokenType.PLUS), sf.use("term")),
            sf.sequence(sf.token(TokenType.MINUS), sf.use("term"))));

    sf.define("return",
        sf.oneOrLess(sf.use("expression")));
    sf.define("switch",
        sf.token(TokenType.SWITCH),
        sf.token(TokenType.OPAREN),
        sf.use("expression"),
        sf.token(TokenType.CPAREN),
        sf.token(TokenType.OCURL),
        sf.oneOrMore(
            sf.sequence(
                sf.token(TokenType.CASE),
                sf.use("expression"),
                sf.token(TokenType.COLON),
                sf.zeroOrMore(sf.use("statement")),
            sf.sequence(
                sf.token(TokenType.BREAK),
                sf.token(TokenType.SEMI)))));
    sf.define("classDef",
      sf.oneOrLess(
          sf.token(TokenType.PUBLIC),
          sf.token(TokenType.PROTECTED)),
      sf.oneOf(
          sf.token(TokenType.ABSTRACT),
          sf.token(TokenType.FINAL)
      ),
      sf.token(TokenType.CLASS),
      sf.ident(),
      sf.oneOrLess(
          sf.sequence(
              sf.token(TokenType.EXTENDS),
              sf.ident()
          )
      ),
      sf.oneOrLess(
          sf.sequence(
              sf.token(TokenType.IMPLEMENTS),
              sf.ident(),
              sf.zeroOrMore(
                  sf.sequence(
                      sf.token(TokenType.COMMA),
                      sf.ident()
                  )
              ),
              sf.token(TokenType.OCURL),
              sf.zeroOrMore(sf.use("classMember")),
              sf.token(TokenType.CCURL)
          )
       )
    );
    sf.define("classMember",
        sf.oneOrLess(
            sf.token(TokenType.PUBLIC),
            sf.token(TokenType.PROTECTED),
            sf.token(TokenType.PRIVATE)
        ),
        sf.oneOf(
            sf.sequence(
                sf.zeroOrMore(
                    sf.token(TokenType.STATIC),
                    sf.token(TokenType.FINAL)
                ),
                sf.use("type"),
                sf.ident(),
                sf.oneOrLess(
                    sf.sequence(
                        sf.token(TokenType.ASSIGN),
                        sf.use("expression"),
                        sf.token(TokenType.SEMI)
                    )
                )
            ),
            sf.sequence(
                sf.token(TokenType.ABSTRACT),
                sf.use("type"),
                sf.ident(),
                sf.use("formalParams"),
                sf.token(TokenType.SEMI)
            ),
            sf.sequence(
                sf.zeroOrMore(
                    sf.oneOf(
                        sf.token(TokenType.STATIC),
                        sf.token(TokenType.FINAL)
                    )
                ),
                sf.use("type"),
                sf.ident(),
                sf.use("formalParams"),
                sf.token(TokenType.OCURL),
                sf.use("statement"),
                sf.token(TokenType.CCURL)
            )
        )
    );
    sf.define("formalParams",
        sf.token(TokenType.OPAREN),
        sf.zeroOrMore(sf.sequence(
            sf.use("type"),
            sf.ident(),
            sf.oneOrLess(sf.token(TokenType.ELIPSIS)),
            sf.zeroOrMore(sf.sequence(
                sf.token(TokenType.COMMA),
                sf.use("type"),
                sf.ident(),
                sf.oneOrLess(sf.token(TokenType.ELIPSIS))
            ))
        )),
        sf.token(TokenType.CPAREN)
    );
    sf.define("type",
        sf.oneOf(
            sf.ident(),
            sf.token(TokenType.TBOOLEAN),
            sf.token(TokenType.TBYTE),
            sf.token(TokenType.TSHORT),
            sf.token(TokenType.TINT),
            sf.token(TokenType.TLONG),
            sf.token(TokenType.TCHAR),
            sf.token(TokenType.TFLOAT),
            sf.token(TokenType.TDOUBLE),
            sf.token(TokenType.TCHAR)
        ),
        sf.zeroOrMore(sf.sequence(
            sf.token(TokenType.OSQUARE),
            sf.token(TokenType.CSQUARE)
        ))
    );
    sf.define("interfaceDef",
        sf.token(TokenType.KINTERFACE),
        sf.ident(),
        sf.use("extendsClause"),
        sf.token(TokenType.OCURL),
        sf.zeroOrMore(
            sf.use("interfaceMember")),
        sf.token(TokenType.CCURL));
    sf.define("extendsClause",
        sf.zeroOrMore(
            sf.sequence(
                sf.token(TokenType.EXTENDS),
                sf.ident(),
                sf.zeroOrMore(
                    sf.token(TokenType.COMMA),
                    sf.ident()
                )
            )
        )
    );
    sf.define("interfaceMember",
        sf.use("type"),
        sf.ident(),
        sf.oneOf(
            sf.sequence(
                sf.token(TokenType.ASSIGN),
                sf.use("expression"),
                sf.token(TokenType.SEMI)
            ),
            sf.sequence(
                sf.use("formalParams"),
                sf.token(TokenType.SEMI)
            )
        )
    );
    sf.define("types",
        sf.zeroOrMore(
            sf.use("typeDef")));
    sf.define("for",
        sf.token(TokenType.FOR),
        sf.token(TokenType.OPAREN),
        sf.oneOf(
            sf.sequence(
                sf.ident(),
                sf.token(TokenType.COLON),
                sf.use("expression")
            ),
            sf.sequence(
                sf.ident(),
                sf.ident(),
                sf.token(TokenType.COLON),
                sf.use("expression")
            ),
            sf.sequence(
                sf.oneOrLess(sf.ident()),
                sf.ident(),
                sf.token(TokenType.ASSIGN),
                sf.use("expression"),
                sf.zeroOrMore(sf.sequence(
                      sf.token(TokenType.COMMA),
                      sf.ident(),
                      sf.oneOrLess(sf.sequence(
                          sf.token(TokenType.ASSIGN),
                          sf.use("expression")
                      ))
                )),
                sf.token(TokenType.SEMI),
                sf.use("expression"),
                sf.zeroOrMore(sf.sequence(
                    sf.token(TokenType.COMMA),
                    sf.use("expression")
                )),
                sf.token(TokenType.SEMI),
                sf.use("expression"),
                sf.zeroOrMore(sf.sequence(
                    sf.token(TokenType.COMMA),
                    sf.use("expression")
                )),
                sf.token(TokenType.CPAREN)
            )

        ),
        sf.use("statement")
    );
    sf.define("try",
        sf.token(TokenType.TRY),
        sf.use("statement"),
        sf.zeroOrMore(sf.sequence(
            sf.token(TokenType.CATCH),
            sf.use("statement")
        )),
        sf.oneOrLess(sf.sequence(
            sf.token(TokenType.FINALLY),
            sf.use("statement")
        ))
        );
    sf.define("statement",
        sf.oneOf(
            sf.use("expression"),
            sf.use("return"),
            sf.use("switch"),
            sf.use("for"),
            sf.use("try"),
            sf.sequence(
                sf.token(TokenType.OCURL),
                sf.use("statements"),
                sf.token(TokenType.CCURL)
            )
        )
    );
    sf.define("statements",
        sf.zeroOrMore(
            sf.use("statement")));
    sf.define("cafenated",
        sf.use("imports"),
        sf.use("types"),
        sf.use("statements"));
    sf.validate();
  }

  // enum SyntaxCondition{
  // ZERO_PLUS, ONE_PLUS, ITEM, GROUP, ONE_OF, ALL_OF
  // };
  // class SyntaxGroup {
  // SyntaxCondition rule;
  // LinkedList<TokenType> items = new LinkedList<>();
  // SyntaxGroup add(SyntaxCondition cardinality, Object... items){
  // return this;
  // }
  // }
  // private final static SyntaxGroup SG_QCLASS = new SyntaxGroup()
  // .add(SyntaxCondition.ONE_OF, new SyntaxGroup()
  // .add(SyntaxCondition.ZERO_PLUS, TokenType.ID, TokenType.DOT),
  // TokenType.ID);
  // private final static SyntaxGroup SG_TYPE = new SyntaxGroup()
  // .add(SyntaxCondition.ONE_OF, TokenType.TBYTE, TokenType.TSHORT,
  // TokenType.TINT, TokenType.TBOOLEAN, TokenType.TLONG,
  // TokenType.TDOUBLE, TokenType.TFLOAT, TokenType.TCHAR,
  // SG_QCLASS, SG_CLASS);
  // public final static SyntaxGroup SG_VAR = new SyntaxGroup()
  // .add(SyntaxCondition.ONE_OF,
  // SG_DECLARE_VAR, SG_VAR_NAME, SG_PROPERTY);
  //
  // public final static SyntaxGroup STX_ALL = new SyntaxGroup()
  // .add(SyntaxCondition.ONE_PLUS,
  // TokenType.KIMPORT, SG_QCLASS, TokenType.SEMI)
  // .add(SyntaxCondition.ONE_PLUS, SG_TYPE, TokenType.ID,
  // new SyntaxGroup().add(SyntaxCondition.ZERO_PLUS,
  // SG_VAR, TokenType.ASSIGN, SG_EXPRESSION)
  // )
  //
  // ;

  /**
   *
   * @author John Hutcheson &lt;witerat.test@gmail.com&gt;
   *
   */
  abstract class TokenPattern {
    /**
     * Character can first in token.
     * @param ch
     *          the character.
     * @return true if character is valid in this token pattern.
     */
    abstract boolean isStart(char ch);

    /**
     * Character can be part of token.
     * @param model
     *          tokenizing model.
     * @param ch
     *          the character
     * @return true if character is valid in this token pattern.
     */
    abstract boolean isPart(Object model, char ch);

    /**
     * Character can't be in token.
     * @param model
     *          the token analysis model.
     * @param ch
     *          character can't be valid in pattern.
     * @return true if character is invalid in pattern.
     */
    abstract boolean isExcluded(Object model, char ch);

    /**
     * Create model used to analyse a possible token.
     * @param model
     *          the execution model.
     * @return An object to hold the state of token analysis.
     */
    abstract Object createModel(TemplateEngineModel model);

    /**
     * Get the analysed token.
     * @param object
     *          the token analysis model.
     * @return the token as analysed.
     */
    abstract Object getToken(Object object);
  }

  /**
   * Process token event. push to a queue or compare with graph node.
   * @author John Hutcheson &lt;witerat.test@gmail.com&gt;
   *
   */
   interface TokenProducer {

    /**
     * @param tkStart
     * @param tkEnd
     * @param token
     */
    void produceToken(int tkStart, int tkEnd, Object token);

  }

  /**
   * @author John Hutcheson &lt;witerat.test@gmail.com&gt;
   *
   */
  class CompileState {
    /** start of token. */
    private int tkStart = -1;
    /** parsing indent spacing. */
    private boolean inIdent = false;
    /** parsing symbol characters. */
    private boolean inSymbol = false;
    /** parsing line comment. */
    private  boolean inLineComment = false;
    /** parsing character after '/'. */
    private  boolean slashBefore = false;
    /** parsing a block comment. */
    private  boolean inLongComment = false;
    /** checking for end of block comment. */
    private  boolean starBefore = false;
    /** parsing token characters. */
    private  boolean inToken = false;
    /** parsing number literal. */
    private  boolean inNumber = false;
    /** parsing fraction part of number literal. */
    private  boolean inFraction = false;
    /** can a radix indicator be expected. */
    private  boolean expectRadix = false;
    /** Radix indicator is specified. */
    private  boolean radixBefore = false;
    /** parsing significant digits. */
    private  boolean inMantissa = false;
    /** parsing exponent part. */
    private  boolean inPow10 = false;
    /** parsing E-notation. */
    private  boolean expectPow10 = false;
    /** can E-notation start. */
    private  boolean expectE = false;
    /** can exponent sign be here. */
    private  boolean expectESign = false;
    /** Literal value in parsing. */
    private  Object literal = null;
    /** Type of literal value. */
    private  Class<?> literalType = null;
    /** exponent sign. */
    private  char eSign = ' ';
    /** Radix, default is decimal. */
    private  int radix = Compiler.RADIX_DEC;
    /** the token. */
    private  String token = null;
    /** default Observer of token production. */
    private TokenProducer defaultTokenProducer = new TokenProducer() {
      @Override
      public void produceToken(final int tkStart0,
          final int tkEnd0, final Object token0) {
      }
    };


    /** Observer of token production. */
    private TokenProducer tokenProducer = defaultTokenProducer;

    /** The identifier. */
    private  String ident = null;
    /** Index of current character. */
    private int chx = -1;
    /** The current character. */
    private  char ch = SimpleExpressionLanguage.CHAR_NIL;
    /** The parse expression. */
    private  String expression = null;
    /** Current symbol. */
    private  SymbolTrei symbolTrial;
    /** token parsing. */
    private  boolean parse;
    /** Index of the {@link #mark()} position. */
    private  int markX = -1;
    /** Column at {@link #mark()} position. */
    private  int markColumn;
    /** line number at {@link #mark()} position. */
    private  int markLine;
    /**
     * The line property.
     */
    private  int line = 1;
    // enum SyntaxCondition{
    // ZERO_PLUS, ONE_PLUS, ITEM, GROUP, ONE_OF, ALL_OF
    // };
    // class SyntaxGroup {
    // SyntaxCondition rule;
    // LinkedList<TokenType> items = new LinkedList<>();
    // SyntaxGroup add(SyntaxCondition cardinality, Object... items){
    // return this;
    // }
    // }
    // private final static SyntaxGroup SG_QCLASS = new SyntaxGroup()
    // .add(SyntaxCondition.ONE_OF, new SyntaxGroup()
    // .add(SyntaxCondition.ZERO_PLUS, TokenType.ID, TokenType.DOT),
    // TokenType.ID);
    // private final static SyntaxGroup SG_TYPE = new SyntaxGroup()
    // .add(SyntaxCondition.ONE_OF, TokenType.TBYTE, TokenType.TSHORT,
    // TokenType.TINT, TokenType.TBOOLEAN, TokenType.TLONG,
    // TokenType.TDOUBLE, TokenType.TFLOAT, TokenType.TCHAR,
    // SG_QCLASS, SG_CLASS);
    // public final static SyntaxGroup SG_VAR = new SyntaxGroup()
    // .add(SyntaxCondition.ONE_OF,
    // SG_DECLARE_VAR, SG_VAR_NAME, SG_PROPERTY);
    //
    // public final static SyntaxGroup STX_ALL = new SyntaxGroup()
    // .add(SyntaxCondition.ONE_PLUS,
    // TokenType.KIMPORT, SG_QCLASS, TokenType.SEMI)
    // .add(SyntaxCondition.ONE_PLUS, SG_TYPE, TokenType.ID,
    // new SyntaxGroup().add(SyntaxCondition.ZERO_PLUS,
    // SG_VAR, TokenType.ASSIGN, SG_EXPRESSION)
    // )
    //
    // ;

    /**
     * The column property.
     */
    private  int column = 0;
    // enum SyntaxCondition{
    // ZERO_PLUS, ONE_PLUS, ITEM, GROUP, ONE_OF, ALL_OF
    // };
    // class SyntaxGroup {
    // SyntaxCondition rule;
    // LinkedList<TokenType> items = new LinkedList<>();
    // SyntaxGroup add(SyntaxCondition cardinality, Object... items){
    // return this;
    // }
    // }
    // private final static SyntaxGroup SG_QCLASS = new SyntaxGroup()
    // .add(SyntaxCondition.ONE_OF, new SyntaxGroup()
    // .add(SyntaxCondition.ZERO_PLUS, TokenType.ID, TokenType.DOT),
    // TokenType.ID);
    // private final static SyntaxGroup SG_TYPE = new SyntaxGroup()
    // .add(SyntaxCondition.ONE_OF, TokenType.TBYTE, TokenType.TSHORT,
    // TokenType.TINT, TokenType.TBOOLEAN, TokenType.TLONG,
    // TokenType.TDOUBLE, TokenType.TFLOAT, TokenType.TCHAR,
    // SG_QCLASS, SG_CLASS);
    // public final static SyntaxGroup SG_VAR = new SyntaxGroup()
    // .add(SyntaxCondition.ONE_OF,
    // SG_DECLARE_VAR, SG_VAR_NAME, SG_PROPERTY);
    //
    // public final static SyntaxGroup STX_ALL = new SyntaxGroup()
    // .add(SyntaxCondition.ONE_PLUS,
    // TokenType.KIMPORT, SG_QCLASS, TokenType.SEMI)
    // .add(SyntaxCondition.ONE_PLUS, SG_TYPE, TokenType.ID,
    // new SyntaxGroup().add(SyntaxCondition.ZERO_PLUS,
    // SG_VAR, TokenType.ASSIGN, SG_EXPRESSION)
    // )
    //
    // ;

    /**
     * The crBefore property.
     */
    private  boolean crBefore = false;
    /** Numeric literal type suffix. */
    private  boolean expectSuffix;
    /** operator precedence control operation ordering.*/
    private  Stack<Object[]> priorities = new Stack<>();
    /** the plan under construction. */
    private ArrayList<AbstractFetch> plan = null;
    /** the in fraction number state. */
    private boolean inFrac;

     /**
     * The expression for parsing.
     * @return the expression being parsed.
     */
    protected String getExpression() {
      return expression;
    }

    /**
     * Set expression for parsing.
     * @param expression0 the new expression for parsing.
     */
    protected void setExpression(final String expression0) {
      this.expression = expression0;
    }

    /**
     * token position.
     * @return index to start of token.
     */
    protected int getTkStart() {
      return tkStart;
    }

    /**
     * the pending operations for the plan.
     *  @return an array of {@Link Ops} that represent the order
     *          of pending operations.
     */
    protected Stack<Object[]> getPriorities() {
      return priorities;
    }

    /**
     * Set pending operations for the plan.
     *  @param  priorities0 an array of {@Link Ops} that represent the order
     *          of pending operations.
     */
    protected void setPriorities(final Stack<Object[]> priorities0) {
      this.priorities = priorities0;
    }

    /**
     * The plan.
     * @return the plan
     */
    protected ArrayList<AbstractFetch> getPlan() {
      return plan;
    }

    /**
     * Set the execution plan.
     * @param plan0 the new plan.
     */
    protected void setPlan(final ArrayList<AbstractFetch> plan0) {
      this.plan = plan0;
    }

    /**
     * Set token start.
     * @param tkStart0 the new tkStart value.
     */
    protected void setTkStart(final int tkStart0) {
      this.tkStart = tkStart0;
    }

    /**
     * is parsing an identifier.
     * @return true if parsing an identifier.
     */
    protected boolean isInIdent() {
      return inIdent;
    }

    /**
     * set is parsing an identifier.
     * @param inIdent0 the new inIdent state.
     */
    protected void setInIdent(final boolean inIdent0) {
      this.inIdent = inIdent0;
    }

    /**
     * is parsing a symbol.
     * @return true if parsing a symbol.
     */
    protected boolean isInSymbol() {
      return inSymbol;

    }
    /**
     * Set is parsing a symbol.
     * @param inSymbol0 the new inSymbol state.
     */
    protected void setInSymbol(final boolean inSymbol0) {
      this.inSymbol = inSymbol0;
    }

    /**
     * is parsing a line comment.
     * @return true if parsing a line comment.
     */
    protected boolean isInLineComment() {
      return inLineComment;
    }

    /**
     * set is parsing a line comment.
     * @param inLineComment0 the new inLineComment start.
     */
    protected void setInLineComment(final boolean inLineComment0) {
      this.inLineComment = inLineComment0;
    }

    /**
     * a slash was the previous character.
     * @return true if slash was the previous character.
     */
    protected boolean isSlashBefore() {
      return slashBefore;
    }
    /**
     * Set a slash is previous for next character.
     * @param slashBefore0 the new slashBefore state.
     */
    protected void setSlashBefore(final boolean slashBefore0) {
      this.slashBefore = slashBefore0;
    }

    /**
     * parsing a block comment.
     * @return true if parsing a block comment.
     */
    protected boolean isInLongComment() {
      return inLongComment;
    }

    /**
     * set parsing a block comment.
     * @param inLongComment0 The new inLongComment state.
     */
    protected void setInLongComment(final boolean inLongComment0) {
      this.inLongComment = inLongComment0;
    }

    /**
     * is star previous character.
     * @return true if star was previous character.
     */
    protected boolean isStarBefore() {
      return starBefore;
    }

    /**
     * set is star previous character.
     * @param starBefore0 the new starBefore state
     */
    protected void setStarBefore(final boolean starBefore0) {
      this.starBefore = starBefore0;
    }

    /**
     * is in token parsing.
     * @return true if in token parsing.
     */
    protected boolean isInToken() {
      return inToken;
    }

    /**
     * set in token parsing.
     * @param inToken0 the new inToken state.
     */
    protected void setInToken(final boolean inToken0) {
      this.inToken = inToken0;
    }

    /**
     * is in number.
     * @return is in number.
     */
    protected boolean isInNumber() {
      return inNumber;
    }

    /**
     * Set is in number.
     * @param inNumber0 the new is in Number state.
     */
    protected void setInNumber(final boolean inNumber0) {
      this.inNumber = inNumber0;
    }

    /**
     * is in fraction.
     * @return is in fraction.
     */
    protected boolean isInFraction() {
      return inFraction;
    }

    /**
     * Set in fraction.
     * @param inFraction0 the new in fraction state.
     */
    protected void setInFraction(final boolean inFraction0) {
      this.inFraction = inFraction0;
    }

    /**
     * expect radix.
     * @return true if expect radix specifier.
     */
    protected boolean isExpectRadix() {
      return expectRadix;
    }

    /**
     * Set Expect radix.
     * @param expectRadix0 the new expect radix state.
     */
    protected void setExpectRadix(final boolean expectRadix0) {
      this.expectRadix = expectRadix0;
    }

    /**
     * is radix before.
     * @return true if radix before.
     */
    protected boolean isRadixBefore() {
      return radixBefore;
    }
    /**
     * Set radix before.
     * @param radixBefore0 the new radix before state.
     */
    protected void setRadixBefore(final boolean radixBefore0) {
      this.radixBefore = radixBefore0;
    }
    /**
     * in mantissa.
     * @return true if in mantissa.
     */
    protected boolean isInMantissa() {
      return inMantissa;
    }

    /**
     * Set in mantissa.
     * @param inMantissa0 the new in mantissa state
     */
    protected void setInMantissa(final boolean inMantissa0) {
      this.inMantissa = inMantissa0;
    }

    /**
     * in pow10.
     * @return true if in pow10.
     */
    protected boolean isInPow10() {
      return inPow10;
    }

    /**
     * Set in pow10.
     * @param pInPow10 the new in Pow10 state.
     */
    protected void setInPow10(final boolean pInPow10) {
      this.inPow10 = pInPow10;
    }

    /**
     * is expect pow10.
     * @return is expect pow10.
     */
    protected boolean isExpectPow10() {
      return expectPow10;
    }

    /**
     * Set expect pow10.
     * @param pExpectPow10 the new expect pow10 state.
     */
    protected void setExpectPow10(final boolean pExpectPow10) {
      this.expectPow10 = pExpectPow10;
    }

    /**
     * is expect E.
     * @return true if expect E.
     */
    protected boolean isExpectE() {
      return expectE;
    }

    /**
     * Set expect E.
     * @param expectE0 the new Expect E state.
     */
    protected void setExpectE(final boolean expectE0) {
      this.expectE = expectE0;
    }

    /**
     * is expect E Sign.
     * @return true if expect E Sign.
     */
    protected boolean isExpectESign() {
      return expectESign;
    }

    /**
     * Set the expectESign state.
     * @param expectESign0 the new expectESign state.
     */
    protected void setExpectESign(final boolean expectESign0) {
      this.expectESign = expectESign0;
    }

    /**
     * The literal.
     * @return the literal.
     */
    protected Object getLiteral() {
      return literal;
    }

    /**
     * Set the Literal.
     * @param literal0 the new literal.
     */
    protected void setLiteral(final Object literal0) {
      this.literal = literal0;
    }

    /**
     * The current Literal type.
     * @return the current literal type.
     */
    protected Class<?> getLiteralType() {
      return literalType;
    }

    /**
     * Set the literal's type.
     * @param literalType0 the new type of the literal.
     */
    protected void setLiteralType(final Class<?> literalType0) {
      this.literalType = literalType0;
    }

    /**
     * the eSign.
     * @return the exponent sign.
     */
    protected char geteSign() {
      return eSign;
    }

    /**
     * Set the eSign.
     * @param eSign0 the eSign.
     */
    protected void seteSign(final char eSign0) {
      this.eSign = eSign0;
    }

    /**
     * The radix.
     * @return the radix.
     */
    protected int getRadix() {
      return radix;
    }

    /**
     * Set the radix.
     * @param radix0 the new radix
     */
    protected void setRadix(final int radix0) {
      this.radix = radix0;
    }

    /**
     * the token.
     * @return the token.
     */
    protected String getToken() {
      return token;
    }

    /**
     * Set the token.
     * @param token0 the new token.
     */
    protected void setToken(final String token0) {
      this.token = token0;
    }

    /**
     * The ident property - the name of an identifier.
     * @return The ident.
     */
    protected String getIdent() {
      return ident;
    }

    /**
     * Set ident.
     * @param ident0 the new ident.
     */
    protected void setIdent(final String ident0) {
      this.ident = ident0;
    }

    /**
     * The current character index.
     * @return the current character index.
     */
    protected int getChx() {
      return chx;
    }

    /**
     * Set the new character index.
     * @param chx0 the new character index.
     */
    protected void setChx(final int chx0) {
      this.chx = chx0;
    }

    /** the current character.
     * @return The current character
     */
    protected char getCh() {
      return ch;
    }

    /**
     * Set current character.
     * @param ch0 the new current character.
     */
    protected void setCh(final char ch0) {
      this.ch = ch0;
    }

    /**
     * parse whitespace character.
     * @return true if current character is white space.
     */
    protected boolean onWhiteSpace() {
      if (Character.isWhitespace(ch)) {
        if (Character.LINE_SEPARATOR == ch || '\n' == ch) {
          inLineComment = false;
        }
        return true;
      }
      return false;
    }

    /**
     * Parse an in-line comment.
     * @return true if parsing an in-line comment content.
     */
    protected boolean onInLineComment() {
      if (inLineComment) {
        if (ch == SimpleExpressionLanguage.CHAR_NIL
            ||  ch == Character.LINE_SEPARATOR) {
          inLineComment = false;
        } else {
          return true;
        }
      }
      return false;
    }

    /**
     * Parse a long comment.
     * @return true if parsing a long comment content.
     * @throws ExpressionFailedException if unterminated comment.
     */
    protected boolean onLongComment() throws ExpressionFailedException {
      if (inLongComment) {
        if (ch == SimpleExpressionLanguage.CHAR_NIL) {
          throw new ExpressionFailedException("unterminated comment");
        }
        if (starBefore) {
          if (ch == '/') {
            inLongComment = false;
            return true;
          }
        }
        starBefore = ch == '*';
      }
      return false;
    }
    /**
     * Parse character following a "/" character.
     * @return true if current character should be ignored.
     */
    protected boolean onSlashBefore() {
      if (slashBefore) {
        if (ch == '/') {
          inLineComment = true;
          return true;
        } else if (ch == '*') {
          inLongComment = true;
          return true;
        }
        slashBefore = false;
      } else {
        if (ch ==  '/') {
          slashBefore = true;
        }
      }
      return false;
    }

    /**
     * Parse an identifier.
     * @return true if the current character should be ignored.
     */
    protected boolean onIdent() {
      if (inIdent) {
        if (!Character.isJavaIdentifierPart(ch)) {
          String tk = expression.substring(tkStart, chx);
          if ((countSymbolMatches(tk) == 1)
            || (findExactTokenIndex(tk) == -1)) {
              ident = tk;
              symbolTrial = Compiler.SYMBOL_ID;
          } else {
              token = tk;
          }
          inToken = false;
          inIdent = false;
          parse = true;
        }
        return true;
      } else if (Character.isJavaIdentifierStart(ch)) {
        inIdent = true;
        tkStart = chx;
      }
      return false;
    }

    /**
     * Parse number formats.
     * @throws ExpressionFailedException if improper numeric input.
     */
    protected void onNumber()
        throws ExpressionFailedException {
      boolean relex;
      do {
        relex = false;
        if (!inNumber) {
          literalType = Integer.class;
          if (Character.isDigit(ch)) {
            inNumber = true;
            radix = Compiler.RADIX_DEC;
            relex = true;
          } else {
            throw new ExpressionFailedException("not a number",
                new NumberFormatException());
          }
        } else if (expectSuffix) {
          Class<?> lt = numberTypeForSuffix(ch);
          if (lt != null) {
            literalType = lt;
          }

          try {
            token = expression.substring(tkStart, chx);
            try {
              literal = literalType.getMethod(
                  "decode", String.class).
                  invoke(null, token);
            } catch (NoSuchMethodException nsme) {
              literal = literalType.getMethod(
                  "valueOf", String.class).invoke(null, token);
            }
          } catch (IllegalAccessException | IllegalArgumentException
               | NoSuchMethodException | SecurityException e) {
            throw new ExpressionFailedException(e);
          } catch (InvocationTargetException ite) {
            throw new ExpressionFailedException(ite.getCause());
          }
          prioritize(Ops.LITERAL, literal, literalType);
          parse = true;
          expectSuffix = false;
        } else if (inPow10) {
          if (!Character.isDigit(ch)) {
            inPow10 = false;
            expectSuffix = true;
            relex = true;
          }
        } else if (expectPow10) {
            if (Character.isDigit(ch)) {
              inPow10 = true;
              expectPow10 = false;
              relex = true;
            } else {
              throw new ExpressionFailedException(
                  "exponent expected in number");
            }
        } else if (expectESign) {
          literalType = Float.class;
          expectESign = false;
          if (ch == '-' || ch == '+') {
            eSign = ch;
          } else {
            eSign = '+';
            relex = true;
          }
          expectPow10 = true;
        } else if (expectE) {
          if (Character.toUpperCase(ch) == 'E') {
            expectE = false;
            expectESign = true;
            literalType = Float.class;
          }
        } else if (inFrac) {
          literalType = Float.class;
          if (Character.isDigit(ch)) {
            inFrac = true;
          } else if ('E' == Character.toUpperCase(ch)) {
            expectESign = true;
          }
        } else if (expectRadix) {
          // Test for appearance of radix symbol, handle it here, otherwise, set
          // <code>relex</code> to handle down-stream pattern.
          expectRadix = false;
          radixBefore = true;
          inMantissa = true;
          switch (Character.toUpperCase(ch)) {
          case '.':
            inFrac = true;
            break;
          // Scientific notation
          case 'E':
            expectESign = true;
            radixBefore = false;
            expectRadix = false;
            eSign = '+';
            break;
          case 'X':
            radix = Compiler.RADIX_HEX;
            break;
          case 'O':
            radix = Compiler.RADIX_OCT;
            break;
          case 'B':
            radix = Compiler.RADIX_BIN;
            break;
          default:
            radixBefore = false;
            relex = true;
          }
        } else if (inMantissa) {
          if (isDigit(ch, radix)) {
            // by default number are integral, not real.
            relex = false;
          } else if (Character.toUpperCase(ch) == 'E') {
            expectESign = true;
          } else if (Character.toUpperCase(ch) == '.') {
            inFrac = true;
          } else {
            expectSuffix = true;
            relex = true;
          }
        } else if (inNumber) {
          if (Character.isDigit(ch)) {
            if ('0' == ch) {
              radix = Compiler.RADIX_OCT;
            }
            inMantissa = true;
            expectRadix = true;
            tkStart = chx;
          } else if ('#' == ch) {
            radix = Compiler.RADIX_HEX;
            inMantissa = true;
          } else if ('.' == ch) {
            inFrac = true;
          }
        } else {
          expectRadix = true;
          relex = true;
        }
      } while (relex);
    }

    /**
     * Push operation onto operation stack export higher priority operations on
     * stack to execution plan.
     * @param op
     *          the operation.
     * @param literal0
     *          a literal value.
     * @param literalType0
     *          formal type of value.
     */
    private void prioritize(final Ops op,
        final Object literal0, final Class<?> literalType0) {
      while (!priorities.isEmpty()) {
        Ops pre = (Ops) priorities .peek()[0];
        if (op.precedence() < pre.precedence()) {
          emit(priorities.pop());
        } else {
          break;
        }
      }
      priorities.push(new Object[]{op, literal0, literalType0});
    }
    /**
     * Flush the priorities queue.
     */
    void prioritize() {
      while (!priorities.isEmpty()) {
          emit(priorities.pop());
      }
    }

    /**
     * translate character to radix.
     * @param ch2 character indicating radix.
     * @return radix value, <code>0</code> if not a radix indicator.
     */
    protected int charToRadix(final char ch2) {
      switch (Character.toUpperCase(ch2)) {
      case 'B': return Compiler.RADIX_BIN;
      case 'O': return Compiler.RADIX_OCT;
      case 'X': return Compiler.RADIX_HEX;
      default: return 0;
      }
    }
    /**
     * get type for literal type suffix.
     * @param suffix literal type indicator.
     * @return representation class for number literal.
     */
    Class<?> numberTypeForSuffix(final char suffix) {
      switch (Character.toLowerCase(suffix)) {
      case 'b': return Byte.class;
      case 's': return Short.class;
      case 'l': return Long.class;
      case 'f': return Float.class;
      case 'd': return Double.class;
      case 'i': return Integer.class;
      default: return null;
      }
    }

    /**
     * parse a symbol.
     * @throws ExpressionFailedException if unrecognised symbol.
     */
    protected void onSymbol() throws ExpressionFailedException {
      if (!inSymbol) {
        inSymbol = true;
        token = null;
        tkStart = chx;
      }
      SymbolTrei nextSymbolTrial;
      if (symbolTrial == null) {
        nextSymbolTrial = symbols.get(ch);
      } else {
        nextSymbolTrial = symbolTrial.get(ch);
      }
      if (nextSymbolTrial == null) {
        if (symbolTrial == null) {
          throw new ExpressionFailedException(
              "unrecognized symbol near " + ch + " character");
        } else {
          reset();
          setParse(true);
        }
      } else {
        if (nextSymbolTrial.isEnd()) {
          if (markX >= 0) {
            unmark();
          }
          mark();
          token = expression.substring(tkStart, chx + 1);
          symbolTrial = nextSymbolTrial;
        }
      }
    }

    /** set the parsing state.
     * @param b true if commencing parsing.
     */
    protected void setParse(final boolean b) {
      parse = b;
      if (!b) {
        expectE = false; expectESign = false; expectPow10 = false;
        expectRadix = false; expectSuffix = false;
        inFrac = false;  inFraction = false; inMantissa = false;
        inNumber = false; inPow10 = false; inSymbol = false; inToken = false;
        radixBefore = false; slashBefore = false;
      }
    }

    /**
     * Start look-ahead reading.
     */
    protected void mark() {
      if (markX != -1) {
        throw new IllegalStateException("Mark already active.");
      }
      markLine = line;
      markColumn = column;
      markX = chx;
    }

    /**
     * Clear look-ahead reading, input since mark() is consider to have been
     * read.
     */
    private void unmark() {
      if (markX == -1) {
        throw new IllegalStateException("Unmark without mark.");
      }
      markX = -1;
    }

    /**
     * Revert to mark point, next character will be the character after the
     * original current character - any input after mark will be considered
     * unread.
     */
    private void reset() {
      if (markX == -1) {
        throw new IllegalStateException("Reset without mark");
      }
      chx = markX;
      line = markLine;
      column = markColumn;
      markX = -1;
    }
    /**
     * Update line and column.
     *
     * @param aCh
     *          the current character.
     */
    protected void updateLocus(final char aCh) {
      if (!crBefore) {
        if (aCh == '\r') {
          line++;
          column = 0;
          crBefore = true;
        } else if (aCh != '\n') {
          column++;
        }
      } else {
        if (aCh == '\r') {
          line++;
          column = 0;
        } else if (aCh != '\n') {
          column++;
        }

        crBefore = false;
      }
    }
    /**
     * Lexical analysis of identifiers, symbols and numbers.
     * @throws ExpressionFailedException if character pattern is unrecognised.
     */
    void onToken() throws ExpressionFailedException {
      if (tkStart == -1) {
        tkStart = chx;
      }
      ch = getChar(expression, chx);
      inToken = true;
      if (Character.isJavaIdentifierStart(ch)) {
        inIdent = true;
      } else if (inNumber || Character.isDigit(ch)) {
        onNumber();
      } else {
        inSymbol = true;
      }
    }

    /**
     * Converts an array of Objects into an abstractFetch sequence then
     * appends the sequence to the execution plan.
     * @param op an operation.
     */
    void emit(final Object[] op) {
      if (op != null) {
        Ops ops = (Ops) op[0];
        AbstractFetch[] af = ops.encode(op);
        for (AbstractFetch f:af) {
          plan.add(f);
        }
      }
    }

    /**
     * Parse the current token.
     */
    void parse() {
      Compiler.this.produceToken(this, tkStart, chx, token);

    }
    /**
     * Determine if a character is a valid digit in the current radix.
     *
     * @param ch0
     *          the character to test
     * @param radix0
     *          the current radix
     * @return true if the character represents a digit in given radix.
     */
    private boolean isDigit(final char ch0, final int radix0) {
      int d = Character.digit(ch0, radix0);
      return d != -1;
    }

    /**
     * Get the parse state.
     * @return true if a complete token has been analysed.
     */
    protected boolean isParse() {
      return parse;
    }
    /**
     * Get the analysed symbol.
     * @return the symbol observed in the input stream.
     */
    protected SymbolTrei getSymbolTrial() {
      return symbolTrial;
    }

    /**
     * @param tkStart0
     * @param tkEnd0
     * @param token0
     */
    public void produceToken(final int tkStart0, final int tkEnd0,
        final Object token0) {
      tokenProducer.produceToken(tkStart0, tkEnd0, token0);
    }

    /**
     * Get the receiver of produce token events.
     * @return a receiver of produce token events.
     */
    protected TokenProducer getTokenProducer() {
      final TokenProducer tp = tokenProducer;
      return tp == defaultTokenProducer ? null : tp;
    }

    /**
     * @param tp
     */
    protected void setTokenProducer(final TokenProducer tp) {
      this.tokenProducer = tp == null ? defaultTokenProducer : tp;
    }

  } // CompileState

  /**
   * Map symbol characters to tokens in a trei.
   * @author John Hutcheson &lt;witerat.test@gmail.com&gt;
   *
   */
  @SuppressWarnings("serial")
  static class SymbolTrei extends HashMap<Character, SymbolTrei> {
    /** the token for any completing pattern. */
    private TokenType token;

    /**
     * Instantiate a SymbolTrei object.
     */
    SymbolTrei() {
      token = null;
    }

    /**
     * Instantiate a token symbol trei.
     * @param tt token invocation o symbol
     */
    SymbolTrei(final TokenType tt) {
      token = tt;
    }

    /**
     *  The token if this character completes the symbol.
     *
     * @return Token for symbol if any, otherwise <code>null</code>.
     */
    protected TokenType getToken() {
      return token;
    }

    /**
     * Assign a token to a complete symbol pattern.
     * @param aToken the token to assign.
     */
    protected void setToken(final TokenType aToken) {
      this.token = aToken;
    }

    /**
     * Check if there is a token for this pattern.
     * @return <code>true</code> a token is completed by the current
     * character.
     */
    protected boolean isEnd() {
      return token != null;
    }

  } //SymbolTrei


  /**
   * Compile an expression into an execution plan.
   * @param expression
   *          the expression to compile.
   * @param model
   *          the model providing input values to the expression.
   * @return an execution plan.
   * @throws ExpressionFailedException
   *           if fail.
   */
  AbstractFetch[] compile(final String expression,
      final TemplateEngineModel model)
          throws ExpressionFailedException {
    final ArrayList<AbstractFetch> protoPlan = new ArrayList<AbstractFetch>();
    CompileState cs = new CompileState();
    cs.expression = expression;
    cs.plan = protoPlan;
    for (cs.chx = 0; cs.chx <= expression.length(); cs.chx++) {

      cs.ch = getChar(expression, cs.chx);
      cs.updateLocus(cs.ch);

      if (cs.onWhiteSpace()) {
        continue;
      }
      if (cs.onInLineComment()) {
        continue;
      } else if (cs.onLongComment()) {
        continue;
      }

      if (cs.onSlashBefore()) {
        continue;
      }
      if (cs.inNumber) {
        cs.onNumber();
      } else if (cs.inSymbol) {
        cs.onSymbol();
      } else if (cs.inIdent) {
        cs.onIdent();
      } else if (!cs.inToken) {
        cs.onToken();
      } else if (cs.parse) {
        cs.parse();
      } else {
        throw new IllegalStateException("Unrecognised pattern near line:"
          + cs.line + ", column: " + cs.column);
      }
    }
    cs.prioritize();
    return cs.getPlan().toArray(new AbstractFetch[cs.getPlan().size()]);
  }

  /** Preset token recognisers. */
  private ArrayList<TokenPattern> possibleTokens = new ArrayList<>();
  /**
   * Alternate implementation focused on a generalised token recognition
   * algorithm.
   * @param expression
   *          The expression to be compiled.
   * @param model
   *          the context model.
   * @return an execution plan.
   * @throws ExpressionFailedException
   *           on syntax.
   */
  protected AbstractFetch[] compile0(final String expression,
      final TemplateEngineModel model) throws ExpressionFailedException {
    ArrayList<TokenPattern> probableTokens = new ArrayList<>();
    ArrayList<AbstractFetch> protoPlan = new ArrayList<>();
    final Map<TokenPattern, Object> models = new HashMap<>();
    CompileState cs = new CompileState();
    cs.plan = protoPlan;
    cs.setExpression(expression);
    for (cs.chx = 0; cs.chx <= expression.length(); cs.chx++) {
      final char ch = getChar(expression, cs.chx);
      cs.updateLocus(ch);
      if (!probableTokens.isEmpty()) {
        final List<TokenPattern> contPtns = new ArrayList<>();
        probableTokens.forEach((pt) -> {
          if (!pt.isExcluded(models.get(pt), ch)) {
            contPtns.add(pt);
          }
        });
        if (contPtns.isEmpty()) {
          if (probableTokens.size() >= 1) {
            TokenPattern pt = probableTokens.get(0);
            cs.produceToken(cs.tkStart, cs.chx, pt.getToken(models.get(pt)));
            models.clear();
          }
        }
      }
      if (probableTokens.isEmpty()) {
        for (TokenPattern pt : possibleTokens) {
          if (pt.isStart(ch)) {
            possibleTokens.add(pt);
            models.put(pt, pt.createModel(model));
          }
        }
        if (possibleTokens.isEmpty()) {
          throw new ExpressionFailedException(
              "Unrecognised character in expression");
        } else {
          cs.tkStart = cs.chx;
        }
      }
    }
    cs.prioritize();
    return protoPlan.toArray(new AbstractFetch[protoPlan.size()]);
  }

  /**
   * A token has been observed on the input stream, it will now be sent to
   * the output. context of the syntax.
   * @param cs
   *          the compile state.
   * @param tkStart
   *          start of token.
   * @param tkEnd
   *          end of token.
   * @param token
   *          the token.
   */
  private void produceToken(final CompileState cs,
      final int tkStart, final int tkEnd, final Object token) {
         cs.tokenProducer.produceToken(tkStart, tkEnd, token);
  }

  /**
   * find token ordinal value.
   *
   * @param tk
   *          a full token
   * @return ordinal for the TokenType
   * @see TokenType
   */
  private int findExactTokenIndex(final String tk) {
    int c = 0;
    for (String sym : TokenType.SYMBOLS) {
      if (sym.equals(tk)) {
        return c + TokenType.LNOT.ordinal();
      }
      c++;
    }
    return -1;
  }

  /**
   * Find the number of formal tokens partially matched by input.
   *
   * @param tk
   *          a partial token.
   * @return the number formal tokens matched by the partial.
   */
  private int countSymbolMatches(final String tk) {
    int c = 0;
    for (String sym : TokenType.SYMBOLS) {
      if (sym.startsWith(tk)) {
        c++;
      }
    }
    return c;
  }

  /**
   * Get the current character.
   *
   * @param expression
   *          the expression text
   * @param chx
   *          current position
   * @return returns the next char or <code>CHAR_NIL</code> if no next
   *         character.
   */
  private char getChar(final String expression, final int chx) {
    if (chx < expression.length()) {
      return expression.charAt(chx);
    } else {
      return SimpleExpressionLanguage.CHAR_NIL;
    }
  }


} // Compiler



