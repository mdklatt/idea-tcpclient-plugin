// Adapted from <https://github.com/JetBrains/intellij-platform-plugin-template>.

import org.jetbrains.changelog.Changelog
import org.jetbrains.changelog.date
import org.jetbrains.changelog.markdownToHTML
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile


fun properties(key: String) = project.findProperty(key).toString()


plugins {
    kotlin("jvm") version("1.9.20")
    id("org.jetbrains.intellij") version "1.16.1"
    id("org.jetbrains.changelog") version "2.0.0"
}


repositories {
    mavenCentral()
}


dependencies {
    implementation(platform("org.jetbrains.kotlin:kotlin-bom"))
    testImplementation(kotlin("test"))

    // JUnit3 is required for running IDEA platform tests.
    testImplementation(platform("org.junit:junit-bom:5.10.1"))
    testRuntimeOnly("org.junit.vintage:junit-vintage-engine")
    
}


tasks {

    withType<JavaCompile> {
        sourceCompatibility = properties("javaVersion")
        targetCompatibility = properties("javaVersion")
    }

    withType<KotlinCompile> {
        kotlinOptions.jvmTarget = properties("javaVersion")
    }

    wrapper {
        gradleVersion = "8.4"
    }

    patchPluginXml {
        // https://plugins.jetbrains.com/docs/intellij/tools-gradle-intellij-plugin.html#tasks-patchpluginxml
        sinceBuild.set(properties("pluginSinceBuild"))
        untilBuild.set(properties("pluginUntilBuild"))

        // Extract the <!-- Plugin description --> section from README.md and provide for the plugin's manifest
        pluginDescription.set(
            File(projectDir, "README.md").readText().lines().run {
                val start = "<!-- Plugin description -->"
                val end = "<!-- Plugin description end -->"
                if (!containsAll(listOf(start, end))) {
                    throw GradleException("Plugin description section not found in README.md:\n$start ... $end")
                }
                subList(indexOf(start) + 1, indexOf(end))
            }.joinToString("\n").run { markdownToHTML(this) }
        )

        // Get the latest available change notes from the changelog file
        changeNotes.set(changelog.renderItem(changelog.getLatest(), Changelog.OutputType.HTML))

    }

    runPluginVerifier {
        // https://github.com/JetBrains/intellij-plugin-verifier
        ideVersions.set(properties("pluginVerifyVersions").split(',').map(String::trim).filter(String::isNotEmpty))
    }

    runIdeForUiTests {
        // <https://github.com/JetBrains/intellij-ui-test-robot>
        systemProperty("robot-server.port", "8082")
        systemProperty("ide.mac.message.dialogs.as.sheets", "false")
        systemProperty("jb.privacy.policy.text", "<!--999.999-->")
        systemProperty("jb.consents.confirmation.enabled", "false")
    }

    publishPlugin {
        dependsOn("patchChangelog")
        token.set(System.getenv("PUBLISH_TOKEN"))
        // pluginVersion is based on the SemVer (https://semver.org) and supports pre-release labels, like 2.1.7-alpha.3
        // Specify pre-release label to publish the plugin in a custom Release Channel automatically. Read more:
        // https://plugins.jetbrains.com/docs/intellij/deployment.html#specifying-a-release-channel
        channels.set(listOf(properties("pluginVersion").split('-').getOrElse(1) { "default" }.split('.').first()))
    }

	test {
        
    }

    check {
        // Add plugin validation tasks to default checks.
        dependsOn(verifyPlugin)
        dependsOn(verifyPluginConfiguration)
        dependsOn(runPluginVerifier)
    }
}


intellij {
    // <https://plugins.jetbrains.com/docs/intellij/tools-gradle-intellij-plugin.html>
    version.set(properties("platformVersion"))
    updateSinceUntilBuild.set(true)
    downloadSources.set(true)
}


changelog {
    // <https://github.com/JetBrains/gradle-changelog-plugin>
    path.set("${project.projectDir}/CHANGELOG.md")
    header.set(provider { "[${version.get()}] - ${date()}" })
    itemPrefix.set("-")
    unreleasedTerm.set("[Unreleased]")
    groups.set(listOf("Added", "Changed", "Deprecated", "Removed", "Fixed", "Security"))
}
