package com.paymentsystem.demo.controllers;


import com.paymentsystem.demo.enttities.Payment;
import com.paymentsystem.demo.enums.Status;
import com.paymentsystem.demo.services.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

@RestController
@RequestMapping("/payments")
public class PaymentController {
    @Autowired
    private PaymentService paymentService;


    @GetMapping
    public List<Payment> getAll() {
        return paymentService.getAll();
    }

    @PostMapping
    public Payment save(@RequestBody Payment payment) {
        return paymentService.createPayment(payment);
    }

    @GetMapping("/{id}")
    public Payment getById(@PathVariable("id") Long id) {
        return paymentService.getById(id);
    }

    @GetMapping("/getByStatus/{status}")
    public List<Payment> getByStatus(@PathVariable("status") Status status) {
        return paymentService.getByStatus(status);
    }

    @GetMapping("/getByClientId/{clientId}")
    public List<Payment> getByClientId(@PathVariable("clientId") Long clientId) {
        return paymentService.getByClientId(clientId);
    }

    @GetMapping("/getByClientIdNative/{clientId}")
    public List<Payment> getByClientIdNative(@PathVariable("clientId") Long clientId) {
        return paymentService.getByClientIdNative(clientId);
    }


    @GetMapping("/GetDatesByMonth")
    @ResponseBody
    public List<Payment> getDate(@RequestParam String date,@RequestParam Long accountFromId ) throws ParseException {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("YYYY-MM");
        simpleDateFormat.parse(date);
        System.out.println(date);
        return paymentService.getDateByMonth(date,accountFromId );
    }

}
