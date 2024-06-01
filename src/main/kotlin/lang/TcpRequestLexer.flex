package dev.mdklatt.idea.tcpclient.lang;

import com.intellij.lexer.FlexLexer;
import com.intellij.psi.tree.IElementType;

import static com.intellij.psi.TokenType.BAD_CHARACTER;
import static com.intellij.psi.TokenType.WHITE_SPACE;
import static dev.mdklatt.idea.tcpclient.lang.TcpRequestTypes.*;

%%

%{
  public TcpRequestLexer() {
    this((java.io.Reader)null);
  }
%}

%public
%class TcpRequestLexer
%implements FlexLexer
%function advance
%type IElementType
%unicode

EOL=\R
WHITE_SPACE=\s+

SPACE=[ \t\n\x0B\f\r]+
COMMENT=#.*
URL=tcp:"//"([a-zA-Z._\-]+):([0-9]+)
QUOTED=('([^'\\]|\\.)*'|\"([^\"\\]|\\.)*\")

%%
<YYINITIAL> {
  {WHITE_SPACE}       { return WHITE_SPACE; }

  "RECV"              { return RECV; }
  "SEND"              { return SEND; }
  "###"               { return SEP; }

  {SPACE}             { return SPACE; }
  {COMMENT}           { return COMMENT; }
  {URL}               { return URL; }
  {QUOTED}            { return QUOTED; }

}

[^] { return BAD_CHARACTER; }
