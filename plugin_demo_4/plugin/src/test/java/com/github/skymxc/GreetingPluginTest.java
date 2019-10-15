package com.github.skymxc;

import com.github.skymxc.task.HelloTask;
import org.gradle.api.Project;
import org.gradle.api.Task;
import org.gradle.testfixtures.ProjectBuilder;
import org.junit.Test;

import java.io.IOException;

public class GreetingPluginTest {

    @Test
    public void applyGreetPluginToProject() {
        System.out.println("执行测试了。");
        Project project = ProjectBuilder.builder().build();
        project.getPluginManager().apply("com.github.skymxc.greet");
        HelloTask task = (HelloTask) project.getTasks().getByName("hello");
        assert task != null;
        try {
            task.greet();
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("应用插件成功了，拥有任务："+task.getPath());
    }

    @Test
    public void applyJarWithLogPluginToProject(){
        Project project = ProjectBuilder.builder().build();
        project.getPluginManager().apply("com.github.skymxc.jar-log");
        Task task = project.getTasks().getByName("jarWithLog");
        assert task != null;
        System.out.println("jar-log 插件应用成功");
    }
}
