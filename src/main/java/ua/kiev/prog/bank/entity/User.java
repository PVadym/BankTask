package ua.kiev.prog.bank.entity;

import lombok.*;

import javax.persistence.*;
import java.util.List;

/**
 * Created by Вадим on 10.09.2017.
 */
@Entity
@Table(name = "users")
@Data
@RequiredArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(exclude = "id")
@ToString
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String address;

    @OneToMany(mappedBy = "user",cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Account> accounts;
}
