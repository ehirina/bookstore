package com.example.bookstore.service;

import com.example.bookstore.model.BookModel;
import com.example.bookstore.repository.BookRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.apache.commons.csv.CSVPrinter;
import org.apache.commons.csv.CSVFormat;

import java.io.FileWriter;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class BookService {

    private BookRepository bookRepository;

    @Autowired
    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }


    public BookModel addNewBook(BookModel book) {

         return bookRepository.save(book);

    }

    public void deleteBook(UUID bookUuid) {
        boolean exists = bookRepository.existsById(bookUuid);
        if (!exists) {
            throw new IllegalStateException("Book not found");
        }
        bookRepository.deleteById(bookUuid);

    }

    public List<BookModel> getBooks() {
        return bookRepository.findAll();
    }

    public Optional<BookModel> getBookById(UUID bookUuid) {
        return bookRepository.findById(bookUuid);
    }
@Transactional
    public BookModel updateBook(UUID bookUuid, BigDecimal stockUpdate) {
    BookModel book = bookRepository.findById(bookUuid).orElseThrow(() -> {
            throw new IllegalStateException("Book not found");
        });

        book.setQuantity(stockUpdate);
    return bookRepository.save(book);

    }

    @Transactional
    public BookModel sellBook(UUID bookUuid, BigDecimal quantity) {
        BookModel book = bookRepository.findById(bookUuid).orElseThrow(() -> {
            throw new IllegalStateException("Book not found");
        });

        book.setQuantity(book.getQuantity().subtract(quantity));
        return bookRepository.save(book);
    }

    public int getStatsByAuthorOrBookName(String authorName, String bookName) {
       List<BookModel> books = bookRepository.findBookModelByAuthorContainingIgnoreCaseAndNameContainingIgnoreCase(authorName, bookName);
       System.out.println(books);

       return  books.size();
    }


    public void createFileByAuthor(String authorName) throws IOException {
        List<BookModel> books = bookRepository.findBookModelByAuthorContainingIgnoreCase(authorName);

        CSVPrinter printer = new CSVPrinter(new FileWriter("books.csv"), CSVFormat.DEFAULT);
        printer.printRecord("Author", "Name", "Code", "Publisher", "Quantity left");

        books.forEach((book) -> {
            try {
                printer.printRecord(book.getAuthor(), book.getName(), book.getCode(), book.getPublisher(), book.getQuantity());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
        printer.flush();
        printer.close();
    }

}
