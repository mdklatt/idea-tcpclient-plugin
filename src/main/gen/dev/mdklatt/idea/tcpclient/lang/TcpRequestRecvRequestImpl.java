// This is a generated file. Not intended for manual editing.
package dev.mdklatt.idea.tcpclient.lang;

import java.util.List;
import org.jetbrains.annotations.*;
import com.intellij.lang.ASTNode;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiElementVisitor;
import com.intellij.psi.util.PsiTreeUtil;
import static dev.mdklatt.idea.tcpclient.lang.TcpRequestTypes.*;
import com.intellij.extapi.psi.ASTWrapperPsiElement;

public class TcpRequestRecvRequestImpl extends ASTWrapperPsiElement implements TcpRequestRecvRequest {

  public TcpRequestRecvRequestImpl(@NotNull ASTNode node) {
    super(node);
  }

  public void accept(@NotNull TcpRequestVisitor visitor) {
    visitor.visitRecvRequest(this);
  }

  @Override
  public void accept(@NotNull PsiElementVisitor visitor) {
    if (visitor instanceof TcpRequestVisitor) accept((TcpRequestVisitor)visitor);
    else super.accept(visitor);
  }

  @Override
  @NotNull
  public PsiElement getUrl() {
    return findNotNullChildByType(URL);
  }

}
