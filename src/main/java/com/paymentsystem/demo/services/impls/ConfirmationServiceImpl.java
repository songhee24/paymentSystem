package com.paymentsystem.demo.services.impls;


import com.paymentsystem.demo.enttities.Account;
import com.paymentsystem.demo.enttities.Confirmation;
import com.paymentsystem.demo.repos.ConfirmationRepo;
import com.paymentsystem.demo.services.AccountService;
import com.paymentsystem.demo.services.ConfirmationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Random;

@Service
public class ConfirmationServiceImpl implements ConfirmationService {
    @Autowired
    private ConfirmationRepo confirmationRepo;

    @Autowired
    private AccountService accountService;

    @Override
    public Confirmation getById(Long id) {
        Optional<Confirmation> confirmation = confirmationRepo.findById(id);
        return confirmation.orElse(new Confirmation());
    }

    @Override
    public List<Confirmation> getAll() {
        return confirmationRepo.findAll();
    }

    @Override
    public Confirmation save(Confirmation item) {
        return confirmationRepo.save(item);
    }

    @Override
    public Confirmation createConfirm(Confirmation confirmation) {
        Account account = accountService.getById(confirmation.getAccountFrom().getId());
        confirmation.setConfirmationCode(randomConfCode());
        confirmation.setAccountFrom(account);
        return save(confirmation);
    }

    @Override
    public Integer getConfirmationCode(Long id) {
        return confirmationRepo.getConfirmationCode(id);
    }

    public int randomConfCode(){
        Random r = new Random();
        int min = 1000;
        int max = 9999;
        return r.nextInt((max-min) + 1) + min ;
    }
}
