package com.paymentsystem.demo.enttities;

import com.paymentsystem.demo.enums.Currency;
import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.persistence.*;
import java.math.BigDecimal;

@Builder
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
@Table(name = "p_accounts")
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Long id;

    @Column(name = "account_number")
    String accountNumber;

    @ManyToOne
    @JoinColumn(name = "client_id")
    Client client;

    @Column(name = "balance")
    BigDecimal balance;

    @Column(name = "currency")
    @Enumerated(EnumType.STRING)
    Currency currency;

}
