package com.paymentsystem.demo.controllers;


import com.paymentsystem.demo.enttities.Confirmation;
import com.paymentsystem.demo.services.ConfirmationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/confirm")
public class ConfirmationController {
    @Autowired
    private ConfirmationService confirmationService;

    @PostMapping
    public Confirmation create(@RequestBody  Confirmation confirmation){
        return confirmationService.createConfirm(confirmation);
    }

}
