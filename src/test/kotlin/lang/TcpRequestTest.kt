/**
 * Unit tests for lang.TcpRequest.
 */
package dev.mdklatt.idea.tcpclient.lang

import com.intellij.testFramework.fixtures.BasePlatformTestCase
import com.intellij.testFramework.utils.vfs.getPsiFile
import kotlin.test.assertIs


/**
 * Unit tests for the TcpFileType class.
 */
internal class TcpRequestFileTypeTest : BasePlatformTestCase() {

    private lateinit var type: TcpRequestFileType

    /**
     * Per-test setup
     */
    override fun setUp() {
        super.setUp()
        type = TcpRequestFileType()
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
        assertIs<TcpRequestFileType>(file.fileType)
    }
}


/**
 * Unit tests for the TcpFileType class.
 */
internal class TcpRequestFileTest : BasePlatformTestCase() {

    /**
     * Get the path for test resource files.
     *
     * @return resource path
     */
    override fun getTestDataPath() = "src/test/resources"

    fun testFile() {
        val vfile = myFixture.copyFileToProject("requests.tcp")
        TcpRequestFile(vfile.getPsiFile(project).viewProvider).let {
            assertEquals("FILE", it.contentElementType.debugName)
        }
        return
    }
}