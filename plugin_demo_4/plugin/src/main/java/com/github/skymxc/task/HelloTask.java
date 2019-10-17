package com.github.skymxc.task;

import com.github.skymxc.extension.Book;
import com.github.skymxc.extension.HelloExtension;
import com.github.skymxc.extension.UserData;
import org.gradle.api.DefaultTask;
import org.gradle.api.NamedDomainObjectContainer;
import org.gradle.api.reflect.TypeOf;
import org.gradle.api.tasks.TaskAction;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.function.Consumer;

import static com.github.skymxc.Utils.getNow;
import static com.github.skymxc.Utils.writeFile;

public class HelloTask extends DefaultTask {


    @TaskAction
    public void greet()  {

        HelloExtension hello = getProject().getExtensions().getByType(HelloExtension.class);
        UserData user = hello.getUser();

        String message = String.format("user(%s,%d) -> %s ;",user.getName(),user.getAge(),hello.getMessage());
        getLogger().quiet("message: {}",message);


        NamedDomainObjectContainer<Book> books = hello.getBooks();
        Iterator<Book> iterator = books.iterator();
        while (iterator.hasNext()){
            Book book = iterator.next();
            String format = String.format("name:%s , price:%f", book.getName(), book.getPrice());
            getLogger().quiet("books: {}",format);
        }
        String path = getProject().getBuildDir().getAbsolutePath()+"\\greet\\hello.txt";
        System.out.println("path->"+path);
        String content = String.format("%s => message: %s \n",getNow(),message);
        try {
            writeFile(path,content);
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println(e.getMessage());
        }
    }

}
