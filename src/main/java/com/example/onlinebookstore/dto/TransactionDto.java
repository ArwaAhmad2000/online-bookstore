package com.example.onlinebookstore.dto;

import java.util.List;
import lombok.Data;

@Data
public class TransactionDto {

    private Integer userId;
    private List<BookCartDto> books; // [{bookId:1,quantity:2},{bookId:1,quantity:2}]

}
