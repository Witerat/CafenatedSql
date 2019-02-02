package net.witerat.cafenatedsql.spi.driver.template.simple;

import java.util.HashMap;
import java.util.Map;

import net.witerat.cafenatedsql.api.driver.template.ExpressionFailedException;

/**
 * @author John Hutcheson &lt;witerat.test@gmail.com&gt;
 *
 */
class SyntaxFactory {
  /** defined language elements. */
  private Map<String, Syntax> defines = new HashMap<>();
  /** tokens. */
  private Map<TokenType, Token> tokens =
        new HashMap<>();
  /** References to defined language elements. */
  private Map<String, UseSyntax> uses = new HashMap<>();

  /** Base class for syntax rules. */
  abstract class Syntax {
    /**
     * Test for pattern match.
     * @param model
     *          State of parse model
     * @return <code>true</code> if match, <code>null</code> if
     *         indeterminate
     */
    abstract Boolean test(Object model);

    /**
     * Validate the parse model.
     * @param model
     *          the model
     * @throws ExpressionFailedException
     *           if invalid.
     */
    abstract void validate(Object model) throws ExpressionFailedException;

    /**
     * Generate a model.
     * @param parent
     *          the model of the parent rule.
     * @return a model for this rule.
     */
    abstract Object getModel(Object parent);
  }

  /** A token rule. */
  class Token extends Syntax {
    /** The current token must match this token type. */
    private final TokenType tokenType;

    /** Parse model for tokens. */
    class Model {
      /** Token referenced. */
      private TokenType token;
      /** Match test result. */
      private boolean match;
    }

    /**
     * Instantiate a Token object.
     * @param tkn
     *          the token.
     */
    Token(final TokenType tkn) {
      tokenType = tkn;
    }

    /**
     * Ensure current token matches this token.
     * @param model
     *          Parse model.
     * @return <code>true</code> if match, <code>null</code> if
     *         indeterminate.
     * @see net.witerat.cafenatedsql.spi.driver.template.simple
     *          .SyntaxFactory.Syntax#test(java.lang.Object)
     */
    Boolean test(final Object model) {
      Model mdl = (Model) model;
      if (model instanceof Model) {
        TokenType token = ((Model) model).token;
        mdl.match = token == tokenType;
        return mdl.match;
      }
      return null;
    }

    @Override
    void validate(final Object model) throws ExpressionFailedException {
      Model mdl = (Model) model;
      if (!mdl.match) {
        throw new ExpressionFailedException(
            "expected token: " + tokenType);
      }
    }

    @Override
    Object getModel(final Object parent) {
      return new Model();
    }
  }

  /** Match a character rule.
   * @author John Hutcheson &lt;witerat.test@gmail.com&gt;
   */
  class CharacterToken extends Syntax {
    /** The character in pattern. */
    private final char theChar;
    /**
     * @author John Hutcheson &lt;witerat.test@gmail.com&gt;
     */
    class Model {
      /** the character in stream. */
      private char ch;
    }
    /**
     * Instantiate a CharacterToken object.
     * @param ch the match character.
     */
    CharacterToken(final char ch) {
      theChar = ch;
    }

    /**
     * @see net.witerat.cafenatedsql.spi.driver.template.simple
     *          .SyntaxFactory.Syntax#test(java.lang.Object)
     */
    @Override
    Boolean test(final Object model) {
      Model mdl  = (Model) model;
      return (mdl.ch == theChar);
    }

    @Override
    void validate(final Object model) throws ExpressionFailedException {
      // No-op
    }

    @Override
    Object getModel(final Object parent) {
      // No-op
      return null;
    }
  }
  /** Invoke named syntax. */
  class UseSyntax extends Syntax {
    /** Cache of definition. */
    private Syntax definition;
    /** the language element name reference. */
    private final String name;

    /**
     * Instantiate an UseSyntax object.
     * @param name0
     *          the reference name;
     */
    UseSyntax(final String name0) {
      super();
      this.name = name0;
    }

    /**
     * Proxy test.
     * @param model
     *          Parse model.
     * @return <code>true</code> if match, <code>null</code>
     *         indeterminate.
     * @see net.witerat.cafenatedsql.spi.driver.template.simple
     *          .SyntaxFactory.Syntax#test(java.lang.Object)
     */
    @Override
    Boolean test(final Object model) {
      return getDefine().test(model);
    }

    /**
     * Dereference the name.
     * @return the named definition.
     */
    Syntax getDefine() {
      if (definition == null) {
        definition = defines.get(name);
        if (definition == null) {
          throw new IllegalStateException("construct undefined:" + name);
        }
      }
      return definition;
    }

    /**
     * Validate the parse model.
     * @param model
     *          the parse model.
     * @throws ExpressionFailedException
     *           if invalid.
     * @see net.witerat.cafenatedsql.spi.driver.template.simple
     *          .SyntaxFactory.Syntax#validate(java.lang.Object)
     */
    void validate(final Object model) throws ExpressionFailedException {
      getDefine().validate(model);
    }

    @Override
    Object getModel(final Object parent) {
      return getDefine().getModel(parent);
    }
  }

  /** Identifier tokens. */
  class Identifier extends Token {
    /**
     * Instantiate an Identifier construct.
     */
    Identifier() {
      super(TokenType.ID);
    }
  }

  /** Mandatory sequence. */
  class SequenceGroup extends Syntax {
    /** the pattern. */
    private final Syntax[] items;

    /** parser model. */
    class Model {
      /** match count. */
      private int count;
      /** progress. */
      private int index;
      /** component models. */
      private Map<Syntax, Object> subModels = new HashMap<>();
    }

    /**
     * Instantiate a(n) SequenceGroup object.
     * @param seq
     *          a sequence of patterns
     */
    SequenceGroup(final Syntax... seq) {
      items = seq;
    }

    /**
     * Items must match in order.
     * @param model
     *          parser model state
     * @return <code>true</code> if match, <code>null</code> if
     *         indeterminate
     * @see net.witerat.cafenatedsql.spi.driver.template.simple
     *          .SyntaxFactory.Syntax#test(java.lang.Object)
     */
    Boolean test(final Object model) {
      Model mdl = (Model) model;
      Syntax item = items[mdl.index];
      Boolean match = item.test(itemModel(item, model));
      if (Boolean.TRUE.equals(match)) {
        ++mdl.index;
        ++mdl.count;
      } else if (Boolean.FALSE.equals(match)) {
        ++mdl.index;
      }
      return match;
    }

    /**
     * Get or create a model for a given item.
     * @param item
     *          syntax pattern rule
     * @param model
     *          parent parser model
     * @return a model for the item in current parser context.
     */
    private Object itemModel(final Syntax item, final Object model) {
      Model mdl = (Model) model;
      if (!mdl.subModels.containsKey(item)) {
        mdl.subModels.put(item, item.getModel(model));
      }
      return mdl.subModels.get(item);
    }

    /**
     * Validate the construct after testing.
     * @param model
     *          Parser model
     * @throws ExpressionFailedException
     *           if invalid.
     */
    void validate(final Object model) throws ExpressionFailedException {
      Model mdl = (Model) model;
      if (mdl.count != items.length) {
        throw new ExpressionFailedException("Missing term.");
      }
    }

    @Override
    Object getModel(final Object parent) {
      return new Model();
    }
  }

  /** A series of options. */
  class SyntaxGroup extends Syntax {
    /** the pattern. */
    private final Syntax[] items;
    /** minimum matches for validation. */
    private int min = 1;
    /** maximum matches for validation. */
    private int max = 1;

    /** Parser state. */
    class Model {
      /** match count. */
      private int count = 0;
    }

    /**
     * Instantiate a(n) SyntaxGroup object.
     * @param lo
     *          match at least <code>lo</code> items.
     * @param hi
     *          match at most <code>hi</code> items.
     * @param seq
     *          patterns to match against.
     */
    SyntaxGroup(final int lo, final int hi, final Syntax... seq) {
      if (lo < 0 && lo != Integer.MIN_VALUE) {
        throw new IllegalArgumentException("negative lo: " + lo);
      } else if (lo > seq.length) {
        throw new IllegalArgumentException("high lo: " + lo);
      } else if (hi < 0) {
        throw new IllegalArgumentException("negative hi: " + hi);
      } else if (hi > seq.length && hi != Integer.MAX_VALUE) {
        throw new IllegalArgumentException("high hi: " + hi);
      }
      items = seq;
      min = lo;
      max = hi;
    }

    /**
     * @param model
     *          Parser model
     * @throws ExpressionFailedException
     *           if invalid.
     */
    void validate(final Object model) throws ExpressionFailedException {
      Model mdl = (Model) model;
      if (mdl.count < min && min != Integer.MIN_VALUE) {
        throw new ExpressionFailedException(
            "too few expected " + min + " got " + mdl.count + ".");
      } else if (mdl.count > max && max != Integer.MAX_VALUE) {
        throw new ExpressionFailedException(
            "too many expected " + max + " got " + mdl.count + ".");
      }
    }

    @Override
    Boolean test(final Object model) {
      final Model mModel = (Model) model;
      mModel.count = 0;
      for (Syntax x:items) {
        Object iModel = x.getModel(this);
        if (x.test(iModel)) {
          mModel.count++;
        }
      }
      return mModel.count >= min && mModel.count <= max;
    }

    @Override
    Object getModel(final Object parent) {
      return new Model();
    }

  }

  /**
   * A keyword.
   * @param tkn
   *          the key word.
   * @return token rule.
   */
  public Syntax token(final TokenType tkn) {
    if (tokens.containsKey(tkn)) {
      tokens.put(tkn, new Token(tkn));
    }
    return tokens.get(tkn);
  }

  /**
   * Use a defined syntax.
   * @param name
   *          name of defined construct
   * @return a defined construct.
   * @see #define(String, Syntax...)
   */
  public Syntax use(final String name) {
    if (!uses.containsKey(name)) {
      uses.put(name, new UseSyntax(name));
    }
    return uses.get(name);
  }

  /**
   * Define a named construct.
   * @param string
   *          name of construct
   * @param def
   *          the construct definition.
   * @return this factory.
   */
  public SyntaxFactory define(final String string, final Syntax... def) {
    Syntax seq;
    if (def.length == 1) {
      seq = def[0];
    } else {
      seq = new SequenceGroup(def);
    }
    defines.put(string, seq);
    return this;
  }

  /**
   * Create an Identifier construct.
   * @return An Identifier construct.
   */
  public Syntax ident() {
    return new Identifier();
  }

  /**
   * Create sequence group.
   * @param items
   *          the sequence definition.
   * @return A sequence construct.
   */
  public Syntax sequence(final Syntax... items) {
    return new SequenceGroup(items);
  }

  /**
   * Create mandatory unitary construct.
   * @param items
   *          the options.
   * @return a "pick one" construct
   */
  public Syntax oneOf(final Syntax... items) {
    return new SyntaxGroup(1, 1, items);
  }

  /**
   * Create mandatory construct of options.
   * @param items
   *          the options.
   * @return a "pick at least one" construct.
   */
  public Syntax oneOrMore(final Syntax... items) {
    return new SyntaxGroup(1, Integer.MAX_VALUE, items);
  }

  /**
   * Create an optional construct.
   * @param items
   *          the options.
   * @return an optional construct.
   */
  public Syntax zeroOrMore(final Syntax... items) {
    return new SyntaxGroup(1, Integer.MAX_VALUE, items);
  }

  /**
   * Create construct with non-zero options.
   * @param items
   *          the options.
   * @return a "pick at most one" construct.
   */
  public Syntax oneOrLess(final Syntax... items) {
    return new SyntaxGroup(0, 1, items);
  }

  /**
   * Create an unsequenced construct.
   * @param items
   *          the options.
   * @return a "pick at least one" in "any order" construct.
   */
  public Syntax anyOf(final Syntax... items) {
    return new SyntaxGroup(1, Integer.MAX_VALUE, items);
  }

  /**
   * Create an unsequenced construct.
   * @param items
   *          the options.
   * @return a "pick all" in "any order" construct.
   */
  public Syntax allOf(final Syntax... items) {
    return new SyntaxGroup(Integer.MIN_VALUE, Integer.MAX_VALUE, items);
  }

  /** Validate the language structure. */
  public void validate() {
    String missing = "";
    for (UseSyntax us:uses.values()) {
      if (!defines.containsKey(us.name)) {
        if (missing.length() == 0) {
          missing = us.name;
        } else {
          missing = missing + ", " + us.name;
        }
      }
    }
    if (missing.length() != 0) {
      throw new IllegalStateException(
              "Internal: missing language def(s): " + missing);
    }
  }

  /**
   * @param c the match character
   * @return rule to match character.
   */
  public Syntax character(final char c) {
    return new CharacterToken(c);
  }
}
