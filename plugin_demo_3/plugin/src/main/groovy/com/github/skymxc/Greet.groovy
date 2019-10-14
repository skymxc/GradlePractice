package com.github.skymxc

import com.github.skymxc.configure.Hello
import org.gradle.api.Plugin
import org.gradle.api.Project

class Greet implements Plugin<Project> {
    @Override
    void apply(Project target) {
        target.extensions.create("hello", Hello)
        target.task("hello") {
            doLast {
                println "message -> ${target.hello.message}"
            }
        }
    }
}

