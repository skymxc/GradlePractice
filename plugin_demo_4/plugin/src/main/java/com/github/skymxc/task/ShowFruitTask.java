package com.github.skymxc.task;

import com.github.skymxc.extension.Fruit;
import org.gradle.api.DefaultTask;
import org.gradle.api.NamedDomainObjectContainer;
import org.gradle.api.reflect.TypeOf;
import org.gradle.api.tasks.TaskAction;

import java.util.function.Consumer;

public class ShowFruitTask extends DefaultTask {

    @TaskAction
    public void show(){

        NamedDomainObjectContainer<Fruit> fruits = (NamedDomainObjectContainer<Fruit>) getProject().getExtensions().getByName("fruits");

        fruits.forEach(fruit -> {
            String format = String.format("name: %s , color: %s", fruit.getName(), fruit.getColor());
            getLogger().quiet("fruit : {}",format);
        });
    }
}
