package ua.kiev.prog.bank.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.hibernate.annotations.Fetch;
import ua.kiev.prog.bank.enums.Currency;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Вадим on 10.09.2017.
 */
@Entity
@Table(name = "accounts")
@Data
@EqualsAndHashCode(exclude = "id")
@ToString(exclude = {"user","transactions"})
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private Currency currency;

    private Double balance;

    @ManyToOne(fetch = FetchType.EAGER)
    private User user;

    @OneToMany(fetch = FetchType.EAGER,mappedBy = "account", orphanRemoval = true, cascade = CascadeType.ALL)
    private List<Transaction> transactions = new ArrayList<>();

    public void addTransaction(Transaction transaction){
        transactions.add(transaction);
        transaction.setAccount(this);
    }
}
