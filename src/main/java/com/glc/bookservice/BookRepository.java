package com.glc.bookservice;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Repository;

@Repository
public class BookRepository implements IBookRepository<Book>{
    private Map<Integer, Book> repository;

    public BookRepository() {
        this.repository = new HashMap<>();
    }

    @Override
    public void save(Book book){
        repository.put(book.getId(), book);
    }

    @Override
    public Collection<Book> getAllBooks(){
        return repository.values();
    }

    
    @Override
    public Book getBookById(int id) {
        // TODO Auto-generated method stub
        return repository.get(id);
    }

    @Override
    public String deleteBookById(int deleteId) {
        
        Book bookToDelete  = getBookById(deleteId);
        if(bookToDelete==null){
            return "This book do not exist";

        }else{
            this.repository.remove(deleteId);
            return "The book is deleted";
        }
        

    }

    @Override
    public String updateBookById(Book book ,int updateId) {
        // TODO Auto-generated method stub


        Book bookToUpdate  = getBookById(updateId);
        if(bookToUpdate==null){
            return "not existing book can not be updated";
        }else{
            this.repository.put(updateId, book);
            return "this book is updated";
        }




        //repository.put(book.getId(), book);
        



    }
}
