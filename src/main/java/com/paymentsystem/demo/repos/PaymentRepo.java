package com.paymentsystem.demo.repos;

import com.paymentsystem.demo.enttities.Payment;
import com.paymentsystem.demo.enums.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PaymentRepo extends JpaRepository<Payment, Long> {
    List<Payment> findAllByStatus(Status status);

    @Query("select p from Payment p where status =:status")
    List<Payment> findAllByStatusQuery(@Param("status") Status status);

    List<Payment> findAllByAccountFrom_Client_Id(Long clientId);

    @Query(value = "select p.* from p_payments p" +
            " left join p_accounts pa on p_payments.account_from_id = pa.id" +
            " where pa.client_id = :client_id", nativeQuery = true)
    List<Payment> findAllByClientId(@Param("client_id") Long clientId);

    @Query(value = "select * from p_payments pp where to_char(pp.created_date, 'YYYY-MM')  = :month_number and pp.account_from_id = :account_from_id", nativeQuery = true)
    List<Payment> getDateByMonth(@Param("month_number") String date,
                                 @Param("account_from_id") Long accountFromId);

}
