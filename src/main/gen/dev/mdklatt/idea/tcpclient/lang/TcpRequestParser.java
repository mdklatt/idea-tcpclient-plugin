// This is a generated file. Not intended for manual editing.
package dev.mdklatt.idea.tcpclient.lang;

import com.intellij.lang.PsiBuilder;
import com.intellij.lang.PsiBuilder.Marker;
import static dev.mdklatt.idea.tcpclient.lang.TcpRequestTypes.*;
import static com.intellij.lang.parser.GeneratedParserUtilBase.*;
import com.intellij.psi.tree.IElementType;
import com.intellij.lang.ASTNode;
import com.intellij.psi.tree.TokenSet;
import com.intellij.lang.PsiParser;
import com.intellij.lang.LightPsiParser;

@SuppressWarnings({"SimplifiableIfStatement", "UnusedAssignment"})
public class TcpRequestParser implements PsiParser, LightPsiParser {

  public ASTNode parse(IElementType t, PsiBuilder b) {
    parseLight(t, b);
    return b.getTreeBuilt();
  }

  public void parseLight(IElementType t, PsiBuilder b) {
    boolean r;
    b = adapt_builder_(t, b, this, null);
    Marker m = enter_section_(b, 0, _COLLAPSE_, null);
    r = parse_root_(t, b);
    exit_section_(b, 0, m, t, r, true, TRUE_CONDITION);
  }

  protected boolean parse_root_(IElementType t, PsiBuilder b) {
    return parse_root_(t, b, 0);
  }

  static boolean parse_root_(IElementType t, PsiBuilder b, int l) {
    return requestFile(b, l + 1);
  }

  /* ********************************************************** */
  // !<<eof>> (sendRequest | recvRequest | SEP)
  static boolean item(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "item")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = item_0(b, l + 1);
    r = r && item_1(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  // !<<eof>>
  private static boolean item_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "item_0")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NOT_);
    r = !eof(b, l + 1);
    exit_section_(b, l, m, r, false, null);
    return r;
  }

  // sendRequest | recvRequest | SEP
  private static boolean item_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "item_1")) return false;
    boolean r;
    r = sendRequest(b, l + 1);
    if (!r) r = recvRequest(b, l + 1);
    if (!r) r = consumeToken(b, SEP);
    return r;
  }

  /* ********************************************************** */
  // RECV url
  public static boolean recvRequest(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "recvRequest")) return false;
    if (!nextTokenIs(b, RECV)) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeTokens(b, 0, RECV, URL);
    exit_section_(b, m, RECV_REQUEST, r);
    return r;
  }

  /* ********************************************************** */
  // item *
  static boolean requestFile(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "requestFile")) return false;
    while (true) {
      int c = current_position_(b);
      if (!item(b, l + 1)) break;
      if (!empty_element_parsed_guard_(b, "requestFile", c)) break;
    }
    return true;
  }

  /* ********************************************************** */
  // SEND url quoted
  public static boolean sendRequest(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "sendRequest")) return false;
    if (!nextTokenIs(b, SEND)) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeTokens(b, 0, SEND, URL, QUOTED);
    exit_section_(b, m, SEND_REQUEST, r);
    return r;
  }

}
