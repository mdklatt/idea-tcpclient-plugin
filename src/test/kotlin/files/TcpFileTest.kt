/**
 * Unit tests for TcpFile.kt.
 */
package dev.mdklatt.idea.tcpclient.files

import com.intellij.testFramework.fixtures.BasePlatformTestCase
import kotlin.test.assertIs


/**
 * Unit tests for the TcpFileType class.
 */
internal class TcpFileTypeTest : BasePlatformTestCase() {

    private lateinit var type: TcpFileType

    /**
     * Per-test setup
     */
    override fun setUp() {
        super.setUp()
        type = TcpFileType()
    }

    /**
     * Get the path for test resource files.
     *
     * @return resource path
     */
    override fun getTestDataPath() = "src/test/resources"

    /**
     * Test the defaultExtension property.
     */
    fun testDefaultExtension() {
        assertEquals("tcp", type.defaultExtension)
    }

    /**
     * Test the icon property.
     */
    fun testIcon() {
        assertNotNull(type.icon)
    }

    /**
     * Test the description property.
     */
    fun testDescription() {
        assertTrue(type.description.isNotEmpty())
    }

    /**
     * Test the isBinary property.
     */
    fun testIsBinary() {
        assertFalse(type.isBinary)
    }

    /**
     * Test file type recognition.
     */
    fun testFileType() {
        val file = myFixture.copyFileToProject("requests.tcp")
        assertIs<TcpFileType>(file.fileType)
    }
}
