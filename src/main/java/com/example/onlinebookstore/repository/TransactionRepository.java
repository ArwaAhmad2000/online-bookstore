package com.example.onlinebookstore.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import com.example.onlinebookstore.entity.TransactionHistory;

public interface TransactionRepository extends JpaRepository<TransactionHistory, Integer> {

    public List<TransactionHistory> findByUserId(Integer userId);

}
