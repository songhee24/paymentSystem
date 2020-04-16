package com.paymentsystem.demo.services;


import com.paymentsystem.demo.enttities.Confirmation;

public interface ConfirmationService extends BaseService<Confirmation> {
    Confirmation createConfirm(Confirmation confirmation);

    Integer getConfirmationCode(Long id);

}
