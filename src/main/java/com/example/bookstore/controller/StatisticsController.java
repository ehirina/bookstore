package com.example.bookstore.controller;

import com.example.bookstore.service.BookService;
import org.openapitools.api.StatisticsApi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.math.BigDecimal;

@RestController
public class StatisticsController implements StatisticsApi {
    private BookService bookService;

    @Autowired
    public StatisticsController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping(value="/statistics")
    public ResponseEntity<BigDecimal> getStatistics(@RequestParam(required = false, defaultValue = "") String authorName, @RequestParam(required = false, defaultValue = "") String bookName) {
        int counter = bookService.getStatsByAuthorOrBookName(authorName, bookName);
        return new ResponseEntity<BigDecimal>(BigDecimal.valueOf(counter), HttpStatus.OK);

    }

    @GetMapping(value="/statistics/file")
    public ResponseEntity<Void> getFileStatistics(@RequestParam(required = false, defaultValue = "") String authorName){
        try{
       bookService.createFileByAuthor(authorName);
        } catch (IOException e) {
            return new ResponseEntity<>(HttpStatus.I_AM_A_TEAPOT);
        }
        return new ResponseEntity<>(HttpStatus.OK);

    }
}
