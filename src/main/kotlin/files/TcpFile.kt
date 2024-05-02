package dev.mdklatt.idea.tcpclient.files

import com.intellij.icons.AllIcons
import com.intellij.openapi.fileTypes.UserFileType
import com.intellij.openapi.options.SettingsEditor


/**
 * TCP Request file type.
 *
 * @see: <a href="https://plugins.jetbrains.com/docs/intellij/registering-file-type.html">Registering a File Type</a>
 */
class TcpFileType: UserFileType<TcpFileType>() {

    companion object {
        val INSTANCE = TcpFileType()
    }

    /**
     * File type name.
     *
     * This must match <fileType name="..."/> in plugin.xml.
     *
     * @return: name
     */
    override fun getName() = "TCP"

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
    override fun getDescription() = "TCP Request File"

    /**
     * Get the 16x16 file type icon.
     *
     * @return: icon
     * @see <a href="https://www.jetbrains.org/intellij/sdk/docs/reference_guide/work_with_icons_and_images.html">Icons and Images</a>
     */
    override fun getIcon() = AllIcons.FileTypes.Custom


    /**
     * Determine if file contains binary content.
     *
     * @return `false`
     */
    override fun isBinary() = false

    /**
     * Get an editor for this file type.
     */
    override fun getEditor(): SettingsEditor<TcpFileType> {
        TODO("Not yet implemented")
    }
}
