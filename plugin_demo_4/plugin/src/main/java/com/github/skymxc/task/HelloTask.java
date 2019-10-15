package com.github.skymxc.task;

import org.gradle.api.DefaultTask;
import org.gradle.api.tasks.Input;
import org.gradle.api.tasks.InputFile;
import org.gradle.api.tasks.OutputFile;
import org.gradle.api.tasks.TaskAction;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import static com.github.skymxc.Utils.getNow;
import static com.github.skymxc.Utils.writeFile;

public class HelloTask extends DefaultTask {

    private String message;

    @TaskAction
    public void greet()  {
        getLogger().quiet("message: {}",getMessage());

        String path = getProject().getBuildDir().getAbsolutePath()+"\\greet\\hello.txt";
        System.out.println("path->"+path);

        String content = String.format("%s => message: %s",getNow(),getMessage());
        try {
            writeFile(path,content);
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println(e.getMessage());
        }
    }

    @Input
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
