package com.github.skymxc

import org.gradle.api.DefaultTask
import org.gradle.api.tasks.TaskAction

class Greet extends DefaultTask{

    private String message

    String getMessage() {
        return message
    }

    void setMessage(String message) {
        this.message = message
    }

    @TaskAction
    void sayGreeting() {
        System.out.println(getMessage())
    }
}
