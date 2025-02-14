package com.glc.bookservice;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.autoconfigure.json.AutoConfigureJsonTesters;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.databind.ObjectMapper;

@AutoConfigureJsonTesters
@SpringBootTest
@AutoConfigureMockMvc
class BookServiceApplicationTests {

	private MockMvc mvc;

	@Mock
	private BookRepository bookRepository;

	@InjectMocks
	private BookController bookController;

	private JacksonTester<Book> jsonBook;
	//private String returnText;
	private JacksonTester<Collection<Book>> jsonBooks;

	@BeforeEach
	public void setUp(){
		JacksonTester.initFields(this, new ObjectMapper());
		mvc = MockMvcBuilders.standaloneSetup(bookController).build();
	}


	@Test
	void contextLoads() {
	}

	//AC1:  When I enter the title, author, year of publication, and length of the book into the UI and hit submit, my book will saved to the list.
	@Test
	public void canCreateANewBook() throws Exception {
		Book book = new Book(1, "The Hobbit", "J.R.R. Tolkein", 1937, 320);
		mvc.perform(post("/books")
			.contentType(MediaType.APPLICATION_JSON)
			.content(jsonBook.write(book).getJson()))
			.andExpect(status().isOk());
	}

	//AC2:  When I click “View All Books” the application will display a list of all the books in my list.
	@Test
	public void canGetAllBooks() throws Exception {
		Book book1 = new Book(1, "The Hobbit", "J.R.R. Tolkein", 1937, 320);
		Book book2 = new Book(2, "It", "Stephen King", 1986, 1138);
		Collection<Book> books = new ArrayList<Book>();
		books.add(book1);
		books.add(book2);
		when(bookRepository.getAllBooks()).thenReturn(books);
		mvc.perform(get("/books/all")
				.contentType(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk())
			.andExpect(content().json(jsonBooks.write(books).getJson()));
	}

	@Test
	public void canGetBookById()throws Exception{

		Book book1 = new Book(1, "The Hobbit", "J.R.R. Tolkein", 1937, 320);
		Book book2 = new Book(2, "It", "Stephen King", 1986, 1138);

		final Map<Integer, Book> myTestRepository;

																										
		//Collection<Book> books = new ArrayList<Book>();

		myTestRepository = new HashMap<>();

		myTestRepository.put(book1.getId(), book1);
		myTestRepository.put(book2.getId(), book2);

		when(bookRepository.getBookById(1)).thenReturn(myTestRepository.get(1));

		mvc.perform(get("/books/1")
				.contentType(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk())
			.andExpect(content().json(jsonBook.write(book1).getJson()));

	}

	@Test
	public void canDeleteBookById() throws Exception {
		Book book1 = new Book(1, "The Hobbit", "J.R.R. Tolkein", 1937, 320);
		Book book2 = new Book(2, "It", "Stephen King", 1986, 1138);

		final Map<Integer, Book> myTestRepository;

																										
		//Collection<Book> books = new ArrayList<Book>();

		myTestRepository = new HashMap<>();

		//adding books
		myTestRepository.put(book1.getId(), book1);
		myTestRepository.put(book2.getId(), book2);
		
		when(bookRepository.deleteBookById(1)).thenReturn("The book is deleted");

		mvc.perform(delete("/books/1")
				.contentType(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk())
			.andExpect(content().string("The book is deleted"));
		
	}
	@Test
	public void canUpdateBookById() throws Exception {
		Book book1 = new Book(1, "The Hobbit", "J.R.R. Tolkein", 1937, 320);
		//Book book2 = new Book(2, "It", "Stephen King", 1986, 1138);

		final Map<Integer, Book> myTestRepository;

																										
		//Collection<Book> books = new ArrayList<Book>();

		myTestRepository = new HashMap<>();

		//adding books
		myTestRepository.put(book1.getId(), book1);
		
		when(bookRepository.updateBookById(any(), anyInt())).thenReturn("this book is updated");

		// mvc.perform(
			
		// put("/books/1")
		// 		.contentType(MediaType.APPLICATION_JSON))
			
		// 	.andExpect(content().json(jsonBook.write(book1).getJson()))
		// 	.andExpect(status().isOk())
		// 	.andExpect(content().string("this book is updated"));

		mvc.perform(put("/books/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonBook.write(book1).getJson()))
            .andExpect(status().isOk())
            .andExpect(content().string("this book is updated"));
		
	}


	// @Test
	// public void canCreateANewBook() throws Exception {
	// 	Book book = new Book(1, "The Hobbit", "J.R.R. Tolkein", 1937, 320);
	// 	mvc.perform(post("/books")
	// 		.contentType(MediaType.APPLICATION_JSON)
	// 		.content(jsonBook.write(book).getJson()))
	// 		.andExpect(status().isOk());
	// }



//	.content(jsonBook.write(book).getJson()))




	// @Test
    // public void UpdateBook() throws Exception{
    //     Book book = new Book(1, "ABC Book","Ashfaq Ahmed",1988,120);
    //     when(bookRepo.updateParticularBook(1,book)).thenReturn("Book #1 has been Updated");
    //     mvc.perform(post("/books/update/1")
    //     .contentType(MediaType.APPLICATION_JSON)
    //     .content(jsonBook.write(book).getJson()))
    //     .andExpect(status().isOk());
    // }
	


}
