package ua.kiev.prog.bank.entity;

import lombok.*;
import ua.kiev.prog.bank.enums.Currency;

import javax.persistence.*;

/**
 * Created by Вадим on 10.09.2017.
 */
@Entity
@Table(name = "exchanges")
@Data
@AllArgsConstructor
@RequiredArgsConstructor
@ToString(exclude = {"id"})
@EqualsAndHashCode(exclude = "id")
public class ExchangeCurrency {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private  Currency currencyFrom;

    @Enumerated(EnumType.STRING)
    private  Currency currencyTo;

    private Double rate;
}
