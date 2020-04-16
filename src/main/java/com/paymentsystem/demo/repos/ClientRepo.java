package com.paymentsystem.demo.repos;


import com.paymentsystem.demo.enttities.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ClientRepo extends JpaRepository<Client, Long> {
    List<Client> findAllByName(String name);
}
