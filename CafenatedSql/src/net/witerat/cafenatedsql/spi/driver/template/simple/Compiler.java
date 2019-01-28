package net.witerat.cafenatedsql.spi.driver.template.simple;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.witerat.cafenatedsql.api.driver.template.ExpressionFailedException;
import net.witerat.cafenatedsql.api.driver.template.TemplateEngineModel;
import net.witerat.cafenatedsql.spi.driver.template.simple.SimpleExpressionLanguage.AbsFetch;

/**
 * @author John Hutcheson &lt;witerat.test@gmail.com&gt;
 *
 */
class Compiler {
  /** Decimal radix. */
  private static final int RADIX_DEC = 10;
  /** Binary radix. */
  private static final int RADIX_BIN = 2;
  /** Octal radix. */
  private static final int RADIX_OCT = 8;
  /** Hexadecimal radix. */
  private static final int RADIX_HEX = 16;
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
              if (c == 0) {
                t = get(ss.charAt(0));
              } else {
                t = p.get(ss.charAt(0));
              }
              if (t == null) {
                t = new SymbolTrei();
                if (c == 0) {
                  put(ss.charAt(0), t);
                } else {
                  p.put(ss.charAt(0), t);
                }
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
   * The column property.
   */
  private int column = 0;
  /**
   * The line property.
   */
  private int line = 1;
  /**
   * The crBefore property.
   */
  private boolean crBefore = false;
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
   * @author John Hutcheson &lt;witerat.test@gmail.com&gt;
   *
   */
  /**
   * @author John Hutcheson &lt;witerat.test@gmail.com&gt;
   *
   */
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
    private boolean inLineComment = false;
    /** parsing character after '/'. */
    private boolean slashBefore = false;
    /** parsing a block comment. */
    private boolean inLongComment = false;
    /** checking for end of block comment. */
    private boolean starBefore = false;
    /** parsing token characters. */
    private boolean inToken = false;
    /** parsing number literal. */
    private boolean inNumber = false;
    /** parsing fraction part of number literal. */
    private boolean inFraction = false;
    /** can a radix indicator be expected. */
    private boolean expectRadix = false;
    /** Radix indicator is specified. */
    private boolean radixBefore = false;
    /** parsing significant digits. */
    private boolean inMantissa = false;
    /** parsing exponent part. */
    private boolean inPow10 = false;
    /** parsing E-notation. */
    private boolean expectPow10 = false;
    /** can E-notation start. */
    private boolean expectE = false;
    /** can exponent sign be here. */
    private boolean expectESign = false;
    /** Literal value in parsing. */
    private Object literal = null;
    /** Type of literal value. */
    private Class<?> literalType = null;
    /** exponent sign. */
    private char eSign = ' ';
    /** Radix, default is decimal. */
    private int radix = RADIX_DEC;
    /** the token. */
    private String token = null;
    /** the identifier. */
    private String ident = null;
    /** Index of current character. */
    private int chx = -1;
    /** the current character. */
    private char ch = SimpleExpressionLanguage.CHAR_NIL;
    /** the parse expression. */
    private String expression = null;
    /** Current symbol. */
    private Compiler.SymbolTrei symbolTrial;
    /** token parsing. */
    private boolean parse;
    /** Index of the {@link #mark()} position. */
    private int markX;
    /** Column at {@link #mark()} position. */
    private int markColumn;
    /** line number at {@link #mark()} position. */
    private int markLine;
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
     * the ident.
     * @return the ident.
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
     * the current character index.
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
        if (ch == SimpleExpressionLanguage.CHAR_NIL) {
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
      if (ch == SimpleExpressionLanguage.CHAR_NIL) {
        throw new ExpressionFailedException("unterminated comment");
      }
      if (starBefore) {
        if (ch == '/') {
          inLongComment = false;
          return true;
        } else {
          starBefore = ch == '*';
        }
      }
      return false;
    }
    /**
     * Parse character following a "/" character.
     * @return true if current character should be ignored.
     */
    protected boolean onSlashBefore() {
      if (ch == '/') {
        inLineComment = true;
      } else if (ch == '*') {
        inLongComment = true;
      }
      slashBefore = false;
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
          if (countSymbolMatches(tk) == 1) {
            if (findExactTokenIndex(tk) != -1) {
              token = tk;
            } else {
              ident = tk;
            }
          }
          inToken = false;
          inIdent = false;
        }
        return true;
      }
      return false;
    }
    /**
     * Parse number formats.
     * @throws ExpressionFailedException if improper numeric input.
     */
    protected void onNumber()
        throws ExpressionFailedException {
      if (inPow10) {
        if (!Character.isDigit(ch)) {
          inPow10 = false;
          literal = Double.valueOf(
              expression.substring(tkStart, chx));
          literalType = Double.class;
          prioritize(Ops.LITERAL, literal, literalType);
        }
      } else if (expectPow10) {
        if (expectESign) {
          expectESign = false;
          if (ch == '-' || ch == '+') {
            eSign = ch;
          } else if (!Character.isDigit(ch)) {
            inPow10 = true;
            expectPow10 = false;
          } else {
            throw new ExpressionFailedException(
                "exponent expected in number");
          }
        } else {
          throw new ExpressionFailedException("Unepected state");
        }
      } else if (expectESign) {
        expectESign = false;
        if (ch == '-' || ch == '+') {
          eSign = ch;
          expectPow10 = true;
        }
      } else if (expectRadix) {
        expectRadix = false;
        radixBefore = true;
        switch (Character.toUpperCase(ch)) {
        case 'E':
          expectESign = true;
          eSign = '+';
          break;
        case 'X':
          radix = RADIX_HEX;
          break;
        case 'O':
          radix = RADIX_OCT;
          break;
        case 'B':
          radix = RADIX_BIN;
          break;
        default:
          radixBefore = false;
          if (!isDigit(ch, radix)) {
            throw new ExpressionFailedException(
                "bad radix indicator - B, X, O only");
          }
        }
      } else if (inMantissa) {
        if (radixBefore) {
          if (!isDigit(ch, radix)) {
            throw new ExpressionFailedException(
                "digit required after radix");
          }
          radixBefore = false;
        }
        if (Character.toLowerCase(ch) == 'l') {
          literal = Long.valueOf(
              expression.substring(tkStart, chx));
          literalType = Long.class;
        } else {
          literal = Integer.valueOf(
              expression.substring(tkStart, chx));
          literalType = Integer.class;
        }
        prioritize(Ops.LITERAL, literal, literalType);
      }
    }

    /**
     * parse a symbol.
     * @throws ExpressionFailedException if unrecognised symbol.
     */
    protected void onSymbol() throws ExpressionFailedException {
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
          this.symbolTrial = nextSymbolTrial;
        }
      } else {
        if (nextSymbolTrial.isEnd()) {
          mark();
          token = expression.substring(tkStart, chx + 1);
        }
        if (nextSymbolTrial.size() == 0) {
          parse = true;
        }
      }
    }

    /**
     * Start look-ahead reading.
     */
    private void mark() {
      if (markX != -1) {
        throw new IllegalStateException("Mark already active.");
      }
      markLine = line;
      markColumn = column;
      markX = chx;
    }

    /**
     * Clear look-ahead reading.
     */
    private void unmark() {
      if (markX == -1) {
        throw new IllegalStateException("Unmark without mark.");
      }
      markX = -1;
    }

    /**
     * Revert to mark point, next character, will be the character after the
     * original current character.
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
    private void updateLocus(final char aCh) {
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
  }
  /**
   * Map symbol characters to tokens in a trei.
   * @author John Hutcheson &lt;witerat.test@gmail.com&gt;
   *
   */
  @SuppressWarnings("serial")
  static class SymbolTrei extends HashMap<Character, SymbolTrei> {
    /** the token for any completing pattern. */
    private TokenType token;

    /** The token if this character completes the symbol.
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

  }
  /**
   * Compile an expression into an execution plan.
   * @param expression
   *          the expression to compile.
   * @param model
   *          the model providing input values to the expression.
   * @return an execution plan
   * @throws ExpressionFailedException
   *           if fail
   */
  AbsFetch[] compile(final String expression,
      final TemplateEngineModel model)
          throws ExpressionFailedException {
    CompileState cs = new CompileState();
    cs.expression = expression;
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
      if (cs.inToken) {
        if (cs.inIdent) {
          cs.onIdent();
        } else if (cs.inNumber) {
          cs.onNumber();
        } else if (cs.inSymbol) {
          cs.onSymbol();
        }
      } else if (cs.tkStart == -1) {
        cs.tkStart = cs.chx;
        cs.inToken = true;
        if (Character.isJavaIdentifierStart(cs.ch)) {
          cs.inIdent = true;
        } else if (Character.isDigit(cs.ch)) {
          cs.inNumber = true;
          cs.radix = RADIX_DEC;
          cs.expectRadix = cs.ch == '0';
          cs.expectE = true;
        } else {
          cs.inSymbol = true;
        }
      }
    }

    return null;
  }

  /** Preset token recognisers. */
  private ArrayList<TokenPattern> possibleTokens = new ArrayList<>();

  /**
   * Alternate implementation focused on a generalised token recognition
   * algorithm.
   * @param expression
   *          The expression.
   * @param model
   *          the context model.
   * @return an execution plan.
   * @throws ExpressionFailedException
   *           on syntax.
   */
  private AbsFetch[] compile0(final String expression,
      final TemplateEngineModel model) throws ExpressionFailedException {
    ArrayList<TokenPattern> probableTokens = new ArrayList<>();
    ArrayList<AbsFetch> protoPlan = new ArrayList<>();
    final Map<TokenPattern, Object> models = new HashMap<>();
    CompileState cs = new CompileState();
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
            produceToken(cs.tkStart, cs.chx, pt.getToken(models.get(pt)));
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
    return protoPlan.toArray(new AbsFetch[protoPlan.size()]);
  }

  /**
   * A token has been observed on the stream it will now analysed in context
   * of the syntax.
   * @param tkStart
   *          start of token.
   * @param tkEnd
   *          end of token.
   * @param token
   *          the token.
   */
  private void produceToken(final int tkStart, final int tkEnd,
      final Object token) {
    // TODO Auto-generated method stub

  }

  /**
   * Push operation onto operation stack export higher priority operations on
   * stack to execution plan.
   * @param op
   *          the operation.
   * @param literal
   *          a literal value.
   * @param literalType
   *          formal type of value.
   */
  private void prioritize(final Ops op,
      final Object literal, final Class<?> literalType) {
    // TODO Auto-generated method stub

  }

  /**
   * Determine if a character is a valid digit in the current radix.
   *
   * @param ch
   *          the character to test
   * @param radix
   *          the current radix
   * @return true if the character represents a digit in given radix.
   */
  private boolean isDigit(final char ch, final int radix) {
    char uc = Character.toUpperCase(ch);
    int n = 0;
    if (Character.isDigit(uc)) {
      n = uc - '0';
    } else if (Character.isAlphabetic(uc)) {
      n = uc - 'A' + RADIX_DEC;
    } else {
      return false;
    }
    if (n < radix) {
      return true;
    }
    return false;
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


}

