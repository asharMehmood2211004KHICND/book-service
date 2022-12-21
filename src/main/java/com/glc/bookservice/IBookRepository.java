package com.glc.bookservice;

import java.util.Collection;

public interface IBookRepository<T> {
    public void save(T t);

    public Collection<T> getAllBooks();

    public Book getBookById(int id);

    public String deleteBookById(int deleteId);

    public String updateBookById(Book book,int updateId);

}
