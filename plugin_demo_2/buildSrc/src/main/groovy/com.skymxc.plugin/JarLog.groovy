package com.skymxc.plugin

import com.skymxc.plugin.configure.LogConfigure
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.tasks.bundling.Jar

import java.text.SimpleDateFormat

/**
 * 输出 生成记录到指定文件
 */
class JarLog implements Plugin<Project> {
    @Override
    void apply(Project target) {
        //增加一个扩展配置用来接收参数
        target.extensions.create("logConfigure", LogConfigure)

        //添加一个任务
        target.task(type: Jar,group:'util','jarWithLog',{
            doLast {
                //使用配置
                def file = target.logConfigure.outputPath;
                if (file==null){
                    file = new File(target.projectDir,"/log/jarlog.txt").getPath()
                }
                println "存储目录是 ${file}"
                def content = "${getArchiveFileName().get()}---${getNow()}\n"
                writeFile(file,content)
            }
        })

        //为 jar 任务添加一个 操作，
        target.tasks.jar.doLast {
            println "当前时间是 ${getNow()},打了一个 jar-> ${version}"
            //存到指定文件记录
            def file = new File(target.projectDir,"/log/jarlog.txt");
            def content = "${version}---${getNow()}\n"
            writeFile(file.getAbsolutePath(),content)
        }

    }

    def String getNow(){
        def dateFormat = new SimpleDateFormat("YYYY-MM-dd HH:mm:ss.SSS");
        return dateFormat.format(Calendar.getInstance().getTime());
    }

    def void writeFile(String path,String content){
        def file = new File(path);
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
