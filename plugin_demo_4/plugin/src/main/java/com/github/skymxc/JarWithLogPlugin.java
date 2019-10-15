package com.github.skymxc;

import com.github.skymxc.extension.JarLogExtension;
import com.github.skymxc.task.JarWithLogTask;
import org.gradle.api.Plugin;
import org.gradle.api.Project;

public class JarWithLogPlugin implements Plugin<Project> {


    @Override
    public void apply(Project target) {
        target.getExtensions().add("jarLog", JarLogExtension.class);
        target.getTasks().create("jarWithLog", JarWithLogTask.class);
    }
}
