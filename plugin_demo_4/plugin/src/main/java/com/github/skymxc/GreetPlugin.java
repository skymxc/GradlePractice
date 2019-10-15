package com.github.skymxc;

import com.github.skymxc.task.HelloTask;
import org.gradle.api.Plugin;
import org.gradle.api.Project;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class GreetPlugin implements Plugin<Project> {
    @Override
    public void apply(Project target) {
        target.getTasks().create("hello", HelloTask.class);
    }


}
