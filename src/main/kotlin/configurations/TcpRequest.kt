/**
 * Sample implementation of a run configuration that calls an external process.
 */
package dev.mdklatt.idea.tcpclient.configurations

import com.intellij.execution.DefaultExecutionResult
import com.intellij.execution.ExecutionResult
import com.intellij.execution.Executor
import com.intellij.execution.configurations.*
import com.intellij.execution.filters.TextConsoleBuilderFactory
import com.intellij.execution.process.NopProcessHandler
import com.intellij.execution.process.ProcessHandler
import com.intellij.execution.process.ProcessTerminatedListener
import com.intellij.execution.runners.ExecutionEnvironment
import com.intellij.execution.runners.ProgramRunner
import com.intellij.execution.ui.ConsoleView
import com.intellij.execution.ui.ConsoleViewContentType
import com.intellij.icons.AllIcons
import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.options.SettingsEditor
import com.intellij.openapi.project.Project
import com.intellij.openapi.ui.DialogPanel
import com.intellij.openapi.util.Key
import com.intellij.ui.dsl.builder.bindText
import com.intellij.ui.dsl.builder.panel
import dev.mdklatt.idea.tcpclient.tcp.TcpClient
import java.io.OutputStream
import javax.swing.JComponent


/**
 * TcpRequest run configuration type.
 *
 * @see <a href="https://plugins.jetbrains.com/docs/intellij/run-configuration-management.html?from=jetbrains.org#configuration-type">Configuration Type</a>
 */
class TcpRequestConfigurationType : ConfigurationTypeBase(
    "TcpRequestConfigurationType",
    "TcpRequest",
    "Execute a TCP request",
    AllIcons.RunConfigurations.Remote
) {
    init {
        addFactory(TcpRequestConfigurationFactory(this))
    }
}


/**
 * Factory for TcpRequestRunConfiguration instances.
 *
 * @see <a href="https://www.jetbrains.org/intellij/sdk/docs/basics/run_configurations/run_configuration_management.html#configuration-factory">Configuration Factory</a>
 */
class TcpRequestConfigurationFactory internal constructor(type: ConfigurationType) : ConfigurationFactory(type) {
    /**
     * Creates a new template run configuration within the context of the specified project.
     *
     * @param project the project in which the run configuration will be used
     * @return the run configuration instance.
     */
    override fun createTemplateConfiguration(project: Project) =
        TcpRequestRunConfiguration(project, this, "")

    /**
     * Run configuration ID used for serialization.
     *
     * @return: unique ID
     */
    override fun getId(): String = this::class.java.simpleName

    /**
     * Return the type of the options storage class.
     *
     * @return: options class type
     */
    override fun getOptionsClass() = TcpRequestRunConfiguration.Options::class.java
}


/**
 * Run configuration for printing "TcpRequest, World!" to the console.
 *
 * @see <a href="https://www.jetbrains.org/intellij/sdk/docs/basics/run_configurations/run_configuration_management.html#run-configuration">Run Configuration</a>
 */
class TcpRequestRunConfiguration internal constructor(project: Project, factory: ConfigurationFactory, name: String) :
    RunConfigurationBase<TcpRequestRunConfiguration.Options>(project, factory, name) {
 
    /**
     * Persistent run configuration options.
     *
     * @see <a href="https://plugins.jetbrains.com/docs/intellij/run-configurations.html#implement-a-configurationfactory">Run Configurations Tutorial</a>
     */
    class Options : RunConfigurationOptions() {
        internal var host by string()
        internal var message by string()
    }
 
    override fun getOptions(): Options {
        return super.getOptions() as Options
    }

    internal var host: String
        get() = options.host ?: ""
        set(value) {
            options.host = value
        }

    internal var message: String
        get() = options.message ?: ""
        set(value) {
            options.message = value
        }

    /**
     * Returns the UI control for editing the run configuration settings. If additional control over validation is required, the object
     * returned from this method may also implement [com.intellij.execution.impl.CheckableRunConfigurationEditor]. The returned object
     * can also implement [com.intellij.openapi.options.SettingsEditorGroup] if the settings it provides need to be displayed in
     * multiple tabs.
     *
     * @return the settings editor component.
     */
    override fun getConfigurationEditor() = TcpRequestSettingsEditor()

    /**
     * Prepares for executing a specific instance of the run configuration.
     *
     * @param executor the execution mode selected by the user (run, debug, profile etc.)
     * @param environment the environment object containing additional settings for executing the configuration.
     * @return the RunProfileState describing the process which is about to be started, or null if it's impossible to start the process.
     */
    override fun getState(executor: Executor, environment: ExecutionEnvironment) =
        TcpRequestState(this, environment)
}


/**
 * Process for executing a TcpRequestRunConfiguration.
 *
 * @param config: run configuration
 * @param environment: execution environment
 * @see <a href="https://plugins.jetbrains.com/docs/intellij/run-configurations.html#implement-a-run-configuration">Run Configurations Tutorial</a>
 */
class TcpRequestState(
    private val runConfiguration: TcpRequestRunConfiguration,
    private val environment: ExecutionEnvironment
) : RunProfileState {

    private val consoleBuilder = TextConsoleBuilderFactory.getInstance().createBuilder(environment.project)

    /**
     * Execute a run configuration.
     *
     * @param executor
     * @param programRunner
     * @return execution result
     */
    override fun execute(executor: Executor?, programRunner: ProgramRunner<*>): ExecutionResult {
        val (host, port) = runConfiguration.host.split(":", limit = 2)
        val client = TcpClient(host, port.toInt())
        client.send(runConfiguration.message.encodeToByteArray())
        val response = client.recv().decodeToString()
        val console = consoleBuilder.console
        console.print(response, ConsoleViewContentType.NORMAL_OUTPUT)
        val processHandler = NopProcessHandler().also {
            ProcessTerminatedListener.attach(it, environment.project)
        }
        return DefaultExecutionResult(console, processHandler)
    }
}


/**
 * UI component for TcpRequest Run Configuration settings.
 *
 * @see <a href="https://www.jetbrains.org/intellij/sdk/docs/basics/run_configurations/run_configuration_management.html#settings-editor">Settings Editor</a>
 */
class TcpRequestSettingsEditor internal constructor() : SettingsEditor<TcpRequestRunConfiguration>() {

    internal var host = ""
    internal var message = ""

    /**
     * Create the widget for this editor.
     *
     * @return UI widget
     */
    override fun createEditor(): JComponent {
       // https://plugins.jetbrains.com/docs/intellij/kotlin-ui-dsl-version-2.html
       return panel {
            row("Host:") { textField().bindText(::host) }
            row("Message:") { textField().bindText(::message) }
        }
    }

    /**
     * Reset editor fields from the configuration settings.
     *
     * @param config: run configuration
     */
    override fun resetEditorFrom(config: TcpRequestRunConfiguration) {
        // Update bound properties from config then reset UI.
        config.let {
            host = it.host
            message = it.message
        }
        (this.component as DialogPanel).reset()
       return
    }

    /**
     * Apply editor fields to the configuration settings.
     *
     * @param config: run configuration
     */
    override fun applyEditorTo(config: TcpRequestRunConfiguration) {
        // Apply UI to bound properties then update config.
        (this.component as DialogPanel).apply()
        config.let {
            it.host = host
            it.message = message
        }
        return
    }
}
