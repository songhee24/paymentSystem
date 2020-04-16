package com.paymentsystem.demo.repos;


import com.paymentsystem.demo.enttities.Confirmation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ConfirmationRepo extends JpaRepository<Confirmation, Long> {
       @Query(value = "select pc.confirmation_code from p_confirmation pc where pc.account_from_id = :accountId", nativeQuery = true)
       Integer getConfirmationCode(@Param("accountId") Long accountId);
}
