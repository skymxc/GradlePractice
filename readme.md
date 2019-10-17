# 自定义插件

> 使用版本 5.6.2

插件被用来封装构建逻辑和一些通用配置。将可重复使用的构建逻辑和默认约定封装到插件里，以便于其他项目使用。

Gradle 有两种插件，脚本插件和二进制插件。

关于插件的介绍，可以参考我的另一篇文章 [ Gradle 插件] ()
这里讲的自定义插件是二进制插件，二进制插件可以打包发布，利于传播。


## 可以在三个地方定义插件

- 在脚本里
- 在 buildSrc 下
- 在单独的项目里

三个地方的插件的用途目的不同

### 在脚本里的插件

其他项目无法使用，只能在本脚本里使用

### 在 buildSrc 下

在项目的 buildSrc 目录下的插件，这个项目里的所有（子）项目都可以使用。

### 在单独的项目里

可以将项目打包发布，提供给其他任何项目使用。

## 创建插件

对于使用什么语言，建议使用静态语言，开发工具建议使用 IntelliJ IDEA 。

一个插件就是个实现了 Plugin<T> 的类。

CustomPLugin.java
```
// 定义一个插件
class CustomPLugin implements Plugin<Project>{

    @Override
    void apply(Project target) {
        // do something
    }
}
```

前面说到可以在三个地方创建插件，现在来一一实现下。

### 在脚本里创建一个插件

build.gradle
```
// 定义一个插件
class CustomPLugin implements Plugin<Project>{

    @Override
    void apply(Project target) {
      //添加一个任务
     target.task('hello', group: 'util') {
         doLast {
             logger.quiet("Hello Plugin.")
         }
     }
    }
}

//直接在脚本里应用
apply plugin:CustomPLugin
```

在 gradle 窗口就可以看到应用插件后的添加的任务

![添加的任务]()

双击任务或者命令行都可以执行 hello 任务

```
gradle hello
```


### 在项目的 buildSrc 目录下创建项目

这次使用的是 Groovy 。

在这个目录下创建项目会被 Gradle 自动识别的。

结构如下
![buildSrc 目录结构]()

1. 在项目根目录下创建目录 buildSrc
2. 在 buildSrc 下按照 java 工程或者 groovy 工程（这取决于你用什么语言）新建目录

$projectDir/buildSrc/src/main/groovy

3. 在 groovy 创建你的包 （可能现在还不能被识别为项目，那就创建目录），例如 com.github.skymxc
4. 在包里创建插件，也就是创建一个实现了 Plugin 的类。

这里做简单的示范： 在插件里为 jar 任务添加一个操作：生成记录文件

JarLogPlugin.groovy
```
/**
 * 输出 生成记录到指定文件
 */
class JarLogPlugin implements Plugin<Project> {
    @Override
    void apply(Project target) {
        //增加一个扩展配置用来接收参数
        target.extensions.create("log", LogExtension)

        //添加一个任务
        target.task(type: Jar,group:'util','jarWithLog',{
            doLast {
                //使用配置
                def file = target.log.outputPath;
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
```

#### 配置 DSL

上面使用了一个扩展来接收参数, 普通的对象就可以，例如

LogExtension
```
class LogExtension {
    String outputPath;
}
```

扩展在这里就是用来为插件配置 DSL 用的。

```
//为 项目添加了一个 LogExtension 类型的属性 名字是 log
project.extensions.create("log", LogExtension)
```


插件可以使用 DSL 接收参数，在插件或者任务里直接通过 Project 实例访问即可

```
def file = project.log.outputPath;
```

插件创建完成后，在项目的里就可以使用了。

现在可以使用类名应用插件了，

```groovy
import com.github.skymxc.JarLogPlugin

apply plugin: JarLogPlugin
```

插件应用成功后就可以使用 DSL 为插件配置参数

配置记录文件地址
```
log {
    outputPath rootProject.projectDir.getPath()+"\\record\\jar.txt"
}
```

#### 为插件创建 ID

1. 在 main 目录下创建 resources 文件夹
2. 在 resources 目录下创建 META-INF 文件夹
3. 在 META-INF 目录下创建 gradle-plugins 文件夹
4. 在 gradle-plugins 目录下创建 properties 文件，名字就是你的插件 ID。
5. 在 id.properties 文件里通过  implementation-class 指向你的实现类。

例如

*src / main / resources / META-INF / gradle-plugins / com.github.skymxc.sample.properties*
```
implementation-class= com.github.skymxc.JarLogPlugin
```

然后就可以使用插件 ID 了

```groovy
plugins {
    id 'com.github.skymxc.sample'
}
```

关于 Groovy 的语法，可以参考 []()


## 在单独的项目里创建插件

这次使用 Java 语言。

插件项目其实就是一个 Java 项目





资料

- 自定义插件 https://docs.gradle.org/current/userguide/custom_plugins.html
- 开发辅助插件 https://docs.gradle.org/current/userguide/java_gradle_plugin.html
- 使用插件 https://docs.gradle.org/current/userguide/plugins.html
- 发布 https://docs.gradle.org/current/userguide/publishing_overview.html
- maven 发布插件 https://docs.gradle.org/current/userguide/publishing_maven.html
- Gradle 教程 https://gradle.org/guides/?q=Plugin%20Development
- Gradle DSL https://blog.csdn.net/zlcjssds/article/details/79229209
