package dev.mdklatt.idea.tcpclient.lang

import com.intellij.icons.AllIcons
import com.intellij.lang.Language
import com.intellij.openapi.fileTypes.LanguageFileType
import com.intellij.psi.tree.IElementType
import org.jetbrains.annotations.NonNls




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
