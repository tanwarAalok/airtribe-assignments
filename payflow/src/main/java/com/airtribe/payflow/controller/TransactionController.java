package com.airtribe.payflow.controller;

import com.airtribe.payflow.entity.Transaction;
import com.airtribe.payflow.entity.User;
import com.airtribe.payflow.service.TransactionService;
import com.airtribe.payflow.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("/transactions")
public class TransactionController {

    @Autowired
    private TransactionService transactionService;

    @Autowired
    private UserService userService;

    @PostMapping
    public Transaction sendTransaction(@RequestBody Transaction transaction) {
        Optional<User> sender = userService.getUserByUpiId(transaction.getSenderUpiId());
        Optional<User> receiver = userService.getUserByUpiId(transaction.getReceiverUpiId());

        if(sender.isEmpty()) {
            throw new IllegalArgumentException("sender id not found");
        }

        if(receiver.isEmpty()) {
            throw new IllegalArgumentException("receiver id not found");
        }


        return transactionService.sendMoney(transaction);
    }
}
