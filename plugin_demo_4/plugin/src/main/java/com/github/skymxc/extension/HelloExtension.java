package com.github.skymxc.extension;

import org.gradle.api.Action;
import org.gradle.api.NamedDomainObjectContainer;

/**
 * 为 HelloTask 创建的扩展，用于接收配置参数
 */
public class HelloExtension {

    private String message;
    private final UserData user = new UserData();

    private NamedDomainObjectContainer<Book> books;

    public HelloExtension(NamedDomainObjectContainer<Book> books) {
        this.books = books;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public UserData getUser() {
        return user;
    }

    /**
     * 注意此方法没有 set
     * @param action
     */
    public void user(Action<? super UserData> action) {
        action.execute(user);
    }

    public void books(Action<NamedDomainObjectContainer<Book>> action){
        action.execute(books);
    }

    public NamedDomainObjectContainer<Book> getBooks() {
        return books;
    }

    public void setBooks(NamedDomainObjectContainer<Book> books) {
        this.books = books;
    }
}
