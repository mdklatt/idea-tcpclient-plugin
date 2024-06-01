// This is a generated file. Not intended for manual editing.
package dev.mdklatt.idea.tcpclient.lang;

import com.intellij.psi.tree.IElementType;
import com.intellij.psi.PsiElement;
import com.intellij.lang.ASTNode;

public interface TcpRequestTypes {

  IElementType RECV_REQUEST = new TcpRequestElementType("RECV_REQUEST");
  IElementType SEND_REQUEST = new TcpRequestElementType("SEND_REQUEST");

  IElementType COMMENT = new TcpRequestTokenType("comment");
  IElementType QUOTED = new TcpRequestTokenType("quoted");
  IElementType RECV = new TcpRequestTokenType("RECV");
  IElementType SEND = new TcpRequestTokenType("SEND");
  IElementType SEP = new TcpRequestTokenType("###");
  IElementType URL = new TcpRequestTokenType("url");

  class Factory {
    public static PsiElement createElement(ASTNode node) {
      IElementType type = node.getElementType();
      if (type == RECV_REQUEST) {
        return new TcpRequestRecvRequestImpl(node);
      }
      else if (type == SEND_REQUEST) {
        return new TcpRequestSendRequestImpl(node);
      }
      throw new AssertionError("Unknown element type: " + type);
    }
  }
}
