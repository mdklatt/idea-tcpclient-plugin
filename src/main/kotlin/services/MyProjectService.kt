package dev.mdklatt.idea.tcpclient.services

import com.intellij.openapi.project.Project
import dev.mdklatt.idea.tcpclient.MyBundle

class MyProjectService(project: Project) {

    init {
        println(MyBundle.message("projectService", project.name))
    }
}
