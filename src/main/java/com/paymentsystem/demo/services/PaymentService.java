package com.paymentsystem.demo.services;


import com.paymentsystem.demo.ConfirmationModel;
import com.paymentsystem.demo.enttities.Payment;
import com.paymentsystem.demo.enums.Status;

import java.util.List;

public interface PaymentService extends BaseService<Payment> {
    Payment createPayment(Payment payment);

    List<Payment> getByStatus(Status status);

    List<Payment> getByClientId(Long clientId);

    List<Payment> getByClientIdNative(Long clientId);

    Payment confirmPayment(ConfirmationModel confirmationModel);

    List<Payment> getDateByMonth(String date, Long accountFromId);


}
