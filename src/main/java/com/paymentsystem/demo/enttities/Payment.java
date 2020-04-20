package com.paymentsystem.demo.enttities;


import com.paymentsystem.demo.enums.Currency;
import com.paymentsystem.demo.enums.Status;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

@Builder
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
@Table(name = "p_payments")
public class Payment {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Long id;

    @CreatedDate
    @Column(name = "created_date")
    Date createdDate = new Date();

    @Column(name = "amount")
    BigDecimal amount;

    @ManyToOne
    @JoinColumn(name = "account_from_id")
    Account accountFrom;

    @ManyToOne
    @JoinColumn(name = "account_to_id")
    Account accountTo;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    Status status;

    @Column(name = "currency")
    @Enumerated(EnumType.STRING)
    Currency currency;

    @Column(name = "confirmation_code", length = 4)
    String confirmationCode;

    @Column(name = "attempt")
    Integer attempt = 0;
}
