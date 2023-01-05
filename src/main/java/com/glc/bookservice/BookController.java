package com.glc.bookservice;

import java.util.Collection;

import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/books")  // Any address like https://localhost:8080/books
public class BookController {
    private final BookRepository repository;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Autowired
    private DirectExchange exchange;




    public BookController(BookRepository repository){
        this.repository = repository;
    }

    @PostMapping("")  // (POST) https://localhost:8080/books
    public void createBook(@RequestBody Book book) {
        
        rabbitTemplate.convertAndSend(  exchange.getName(), "routing_A",book);  // sending data to the queue





        this.repository.save(book);




    }
@RabbitListener(queues = "queue.A")
    public void listner(Book book){
        repository.save(book);
    System.out.println("message recevied : "+book.getAuthor());
}




    @GetMapping("/all") // (GET) https://localhost:8080/books/all
    public Collection<Book> getAllBooks(){



        return this.repository.getAllBooks();
    }

    //getting by id :  
                        //     @GetMapping("/{id}")
                        // public Book getBook(@PathVariable int id){
                        // }
    
    @GetMapping("/{id}")
    public Book getBookById(@PathVariable int id){
    return this.repository.getBookById(id);
    }


    @DeleteMapping("/{deleteId}")
    public String deleteBookById(@PathVariable int deleteId){
        
       return this.repository.deleteBookById(deleteId);

    }

    
    @PutMapping("/{updateId}")
    public String updateBookById(@PathVariable int updateId,@RequestBody Book book){
        


     return this.repository.updateBookById(book, updateId);

        
    }    

    
                    
}
