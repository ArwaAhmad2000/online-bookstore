package com.example.onlinebookstore.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.example.onlinebookstore.dto.BookCartDto;
import com.example.onlinebookstore.dto.TransactionDto;
import com.example.onlinebookstore.entity.Book;
import com.example.onlinebookstore.entity.TransactionBook;
import com.example.onlinebookstore.entity.TransactionHistory;
import com.example.onlinebookstore.entity.UserEntity;
import com.example.onlinebookstore.repository.TransactionRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TransactionService {

    private final TransactionRepository transactionRepository;
    private final UserService userService;
    private final BookService bookService;

    @Transactional
    public TransactionHistory createTransaction(TransactionDto transactionDto) {
        TransactionHistory transaction = new TransactionHistory();
        List<TransactionBook> transactionBooks = new ArrayList<>();
        // get userObject
        UserEntity user = userService.getUserById(transactionDto.getUserId());
        // get books which user wants to buy [{bookId:1,quantity:2},......]
        List<BookCartDto> selectedBooks = transactionDto.getBooks();
        // get ids of the selected books
        List<Integer> bookIds = selectedBooks.stream().map(bookObj -> bookObj.getBookId()).toList();
        // get the selected books from DB
        // [{bookId:1,name:english,price:10,quantity:2},......]
        List<Book> dbBooks = bookService.getAllBooksById(bookIds);
        // update quantity in DB & calculcate the totalPrice
        Double totalPrice = 0.0;
        for (Book bookDb : dbBooks) {
            for (BookCartDto boughtbook : selectedBooks) {
                if (bookDb.getId() == boughtbook.getBookId()) {
                    // update quantity
                    if (bookDb.getAvailable()) {
                        bookDb.setQuantity(bookDb.getQuantity() - boughtbook.getQuantity());
                    } else {
                        // throw 
                    }
                    // calculate total price
                    totalPrice += bookDb.getPrice() * boughtbook.getQuantity();
                    TransactionBook tb = new TransactionBook();
                    tb.setName(bookDb.getName());
                    tb.setPrice(bookDb.getPrice());
                    tb.setQuantity(boughtbook.getQuantity());
                    tb.setBook(bookDb);
                    tb.setTransaction(transaction);
                    transactionBooks.add(tb);
                }
            }
        }
        // set transactionHistoryObject
        transaction.setUser(user);
        transaction.setBooks(transactionBooks);
        transaction.setTotalPrice(totalPrice);
        transaction.setDate(new Date());
        // save transactionHistoryObject in DB
        return transactionRepository.save(transaction);
    }

    public List<TransactionHistory> getAllTransactions() {
        return transactionRepository.findAll();
    }

    public TransactionHistory getTransactionById(Integer id) {
        return transactionRepository.findById(id).orElseThrow();
    }

    public List<TransactionHistory> getAllTransactionsByUser(Integer id) {
        return transactionRepository.findByUserId(id);
    }

    public void deleteTransactionById(Integer id) {
        transactionRepository.deleteById(id);
    }

}
