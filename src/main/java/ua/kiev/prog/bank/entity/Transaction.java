package ua.kiev.prog.bank.entity;

import lombok.*;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by Вадим on 10.09.2017.
 */
@Entity
@Table(name = "transactions")
@Data
@RequiredArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(exclude = "id")
@ToString(exclude = {"id","account"})
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Temporal(value = TemporalType.DATE)
    private Date date;

    private Double balanceBefore;

    private Double balanceAfter;

    @ManyToOne(cascade = CascadeType.ALL)
    private Account account;
}
