# 在 buildSrc 目录下创建插件

在 *rootProjectDir*/buildSrc/ 下源代码可以被 Gradle 检测到。



在这里定义的插件在本项目的所有子项目里都可以使用。



resources / META-INF/.gradle-plugins 目录下创建 properties 文件；
com.github.skymxc.sample.properties 

这个名字就是插件的 id 。