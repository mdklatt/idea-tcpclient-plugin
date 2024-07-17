/**
 * Unit tests for the TcpRequest module.
 */
package dev.mdklatt.idea.tcpclient.configurations

import com.intellij.execution.RunManager
import com.intellij.execution.executors.DefaultRunExecutor
import com.intellij.execution.runners.ExecutionEnvironment
import com.intellij.execution.runners.ExecutionEnvironmentBuilder
import com.intellij.testFramework.fixtures.BasePlatformTestCase
import kotlin.test.Test
import kotlin.test.assertNotNull
import kotlin.test.assertTrue
import org.jdom.Element


/**
 * Unit tests for the TcpRequestConfigurationType class.
 */
class TcpRequestConfigurationTypeTest {

    private var type = TcpRequestConfigurationType()

    /**
     * Test the id property.
     */
    @Test
    fun testId() {
        assertTrue(type.id.isNotBlank())
    }

    /**
     * Test the icon property.
     */
    @Test
    fun testIcon() {
        assertNotNull(type.icon)
    }

    /**
     * Test the configurationTypeDescription property.
     */
    @Test
    fun testConfigurationTypeDescription() {
        assertTrue(type.configurationTypeDescription.isNotBlank())
    }

    /**
     * Test the displayName property.
     */
    @Test
    fun testDisplayName() {
        assertTrue(type.displayName.isNotBlank())
    }

    /**
     * Test the configurationFactories property.
     */
    @Test
    fun testConfigurationFactories() {
        type.configurationFactories.isNotEmpty()
    }
}


// The IDEA platform tests use JUnit3, so test class method names are used to 
// determine behavior instead of annotations. Notably, test classes are *not* 
// constructed before each test, so setUp() methods should be used for 
// per-test initialization where necessary. Also, test functions must be named 
// `testXXX` or they will not be found during automatic discovery.


/**
 * Unit tests for the TcpRequestConfigurationFactory class.
 */
class TcpRequestConfigurationFactoryTest : BasePlatformTestCase() {

    private lateinit var factory: TcpRequestConfigurationFactory

    /**
     * Per-test initialization.
     */
    override fun setUp() {
        super.setUp()
        factory = TcpRequestConfigurationFactory(TcpRequestConfigurationType())
    }

    /**
     * Test the `id` property.
     */
    fun testId() {
        assertTrue(factory.id.isNotBlank())
    }

    /**
     * Test the `name` property.
     */
    fun testName() {
        assertTrue(factory.name.isNotBlank())
    }

    /**
     * Test the testCreateTemplateConfiguration() method.
     */
    fun testCreateTemplateConfiguration() {
        // Just a smoke test to ensure that the expected RunConfiguration type
        // is returned.
        assertInstanceOf(factory.createTemplateConfiguration(project), TcpRequestRunConfiguration::class.java)
    }

}

/**
 * Unit tests for the TcpRequestRunConfiguration class.
 */
class TcpRequestRunConfigurationTest : BasePlatformTestCase() {

    private lateinit var factory: TcpRequestConfigurationFactory
    private lateinit var config: TcpRequestRunConfiguration

    /**
     * Per-test initialization.
     */
    override fun setUp() {
        super.setUp()
        factory = TcpRequestConfigurationFactory(TcpRequestConfigurationType())
        config = TcpRequestRunConfiguration(project, factory, "TcpRequest Test")
        return
    }

    /**
     * Test the constructor.
     */
    fun testConstructor() {
        assertEquals("", config.host)
        return
    }

    /**
     * Test round-trip write/read of settings.
     */
    fun testPersistence() {
        val element = Element("configuration")
        config.let {
            it.host = "localhost:8080"
            it.message = "hello"
            it.writeExternal(element)
        }
        TcpRequestRunConfiguration(project, factory, "Persistence Test").let {
            it.readExternal(element)
            assertEquals(config.host, it.host)
            assertEquals(config.message, it.message)
        }
        return
    }
}


/**
 * Unit tests for the TcpRequestSettingsEditor class.
 */
class TcpRequestSettingsEditorTest : BasePlatformTestCase() {
    /**
     * Test the constructor.
     */
    fun testConstructor() {
        TcpRequestSettingsEditor().apply {
            assertTrue(host.isEmpty())
            assertTrue(message.isEmpty())
        }
        return
    }
}


/**
 * Unit tests for the TcpRequestState class.
 */
internal class TcpRequestStateTest: BasePlatformTestCase() {

    private lateinit var configuration: TcpRequestRunConfiguration
    private lateinit var state: TcpRequestState
    private lateinit var environment: ExecutionEnvironment

    /**
     * Per-test initialization.
     */
    override fun setUp() {
        super.setUp()
        val factory = TcpRequestConfigurationFactory(TcpRequestConfigurationType())
        val settings = RunManager.getInstance(project).createConfiguration("TCP Request Test", factory)
        configuration = (settings.configuration as TcpRequestRunConfiguration).also {
            it.host = "www.example.com:80"
            it.message = """
                GET / HTTP/1.1\r
                \r
            """.trimIndent()
        }
        val executor = DefaultRunExecutor.getRunExecutorInstance()
        environment = ExecutionEnvironmentBuilder.create(executor, settings).build()
        state = TcpRequestState(configuration, environment)
    }

    /**
     * Test execution.
     */
    fun testExec() {
        // This uses the default test installation, which is a local Python
        // virtualenv (see AnsibleCommandLineStateTest).
        state.execute(environment.executor, environment.runner).processHandler.let {
            it.startNotify()
            it.waitFor()
            assertEquals(0, it.exitCode)
        }
    }
}
