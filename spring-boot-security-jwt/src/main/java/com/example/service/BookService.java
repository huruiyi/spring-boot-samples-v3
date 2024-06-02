package com.example.service;

import com.example.model.book.Book;
import com.example.model.book.BookRequest;
import com.example.repository.BookRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BookService {

  private final BookRepository repository;

  public void save(BookRequest request) {
    var book = Book.builder().id(request.getId()).author(request.getAuthor()).isbn(request.getIsbn()).build();
    repository.save(book);
  }

  public List<Book> findAll() {
    return repository.findAll();
  }
}
