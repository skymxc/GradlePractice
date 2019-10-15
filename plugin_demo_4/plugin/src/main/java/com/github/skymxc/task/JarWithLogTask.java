package com.github.skymxc.task;

import com.github.skymxc.extension.JarLogExtension;
import org.gradle.api.tasks.Input;
import org.gradle.api.tasks.OutputFile;
import org.gradle.api.tasks.TaskAction;
import org.gradle.api.tasks.bundling.Jar;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class JarWithLogTask extends Jar {


    @TaskAction
    private void writeLog() throws IOException {
        JarLogExtension extension = getProject().getExtensions().getByType(JarLogExtension.class);

        File file = new File(extension.getPath(),extension.getName());
        String s = file.getAbsolutePath();
        String content = getNow()+" --- "+getArchiveFileName().get();
        System.out.println("path --> "+s);
        writeFile(s,content);
    }


    String getNow(){
        SimpleDateFormat dateFormat = new SimpleDateFormat("YYYY-MM-dd HH:mm:ss.SSS");
        return dateFormat.format(Calendar.getInstance().getTime());
    }

    void writeFile(String path,String content) throws IOException {
        File file = new File(path);
        if (!file.exists()){
            if (!file.getParentFile().exists()){
                file.getParentFile().mkdirs();
            }
            file.createNewFile();
        }
        FileWriter writer = new FileWriter(file.getAbsolutePath(),true);
        BufferedWriter bufferedWriter = new BufferedWriter(writer);
        bufferedWriter.write(content);
        bufferedWriter.close();
    }
}
