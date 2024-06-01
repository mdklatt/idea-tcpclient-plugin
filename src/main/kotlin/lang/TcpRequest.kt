package dev.mdklatt.idea.tcpclient.lang

import com.intellij.extapi.psi.PsiFileBase
import com.intellij.icons.AllIcons
import com.intellij.lang.ASTNode
import com.intellij.lang.Language
import com.intellij.lang.ParserDefinition
import com.intellij.lexer.FlexAdapter
import com.intellij.openapi.fileTypes.LanguageFileType
import com.intellij.openapi.project.Project
import com.intellij.psi.FileViewProvider
import com.intellij.psi.PsiElement
import com.intellij.psi.tree.IElementType
import com.intellij.psi.tree.IFileElementType
import com.intellij.psi.tree.TokenSet


/**
 * TCP Request file type.
 *
 * @see: <a href="https://plugins.jetbrains.com/docs/intellij/registering-file-type.html">Registering a File Type</a>
 */
class TcpRequestFileType: LanguageFileType(TcpRequestLanguage.INSTANCE) {

    companion object {
        val INSTANCE = TcpRequestFileType()
    }

    /**
     * File type name.
     *
     * This must match <fileType name="..."/> in plugin.xml.
     *
     * @return: name
     */
    override fun getName() = "TCP Request"

    /**
     * Default file extension.
     *
     * @return: extension
     */
    override fun getDefaultExtension() = "tcp"

    /**
     * File type description.
     *
     * @return: description
     */
    override fun getDescription() = "TCP Request file"

    /**
     * Get the 16x16 file type icon.
     *
     * @return: icon
     * @see <a href="https://www.jetbrains.org/intellij/sdk/docs/reference_guide/work_with_icons_and_images.html">Icons and Images</a>
     */
    override fun getIcon() = AllIcons.FileTypes.Custom
}


/**
 *
 */
class TcpRequestLanguage: Language("TCP Request") {

    companion object {
        val INSTANCE = TcpRequestLanguage()
    }
}


/**
 *
 */
class TcpRequestTokenType(debugName: String) :
    IElementType(debugName, TcpRequestLanguage.INSTANCE) {

    override fun toString(): String {
        return "TcpRequestTokenType." + super.toString()
    }
}


/**
 *
 */
class TcpRequestElementType(debugName: String) :
    IElementType(debugName, TcpRequestLanguage.INSTANCE)


/**
 *
 */
class TcpRequestLexerAdapter : FlexAdapter(TcpRequestLexer(null))


/**
 * 
 */
class TcpRequestFile(viewProvider: FileViewProvider) :
    PsiFileBase(viewProvider, TcpRequestLanguage.INSTANCE) {

    /**
     *
     */
    override fun getFileType() = TcpRequestFileType.INSTANCE

    /**
     *
     */
    override fun toString() = "TCP Request File"
}


/**
 * 
 */
interface TcpRequestTokenSets {
    companion object {
        val RECV = TokenSet.create(TcpRequestTypes.RECV)
        val SEND = TokenSet.create(TcpRequestTypes.SEND)
        val SEP = TokenSet.create(TcpRequestTypes.SEP)
        val URL = TokenSet.create(TcpRequestTypes.URL)
        val QUOTED = TokenSet.create(TcpRequestTypes.QUOTED)
        val COMMENT = TokenSet.create(TcpRequestTypes.COMMENT)
    }
}


/**
 * 
 */
internal class TcpRequestParserDefinition : ParserDefinition {

    override fun createLexer(project: Project?) = TcpRequestLexerAdapter()

    override fun getCommentTokens(): TokenSet = TcpRequestTokenSets.COMMENT

    override fun getStringLiteralElements(): TokenSet = TokenSet.EMPTY

    override fun createParser(project: Project?) = TcpRequestParser()

    override fun getFileNodeType() = FILE

    override fun createFile(viewProvider: FileViewProvider) = TcpRequestFile(viewProvider)

    override fun createElement(node: ASTNode?): PsiElement = TcpRequestTypes.Factory.createElement(node)

    companion object {
        val FILE = IFileElementType(TcpRequestLanguage.INSTANCE)
    }
}
