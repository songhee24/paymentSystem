package com.paymentsystem.demo.services.impls;


import com.paymentsystem.demo.enttities.Account;
import com.paymentsystem.demo.enttities.Payment;
import com.paymentsystem.demo.enums.Status;
import com.paymentsystem.demo.repos.PaymentRepo;
import com.paymentsystem.demo.services.AccountService;
import com.paymentsystem.demo.services.ConfirmationService;
import com.paymentsystem.demo.services.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PaymentServiceImpl implements PaymentService {
    @Autowired
    private PaymentRepo paymentRepo;
    @Autowired
    private AccountService accountService;
    @Autowired
    private ConfirmationService confirmationService;

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
        payment.setStatus(payment.getAmount().intValue()%2 == 0 ? Status.SUCCESS : Status.FAILED);
        if (payment.getStatus().equals(Status.SUCCESS)) {
            payment.setStatus(Status.AWAIT);
            Account confirm = accountService.getById(payment.getAccountFrom().getId());
            //waiting confirm
            int code  = confirmationService.getConfirmationCode(confirm.getId());
            if (code == payment.getConfirmationCode()){
                //Get money from
                payment.setStatus(Status.SUCCESS);
                Account from = accountService.getById(payment.getAccountFrom().getId());
                from.setBalance(from.getBalance().subtract(payment.getAmount()));
                Account to = accountService.getById(payment.getAccountTo().getId());
                to.setBalance(to.getBalance().add(payment.getAmount()));
                accountService.save(from);
                accountService.save(to);
                payment.setAccountFrom(from);
                payment.setAccountTo(to);
                //Put money to
            } else {
                payment.setStatus(Status.FAILED);
                return save(payment);
            }

        }
        return save(payment);
    }


/*    @Override
    public Payment createPayment(Payment payment) {
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
        return save(payment);
    }*/


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
