package com.paymentsystem.demo.services.impls;


import com.paymentsystem.demo.ConfirmationModel;
import com.paymentsystem.demo.enttities.Account;
import com.paymentsystem.demo.enttities.Payment;
import com.paymentsystem.demo.enums.Status;
import com.paymentsystem.demo.repos.PaymentRepo;
import com.paymentsystem.demo.services.AccountService;
import com.paymentsystem.demo.services.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Random;

@Service
public class PaymentServiceImpl implements PaymentService {
    @Autowired
    private PaymentRepo paymentRepo;
    @Autowired
    private AccountService accountService;

    private Random random = new Random();


    @Override
    public Payment getById(Long id) {
        Optional<Payment> optionalPayment = paymentRepo.findById(id);
        return optionalPayment.get();
    }

    @Override
    public List<Payment> getAll() {
        return paymentRepo.findAll();
    }

    @Override
    public Payment save(Payment item) {
        return paymentRepo.save(item);
    }



    @Override
    public Payment createPayment(Payment payment) {
        payment.setStatus(Status.AWAITING_CONFIRMATION);

        int confirmationCode = random.nextInt(9999 - 1000 + 1) + 1000;
        payment.setConfirmationCode(""+confirmationCode);

        return save(payment);
    }
    int count = 0;
    @Override
    public Payment confirmPayment(ConfirmationModel confirmationModel) {
        Payment payment = getById(confirmationModel.getPaymentId());

        if (!payment.getConfirmationCode().equals(confirmationModel.getConfirmationCode())){
            payment.setAttempt(++count);
            System.out.println("1 "+payment.getAttempt());
            if (payment.getAttempt() == 3){
                payment.setStatus(Status.BLOCKED);
                save(payment);
                System.out.println("2 "+payment.getAttempt());
                return null;
            }
            System.out.println("3 "+payment.getAttempt());
            return null;
        }
        else {
            payment.setAttempt(0);
            System.out.println("4 "+payment.getAttempt());
        }
        processPayment(payment);
        return payment;
    }

    private void processPayment(Payment payment){
        payment.setStatus(payment.getAmount().intValue()%2 == 0 ? Status.SUCCESS : Status.FAILED);
        if (payment.getStatus().equals(Status.SUCCESS)) {
            //Get money from
            Account from = accountService.getById(payment.getAccountFrom().getId());
            from.setBalance(from.getBalance().subtract(payment.getAmount()));
            Account to = accountService.getById(payment.getAccountTo().getId());
            to.setBalance(to.getBalance().add(payment.getAmount()));
            accountService.save(from);
            accountService.save(to);
            payment.setAccountFrom(from);
            payment.setAccountTo(to);
            //Put money to
        }
        save(payment);
    }

    @Override
    public List<Payment> getDateByMonth(String date, Long accountFromId ) {
        return paymentRepo.getDateByMonth(date, accountFromId );
    }

    @Override
    public List<Payment> getByStatus(Status status) {
        return paymentRepo.findAllByStatusQuery(status);
//        return paymentRepo.findAllByStatus(status);
    }

    @Override
    public List<Payment> getByClientId(Long clientId) {
        return paymentRepo.findAllByAccountFrom_Client_Id(clientId);
    }

    @Override
    public List<Payment> getByClientIdNative(Long clientId) {
        return paymentRepo.findAllByClientId(clientId);
    }

}
