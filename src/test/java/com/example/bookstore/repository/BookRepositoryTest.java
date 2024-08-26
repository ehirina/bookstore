package com.example.bookstore.repository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.jupiter.api.Assertions.*;

class BookRepositoryTest {

    @Autowired
    private BookRepository bookRepository;

    @BeforeEach
    void setUp() {
    }

    @Test
    void findBookByAuthorContainingIgnoreCase() {
    }

    @Test
    void findBookByAuthorContainingIgnoreCaseAndNameContainingIgnoreCase() {
    }
}