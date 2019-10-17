package com.github.skymxc;

import com.github.skymxc.extension.*;
import com.github.skymxc.task.HelloTask;
import com.github.skymxc.task.ShowFruitTask;
import groovy.lang.Closure;
import org.gradle.api.NamedDomainObjectContainer;
import org.gradle.api.Plugin;
import org.gradle.api.Project;
import org.gradle.internal.reflect.Instantiator;
import org.gradle.invocation.DefaultGradle;


public class GreetPlugin implements Plugin<Project> {
    @Override
    public void apply(Project target) {

        Instantiator instantiator = ((DefaultGradle)target.getGradle()).getServices().get(Instantiator.class);
        NamedDomainObjectContainer<Book > books = target.container(Book.class,new BookExtensionFactory(instantiator));

        target.getExtensions().create("hello", HelloExtension.class,books);
        target.getTasks().create("hello", HelloTask.class);

        NamedDomainObjectContainer<Fruit> fruits = target.container(Fruit.class,new FruitFactory(instantiator));

        target.getExtensions().add("fruits",fruits);

        target.getTasks().create("printlnFruits", ShowFruitTask.class);
    }


}
