package net.witerat.cafenatedsql.spi.driver.template.simple;

/**
 * A factory for creating SimpleSyntax objects.
 */
class SimpleSyntaxFactory extends SyntaxFactory {
  {
  SyntaxFactory sf = this;
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
}
