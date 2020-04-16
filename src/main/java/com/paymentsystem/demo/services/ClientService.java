package com.paymentsystem.demo.services;

import com.paymentsystem.demo.enttities.Client;


import java.util.List;

public interface ClientService extends BaseService<Client> {
    List<Client> findByName(String name);
}
