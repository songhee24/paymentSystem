package com.paymentsystem.demo.services.impls;


import com.paymentsystem.demo.enttities.Account;
import com.paymentsystem.demo.repos.AccountRepo;
import com.paymentsystem.demo.services.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AccountServiceImpl implements AccountService {
    @Autowired
    private AccountRepo accountRepo;

    @Override
    public Account getById(Long id) {
        Optional<Account> optionalPayment = accountRepo.findById(id);
        return optionalPayment.get();
    }

    @Override
    public List<Account> getAll() {
        return accountRepo.findAll();
    }

    @Override
    public Account save(Account item) {
        return accountRepo.save(item);
    }
}
