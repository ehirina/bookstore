package com.example.bookstore.repository;

import com.example.bookstore.model.BookModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;
@Repository
public interface BookRepository extends JpaRepository<BookModel, UUID> {
//    @Query(value = "COUNT * FROM BookModel b WHERE b.author LIKE %:author% AND b.name LIKE %:book%")
    List<BookModel> findBookModelByAuthorContainingIgnoreCase(String author);
    List<BookModel> findBookModelByAuthorContainingIgnoreCaseAndNameContainingIgnoreCase(String author, String name);
}
