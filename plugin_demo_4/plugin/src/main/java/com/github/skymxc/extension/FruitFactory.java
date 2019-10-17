package com.github.skymxc.extension;

import org.gradle.api.NamedDomainObjectFactory;
import org.gradle.internal.reflect.Instantiator;

public class FruitFactory implements NamedDomainObjectFactory<Fruit> {

    private Instantiator instantiator;

    public FruitFactory(Instantiator instantiator) {
        this.instantiator = instantiator;
    }

    @Override
    public Fruit create(String name) {
        return instantiator.newInstance(Fruit.class, name);
    }
}
