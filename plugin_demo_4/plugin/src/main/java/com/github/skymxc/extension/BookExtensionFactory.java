package com.github.skymxc.extension;

import org.gradle.api.NamedDomainObjectFactory;
import org.gradle.internal.reflect.Instantiator;

public class BookExtensionFactory implements NamedDomainObjectFactory<Book> {

    private Instantiator instantiator;

    public BookExtensionFactory(Instantiator instantiator) {
        this.instantiator = instantiator;
    }

    @Override
    public Book create(String name) {
        return instantiator.newInstance(Book.class,name);
    }
}
