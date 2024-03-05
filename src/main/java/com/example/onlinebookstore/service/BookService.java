package com.example.onlinebookstore.service;

import java.util.List;
import org.springframework.stereotype.Service;
import com.example.onlinebookstore.dto.BookDto;
import com.example.onlinebookstore.entity.Book;
import com.example.onlinebookstore.repository.BookRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BookService {

    private final BookRepository bookRepository;

    public Book createBook(BookDto bookDto) {
        Book newBook = new Book();
        newBook.setName(bookDto.getName());
        newBook.setPrice(bookDto.getPrice());
        newBook.setQuantity(bookDto.getQuantity());
        return bookRepository.save(newBook);
    }

    public List<Book> saveBooks(List<Book> books) {
        return bookRepository.saveAll(books);
    }

    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }

    public Book getBookById(Integer id) {
        return bookRepository.findById(id).orElseThrow();
    }

    public List<Book> getAllBooksById(List<Integer> ids) {
        return bookRepository.findAllByIdIn(ids);
    }

    public Book updateBook(Integer id, BookDto bookDto) {
        Book oldBook = bookRepository.findById(id).orElseThrow();
        if (bookDto.getName() != null) {
            oldBook.setName(bookDto.getName());
        }
        if (bookDto.getPrice() != null) {
            oldBook.setPrice(bookDto.getPrice());
        }
        if (bookDto.getQuantity() != null) {
            oldBook.setQuantity(bookDto.getQuantity());
        }
        return bookRepository.save(oldBook);
    }

    public void deleteBookById(Integer id) {
        bookRepository.deleteById(id);
    }
}
