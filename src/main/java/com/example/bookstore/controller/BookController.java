package com.example.bookstore.controller;

import com.example.bookstore.model.BookModel;
import com.example.bookstore.service.BookService;
import org.modelmapper.ModelMapper;
import org.openapitools.api.BooksApi;
import org.openapitools.model.Book;
import org.openapitools.model.CreateBookRequest;
import org.openapitools.model.SellBookRequest;
import org.openapitools.model.StockUpdateRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
public class BookController implements BooksApi {

    private BookService bookService;
    private ModelMapper modelMapper;

    @Autowired
    public BookController(BookService bookService, ModelMapper modelMapper) {
        this.bookService = bookService;
        this.modelMapper = modelMapper;
    }

    @PostMapping(value="/books")
    public ResponseEntity<Book> createBook(@RequestBody CreateBookRequest book) {
        BookModel newBook = modelMapper.map(book, BookModel.class);

        bookService.addNewBook(newBook);

        Book bookDTO = modelMapper.map(newBook, Book.class);

        return new ResponseEntity<Book>(bookDTO, HttpStatus.CREATED);
    }

    @GetMapping(value="/books")
    public ResponseEntity<List<Book>> getBooks() {
        List<BookModel> books = bookService.getBooks();
        List<Book> booksDTO = books.stream().map(b -> modelMapper.map(b, Book.class)).toList();

        return new ResponseEntity<>(booksDTO, HttpStatus.OK);
    }

    @GetMapping(value="/books/{bookUuid}")
    public ResponseEntity<Book> getBookById(@PathVariable UUID bookUuid) {
        Optional<BookModel> book = bookService.getBookById(bookUuid);
        Book bookDTO = modelMapper.map(book, Book.class);

        if(book.isPresent()){
            return new ResponseEntity<Book>(bookDTO, HttpStatus.OK);
        }

        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PutMapping(value="/books/{bookUuid}")
    public ResponseEntity<Book> updateBook(@PathVariable UUID bookUuid, @RequestBody StockUpdateRequest stockUpdateRequest) {
        try {
            BookModel book = bookService.updateBook(bookUuid, stockUpdateRequest.getStock());
            Book bookDTO = modelMapper.map(book, Book.class);
            return new ResponseEntity<Book>(bookDTO, HttpStatus.OK);
        } catch (IllegalStateException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping(value="/books/{bookUuid}")
    public ResponseEntity<Void> deleteBook(@PathVariable UUID bookUuid) {
        try {
            bookService.deleteBook(bookUuid);
        } catch (IllegalStateException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        }

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PostMapping(value="/books/{bookUuid}/sell")
    public ResponseEntity<Book> sellBook(@PathVariable UUID bookUuid,  @RequestBody SellBookRequest sellBookRequest) {
        try {
            BookModel book = bookService.sellBook(bookUuid, sellBookRequest.getQuantity());
            Book bookDTO = modelMapper.map(book, Book.class);
            return new ResponseEntity<Book>(bookDTO, HttpStatus.OK);
        } catch (IllegalStateException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

}
