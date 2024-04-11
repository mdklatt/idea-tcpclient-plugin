/**
 * Sample implementation of a run configuration that calls an external process.
 */
package dev.mdklatt.idea.tcpclient.configurations

import com.intellij.execution.Executor
import com.intellij.execution.configurations.*
import com.intellij.execution.process.KillableColoredProcessHandler
import com.intellij.execution.process.ProcessHandler
import com.intellij.execution.process.ProcessTerminatedListener
import com.intellij.execution.runners.ExecutionEnvironment
import com.intellij.icons.AllIcons
import com.intellij.openapi.options.SettingsEditor
import com.intellij.openapi.project.Project
import com.intellij.openapi.ui.DialogPanel
import com.intellij.ui.dsl.builder.bindText
import com.intellij.ui.dsl.builder.panel
import javax.swing.JComponent


/**
 * Hello run configuration type.
 *
 * @see <a href="https://plugins.jetbrains.com/docs/intellij/run-configuration-management.html?from=jetbrains.org#configuration-type">Configuration Type</a>
 */
class HelloConfigurationType : ConfigurationTypeBase(
    "HelloConfigurationType",
    "Hello",
    "Display a greeting",
    AllIcons.General.GearPlain
) {
    init {
        addFactory(HelloConfigurationFactory(this))
    }
}

/**
 * Factory for HelloRunConfiguration instances.
 *
 * @see <a href="https://www.jetbrains.org/intellij/sdk/docs/basics/run_configurations/run_configuration_management.html#configuration-factory">Configuration Factory</a>
 */
class HelloConfigurationFactory internal constructor(type: ConfigurationType) : ConfigurationFactory(type) {
    /**
     * Creates a new template run configuration within the context of the specified project.
     *
     * @param project the project in which the run configuration will be used
     * @return the run configuration instance.
     */
    override fun createTemplateConfiguration(project: Project) =
        HelloRunConfiguration(project, this, "")

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
    override fun getOptionsClass() = HelloRunConfiguration.Options::class.java
}

/**
 * Run configuration for printing "Hello, World!" to the console.
 *
 * @see <a href="https://www.jetbrains.org/intellij/sdk/docs/basics/run_configurations/run_configuration_management.html#run-configuration">Run Configuration</a>
 */
class HelloRunConfiguration internal constructor(project: Project, factory: ConfigurationFactory, name: String) :
    RunConfigurationBase<HelloRunConfiguration.Options>(project, factory, name) {
 
    /**
     * Persistent run configuration options.
     *
     * @see <a href="https://plugins.jetbrains.com/docs/intellij/run-configurations.html#implement-a-configurationfactory">Run Configurations Tutorial</a>
     */
    class Options : RunConfigurationOptions() {
        internal var subject by string()
    }
 
    override fun getOptions(): Options {
        return super.getOptions() as Options
    }

    internal var subject: String
        get() = options.subject ?: "World"
        set(value) {
            options.subject = value
        }

    /**
     * Returns the UI control for editing the run configuration settings. If additional control over validation is required, the object
     * returned from this method may also implement [com.intellij.execution.impl.CheckableRunConfigurationEditor]. The returned object
     * can also implement [com.intellij.openapi.options.SettingsEditorGroup] if the settings it provides need to be displayed in
     * multiple tabs.
     *
     * @return the settings editor component.
     */
    override fun getConfigurationEditor() = HelloSettingsEditor()

    /**
     * Prepares for executing a specific instance of the run configuration.
     *
     * @param executor the execution mode selected by the user (run, debug, profile etc.)
     * @param environment the environment object containing additional settings for executing the configuration.
     * @return the RunProfileState describing the process which is about to be started, or null if it's impossible to start the process.
     */
    override fun getState(executor: Executor, environment: ExecutionEnvironment) =
        HelloCommandLineState(this, environment)
}


/**
 * Command line process for executing the run configuration.
 *
 * @param config: run configuration
 * @param environment: execution environment
 * @see <a href="https://plugins.jetbrains.com/docs/intellij/run-configurations.html#implement-a-run-configuration">Run Configurations Tutorial</a>
 */
class HelloCommandLineState internal constructor(private val config: HelloRunConfiguration, environment: ExecutionEnvironment) :
    CommandLineState(environment) {

    /**
     * Start the external process.
     *
     * @return the handler for the running process
     * @throws ExecutionException if the execution failed.
     * @see GeneralCommandLine
     *
     * @see com.intellij.execution.process.OSProcessHandler
     */
    override fun startProcess(): ProcessHandler {
        val command = GeneralCommandLine("echo", "Hello, ${config.subject}!")
        return KillableColoredProcessHandler(command).also {
            ProcessTerminatedListener.attach(it, environment.project)
        }
    }
}


/**
 * UI component for Hello Run Configuration settings.
 *
 * @see <a href="https://www.jetbrains.org/intellij/sdk/docs/basics/run_configurations/run_configuration_management.html#settings-editor">Settings Editor</a>
 */
class HelloSettingsEditor internal constructor() : SettingsEditor<HelloRunConfiguration>() {

    internal var subject = ""

    /**
     * Create the widget for this editor.
     *
     * @return UI widget
     */
    override fun createEditor(): JComponent {
       // https://plugins.jetbrains.com/docs/intellij/kotlin-ui-dsl-version-2.html
       return panel {
            row("Subject:") { textField().bindText(::subject) }
        }
    }

    /**
     * Reset editor fields from the configuration settings.
     *
     * @param config: run configuration
     */
    override fun resetEditorFrom(config: HelloRunConfiguration) {
        // Update bound properties from config then reset UI.
        config.let {
            subject = it.subject
        }
        (this.component as DialogPanel).reset()
       return
    }

    /**
     * Apply editor fields to the configuration settings.
     *
     * @param config: run configuration
     */
    override fun applyEditorTo(config: HelloRunConfiguration) {
        // Apply UI to bound properties then update config.
        (this.component as DialogPanel).apply()
        config.let {
            it.subject = subject
        }
        return
    }
}
